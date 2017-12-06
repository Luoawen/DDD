package cn.m2c.scm.application.special.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 特惠价增加
 */
public class GoodsSpecialAddCommand extends AssertionConcern implements Serializable {
    private String specialId;
    private String goodsId;
    private String goodsName;
    private Integer skuFlag;
    private String dealerId;
    private String dealerName;
    private String startTime;
    private String endTime;
    private String congratulations;
    private String activityDescription;
    private String goodsSkuSpecials;

    public GoodsSpecialAddCommand(String specialId, String goodsId, String goodsName, Integer skuFlag, String dealerId,
                                  String dealerName, String startTime, String endTime, String congratulations,
                                  String activityDescription, String goodsSkuSpecials) {
        this.specialId = specialId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.skuFlag = skuFlag;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.congratulations = congratulations;
        this.activityDescription = activityDescription;
        this.goodsSkuSpecials = goodsSkuSpecials;
    }

    public String getSpecialId() {
        return specialId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getCongratulations() {
        return congratulations;
    }

    public String getActivityDescription() {
        return activityDescription;
    }

    public String getGoodsSkuSpecials() {
        return goodsSkuSpecials;
    }
}
