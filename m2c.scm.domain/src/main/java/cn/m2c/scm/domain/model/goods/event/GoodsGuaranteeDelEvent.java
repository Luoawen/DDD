package cn.m2c.scm.domain.model.goods.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 删除商品保障(需同时删除已选的商品保障) 
 */
public class GoodsGuaranteeDelEvent implements DomainEvent {

	private String dealerId;   //商家ID
	private String guaranteeId;//保障ID
	private Date occurredOn;
    private int eventVersion;
	
	public GoodsGuaranteeDelEvent(String guaranteeId, String dealerId) {
		super();
		this.dealerId = dealerId;
		this.guaranteeId = guaranteeId;
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
