package cn.m2c.scm.domain.model.goods;

import java.util.List;

/**
 * 商品
 */
public interface GoodsSkuRepository {
    GoodsSku queryGoodsSkuById(String skuId);

    int outInventory(String skuId, Integer num, Integer concurrencyVersion);

    boolean goodsCodeIsRepeat(String dealerId, List<String> codes);

    boolean goodsCodeIsRepeat(String dealerId, String skuId, String code);
}
