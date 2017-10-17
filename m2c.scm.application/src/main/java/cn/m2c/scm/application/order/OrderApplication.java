package cn.m2c.scm.application.order;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;

import cn.m2c.scm.application.order.command.CancelOrderCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.domain.NegativeException;
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
	/**
	 * 提交订单
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public OrderResult submitOrder(OrderAddCommand cmd) {
		
		JSONArray gdes = cmd.getGoodses();
		List<Map<String, Object>> goodses = null;
		if (gdes == null || gdes.size() < 1) {
			// 获取购物车数据
			goodses = orderDomainService.getShopCarGoods(cmd.getUserId());
		}
		// 判断库存
		orderDomainService.judgeStock(null);
		// 锁定库存
		orderDomainService.lockStock(null);
		// 满足优惠券后，修改优惠券(锁定)
		orderDomainService.lockCoupons(null);
		// 获取商品详情
		List<Map<String, Object>> list = orderDomainService.getGoodsDtl(null);
		//若有媒体信息则需要查询媒体信息
		orderDomainService.getMediaBdByResIds(null);
		// 拆单 设置商品数量即按商家来拆分
		// 获取运费模板，计算运费
		// 计算是否满足营销策略, 若满足选择最优
		
		MainOrder order = new MainOrder();
		// 计算
		// 组织保存(重新设置计算好的价格)		
		order.add();
		orderRepository.save(order);
		
		return new OrderResult();
	}
	/***
	 * 取消订单(只能取消未支付的)
	 * @param cmd
	 */
	public void cancelOrder(CancelOrderCmd cmd) {
		
		MainOrder order = orderRepository.getOrderById(cmd.getOrderId());
		// 检查是否可取消,若不可取消抛出异常。
		order.cancel();
		// 可能是逻辑删除或是改成取消状态(全部要改)
		orderRepository.save(order);
		// 若订单中有优惠券则需要解锁
		orderDomainService.unlockCoupons(null, "");
		// 解锁库存
		orderDomainService.unlockStock(null);
	}
	
	
}
