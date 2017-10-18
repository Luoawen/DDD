package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 商品删除
 */
public class GoodsDeleteEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsDeleteEvent(String goodsId) {
        this.goodsId = goodsId;
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
