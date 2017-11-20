package cn.m2c.scm.domain.model.order;

import java.util.List;

/**
 * 订单仓储
 * @author fanjc
 *
 */
public interface OrderRepository {
	/***
	 * 保存订单
	 * @param order 主订单
	 */
	public void save(MainOrder order);
	/***
	 * 获取订单通过订单号
	 * @param orderId 订单号
	 */
	public MainOrder getOrderById(String orderId);
	/***
	 * 获取订单通过订单号
	 * @param orderId 订单号
	 * @param userId
	 */
	public MainOrder getOrderById(String orderId, String userId);
	/***
	 * 主要用于更新订单状态
	 * @param order
	 */
	public void updateMainOrder(MainOrder order);
	/***
	 * 获取商家订单
	 * @param dealerOrderId
	 * @return
	 */
	public DealerOrder getDealerOrderByNo(String dealerOrderId);
	
	/***
	 * 获取商家订单中的某一详情
	 * @param dealerOrderId
	 * @param skuId
	 * @return
	 */
	public DealerOrderDtl getDealerOrderDtlBySku(String dealerOrderId, String sku);
	/**
	 * 更新商家订单
	 * @param dealOrder
	 */
	public void updateDealerOrder(DealerOrder dealOrder);
	/***
	 * 获取订单中的商品给计算用
	 * @param orderNo
	 * @param clss
	 * @return
	 */
	public <T> List<T> getOrderGoodsForCal(String orderNo, Class<T> clss);
	/***
	 * 获取未支付的订单
	 * @return
	 */
	public List<MainOrder> getNotPayedOrders(int h);
	
	public DealerOrder getDealerOrderById(String orderId, String userId, String dealerOrderId);
	/***
	 * 获取满足条件的可以交易完成的订单并改变其状态
	 * @return
	 */
	public void getSpecifiedOrderStatus();
}
