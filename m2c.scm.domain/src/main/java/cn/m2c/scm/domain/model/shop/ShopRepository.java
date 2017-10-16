package cn.m2c.scm.domain.model.shop;

public interface ShopRepository {
	 /**
	    * 获取经销商店铺
	    * @param modelId
	    * @return
	    */
	   public Shop getShop(String dealerId);

	   /**
	    * 保存经销商
	    * @param postageModel
	    */
	   public void save(Shop shop);

	   /**
	    * 获取经销商店铺
	    * @param shopId
	    * @return
	    */
	public Shop getShopByShopID(String shopId);
	   
}
