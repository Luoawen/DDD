package cn.m2c.scm.domain.model.seller;


public interface SellerRepository {
	/**
     * 获取业务员
     * @param modelId
     * @return
     */
    public Seller getSeller(String sellerId);

    /**
     * 保存业务员
     * @param postageModel
     */
    public void save(Seller seller);
}
