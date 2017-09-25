package cn.m2c.goods.domain.seller;

public interface SellerRepository {

	/**
	 * 获取业务员
	 * @param sellerId
	 * @return
	 */
	Seller getSeller(String sellerId);

	/**
	 * 保存或者更新操作
	 * @param seller
	 */
	void save(Seller seller);

	/**
	 * 根据系统用户id获取业务员
	 * @param sysuserId
	 * @return
	 */
	Seller getSysSeller(String sysuserId);

}