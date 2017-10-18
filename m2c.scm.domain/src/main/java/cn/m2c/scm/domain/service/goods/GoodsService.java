package cn.m2c.scm.domain.service.goods;

import java.util.List;
import java.util.Map;

/**
 * 商品
 */
public interface GoodsService {
    /**
     * 获取商品标签（满减、优惠券等）
     *
     * @param dealerId   商家ID
     * @param goodsId    商品ID
     * @param classifyId 商品分类ID
     * @return
     */
    List<Map> getGoodsTags(String dealerId, String goodsId, String classifyId);
}
