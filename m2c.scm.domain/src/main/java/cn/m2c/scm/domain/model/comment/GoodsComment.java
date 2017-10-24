package cn.m2c.scm.domain.model.comment;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.comment.event.GoodsCommentAddEvent;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * 商品评论
 */
public class GoodsComment extends ConcurrencySafeEntity {
    /**
     * 评论编号
     */
    private String commentId;

    /**
     * 商家订单编号
     */
    private String orderId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 规格id
     */
    private String skuId;

    /**
     * 规格名称
     */
    private String skuName;

    /**
     * 商品数量
     */
    private Integer goodsNum;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 供应商编号
     */
    private String dealerId;

    /**
     * 供应商名称
     */
    private String dealerName;

    /**
     * 买家编号
     */
    private String buyerId;

    /**
     * 买家名称
     */
    private String buyerName;

    /**
     * 买家手机号
     */
    private String buyerPhoneNumber;

    /**
     * 买家头像
     */
    private String buyerIcon;

    /**
     * 评论
     */
    private String commentContent;

    /**
     * 评论图片
     */
    private String commentImages;

    /**
     * 评论是否有图片，1:否 2有
     */
    private Integer imageStatus;

    /**
     * 回复,1:否 2：有
     */
    private Integer replyStatus;

    /**
     * 评论级别 1好 2中 3差
     */
    private Integer commentLevel;

    /**
     * 星级 1 2 3 4 5 评价星级，1-5星，好评为5星，中评为2-4星，差评为1星
     */
    private Integer starLevel;

    /**
     * 状态 1正常  2 删除
     */
    private Integer commentStatus;

    /**
     * 评论时间
     */
    private BigInteger commentTime;

    /**
     * 回评
     */
    private GoodsReplyComment goodsReplyComment;

    public GoodsComment() {
        super();
    }

    /**
     * 买家增加评论
     */
    public GoodsComment(String commentId, String orderId, String goodsId, String skuId, String skuName, Integer goodsNum, String goodsName,
                        String dealerId, String dealerName, String buyerId, String buyerName,
                        String buyerPhoneNumber, String buyerIcon, String commentContent, String commentImages, Integer starLevel) {
        this.commentId = commentId;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.skuId = skuId;
        this.skuName = skuName;
        this.goodsNum = goodsNum;
        this.goodsName = goodsName;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhoneNumber = buyerPhoneNumber;
        this.buyerIcon = buyerIcon;
        this.commentContent = commentContent;
        this.commentImages = commentImages;

        if (StringUtils.isNotEmpty(commentImages)) {
            List<String> images = JsonUtils.toList(commentImages, String.class);
            if (null != images && images.size() > 0) {
                this.imageStatus = 2;
            }
        }
        // 星级 1 2 3 4 5 评价星级，1-5星，好评为5星，中评为2-4星，差评为1星
        this.starLevel = starLevel;
        if (starLevel == 5) {
            this.commentLevel = 1;  //评论级别 1好 2中 3差
        } else if (starLevel >= 2 && starLevel <= 4) {
            this.commentLevel = 2;
        } else {
            this.commentLevel = 3;
        }
        this.replyStatus = 1;
        this.commentStatus = 1;

        Date date = new Date();
        this.commentTime = new BigInteger(String.valueOf(date.getTime()));

        DomainEventPublisher
                .instance()
                .publish(new GoodsCommentAddEvent(this.orderId, this.skuId));
    }
}