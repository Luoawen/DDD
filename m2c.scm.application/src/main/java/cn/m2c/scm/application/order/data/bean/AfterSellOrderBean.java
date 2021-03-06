package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

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
	private Long backMoney;
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

	@ColumnAlias(value = "return_freight")
	private Long rtFreight;

	@ColumnAlias(value = "is_special")
	private Integer isSpecial;
	
	@ColumnAlias(value = "special_price")
	private long specialPrice;
	
	@ColumnAlias(value="sort_no")
	private Integer sortNo;
	
	@ColumnAlias(value = "coupon_discount")
	private long couponDiscount;
	
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	private GoodsInfoBean goodsInfo;

	public Long getRtFreight() {
		if (null == rtFreight)
			rtFreight = 0l;
		return rtFreight/100;
	}
	
	
	public long getCouponDiscount() {
		return couponDiscount/10000;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public String getStrcouponDiscount() {
		return Utils.moneyFormatCN(couponDiscount);
	}
	
	public String getStrRtFreight() {
		return Utils.moneyFormatCN(rtFreight);
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public long getSpecialPrice() {
		return specialPrice/100;
	}
	
	public String getStrSpecialPrice() {
		return Utils.moneyFormatCN(specialPrice);
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public void setSpecialPrice(long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public void setRtFreight(Long rtFreight) {
		if (rtFreight == null)
			rtFreight = 0l;
		this.rtFreight = rtFreight;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public Long getBackMoney() {
		if (backMoney == null)
			backMoney = 0l;
		return backMoney / 100;
	}
	
	public String getStrBackMoney() {
		return Utils.moneyFormatCN(backMoney);
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

	public void setBackMoney(Long backMoney) {
		if (backMoney == null)
			backMoney = 0l;
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
