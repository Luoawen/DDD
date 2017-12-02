package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 修改商品审核：修改商品的拍获价，供货价，规格
 */
public class GoodsModifyApproveSkuEvent implements DomainEvent {
    /**
     * 商品id
     */
    private String goodsId;
    private Date occurredOn;
    private int eventVersion;

    public GoodsModifyApproveSkuEvent(String goodsId) {
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
