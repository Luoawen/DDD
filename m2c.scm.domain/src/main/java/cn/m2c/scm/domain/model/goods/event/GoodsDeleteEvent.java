package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.List;

/**
 * 商品删除
 */
public class GoodsDeleteEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private String goodsPostageId;
    private String goodsUnitId; //计量单位id
    private List<String> standardIds; //规格id
    private Date occurredOn;
    private int eventVersion;

    public GoodsDeleteEvent(String goodsId, String goodsPostageId, String goodsUnitId, List<String> standardIds) {
        this.goodsId = goodsId;
        this.goodsPostageId = goodsPostageId;
        this.goodsUnitId = goodsUnitId;
        this.standardIds = standardIds;
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
