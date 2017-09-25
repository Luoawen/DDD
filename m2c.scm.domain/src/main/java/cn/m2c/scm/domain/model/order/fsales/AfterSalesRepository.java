package cn.m2c.scm.domain.model.order.fsales;

import cn.m2c.scm.domain.NegativeException;

public interface AfterSalesRepository {
	/**
	 * 创建/更新订单
	 * @param order
	 * @return
	 * @throws NegativeException
	 */
	public void save(AfterSales afterSales) throws NegativeException;
	
	/**
	 * 通过订单编号获取订单信息
	 * @param orderId
	 * @return
	 * @throws NegativeException
	 */
	public AfterSales findT(String fsalesId) throws NegativeException;
}
