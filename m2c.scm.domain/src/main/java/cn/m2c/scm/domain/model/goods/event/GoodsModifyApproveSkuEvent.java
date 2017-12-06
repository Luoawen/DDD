package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 修改商品审核：修改商品的拍获价，供货价，规格
 */
public class GoodsModifyApproveSkuEvent implements DomainEvent {
    /**
     * 商品id
     */
    private String goodsId;
    private List<Map> addSkuList;
    private Date occurredOn;
    private int eventVersion;

    public GoodsModifyApproveSkuEvent(String goodsId, List<Map> addSkuList) {
        this.goodsId = goodsId;
        this.addSkuList = addSkuList;
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
