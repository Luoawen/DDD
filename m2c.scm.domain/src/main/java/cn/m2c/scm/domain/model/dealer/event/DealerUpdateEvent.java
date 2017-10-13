package cn.m2c.scm.domain.model.dealer.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class DealerUpdateEvent implements DomainEvent{
	
//	private String oldUserId;
//	private String newUserId;
//	private 

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return null;
	}

}
