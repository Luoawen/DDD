package cn.m2c.scm.application.order.data.representation;

import cn.m2c.scm.application.order.data.bean.OrderExpressDetailBean;

public class OrderExpressDetailRepresentation {
	private String expressWay;
	
	private String expressName;
	
	private String expressNo;
	
	private String expressCode;
	
	private String expressPerson;
	
	private String expressPhone;
	
	private String expressNote;
	
	private String expressTime;
	
	public OrderExpressDetailRepresentation(OrderExpressDetailBean orderExpressDetailBean) {
		this.expressCode = orderExpressDetailBean.getExpressCode();
		this.expressName = orderExpressDetailBean.getExpressName();
		this.expressNo   = orderExpressDetailBean.getExpressNo();
		this.expressNote = orderExpressDetailBean.getExpressNote();
		this.expressPerson = orderExpressDetailBean.getExpressPerson();
		this.expressPhone= orderExpressDetailBean.getExpressPhone();
		this.expressTime = orderExpressDetailBean.getExpressTime();
		this.expressWay = orderExpressDetailBean.getExpressWay();
	}
	public String getExpressWay() {
		return expressWay;
	}
	public void setExpressWay(String expressWay) {
		this.expressWay = expressWay;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
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
	public String getExpressNote() {
		return expressNote;
	}
	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}
	public String getExpressTime() {
		return expressTime;
	}
	public void setExpressTime(String expressTime) {
		this.expressTime = expressTime;
	}
	
}
