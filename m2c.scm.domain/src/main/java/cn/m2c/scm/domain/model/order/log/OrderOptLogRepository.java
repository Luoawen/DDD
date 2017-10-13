package cn.m2c.scm.domain.model.order.log;
/**
 * 订单操作日志仓储
 * @author fanjc
 *
 */
public interface OrderOptLogRepository {
	/***
	 * 保存订单操作日志
	 * @param log 
	 */
	public void save(OrderOptLog log);	
}
