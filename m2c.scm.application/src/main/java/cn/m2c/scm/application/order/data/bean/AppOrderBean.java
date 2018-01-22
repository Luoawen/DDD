package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;
/***
 * 主订单bean, 用于查询模式，APP订单列表
 * @author fanjc
 * created date 2017年11月4日
 * copyrighted@m2c
 */
public class AppOrderBean extends AssertionConcern implements Serializable {

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
	private Long goodAmount;
	/**
	 * 订单运费
	 */
	@ColumnAlias(value = "order_freight")
	private Long oderFreight;
	/**
	 * 平台优惠
	 */
	@ColumnAlias(value = "plateform_discount")
	private Long plateFormDiscount;
	/**
	 * 商家优惠
	 */
	@ColumnAlias(value = "dealer_discount")
	private Long dealerDiscount;
	
	@ColumnAlias(value = "dealer_id")
	private String dealerId;
	
	@ColumnAlias(value = "shop_name")
	private String dealerName;
	
	@ColumnAlias(value = "_status")
	private Integer status;
	
	@ColumnAlias(value = "coupon_discount")
	private long couponDiscount;
	/**
	 * 商品列表
	 */
	private List<OrderDetailBean> goodses;
	
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	
	@ColumnAlias(value = "dOrderFreight")
	private long dOrderFreight;
	@ColumnAlias(value = "dGoodsAmount")
	private long dGoodsAmount;
	@ColumnAlias(value = "dPlateformDiscount")
	private long dPlateformDiscount;
	@ColumnAlias(value = "dDealerDiscount")
	private long dDealerDiscount;
	
	public void setdOrderFreight(long dOrderFreight) {
		this.dOrderFreight = dOrderFreight;
	}

	public void setdGoodsAmount(long dGoodsAmount) {
		this.dGoodsAmount = dGoodsAmount;
	}

	public void setdPlateformDiscount(long dPlateformDiscount) {
		this.dPlateformDiscount = dPlateformDiscount;
	}

	public void setdDealerDiscount(long dDealerDiscount) {
		this.dDealerDiscount = dDealerDiscount;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public long getCouponDiscount() {
		return couponDiscount/10000;
	}

	public String getStrCouponDiscount(){
		return Utils.moneyFormatCN(couponDiscount);
	}
	
	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public String getPayNo() {
		return payNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public long getGoodAmount() {
		if (status != null && status >=1)
			return dGoodsAmount/100;
		return goodAmount/100;
	}
	
	public String getStrGoodAmount() {
		if (status != null && status >=1)
			return Utils.moneyFormatCN(dGoodsAmount);
		return Utils.moneyFormatCN(goodAmount);
	}

	public long getOderFreight() {
		if (status != null && status >=1)
			return dOrderFreight/100;
		return oderFreight/100;
	}
	
	public String getStrOrderFreight() {
		if (status != null && status >=1)
			return Utils.moneyFormatCN(dOrderFreight);
		return Utils.moneyFormatCN(oderFreight);
	}

	public long getPlateFormDiscount() {
		if (status != null && status >=1)
			return dPlateformDiscount/100;
		return plateFormDiscount/100;
	}
	
	public String getStrPlateFormDiscount() {
		if (status != null && status >=1)
			return Utils.moneyFormatCN(dPlateformDiscount);
		return Utils.moneyFormatCN(plateFormDiscount);
	}

	public long getDealerDiscount() {
		if (status != null && status >=1)
			return dDealerDiscount/100;
		return dealerDiscount/100;
	}
	
	public String getStrDealerDiscount() {
		if (status != null && status >=1)
			return Utils.moneyFormatCN(dDealerDiscount);
		return Utils.moneyFormatCN(dealerDiscount);
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

	public void setGoodAmount(Long goodAmount) {
		if (null == goodAmount)
			goodAmount = 0l;
		this.goodAmount = goodAmount;
	}

	public void setOderFreight(Long oderFreight) {
		if (null == oderFreight)
			oderFreight = 0l;
		this.oderFreight = oderFreight;
	}

	public void setPlateFormDiscount(Long plateFormDiscount) {
		if (null == plateFormDiscount)
			plateFormDiscount = 0l;
		this.plateFormDiscount = plateFormDiscount;
	}

	public void setDealerDiscount(Long dealerDiscount) {
		if (null == dealerDiscount)
			dealerDiscount = 0l;
		this.dealerDiscount = dealerDiscount;
	}
	
	public List<OrderDetailBean> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<OrderDetailBean> goodses) {
		this.goodses = goodses;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "mainOrderBean [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ goodAmount + ", oderFreight=" + oderFreight + ", plateFormDiscount=" + plateFormDiscount
				+ ", dealerDiscount=" + dealerDiscount + ", goodses=" + goodses + "]";
	}
	
}
