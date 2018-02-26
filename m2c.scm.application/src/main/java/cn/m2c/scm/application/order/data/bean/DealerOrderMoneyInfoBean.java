package cn.m2c.scm.application.order.data.bean;

/**
 * 给基础组提供,查询商家订单金额 
 */
public class DealerOrderMoneyInfoBean {
	
	private String dealerOrderId;
	/**
	 * 订单商品总额(Sum(totalPrice))
	 */
	private long totalOrderPrice;
	/**
	 * 总运费
	 */
	private long totalFreight;
	/**
	 * 平台优惠券金额
	 */
	private long plateformDiscount;
	/**
	 * 商家优惠券金额
	 */
	private long dealerDiscount;
	/**
	 * 优惠券优惠金额
	 */
	private long couponDiscount;
	/**
	 * 订单总额(订单商品总额 - 平台优惠券 - 商家优惠券 - 优惠券 + 运费)
	 */
	private long orderPrice;
	public long getTotalOrderPrice() {
		return totalOrderPrice;
	}
	public void setTotalOrderPrice(long totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}
	public long getTotalFreight() {
		return totalFreight;
	}
	public void setTotalFreight(long totalFreight) {
		this.totalFreight = totalFreight;
	}
	public long getPlateformDiscount() {
		return plateformDiscount;
	}
	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}
	public long getDealerDiscount() {
		return dealerDiscount;
	}
	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
	public long getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public long getOrderPrice() {
		orderPrice = totalOrderPrice + totalFreight - plateformDiscount - dealerDiscount - couponDiscount;
		return orderPrice;
	}
	public void setOrderPrice(long orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	
}
