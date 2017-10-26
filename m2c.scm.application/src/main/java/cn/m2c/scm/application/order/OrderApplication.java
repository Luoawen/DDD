package cn.m2c.scm.application.order;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.order.command.CancelOrderCmd;
import cn.m2c.scm.application.order.command.ConfirmSkuCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.application.order.command.OrderPayedCmd;
import cn.m2c.scm.application.order.command.PayOrderCmd;
import cn.m2c.scm.application.order.data.bean.MediaResBean;
import cn.m2c.scm.application.order.data.representation.OrderMoney;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.application.order.query.dto.GoodsDto;
import cn.m2c.scm.application.postage.data.representation.PostageModelRuleRepresentation;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.domain.service.order.OrderService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	private GoodsApplication goodsApp;
	
	@Autowired
	private GoodsQueryApplication gQueryApp;
	
	@Autowired
	DealerQuery dealerQuery; // getDealers
	@Autowired
	PostageModelQueryApplication postApp;
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
		// skuId, num
		Map<String, Integer> skus = new HashMap<String, Integer>();
		List<String> mediaResIds = new ArrayList<String>();
		Map<String, String> skuMedia = new HashMap<String, String>();
		if (gdes == null || gdes.size() < 1) {
			// 获取购物车数据
			goodses = orderDomainService.getShopCarGoods(cmd.getUserId());
			if (goodses == null)
				throw new NegativeException(MCode.V_1, "购物车中的商品为空！");
			
			for (Map<String, Object> it : goodses) {
				String sku = it.get("skuId").toString();
				skus.put(sku, (Integer)it.get("num"));
				String mResId = (String)it.get("mediaResId");
				if (!StringUtils.isEmpty(mResId) && !mediaResIds.contains(mResId)) {
					mediaResIds.add(mResId);
				}
				skuMedia.put(sku, mResId);
			}
		}
		else {
			int sz = gdes.size();
			for (int i=0; i<sz; i++) {
				JSONObject o = gdes.getJSONObject(i);
				skus.put(o.getString("skuId"), o.getIntValue("purNum"));
				String mResId = o.getString("mediaResId");
				if (!StringUtils.isEmpty(mResId) && !mediaResIds.contains(mResId))
					mediaResIds.add(mResId);
			}
		}
		// 判断库存
		orderDomainService.judgeStock(skus);
		// 锁定库存
		orderDomainService.lockStock(null);
		try {
			goodsApp.outInventory(skus);
		}
		catch (NegativeException e) {
			throw new NegativeException(MCode.V_100, e.getMessage() + "不存在或库存不够。");
		}
		
		long orderTime = System.currentTimeMillis();
		// 满足优惠券后，修改优惠券(锁定)
		//JSONArray coups = cmd.getCoupons();
		//orderDomainService.lockCoupons(null);
		// 获取商品详情
		List<GoodsDto> list = gQueryApp.getGoodsDtl(skus.keySet());
		//若有媒体信息则需要查询媒体信息
		Map<String, Object> resMap = orderDomainService.getMediaBdByResIds(mediaResIds, orderTime);
		// 获取运费模板，计算运费
		calFreight(skus, list, cmd.getAddr().getCityCode());
		// 拆单 设置商品数量即按商家来拆分
		Set<String> idsSet = new HashSet<String>();
		Map<String, List<GoodsDto>> dealerOrderMap = splitOrder(list, idsSet);
		// 计算是否满足营销策略, 若满足选择最优
		Map<String, Integer> dealerCount = getDealerWay(idsSet);
		List<DealerOrder> dealerOrders = trueSplit(dealerOrderMap, cmd, dealerCount,
				resMap, skuMedia);
		
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
	 * @param ids
	 * @return
	 */
	private Map<String, List<GoodsDto>> splitOrder(List<GoodsDto> ls, Set<String> ids) {
		Map<String, List<GoodsDto>> rs = new HashMap<String, List<GoodsDto>>();
		List<GoodsDto> dtos = null;
		for (GoodsDto bean : ls) {
			dtos = rs.get(bean.getDealerId());
			ids.add(bean.getDealerId());
			if (dtos == null) {
				dtos = new ArrayList<GoodsDto>();
				rs.put(bean.getDealerId(), dtos);
			}
			dtos.add(bean);
		}		
		return rs;
	}
	/***
	 * 真实拆分订单到商家
	 * @param map
	 * @param cmd
	 * @param dc 商家结算方式
	 * @param medias 媒体信息
	 * @return
	 */
	private List<DealerOrder> trueSplit(Map<String, List<GoodsDto>> map, OrderAddCommand cmd
			, Map<String, Integer> dc, Map<String, Object> medias, Map<String, String> skuMedia) {
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
				String resId = skuMedia.get(bean.getSkuId());
				MediaResBean mb = null;
				if (!StringUtils.isEmpty(resId))
					mb = (MediaResBean)medias.get(resId);
				
				if (mb == null) {
					dtls.add(new DealerOrderDtl(cmd.getOrderId(), dealerOrderId, cmd.getAddr(), 
							cmd.getInvoice(), null, null, null, null, "0", "",
							0, 0, bean.toGoodsInfo(), 0, 0, cmd.getNoted(), "0"));
				}
				else {
					dtls.add(new DealerOrderDtl(cmd.getOrderId(), dealerOrderId, cmd.getAddr(), 
							cmd.getInvoice(), null, mb.getMresId(), mb.getSalesmanId(), mb.getBdStaffRatio(), 
							mb.getMediaRatio(), mb.getMediaId(),
							0, 0, bean.toGoodsInfo(), 0, 0, cmd.getNoted(), mb.getSalesmanRatio()));
				}
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
			// 可能是逻辑删除或是改成取消状态(子订单也要改)
			orderRepository.updateMainOrder(order);
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
	private void calFreight(Map<String, Integer> skus, List<GoodsDto> ls, String cityCode) {
		LOGGER.info("==fanjc==计算运费.");
		Iterator<String> it = skus.keySet().iterator();
		List<String> skuIds = new ArrayList<String>();
		while(it.hasNext()) {
			skuIds.add(it.next());
		}
		
		Map<String, PostageModelRuleRepresentation> postMap = postApp.getGoodsPostageRule(skuIds, cityCode);
		
		for (GoodsDto bean : ls) {
			String skuId = bean.getSkuId();
			bean.setPurNum(skus.get(skuId));
			calFrt(bean, postMap.get(skuId));
			// bean.setFreight(1000);
		}
	}
	/***
	 * 计算单个物品运费
	 * @param b
	 * @param pb
	 * @return
	 */
	private void calFrt(GoodsDto b, PostageModelRuleRepresentation pb) {
		if (pb == null) {
			b.setFreight(0);
			return ;
		}
		
		if (pb.getChargeType() == 1) {//0:按重量,1:按件数
			long ft = pb.getFirstPostage();
			long ct = pb.getContinuedPostage();
			int fpt = pb.getFirstPiece();
			int cpt = pb.getContinuedPiece();
			int ss = b.getPurNum() - fpt; // 续件
			if (ss > 0) {
				b.setFreight(ft + (ss/cpt + (ss%cpt >0 ? 1:0)) * ct);
			}
			else
				b.setFreight(ft);
		}
		else {
			long ft = pb.getFirstPostage();
			long ct = pb.getContinuedPostage();
			float fpt = pb.getFirstWeight();
			float cpt = pb.getContinuedWeight();
			float ss = b.getPurNum()*b.getWeight() - fpt; // 续件
			if (ss > 0) {
				int t = (int)(ss/cpt); //倍数
				b.setFreight(ft + (t + (ss > (t * cpt) ? 1:0)) * ct);
			}
			else
				b.setFreight(ft);
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
	/**
	 * 订单支付
	 * @param cmd
	 * @return
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public Object payOrder(PayOrderCmd cmd) {
		MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
		// 获取订单所用营销策略
		List<String> mks = order.getMkIds();
		// 获取营销策略详情看是否有变化
		orderDomainService.getMarketingsByIds(mks);
		// 若有变化则需要重新计算金额并更新
		
		// 请求发起支付
		order.getActual();
		// 调用restful接口;
		return null;
	}
	
	/***
	 * 确认收货(只能取消未支付的)
	 * @param cmd
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void confirmSku(ConfirmSkuCmd cmd) throws NegativeException {
		
		DealerOrderDtl dtl = orderRepository.getDealerOrderDtlBySku(cmd.getDealerOrderId(), cmd.getSkuId());
		// 检查是否可确认收货
		if (dtl.confirmRev(cmd.getUserId())) {
			// 可能是逻辑删除或是改成取消状态(子订单也要改)
			DealerOrder order = orderRepository.getDealerOrderByNo(cmd.getDealerOrderId());
			if (order.checkAllRev(cmd.getSkuId(), dtl)) { // 同一个运单号一起确认收货
				order.confirmRev();
				// 检查主订单下的所有商家订单是不是已经全部确认收货了
			}			
			orderRepository.updateDealerOrder(order);
		}
		else {
			throw new NegativeException(MCode.V_1, "确认收货出错！");
		}
	}
	/***
	 * 获取订单及支付金额
	 * @param orderNo
	 * @return
	 */
	@Transactional(rollbackFor= {Exception.class, RuntimeException.class, NegativeException.class})
	public OrderMoney getOrderMoney(String orderNo, String userId) throws NegativeException {
		
		if (StringUtils.isEmpty(orderNo)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空！");
		}
		
		OrderMoney result = new OrderMoney();
		MainOrder order = orderRepository.getOrderById(orderNo);
		// 判断是否符合营销规则，不符合需要重新计算，保存
		result.setAmountOfMoney(order.getActual());
		result.setOrderNo(orderNo);
		return result;
	}
	/***
	 * 订单支付成功
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void orderPayed(OrderPayedCmd cmd) {
		MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
		if (order.paySuccess(cmd.getPayNo(), cmd.getPayWay(), cmd.getPayTime(), cmd.getUserId()))
			orderRepository.updateMainOrder(order);
		return ;
	}
}
