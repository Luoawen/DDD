package cn.m2c.scm.application.comment.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品评论
 */
public class GoodsCommentBean {

    @ColumnAlias(value = "id")
    private Integer id;
    /**
     * 评论编号
     */
    @ColumnAlias(value = "comment_id")
    private String commentId;

    /**
     * 商家订单编号
     */
    @ColumnAlias(value = "order_id")
    private String orderId;

    /**
     * 商品id
     */
    @ColumnAlias(value = "goods_id")
    private String goodsId;

    /**
     * 规格id
     */
    @ColumnAlias(value = "sku_id")
    private String skuId;

    /**
     * 规格名称
     */
    @ColumnAlias(value = "sku_name")
    private String skuName;

    /**
     * 商品数量
     */
    @ColumnAlias(value = "goods_num")
    private Integer goodsNum;

    /**
     * 商品名称
     */
    @ColumnAlias(value = "goods_name")
    private String goodsName;

    /**
     * 供应商编号
     */
    @ColumnAlias(value = "dealer_id")
    private String dealerId;

    /**
     * 供应商名称
     */
    @ColumnAlias(value = "dealer_name")
    private String dealerName;

    /**
     * 买家编号
     */
    @ColumnAlias(value = "buyer_id")
    private String buyerId;

    /**
     * 买家名称
     */
    @ColumnAlias(value = "buyer_name")
    private String buyerName;

    /**
     * 买家手机号
     */
    @ColumnAlias(value = "buyer_phone_number")
    private String buyerPhoneNumber;

    /**
     * 买家头像
     */
    @ColumnAlias(value = "buyer_icon")
    private String buyerIcon;

    /**
     * 评论
     */
    @ColumnAlias(value = "comment_content")
    private String commentContent;

    /**
     * 评论图片
     */
    @ColumnAlias(value = "comment_images")
    private String commentImages;

    /**
     * 评论是否有图片，1:否 2有
     */
    @ColumnAlias(value = "image_status")
    private Integer imageStatus;

    /**
     * 评论级别 1好 2中 3差
     */
    @ColumnAlias(value = "comment_level")
    private Integer commentLevel;

    /**
     * 星级 1 2 3 4 5 评价星级，1-5星，好评为5星，中评为2-4星，差评为1星
     */
    @ColumnAlias(value = "star_level")
    private Integer starLevel;

    /**
     * 回复状态 1未回复  2 已回复
     */
    @ColumnAlias(value = "reply_status")
    private Integer replyStatus;

    /**
     * 状态 1正常  2 删除
     */
    @ColumnAlias(value = "comment_status")
    private Integer commentStatus;

    /**
     * 评论时间
     */
    @ColumnAlias(value = "comment_time")
    private String commentTime;

    private GoodsReplyCommentBean goodsReplyCommentBean;

    public GoodsReplyCommentBean getGoodsReplyCommentBean() {
        return goodsReplyCommentBean;
    }

    public void setGoodsReplyCommentBean(GoodsReplyCommentBean goodsReplyCommentBean) {
        this.goodsReplyCommentBean = goodsReplyCommentBean;
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

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

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(String buyerId) {
        this.buyerId = buyerId;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getBuyerIcon() {
        return buyerIcon;
    }

    public void setBuyerIcon(String buyerIcon) {
        this.buyerIcon = buyerIcon;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(String commentImages) {
        this.commentImages = commentImages;
    }

    public Integer getImageStatus() {
        return imageStatus;
    }

    public void setImageStatus(Integer imageStatus) {
        this.imageStatus = imageStatus;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public Integer getReplyStatus() {
        return replyStatus;
    }

    public void setReplyStatus(Integer replyStatus) {
        this.replyStatus = replyStatus;
    }

    public Integer getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(Integer commentStatus) {
        this.commentStatus = commentStatus;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}