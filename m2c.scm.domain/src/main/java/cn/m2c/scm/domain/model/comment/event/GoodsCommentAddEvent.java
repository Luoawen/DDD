package cn.m2c.scm.domain.model.comment.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 增加评论
 */
public class GoodsCommentAddEvent implements DomainEvent {
    private String userId;
    private String commentId;
    private String orderId;
    private String goodsId;
    private String skuId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsCommentAddEvent(String userId, String commentId, String orderId, String goodsId, String skuId) {
        this.userId = userId;
        this.commentId = commentId;
        this.orderId = orderId;
        this.goodsId = goodsId;
        this.skuId = skuId;
        this.occurredOn = new Date();
        this.eventVersion = 1;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
