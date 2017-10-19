package cn.m2c.scm.application.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.order.command.CancelOrderCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.domain.service.order.OrderService;

/***
 * 订单应用服务类
 * @author fanjc
 *
 */
@Service
public class OrderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderService orderDomainService;
	
	@Autowired
	OrderQueryApplication queryApp;
	
	@Autowired
	DealerQuery dealerQuery; // getDealers
	/**
	 * 提交订单
	 * @param cmd
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening = true)
	public OrderResult submitOrder(OrderAddCommand cmd) throws NegativeException {
		
		JSONArray gdes = cmd.getGoodses();
		List<Map<String, Object>> goodses = null;
		Map<String, Float> skus = new HashMap<String, Float>();
		if (gdes == null || gdes.size() < 1) {
			// 获取购物车数据
			goodses = orderDomainService.getShopCarGoods(cmd.getUserId());
			if (goodses == null)
				throw new NegativeException(MCode.V_1, "购物车中的商品为空！");
			
			for (Map<String, Object> it : goodses) {
				skus.put(it.get("skuId").toString(), (Float)it.get("num"));
			}
		}
		else {
			int sz = gdes.size();
			for (int i=0; i<sz; i++) {
				JSONObject o = gdes.getJSONObject(i);
				skus.put(o.getString("skuId"), o.getFloatValue("purNum"));
			}
		}
		// 判断库存
		orderDomainService.judgeStock(skus);
		// 锁定库存
		orderDomainService.lockStock(null);
		// 满足优惠券后，修改优惠券(锁定)
		//JSONArray coups = cmd.getCoupons();
		//orderDomainService.lockCoupons(null);
		// 获取商品详情
		List<GoodsDto> list = queryApp.getGoodsDtl(skus.keySet());
		//若有媒体信息则需要查询媒体信息
		orderDomainService.getMediaBdByResIds(null);
		// 获取运费模板，计算运费
		calFreight(skus, list, cmd.getAddr().getCityCode());
		// 拆单 设置商品数量即按商家来拆分
		Set<String> idsSet = new HashSet<String>();
		Map<String, List<GoodsDto>> dealerOrderMap = splitOrder(list, skus, idsSet);
		// 计算是否满足营销策略, 若满足选择最优
		Map<String, Integer> dealerCount = getDealerWay(idsSet);
		List<DealerOrder> dealerOrders = trueSplit(dealerOrderMap, cmd, dealerCount);
		
		int goodsAmounts = 0;
		int freight = 0;
		int plateDiscount = 0;
		int dealerDiscount = 0;
		// 计算主订单费用
		for (DealerOrder d : dealerOrders) {
			freight += d.getOrderFreight();
			goodsAmounts += d.getGoodsAmount();
			plateDiscount += (d.getPlateformDiscount() == null?0:d.getPlateformDiscount());
			dealerDiscount += d.getDealerDiscount();
		}
		
		MainOrder order = new MainOrder(cmd.getOrderId(), cmd.getAddr(), goodsAmounts, freight
				, plateDiscount, dealerDiscount, cmd.getUserId(), cmd.getNoted(), dealerOrders, null, null);
		// 组织保存(重新设置计算好的价格)		
		order.add();
		orderRepository.save(order);
		
		return new OrderResult(cmd.getOrderId(), goodsAmounts, freight, plateDiscount, dealerDiscount);
	}
	/***
	 * 订单拆分
	 * @param ls
	 * @param sl
	 * @return
	 */
	private Map<String, List<GoodsDto>> splitOrder(List<GoodsDto> ls, Map<String, Float> sl
			, Set<String> ids) {
		Map<String, List<GoodsDto>> rs = new HashMap<String, List<GoodsDto>>();
		List<GoodsDto> dtos = null;
		for (GoodsDto bean : ls) {
			dtos = rs.get(bean.getDealerId());
			ids.add(bean.getDealerId());
			if (dtos == null) {
				dtos = new ArrayList<GoodsDto>();
				rs.put(bean.getDealerId(), dtos);
			}
			Float num = sl.get(bean.getSkuId());
			if (num != null) {
				bean.setPurNum(num);
				dtos.add(bean);
			}
		}		
		return rs;
	}
	/***
	 * 真实拆分订单到商家
	 * @param dealerOrderMap
	 * @param orderId
	 * @param dc
	 * @return
	 */
	private List<DealerOrder> trueSplit(Map<String, List<GoodsDto>> map, OrderAddCommand cmd
			, Map<String, Integer> dc) {
		List<DealerOrder> rs = new ArrayList<DealerOrder>();
		
		Iterator<String> it = map.keySet().iterator();
		int c = 0;
		while(it.hasNext()) {
			String dealerId = it.next();
			List<GoodsDto> dtos = map.get(dealerId);
			
			List<DealerOrderDtl> dtls = new ArrayList<DealerOrderDtl>();
			int freight = 0;
			int goodsAmount = 0;
			int plateDiscount = 0;
			int dealerDiscount = 0;
			int termOfPayment = dc.get(dealerId);
			String dealerOrderId = cmd.getOrderId() + c;
			for (GoodsDto bean : dtos) {
				float num = bean.getPurNum();
				freight += bean.getFreight();
				goodsAmount += (int)(num * bean.getDiscountPrice());
				
				dtls.add(new DealerOrderDtl(cmd.getOrderId(), dealerOrderId, cmd.getAddr(), 
						cmd.getInvoice(), null, null, null, null, 0, "mediaId",
						0, 0, bean.toGoodsInfo(), 0, 0, cmd.getNoted()));
			}
			rs.add(new DealerOrder(cmd.getOrderId(), dealerOrderId, dealerId, goodsAmount, freight,
					plateDiscount, dealerDiscount, cmd.getNoted(), termOfPayment
					,cmd.getAddr(), cmd.getInvoice(), dtls));
			c ++;
		}
		return rs;
	}
	/***
	 * 取消订单(只能取消未支付的)
	 * @param cmd
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening = true)
	public void cancelOrder(CancelOrderCmd cmd) throws NegativeException {
		
		MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
		// 检查是否可取消,若不可取消抛出异常。
		if (order.cancel()) {
			// 可能是逻辑删除或是改成取消状态(全部要改)
			orderRepository.save(order);
			// 若订单中有优惠券则需要解锁
			orderDomainService.unlockCoupons(queryApp.getCouponsByOrderId(cmd.getOrderId()), "");
			// 解锁库存
			orderDomainService.unlockStock(queryApp.getSkusByOrderId(cmd.getOrderId()));
		}
		else {
			throw new NegativeException(MCode.V_1, "订单处于不可取消状态！");
		}
	}
	
	/***
	 * 计算运费
	 * @param skus
	 * @param ls
	 * @param cityCode
	 */
	private void calFreight(Map<String, Float> skus, List<GoodsDto> ls, String cityCode) {
		for (GoodsDto bean : ls) {
			bean.setFreight(1000);
		}
	}
	
	/***
	 * 获取商家结算方式
	 * @param ids
	 * @return 商家对应的支付方式
	 * @throws NegativeException 
	 */
	private Map<String, Integer> getDealerWay(Set<String> ids) throws NegativeException {
		Iterator<String> it = ids.iterator();
		StringBuilder dealerIds = new StringBuilder();
		int c = 0;
		while(it.hasNext()) {
			if (c > 0)
				dealerIds.append(",").append(it.next());
			else
				dealerIds.append(it.next());
			c ++;
		}
		Map<String, Integer> rs = null;
		List<DealerBean> beans = dealerQuery.getDealers(dealerIds.toString());
		if (beans == null || beans.size() < 1)
			return rs;
		rs = new HashMap<String, Integer>();
		for (DealerBean b : beans) {
			rs.put(b.getDealerId(), b.getCountMode());
		}
		return rs;
	}
}
