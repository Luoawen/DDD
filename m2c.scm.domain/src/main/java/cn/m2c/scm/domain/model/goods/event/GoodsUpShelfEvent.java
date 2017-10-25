package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 商品上架
 */
public class GoodsUpShelfEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private String goodsPostageId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsUpShelfEvent(String goodsId, String goodsPostageId) {
        this.goodsPostageId = goodsPostageId;
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
