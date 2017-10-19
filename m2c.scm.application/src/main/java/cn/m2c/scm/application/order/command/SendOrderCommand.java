package cn.m2c.scm.application.order.command;

import cn.m2c.ddd.common.AssertionConcern;

public class SendOrderCommand extends AssertionConcern{

	/**商家订单id**/
	private String dealerOrderId;
	/**快递单号**/
	private String expressNo;
	/**快递公司*/
	private String expressName;
	/**送货人姓名*/
	private String expressPerson;
	/**送货人电话*/
	private String expressPhone;
	/**配送方式 0:物流，1自有物流*/
	private Integer expressWay;
	/**配送说明*/
	private String expressNote;
	
	
	public SendOrderCommand() {
		super();
	}
	
	
	public SendOrderCommand(String dealerOrderId, String expressNo,
			String expressName, String expressPerson, String expressPhone,
			Integer expressWay, String expressNote) {
		super();
		this.dealerOrderId = dealerOrderId;
		this.expressNo = expressNo;
		this.expressName = expressName;
		this.expressPerson = expressPerson;
		this.expressPhone = expressPhone;
		this.expressWay = expressWay;
		this.expressNote = expressNote;
	}


	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressPerson() {
		return expressPerson;
	}
	public void setExpressPerson(String expressPerson) {
		this.expressPerson = expressPerson;
	}
	public String getExpressPhone() {
		return expressPhone;
	}
	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}
	public Integer getExpressWay() {
		return expressWay;
	}
	public void setExpressWay(Integer expressWay) {
		this.expressWay = expressWay;
	}
	public String getExpressNote() {
		return expressNote;
	}
	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}
	
	
	
}
