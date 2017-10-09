package cn.m2c.scm.domain.model.dealer;


public interface DealerRepository {

	 /**
     * 获取经销商
     * @param modelId
     * @return
     */
    public Dealer getDealer(String dealerId);

    /**
     * 保存经销商
     * @param postageModel
     */
    public void save(Dealer dealer);

}
