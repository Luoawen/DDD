package cn.m2c.scm.domain.model.seller.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class SellerDisableEvent implements DomainEvent {
	
	private String sellerId;
	private Date currentDate;
	private Integer eventVersion;
	

	public SellerDisableEvent(String sellerId) {
		this.sellerId = sellerId;
		this.currentDate = new Date();
	}
	
	public String getSellerId() {
		return sellerId;
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public Integer getEventVersion() {
		return eventVersion;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public void setEventVersion(Integer eventVersion) {
		this.eventVersion = eventVersion;
	}

	@Override
	public int eventVersion() {
		return this.eventVersion;
	}

	@Override
	public Date occurredOn() {
		return this.currentDate;
	}

}
