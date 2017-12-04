package cn.m2c.scm.application.special.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品特惠价
 */
public class GoodsSkuSpecialBean {
    @ColumnAlias(value = "special_id")
    private Integer specialId;
    @ColumnAlias(value = "sku_id")
    private String skuId;
    @ColumnAlias(value = "sku_name")
    private String skuName;
    /**
     * 供货价
     */
    @ColumnAlias(value = "supply_price")
    private Long supplyPrice;
    /**
     * 特惠价
     */
    @ColumnAlias(value = "special_price")
    private Long specialPrice;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getSupplyPrice() {
        return supplyPrice;
    }

    public void setSupplyPrice(Long supplyPrice) {
        this.supplyPrice = supplyPrice;
    }

    public Long getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Long specialPrice) {
        this.specialPrice = specialPrice;
    }

    public Integer getSpecialId() {
        return specialId;
    }

    public void setSpecialId(Integer specialId) {
        this.specialId = specialId;
    }
}
