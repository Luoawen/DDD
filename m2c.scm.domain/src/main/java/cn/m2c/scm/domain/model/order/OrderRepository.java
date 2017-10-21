package cn.m2c.scm.domain.model.order;
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
	
	public void updateDealerOrder(DealerOrder dealOrder);
}
