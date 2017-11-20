package cn.m2c.scm.domain.model.order;

import java.util.List;


public interface DealerOrderDtlRepository {

	/***
	 * 保存订单
	 * @param dealerOrder 详情单项
	 */
	public void save(DealerOrderDtl dealerOrder);	
	/***
	 * 获取符合条件的订单详情项数据
	 * @param hour
	 * @param status 状态
	 * @return
	 */
	public List<DealerOrderDtl> getOrderDtlStatusQeury(int hour, int status);
}
