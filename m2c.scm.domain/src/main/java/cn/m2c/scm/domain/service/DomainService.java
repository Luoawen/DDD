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
     * 生成商城SKU编码
     *
     * @return
     */
    List<String> generateGoodsSKUs(Integer num);

    /**
     * 生成编码记录
     *
     * @param no   编号
     * @param type 编号类型，1：订单号，2：商城sku
     */
    void saveGenerateNo(String no, Integer type);
}
