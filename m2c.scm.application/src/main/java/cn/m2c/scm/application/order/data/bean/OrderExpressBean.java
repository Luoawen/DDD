package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class OrderExpressBean {
	
	@ColumnAlias(value= "exp_company_code")
	private String expressCode;
	@ColumnAlias(value= "exp_company_Name")
	private String expressName;
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	
	
}
