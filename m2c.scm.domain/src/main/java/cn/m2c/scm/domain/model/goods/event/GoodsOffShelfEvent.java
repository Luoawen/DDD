package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.DomainEvent;
import cn.m2c.scm.domain.model.goods.GoodsRecognized;

import java.util.Date;
import java.util.List;

/**
 * 商品下架
 */
public class GoodsOffShelfEvent implements DomainEvent {

    /**
     * 商品id
     */
    private String goodsId;
    private String goodsPostageId;
    
    private String goodsRecognizeds;
    private Integer status;
    
    private Date occurredOn;
    private int eventVersion;

    public GoodsOffShelfEvent(String goodsId,String goodsPostageId, List<GoodsRecognized> goodsRecognizeds, Integer status) {
        this.goodsPostageId=goodsPostageId;
        this.goodsId = goodsId;
        
        this.goodsRecognizeds = JsonUtils.toStr(goodsRecognizeds);
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
