package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 主订单bean, 用于查询模式，APP订单列表
 * @author 89776
 * created date 2017年10月31日
 * copyrighted@m2c
 */
public class OrderBean extends AssertionConcern implements Serializable {

	/**
	 * 主订单号
	 */
	@ColumnAlias(value = "order_id")
	private String orderId;
	/**
	 * 支付单号
	 */
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/**
	 * 下单时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createDate;
	/**
	 * 订单商品金额
	 */
	@ColumnAlias(value = "goods_amount")
	private int goodAmount;
	/**
	 * 订单运费
	 */
	@ColumnAlias(value = "order_freight")
	private int oderFreight;
	/**
	 * 平台优惠
	 */
	@ColumnAlias(value = "plateform_discount")
	private int plateFormDiscount;
	/**
	 * 商家优惠
	 */
	@ColumnAlias(value = "dealer_discount")
	private int dealerDiscount;
	/**
	 * 商家订单列表
	 */
	private List<OrderDetailBean> goodses;

	public String getOrderId() {
		return orderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public Date getCreateDate() {
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

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setCreateDate(Date createDate) {
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
	
	public List<OrderDetailBean> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<OrderDetailBean> goodses) {
		this.goodses = goodses;
	}

	@Override
	public String toString() {
		return "mainOrderBean [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ goodAmount + ", oderFreight=" + oderFreight + ", plateFormDiscount=" + plateFormDiscount
				+ ", dealerDiscount=" + dealerDiscount + ", goodses=" + goodses + "]";
	}
	
}
