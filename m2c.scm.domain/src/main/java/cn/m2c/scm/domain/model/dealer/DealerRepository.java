package cn.m2c.scm.domain.model.dealer;

import cn.m2c.scm.domain.NegativeException;

import java.util.List;

public interface DealerRepository {

    /**
     * 获取经销商
     *
     * @param modelId
     * @return
     */
    public Dealer getDealer(String dealerId);

    /**
     * 保存经销商
     *
     * @param postageModel
     */
    public void save(Dealer dealer);

    public Dealer getDealerBySellerId(String sellerId);

    /**
     * 用户Id获取经销商
     *
     * @return
     */
    public List<Dealer> getDealerByUserId(String userId);

    void saveDealerDayReport(String dealerId, String type, Integer num, Long money, Integer day) throws NegativeException;
}
