package cn.m2c.scm.domain.model.order;
/**
 * 订单ID仓储
 * @author fanjc
 *
 */
public interface OrderIDRepository {
	/***
	 * 保存订单号
	 * @param strTime 当前时间yyyyMMddHHmmss
	 * @param suffix  6位随机数
	 */
	public void save(String strTime, String suffix);
	/***
	 * 删除给定时间及之前的订单号
	 * @param strTime yyyyMMddHHmmss
	 */
	public void delete(String strTime);
}
