package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class AfterInGoods {
	
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	
	@ColumnAlias(value = "sku_id")
	private String sku_id;
	
	@ColumnAlias(value = "_status")
	private Integer status;
	
	@ColumnAlias(value = "order_type")
	private Integer orderType;
	
	@ColumnAlias(value = "sell_num")
	private Integer sellNum;

	public Integer getOrderType() {
		return orderType;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public String getSku_id() {
		return sku_id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setSku_id(String sku_id) {
		this.sku_id = sku_id;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	

}
