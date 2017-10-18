package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 商品规格值
 */
public class GoodsSpecValue extends ConcurrencySafeEntity {
    private String specId;
    private String dealerId;
    private String specValue;

    public GoodsSpecValue() {
        super();
    }

    public GoodsSpecValue(String specId, String dealerId, String specValue) {
        this.specId = specId;
        this.dealerId = dealerId;
        this.specValue = specValue;
    }
}
