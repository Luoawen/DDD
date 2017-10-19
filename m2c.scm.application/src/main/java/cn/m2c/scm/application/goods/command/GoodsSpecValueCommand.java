package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商品规格值
 */
public class GoodsSpecValueCommand extends AssertionConcern implements Serializable {
    /**
     * 商品规格值id
     */
    private String specId;

    /**
     * 商家ID
     */
    private String dealerId;

    /**
     * 商品规格值
     */
    private String specValue;

    public GoodsSpecValueCommand(String specId, String dealerId, String specValue) {
        this.specId = specId;
        this.dealerId = dealerId;
        this.specValue = specValue;
    }

    public String getSpecId() {
        return specId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getSpecValue() {
        return specValue;
    }
}
