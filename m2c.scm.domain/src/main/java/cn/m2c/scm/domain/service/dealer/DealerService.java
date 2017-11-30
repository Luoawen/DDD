package cn.m2c.scm.domain.service.dealer;

import cn.m2c.scm.domain.NegativeException;

public interface DealerService {
	
	/**
	 * 新增商家的时候自动创建商家店铺
	 * @param dealerId
	 * @param dealerName
	 * @throws NegativeException 
	 */
	public void addShop(String dealerId,String dealerName,String userPhone) throws NegativeException;
}
