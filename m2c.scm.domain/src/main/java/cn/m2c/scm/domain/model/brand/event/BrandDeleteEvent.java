package cn.m2c.scm.domain.model.brand.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 删除品牌事件
 */
public class BrandDeleteEvent implements DomainEvent {

    /**
     * 品牌id
     */
    private String brandId;
    private Date occurredOn;
    private int eventVersion;

    public BrandDeleteEvent(String brandId) {
        this.brandId = brandId;
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
