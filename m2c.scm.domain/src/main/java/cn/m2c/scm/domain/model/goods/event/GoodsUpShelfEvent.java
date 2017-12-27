package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import cn.m2c.scm.domain.model.goods.GoodsRecognized;

import java.util.Date;
import java.util.List;

/**
 * 商品上架
 */
public class GoodsUpShelfEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private String goodsPostageId;
    
    private List<GoodsRecognized> goodsRecognizeds;
    private Integer status;
    
    private Date occurredOn;
    private int eventVersion;

    public GoodsUpShelfEvent(String goodsId, String goodsPostageId, List<GoodsRecognized> goodsRecognizeds, Integer status) {
        this.goodsPostageId = goodsPostageId;
        this.goodsId = goodsId;
        
        this.goodsRecognizeds = goodsRecognizeds;
        this.status = status;
        
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
