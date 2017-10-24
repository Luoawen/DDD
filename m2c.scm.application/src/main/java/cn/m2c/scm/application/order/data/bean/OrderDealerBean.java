package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class OrderDealerBean {

	/**
	 * 平台订单号
	 */
	@ColumnAlias(value = "order_id")
	private String orderId;
	/**
	 * 商家Id
	 */
	@ColumnAlias(value = "dealer_id")
	private String dealerId;
	/**
	 * 商家订单号
	 */
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	/**
	 * 商家或店铺名称
	 */
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	/**
	 * 订单状态：0待付款;1待发货;2待收货;3完成;4交易完成;5交易关闭;-1已取消
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 订单商品金额
	 */
	@ColumnAlias(value = "goods_amount")
	private int goodAmount;
	/**
	 * 订单运费
	 */
	private int oderFreight;
	/**
	 * 平台优惠
	 */
	private int plateFormDiscount;
	/**
	 * 商家优惠
	 */
	private int dealerDiscount;

	public String getOrderId() {
		return orderId;
	}

	public String getDealerId() {
		return dealerId;
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

	public int getGoodAmount() {
		return goodAmount;
	}

	public int getOderFreight() {
		return oderFreight;
	}

	public int getPlateFormDiscount() {
		return plateFormDiscount;
	}

	public int getDealerDiscount() {
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

	public void setGoodAmount(int goodAmount) {
		this.goodAmount = goodAmount;
	}

	public void setOderFreight(int oderFreight) {
		this.oderFreight = oderFreight;
	}

	public void setPlateFormDiscount(int plateFormDiscount) {
		this.plateFormDiscount = plateFormDiscount;
	}

	public void setDealerDiscount(int dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	@Override
	public String toString() {
		return "OrderDealerBean [orderId=" + orderId + ", dealerId=" + dealerId + ", dealerOrderId=" + dealerOrderId
				+ ", dealerName=" + dealerName + ", status=" + status + ", goodAmount=" + goodAmount + ", oderFreight="
				+ oderFreight + ", plateFormDiscount=" + plateFormDiscount + ", dealerDiscount=" + dealerDiscount + "]";
	}

}
