package cn.m2c.scm.domain.model.order.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class OrderDealCompleteEvt implements DomainEvent {

	private Date occurredOn;
	
    private int eventVersion = 1;
    
    private String orderId;
    
	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return eventVersion;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return occurredOn;
	}
	
	public OrderDealCompleteEvt() {
		occurredOn = new Date();
	}

	public OrderDealCompleteEvt(String orderId) {
		this();
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}	
	
}
