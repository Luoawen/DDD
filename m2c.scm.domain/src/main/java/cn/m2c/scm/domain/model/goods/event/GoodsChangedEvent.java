package cn.m2c.scm.domain.model.goods.event;

import java.util.Date;

import com.google.gson.Gson;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 商品名修改
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
    private Date occurredOn;
    private int eventVersion;

    public GoodsChangedEvent(String goodsId, String goodsName, String dealerId, String dealerName) {
    	this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
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
    
    public static void main(String[] args) {
    	Gson gson = new Gson();
    	GoodsChangedEvent godnEvent = new GoodsChangedEvent("SP7C5DB9C7AD334810AA308225B1757567", "华为手机", "JXS0256D0FE28B1467F87AAB6AEC5D73839", "摸摸旗舰ddd店");
    	System.out.println(gson.toJson(godnEvent));
    }
}
