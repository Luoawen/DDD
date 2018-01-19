package cn.m2c.scm.domain.model.order;

public interface OrderMediaRepository {

	/**
	 * 保存广告位订单
	 * @param orderMedia
	 */
	void save(OrderMedia orderMedia);

}
