package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class AllOrderBean extends AssertionConcern implements Serializable {
	/** 商家订单号 **/
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	/** 平台订单号 **/
	@ColumnAlias(value = "order_id")
	private String orderId;
	/** 支付编号 **/
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/** 支付方式 **/
	@ColumnAlias(value = "pay_way")
	private Integer payWay; // 1:支付宝 2：微信
	/** 创建时间 **/
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/** 平台订单商品总额 **/
	@ColumnAlias(value = "goods_amount")
	private long mainGoodsAmount;
	/** 平台订单运费 **/
	@ColumnAlias(value = "order_freight")
	private long mainOrderFreight;
	/** 商家订单运费 **/
	@ColumnAlias(value = "orderFreight")
	private long dealerOrderFreight;
	/** 平台优惠金额 **/
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;
	/** 商家优惠金额 **/
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/** 商家订单商品总额 **/
	@ColumnAlias(value = "dealerAmount")
	private long dealerGoodsAmount;
	/** 订单状态 **/
	@ColumnAlias(value = "_status")
	private Integer status;
	/** 商家名 **/
	@ColumnAlias(value = "dealer_name")
	private String dealerName;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public long getMainGoodsAmount() {
		return mainGoodsAmount;
	}

	public long getMainOrderFreight() {
		return mainOrderFreight;
	}

	public long getDealerOrderFreight() {
		return dealerOrderFreight;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public long getDealerGoodsAmount() {
		return dealerGoodsAmount;
	}

	public Integer getStatus() {
		return status;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setMainGoodsAmount(long mainGoodsAmount) {
		this.mainGoodsAmount = mainGoodsAmount;
	}

	public void setMainOrderFreight(long mainOrderFreight) {
		this.mainOrderFreight = mainOrderFreight;
	}

	public void setDealerOrderFreight(long dealerOrderFreight) {
		this.dealerOrderFreight = dealerOrderFreight;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setDealerGoodsAmount(long dealerGoodsAmount) {
		this.dealerGoodsAmount = dealerGoodsAmount;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Override
	public String toString() {
		return "AllOrderBean [dealerOrderId=" + dealerOrderId + ", orderId=" + orderId + ", payNo=" + payNo
				+ ", payWay=" + payWay + ", createdDate=" + createdDate + ", mainGoodsAmount=" + mainGoodsAmount
				+ ", mainOrderFreight=" + mainOrderFreight + ", dealerOrderFreight=" + dealerOrderFreight
				+ ", plateformDiscount=" + plateformDiscount + ", dealerDiscount=" + dealerDiscount
				+ ", dealerGoodsAmount=" + dealerGoodsAmount + ", status=" + status + ", dealerName=" + dealerName
				+ "]";
	}

}
