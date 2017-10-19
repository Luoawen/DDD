package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

public class mainOrderBean extends AssertionConcern implements Serializable {

	/**
	 * 主订单号
	 */
	private String orderId;
	/**
	 * 支付单号
	 */
	private String payNo;
	/**
	 * 下单时间
	 */
	private String createDate;
	/**
	 * 订单商品金额
	 */
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
	/**
	 * 商家订单列表
	 */
	private List<DealerOrderBean> dealerOrderBeans;

	public String getOrderId() {
		return orderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public String getCreateDate() {
		return createDate;
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

	public List<DealerOrderBean> getDealerOrderBeans() {
		return dealerOrderBeans;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
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

	public void setDealerOrderBeans(List<DealerOrderBean> dealerOrderBeans) {
		this.dealerOrderBeans = dealerOrderBeans;
	}

	@Override
	public String toString() {
		return "mainOrderBean [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ goodAmount + ", oderFreight=" + oderFreight + ", plateFormDiscount=" + plateFormDiscount
				+ ", dealerDiscount=" + dealerDiscount + ", dealerOrderBeans=" + dealerOrderBeans + "]";
	}

	
}
