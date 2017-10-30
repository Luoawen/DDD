package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 售后单详情
 * 
 * @author lqwen
 *
 */
public class AfterSellOrderDetailBean {

	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 售后期望(退货、换货、仅退款)
	 */
	@ColumnAlias(value = "order_type")
	private Integer orderType;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	/**
	 * 售后总额
	 */
	@ColumnAlias(value = "back_money")
	private long backMoney;
	/**
	 * 售后运费
	 */
	@ColumnAlias(value = "freight")
	private long backFreight;
	/**
	 * 申请原因
	 */
	@ColumnAlias(value = "reason")
	private String reason;
	/**
	 * 申请时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 关联订单号
	 */
	@ColumnAlias(value = "order_id")
	private String orderId;
	/**
	 * 商家信息<商家名>
	 */
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	/**
	 * 商家信息<商家分类>
	 */
	@ColumnAlias(value = "dealer_classify")
	private String dealerClassify;
	/**
	 * 订单总额
	 */
	@ColumnAlias(value = "goods_amount")
	private long orderTotalMoney;
	/** 订单运费 **/
	@ColumnAlias(value = "order_freight")
	private long orderFreight;
	/** 平台优惠金额 **/
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;

	/** 商家优惠金额 **/
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/**
	 * 业务员信息<业务员姓名>
	 */
	@ColumnAlias(value = "seller_name")
	private String sellerName;
	/**
	 * 业务员信息<业务员电话>
	 */
	@ColumnAlias(value = "seller_phone")
	private String sellerPhone;
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

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public long getBackMoney() {
		return backMoney;
	}

	public long getBackFreight() {
		return backFreight;
	}

	public String getReason() {
		return reason;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public String getDealerClassify() {
		return dealerClassify;
	}

	public long getOrderTotalMoney() {
		return (this.orderTotalMoney + this.getOrderFreight() - this.getPlateformDiscount() - this.getDealerDiscount());
	}

	public long getOrderFreight() {
		return orderFreight;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public GoodsInfoBean getGoodsInfo() {
		return goodsInfo;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setBackMoney(long backMoney) {
		this.backMoney = backMoney;
	}

	public void setBackFreight(long backFreight) {
		this.backFreight = backFreight;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public void setDealerClassify(String dealerClassify) {
		this.dealerClassify = dealerClassify;
	}

	public void setOrderTotalMoney(long orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
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

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public void setGoodsInfo(GoodsInfoBean goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

}
