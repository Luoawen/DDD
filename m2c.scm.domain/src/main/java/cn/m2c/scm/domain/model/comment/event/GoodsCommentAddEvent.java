package cn.m2c.scm.domain.model.comment.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 增加评论
 */
public class GoodsCommentAddEvent implements DomainEvent {
    private String orderId;
    private String skuId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsCommentAddEvent(String orderId, String skuId) {
        this.orderId = orderId;
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
