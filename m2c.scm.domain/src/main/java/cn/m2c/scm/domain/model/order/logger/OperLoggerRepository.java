package cn.m2c.scm.domain.model.order.logger;

import cn.m2c.scm.domain.NegativeException;

public interface OperLoggerRepository {
	/**
	 * 创建/更新
	 * @param order
	 * @return
	 * @throws NegativeException
	 */
	public void save(OperLogger operLogger) throws NegativeException;
	
	/**
	 * 通过ID获取
	 * @param orderId
	 * @return
	 * @throws NegativeException
	 */
	public OperLogger findT(String operLoggerId) throws NegativeException;
	
	
}
