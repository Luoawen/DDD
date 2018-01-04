package cn.m2c.scm.application.order.command;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ExpressInfoBean {

	@ColumnAlias(value = "res_data")
	private String resData;

	@ColumnAlias(value = "ship_goods_time")
	private Date shipGoodsTime;

	@ColumnAlias(value = "ship_type")
	private Integer shipType;
	
	@ColumnAlias(value = "com")
	private String expressCode;

	public String getResData() {
		return resData;
	}

	public Date getShipGoodsTime() {
		return shipGoodsTime;
	}

	public Integer getShipType() {
		return shipType;
	}

	public void setResData(String resData) {
		this.resData = resData;
	}

	public void setShipGoodsTime(Date shipGoodsTime) {
		this.shipGoodsTime = shipGoodsTime;
	}

	public void setShipType(Integer shipType) {
		this.shipType = shipType;
	}

	public String getExpressCode() {
		return expressCode;
	}

	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}

	
	
	

}
