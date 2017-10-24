package cn.m2c.scm.domain.model.dealer.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

public class DealerAddEvent implements DomainEvent{

	private String userId;
	private String userName;
	private String userPhone;
	private String dealerId;
	private String dealerName;
	private Date currentDate;
	
	public DealerAddEvent() {
		super();
	}

	public DealerAddEvent(String userId, String userName, String userPhone,
			String dealerId, String dealerName) {
		super();
		this.setCurrentDate(new Date());
		this.setUserId(userId);
		this.setUserPhone(userPhone);
		this.setUserName(userName);
		this.setDealerId(dealerId);
		this.setDealerName(dealerName);
	}
	

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public Date getCurrentDate() {
		return currentDate;
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
