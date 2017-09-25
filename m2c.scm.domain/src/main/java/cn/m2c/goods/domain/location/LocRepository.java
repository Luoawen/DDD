package cn.m2c.goods.domain.location;


public interface LocRepository {
	/**
	 * 根据id露出聚合
	 * @param sellerId
	 */
	public Location getLocDetail(String locationId);

	/**
	 * 保存业务员操作
	 * @param dseller
	 */
	public void save(Location location);
}
