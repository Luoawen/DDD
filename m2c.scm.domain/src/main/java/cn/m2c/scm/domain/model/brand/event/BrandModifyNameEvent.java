package cn.m2c.scm.domain.model.brand.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 修改品牌名称事件
 */
public class BrandModifyNameEvent implements DomainEvent {

    /**
     * 品牌id
     */
    private String brandId;
    private String brandName;
    private Date occurredOn;
    private int eventVersion;

    public BrandModifyNameEvent(String brandId, String brandName) {
        this.brandId = brandId;
        this.brandName = brandName;
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
