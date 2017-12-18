package cn.m2c.scm.domain.service.seller;
/**
 * 业务员服务
 * @author yezp
 *
 */
public interface SellerService {

	/**
	 * 200 :true 手机号可以添加
	 * 400 ：false 手机号不可以添加
	 * @param sellerPhone
	 * @return
	 */
	public boolean isSellerPhoneExist(String sellerPhone);
}
