package cn.m2c.scm.application.comment.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 增加商品评论
 */
public class AddGoodsCommentCommand extends AssertionConcern implements Serializable {
    private String commentId;
    private String orderId;
    private String skuId;
    private String skuName;
    private Integer goodsNum;
    private String buyerId;
    private String buyerName;
    private String buyerPhoneNumber;
    private String buyerIcon;
    private String commentContent;
    private String commentImages;
    private String goodsId;
    private String goodsName;
    private String dealerId;
    private String dealerName;
    /**
     * 星级 1 2 3 4 5 评价星级，1-5星，好评为5星，中评为2-4星，差评为1星
     */
    private Integer starLevel;

    public AddGoodsCommentCommand(String commentId, String orderId, String skuId, String skuName, Integer goodsNum, String buyerId, String buyerName,
                                  String buyerPhoneNumber, String buyerIcon, String commentContent, String commentImages,
                                  String goodsId, String goodsName, String dealerId, String dealerName, Integer starLevel) {
        this.commentId = commentId;
        this.orderId = orderId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.goodsNum = goodsNum;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.buyerIcon = buyerIcon;
        this.commentContent = commentContent;
        this.commentImages = commentImages;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.starLevel = starLevel;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getSkuId() {
        return skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public String getBuyerIcon() {
        return buyerIcon;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public String getCommentImages() {
        return commentImages;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getCommentId() {
        return commentId;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }
}
