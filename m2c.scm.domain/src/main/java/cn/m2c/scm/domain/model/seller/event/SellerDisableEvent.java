package cn.m2c.scm.domain.model.seller.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class SellerDisableEvent implements DomainEvent {
	
	private String sellerId;
	private Date currentDate;
	
	public SellerDisableEvent() {
		super();
	}

	public SellerDisableEvent(String sellerId) {
		super();
		
		this.setCurrentDate(new Date());
		this.setSellerId(sellerId);
	}
	

	public String getSellerId() {
		return sellerId;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return this.getCurrentDate();
	}

}
