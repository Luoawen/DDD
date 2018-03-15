package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 活动商品冻结库存
 */
public class GoodsActInventoryFreezeCommand extends AssertionConcern implements Serializable {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 规格id
     */
    private String skuId;
    /**
     * 活动规则id
     */
    private String ruleId;
    /**
     * 活动价格
     */
    private Long price;
    /**
     * 冻结库存数量
     */
    private Integer skuNum;

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Integer getSkuNum() {
        return skuNum;
    }

    public void setSkuNum(Integer skuNum) {
        this.skuNum = skuNum;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
