package cn.m2c.scm.domain.model.seller;


public interface SellerRepository {
	/**
     * 获取经销商
     * @param modelId
     * @return
     */
    public Seller getSeller(String sellerId);

    /**
     * 保存经销商
     * @param postageModel
     */
    public void save(Seller seller);
}
