package cn.m2c.scm.domain.model.special;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

/**
 * 商品sku对应的特惠价格
 */
public class GoodsSkuSpecial extends IdentifiedValueObject {
    private GoodsSpecial goodsSpecial;
    private String skuId;
    private String skuName;
    /**
     * 供货价
     */
    private Long supplyPrice;
    /**
     * 特惠价
     */
    private Long specialPrice;

    public GoodsSkuSpecial() {
        super();
    }

    public GoodsSkuSpecial(GoodsSpecial goodsSpecial, String skuId, String skuName, Long supplyPrice, Long specialPrice) {
        this.goodsSpecial = goodsSpecial;
        this.skuId = skuId;
        this.skuName = skuName;
        this.supplyPrice = supplyPrice;
        this.specialPrice = specialPrice;
    }

    public void modifyGoodsSkuSpecialPrice(Long supplyPrice, Long specialPrice) {
        this.supplyPrice = supplyPrice;
        this.specialPrice = specialPrice;
    }

    public String skuId() {
        return skuId;
    }

    public Long supplyPrice() {
        return supplyPrice;
    }

    public Long specialPrice() {
        return specialPrice;
    }
}
