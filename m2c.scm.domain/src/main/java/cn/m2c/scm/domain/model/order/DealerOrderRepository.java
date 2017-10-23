package cn.m2c.scm.domain.model.order;

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
}
