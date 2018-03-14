package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShipExpressBean {
	
	@ColumnAlias(value = "order_id")
	private String orderId;
	
	@ColumnAlias(value = "rev_phone")
	private String expressPhone;
	
	@ColumnAlias(value = "rev_person")
	private String expressPerson;

	public String getOrderId() {
		return orderId;
	}

	public String getExpressPhone() {
		return expressPhone;
	}

	public String getExpressPerson() {
		return expressPerson;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}

	public void setExpressPerson(String expressPerson) {
		this.expressPerson = expressPerson;
	}
	

}
