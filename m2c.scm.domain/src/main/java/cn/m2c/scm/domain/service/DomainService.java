package cn.m2c.scm.domain.service;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface DomainService {
    /**
     * 获取商品标签（满减、优惠券等）
     *
     * @param goodsId 商品ID
     * @return
     */
    List<Map> getGoodsTags(String goodsId);

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
