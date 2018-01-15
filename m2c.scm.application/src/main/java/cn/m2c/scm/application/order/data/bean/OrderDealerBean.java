package cn.m2c.scm.application.order.data.bean;

import cn.m2c.scm.application.utils.Utils;

public class OrderDealerBean {

	/**
	 * 平台订单号
	 */
	//@ColumnAlias(value = "order_id")
	private String orderId;
	/**
	 * 商家Id
	 */
	//@ColumnAlias(value = "dealer_id")
	private String dealerId;
	/**
	 * 商家订单号
	 */
	//@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	/**
	 * 商家或店铺名称
	 */
	//@ColumnAlias(value = "dealer_name")
	private String dealerName;
	/**
	 * 订单状态：0待付款;1待发货;2待收货;3完成;4交易完成;5交易关闭;-1已取消
	 */
	//@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 订单商品金额
	 */
	//@ColumnAlias(value = "goods_amount")
	private String goodAmount;
	/**
	 * 订单运费
	 */
	private String oderFreight;
	/**
	 * 平台优惠
	 */
	private String plateFormDiscount;
	/**
	 * 商家优惠
	 */
	private String dealerDiscount;
	/**结算方式*/
	private Integer termOfPayment;
	
	private long couponDiscount;
	
	public Integer getTermOfPayment() {
		return termOfPayment;
	}

	public void setTermOfPayment(Integer termOfPayment) {
		this.termOfPayment = termOfPayment;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerId() {
		return dealerId;
	}
	
	public long getCouponDiscount() {
		return couponDiscount/10000;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	public String getStrCouponDiscount() {
		return Utils.moneyFormatCN(couponDiscount);
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public Integer getStatus() {
		return status;
	}

	public String getGoodAmount() {
		return goodAmount;
	}

	public String getOderFreight() {
		return oderFreight;
	}
	
	public String getPlateFormDiscount() {
		return plateFormDiscount;
	}

	public String getDealerDiscount() {
		return dealerDiscount;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setGoodAmount(String goodAmount) {
		this.goodAmount = goodAmount;
	}

	public void setOderFreight(String oderFreight) {
		this.oderFreight = oderFreight;
	}

	public void setPlateFormDiscount(String plateFormDiscount) {
		this.plateFormDiscount = plateFormDiscount;
	}

	public void setDealerDiscount(String dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

}
