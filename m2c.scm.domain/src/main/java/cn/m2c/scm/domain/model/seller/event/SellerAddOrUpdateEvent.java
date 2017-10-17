package cn.m2c.scm.domain.model.seller.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class SellerAddOrUpdateEvent implements DomainEvent {
	private String sellerId;
	private String sellerName;
	private String sellerPhone;
	
	private Date occurredOn;
    private int eventVersion;
    
    
    
    
	

	public SellerAddOrUpdateEvent(String sellerId, String sellerName, String sellerPhone) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
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
