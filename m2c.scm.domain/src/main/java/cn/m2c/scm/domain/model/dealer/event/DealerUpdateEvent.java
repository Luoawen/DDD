package cn.m2c.scm.domain.model.dealer.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class DealerUpdateEvent implements DomainEvent{
	
	private String oldUserId;//修改经销商管理员的时候原来用户id
	private String newUserId;//command 管理员原来用户id
	private String userName;
	private String userPhone;
	
	private String dealerId;
	private String dealerName;
	
	private Date current;

	
	public String getOldUserId() {
		return oldUserId;
	}

	public void setOldUserId(String oldUserId) {
		this.oldUserId = oldUserId;
	}

	public String getNewUserId() {
		return newUserId;
	}

	public void setNewUserId(String newUserId) {
		this.newUserId = newUserId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

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
