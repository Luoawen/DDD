package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class AfterSellOrderBean {

	/**
	 * 退货单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	/**
	 * 期望售后
	 */
	@ColumnAlias(value = "order_type")
	private String orderType;
	/**
	 * 售后总额
	 */
	@ColumnAlias(value = "back_money")
	private Integer backMoney;
	/**
	 * 订单状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 商家信息
	 */
	@ColumnAlias(value = "dealer_name")
	private String dealerInfo;
	/**
	 * 申请时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createDate;

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public String getOrderType() {
		return orderType;
	}

	public Integer getBackMoney() {
		return backMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDealerInfo() {
		return dealerInfo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setBackMoney(Integer backMoney) {
		this.backMoney = backMoney;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setDealerInfo(String dealerInfo) {
		this.dealerInfo = dealerInfo;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public String toString() {
		return "AfterSellOrderBean [afterSellOrderId=" + afterSellOrderId + ", orderType=" + orderType + ", backMoney="
				+ backMoney + ", status=" + status + ", dealerInfo=" + dealerInfo + ", createDate=" + createDate + "]";
	}

}
