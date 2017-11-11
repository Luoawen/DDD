package cn.m2c.scm.domain.model.comment.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 删除评论
 */
public class GoodsCommentDeleteEvent implements DomainEvent {
    private String orderId;
    private String skuId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsCommentDeleteEvent(String orderId, String skuId) {
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
