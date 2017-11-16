package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

public class DealerOrderAfterSellDetailBean {

	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	
	@ColumnAlias(value = "doStatus")
	private Integer doStatus;
	/**
	 * 售后期望(退货、换货、仅退款)
	 */
	@ColumnAlias(value = "order_type")
	private Integer orderType;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellDealerOrderId;
	/**
	 * 售后总额
	 */
	@ColumnAlias(value = "back_money")
	private long backMoney;
	/**
	 * 申请原因
	 */
	@ColumnAlias(value = "reason")
	private String reason;
	/**
	 * 拒绝原因
	 */
	@ColumnAlias(value = "reject_reason")
	private String rejectReason;
	/**
	 * 申请时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 关联订单号
	 */
	//@ColumnAlias(value = "order_id")
	@ColumnAlias(value = "dealer_order_id")
	private String orderId;
	/**
	 * 订单总额
	 */
	@ColumnAlias(value = "goods_amount")
	private long orderTotalMoney;
	/**
	 * 订单运费
	 */
	@ColumnAlias(value = "order_freight")
	private long orderFreight;
	/**
	 * 售后退回的运费
	 */
	@ColumnAlias(value = "return_freight")
	private long backFreight;

	/** 平台优惠金额 **/
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;

	/** 商家优惠金额 **/
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	
	private String dealerId;
	
	
	public Integer getDoStatus() {
		return doStatus;
	}

	public void setDoStatus(Integer doStatus) {
		this.doStatus = doStatus;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	/**
	 * 商品信息
	 */
	private GoodsInfoBean goodsInfo;

	public Integer getStatus() {
		return status;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public String getAfterSellDealerOrderId() {
		return afterSellDealerOrderId;
	}

	public long getBackMoney() {
		return backMoney;
	}

	public String getReason() {
		return reason;
	}

	public Date getCreatedDate() {
		return createdDate;
	}


	public long getOrderTotalMoney() {
		return (orderTotalMoney + this.getOrderFreight() - this.getPlateformDiscount() - this.getDealerDiscount());
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public long getOrderFreight() {
		return orderFreight;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public void setOrderFreight(long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setAfterSellDealerOrderId(String afterSellDealerOrderId) {
		this.afterSellDealerOrderId = afterSellDealerOrderId;
	}

	public void setBackMoney(long backMoney) {
		this.backMoney = backMoney;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderTotalMoney(long orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}

	public GoodsInfoBean getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfoBean goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

	public long getBackFreight() {
		return backFreight;
	}

	public void setBackFreight(long backFreight) {
		this.backFreight = backFreight;
	}

}
