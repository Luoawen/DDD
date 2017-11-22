package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 订单配送方式详情
 */
public class OrderExpressDetailBean {
	/**
	 * 配送方式
	 */
	@ColumnAlias(value= "express_way")
	private String expressWay;
	/**
	 * 物流公司名
	 */
	@ColumnAlias(value= "express_name")
	private String expressName;
	/**
	 * 物流单号
	 */
	@ColumnAlias(value= "express_no")
	private String expressNo;
	
	/**
	 * 快递公司编码
	 */
	@ColumnAlias(value= "express_code")
	private String expressCode;
	
	/**
	 * 送货人姓名
	 */
	@ColumnAlias(value= "express_person")
	private String expressPerson;
	
	/**
	 * 送货人电话
	 */
	@ColumnAlias(value= "express_phone")
	private String expressPhone;
	
	/**
	 * 配送说明
	 */
	@ColumnAlias(value= "express_note")
	private String expressNote;
	
	/**
	 * 发货时间
	 */
	@ColumnAlias(value= "express_time")
	private String expressTime;

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
