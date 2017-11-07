package cn.m2c.scm.domain.model.goods;


import cn.m2c.ddd.common.domain.model.Entity;

/**
 * 商品规格值
 */
public class GoodsSpecValue extends Entity {
    private String specId;
    private String dealerId;
    private String standardId;
    private String specValue;

    public GoodsSpecValue() {
        super();
    }

    public GoodsSpecValue(String specId, String dealerId, String standardId, String specValue) {
        this.specId = specId;
        this.dealerId = dealerId;
        this.standardId = standardId;
        this.specValue = specValue;
    }
}
