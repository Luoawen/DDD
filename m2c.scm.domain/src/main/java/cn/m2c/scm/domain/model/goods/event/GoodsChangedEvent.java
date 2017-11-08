package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.List;

/**
 * 商品修改
 */
public class GoodsChangedEvent implements DomainEvent {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 供应商id
     */
    private String dealerId;
    /**
     * 供应商名
     */
    private String dealerName;

    private String oldGoodsUnitId;

    private String newGoodsUnitId;

    private List<String> oldStandardIds;

    private List<String> newStandardIds;

    private Date occurredOn;
    private int eventVersion;

    public GoodsChangedEvent(String goodsId, String goodsName, String dealerId, String dealerName,
                             String oldGoodsUnitId, String newGoodsUnitId, List<String> oldStandardIds, List<String> newStandardIds) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.oldGoodsUnitId = oldGoodsUnitId;
        this.newGoodsUnitId = newGoodsUnitId;
        this.oldStandardIds = oldStandardIds;
        this.newStandardIds = newStandardIds;
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
