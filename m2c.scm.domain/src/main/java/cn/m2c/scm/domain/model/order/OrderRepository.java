package cn.m2c.scm.domain.model.order;


import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * @ClassName: OrderRepository
 * @Description: 
 * @author moyj
 * @date 2017年4月18日 上午9:40:17
 *
 */
public interface OrderRepository {
	/**
	 * 创建/更新订单
	 * @param order
	 * @return
	 * @throws NegativeException
	 */
	public void save(Order order) throws NegativeException;
	
	/**
	 * 通过订单编号获取订单信息
	 * @param orderId
	 * @return
	 * @throws NegativeException
	 */
	public Order findT(String orderId) throws NegativeException;
	
	
}
