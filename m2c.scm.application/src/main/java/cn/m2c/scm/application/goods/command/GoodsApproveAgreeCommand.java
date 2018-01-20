package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商品审核同意
 */
public class GoodsApproveAgreeCommand extends AssertionConcern implements Serializable {
    /**
     * 商品id
     */
    private String goodsId;
    private String newServiceRate;
    private String oldServiceRate;
    private String oldClassifyName;
    private String newClassifyName;
    private Integer settlementMode;

    public GoodsApproveAgreeCommand(String goodsId, String newServiceRate, String oldServiceRate, String oldClassifyName,
                                    String newClassifyName, Integer settlementMode) {
        this.goodsId = goodsId;
        this.newServiceRate = newServiceRate;
        this.oldServiceRate = oldServiceRate;
        this.oldClassifyName = oldClassifyName;
        this.newClassifyName = newClassifyName;
        this.settlementMode = settlementMode;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getNewServiceRate() {
        return newServiceRate;
    }

    public String getOldServiceRate() {
        return oldServiceRate;
    }

    public String getOldClassifyName() {
        return oldClassifyName;
    }

    public String getNewClassifyName() {
        return newClassifyName;
    }

    public Integer getSettlementMode() {
        return settlementMode;
    }
}
