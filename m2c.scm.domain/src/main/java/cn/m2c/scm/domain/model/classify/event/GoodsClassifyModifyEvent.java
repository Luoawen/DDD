package cn.m2c.scm.domain.model.classify.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 修改分类事件
 */
public class GoodsClassifyModifyEvent implements DomainEvent {
    private Date occurredOn;
    private int eventVersion;
    private String goodsClassifyId;
    private String goodsClassifyName;

    public GoodsClassifyModifyEvent(String goodsClassifyId, String goodsClassifyName) {
        this.goodsClassifyId = goodsClassifyId;
        this.goodsClassifyName = goodsClassifyName;
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
