package cn.m2c.scm.domain.model.order;

import java.util.List;

public interface DealerOrderRepository {

	/***
	 * 保存订单
	 * @param dealerOrder 商家订单
	 */
	public void save(DealerOrder dealerOrder);
	/***
	 * 获取商家订单通过商家订单号
	 * @param dealerOrderId 商家订单号
	 */
	public DealerOrder getDealerOrderById(String dealerOrderId);
	
	/**
	 * 获取状态为'待收货'的订单
	 */
	public List<DealerOrder> getDealerOrderStatusQeury();
	
	/**
	 * 获取状态为'完成'的订单
	 */
	public List<DealerOrder> getDealerOrderFinishied(); 
	
	/**
	 * 获取订单状态为'待付款的订单'
	 */
	public List<DealerOrder> getDealerOrderWaitPay();
	
	/**
	 * 更新运费及收货信息
	 */
	public void updateFreight(DealerOrder dealerOrder);
	
	/**
	 * 更新评论状态
	 */
	public void updateComment(String orderId, String skuId, int flag);
	/***
	 * 获取子单中的商品已经完成的子订单，并改变其状态
	 */
	public void getSpecifiedDtlStatus(int hour);
}
