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
    public List<Map> getGoodsTags(String goodsId);
}
