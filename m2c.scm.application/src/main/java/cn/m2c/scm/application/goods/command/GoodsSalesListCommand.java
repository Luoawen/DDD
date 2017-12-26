package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商品销量榜
 */
public class GoodsSalesListCommand extends AssertionConcern implements Serializable {
    private Integer month;
    private String dealerId;
    private String goodsId;
    private String goodsName;
    private Integer goodsNum;

    public GoodsSalesListCommand(Integer month, String dealerId, String goodsId, String goodsName, Integer goodsNum) {
        this.month = month;
        this.dealerId = dealerId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsNum = goodsNum;
    }

    public Integer getMonth() {
        return month;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    @Override
    public String toString() {
        return "GoodsSalesListCommand{" +
                "month=" + month +
                ", dealerId='" + dealerId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsNum='" + goodsNum + '\'' +
                '}';
    }
}
