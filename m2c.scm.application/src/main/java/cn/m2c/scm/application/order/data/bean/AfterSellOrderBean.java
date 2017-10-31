package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class AfterSellOrderBean {

	/**
	 * 退货单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;

	/** 订货号 **/
	@ColumnAlias(value = "order_id")
	private String orderId;

	/**
	 * 期望售后
	 */
	@ColumnAlias(value = "order_type")
	private Integer orderType;
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
	/** 规格ID */
	@ColumnAlias(value = "sku_id")
	private String skuId;
	/**
	 * 申请时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createDate;

	private GoodsInfoBean goodsInfo;

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public Integer getOrderType() {
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

	public GoodsInfoBean getGoodsInfo() {
		return goodsInfo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setOrderType(Integer orderType) {
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

	public void setGoodsInfo(GoodsInfoBean goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	@Override
	public String toString() {
		return "AfterSellOrderBean [afterSellOrderId=" + afterSellOrderId + ", orderId=" + orderId + ", orderType="
				+ orderType + ", backMoney=" + backMoney + ", status=" + status + ", dealerInfo=" + dealerInfo
				+ ", skuId=" + skuId + ", createDate=" + createDate + ", goodsInfo=" + goodsInfo + "]";
	}

}
