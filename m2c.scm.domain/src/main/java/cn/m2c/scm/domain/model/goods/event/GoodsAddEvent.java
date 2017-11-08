package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.List;

/**
 * 商品增加
 */
public class GoodsAddEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private String goodsUnitId; //计量单位id
    private List<String> standardIds; //规格id
    private Date occurredOn;
    private int eventVersion;

    public GoodsAddEvent(String goodsId, String goodsUnitId, List<String> standardIds) {
        this.goodsId = goodsId;
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
