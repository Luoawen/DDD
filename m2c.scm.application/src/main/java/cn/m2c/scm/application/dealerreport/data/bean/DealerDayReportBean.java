package cn.m2c.scm.application.dealerreport.data.bean;


import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerDayReportBean {
    @ColumnAlias(value = "day")
    private Integer day;
    @ColumnAlias(value = "dealer_id")
    private String dealerId;
    @ColumnAlias(value = "order_num")
    private Integer orderNum;
    @ColumnAlias(value = "order_refund_num")
    private Integer orderRefundNum;
    @ColumnAlias(value = "goods_add_num")
    private Integer goodsAddNum;
    @ColumnAlias(value = "sell_money")
    private Long sellMoney;
    @ColumnAlias(value = "refund_money")
    private Long refundMoney;
    @ColumnAlias(value = "goods_comment_num")
    private Integer goodsCommentNum;

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOrderRefundNum() {
        return orderRefundNum;
    }

    public void setOrderRefundNum(Integer orderRefundNum) {
        this.orderRefundNum = orderRefundNum;
    }

    public Integer getGoodsAddNum() {
        return goodsAddNum;
    }

    public void setGoodsAddNum(Integer goodsAddNum) {
        this.goodsAddNum = goodsAddNum;
    }

    public Long getSellMoney() {
        return sellMoney;
    }

    public void setSellMoney(Long sellMoney) {
        this.sellMoney = sellMoney;
    }

    public Long getRefundMoney() {
        return refundMoney;
    }

    public void setRefundMoney(Long refundMoney) {
        this.refundMoney = refundMoney;
    }

    public Integer getGoodsCommentNum() {
        return goodsCommentNum;
    }

    public void setGoodsCommentNum(Integer goodsCommentNum) {
        this.goodsCommentNum = goodsCommentNum;
    }
}
