package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.List;

/**
 * 商品扣库存
 */
public class GoodsOutInventoryEvent implements DomainEvent {
    /**
     * 商品id
     */
    private List<Integer> goodsIds;
    private Date occurredOn;
    private int eventVersion;

    public GoodsOutInventoryEvent(List<Integer> goodsIds) {
        this.goodsIds = goodsIds;
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
