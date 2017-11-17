package cn.m2c.scm.domain.service.dealer;

public interface DealerService {
	
	/**
	 * 新增商家的时候自动创建商家店铺
	 * @param dealerId
	 * @param dealerName
	 */
	public void addShop(String dealerId,String dealerName);
}
