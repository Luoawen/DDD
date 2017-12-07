package cn.m2c.scm.domain.service;

/**
 *
 */
public interface DomainService {
    /**
     * 生成单个商城SKU编码
     *
     * @return
     */
    String generateGoodsSku();

    /**
     * 生成编码记录
     *
     * @param no   编号
     * @param type 编号类型，1：订单号，2：商城sku
     */
    void saveGenerateNo(String no, Integer type);
    /***
     * 生成订单号
     * @return
     */
    String generateOrderNo();
}
