package cn.m2c.scm.domain.model.goods.dealer;

import java.util.List;

public interface DealerRepository {

	/**
	 * 根据id露出聚合
	 * @param sellerId
	 */
	public Dealer getDealerDetail(String dealerId);

	/**
	 * 保存业务员操作
	 * @param dseller
	 */
	public void save(Dealer dealer);

	/**
	 * 
	 * @param mobile
	 * @return
	 */
	public Dealer getDealerByMobile(String mobile);

	/**
	 * 获取经销商
	 * @param userId
	 * @return
	 */
	public Dealer getDealerByUserId(String userId);


}
