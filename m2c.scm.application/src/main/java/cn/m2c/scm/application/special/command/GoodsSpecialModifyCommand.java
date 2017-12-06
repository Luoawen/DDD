package cn.m2c.scm.application.special.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 特惠价增加
 */
public class GoodsSpecialModifyCommand extends AssertionConcern implements Serializable {
    private String specialId;
    private String startTime;
    private String endTime;
    private String congratulations;
    private String activityDescription;
    private String goodsSkuSpecials;

    public GoodsSpecialModifyCommand(String specialId, String startTime, String endTime, String congratulations,
                                     String activityDescription, String goodsSkuSpecials) {
        this.specialId = specialId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.congratulations = congratulations;
        this.activityDescription = activityDescription;
        this.goodsSkuSpecials = goodsSkuSpecials;
    }

    public String getSpecialId() {
        return specialId;
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
