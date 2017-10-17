package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品规格值
 */
public class GoodsSpecValueBean {
    @ColumnAlias(value = "spec_id")
    private String specId;
    @ColumnAlias(value = "dealer_id")
    private String dealerId;
    @ColumnAlias(value = "spec_value")
    private String specValue;

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getSpecValue() {
        return specValue;
    }

    public void setSpecValue(String specValue) {
        this.specValue = specValue;
    }
}
