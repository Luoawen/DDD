package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

public class OrderDetailBean {

	@ColumnAlias(value= "dealer_order_id")
	private String dealerOrderId;
	@ColumnAlias(value= "goods_name")
	private String goodsName;
	@ColumnAlias(value= "sku_id")
	private String skuId;
	@ColumnAlias(value= "sku_name")
	private String skuName;
	@ColumnAlias(value= "goods_icon")
	private String goodsIcon;
	@ColumnAlias(value= "sell_num")
	private Integer  sellNum;
	@ColumnAlias(value= "express_way")
	private Integer  expressWay;
	@ColumnAlias(value= "express_note")
	private String expressNote;
	@ColumnAlias(value= "express_phone")
	private String expressPhone;
	@ColumnAlias(value= "express_person")
	private String expressPerson;
	@ColumnAlias(value= "express_name")
	private String expressName;
	@ColumnAlias(value= "express_no")
	private String expressNo;
	@ColumnAlias(value= "express_code")
	private String expressCode;
	@ColumnAlias(value= "express_time")
	private Date expressTime;
	/**主订单号*/
	@ColumnAlias(value= "order_id")
	private String orderId;
	
	@ColumnAlias(value= "is_change")
	private Integer isChange;
	
	@ColumnAlias(value= "change_price")
	private Long changePrice;
	
	@ColumnAlias(value = "is_special")
	private Integer isSpecial;
	@ColumnAlias(value = "special_price")
	private long specialPrice;
	
	@ColumnAlias(value = "sort_no")
	private Integer sortNo;
	
	@ColumnAlias(value = "coupon_discount")
	private long couponDiscount;
	
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}
	
	public long getSpecialPrice() {
		return specialPrice/100;
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
	public String getStrSpecialPrice() {
		return Utils.moneyFormatCN(specialPrice);
	}
	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}
	public void setSpecialPrice(long specialPrice) {
		this.specialPrice = specialPrice;
	}
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	public long getChangePrice() {
		if (null == changePrice)
			return 0;
		return changePrice/100;
	}
	
	public String getStrChangePrice() {
		if (null == changePrice)
			return "";
		return Utils.moneyFormatCN(changePrice);
	}
	
	public void setChangePrice(Long changePrice) {
		if (null == changePrice)
			changePrice = 0l;
		this.changePrice = changePrice;
	}
	public Integer getAfterStatus() {
		if (null == afterStatus)
			afterStatus = -2;
		return afterStatus;
	}
	public void setAfterStatus(Integer afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsTypeId() {
		return goodsTypeId;
	}
	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	@ColumnAlias(value= "discount_price")
	private long discountPrice;
	
	@ColumnAlias(value= "freight")
	private long freight;
	
	@ColumnAlias(value= "comment_status")
	private int commentStatus;
	
	@ColumnAlias(value= "afterStatus")
	private Integer afterStatus;
	
	@ColumnAlias(value= "goods_id")
	private String goodsId;
	
	@ColumnAlias(value= "goods_type_id")
	private String goodsTypeId;
	
	public long getDiscountPrice() {
		return discountPrice/100;
	}
	
	public String getStrDiscountPrice() {
		return Utils.moneyFormatCN(discountPrice);
	}
	
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public long getFreight() {
		return freight/100;
	}
	public String getStrFreight() {
		return Utils.moneyFormatCN(freight);
	}
	
	
	public void setFreight(long freight) {
		this.freight = freight;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getSkuId() {
		return skuId;
	}
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getGoodsIcon() {
		return goodsIcon;
	}
	public void setGoodsIcon(String goodsIcon) {
		this.goodsIcon = goodsIcon;
	}
	public Integer getSellNum() {
		return sellNum;
	}
	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}
	public Integer getExpressWay() {
		return expressWay;
	}
	public void setExpressWay(Integer expressWay) {
		this.expressWay = expressWay;
	}
	public String getExpressNote() {
		return expressNote;
	}
	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}
	public String getExpressPhone() {
		return expressPhone;
	}
	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}
	public String getExpressPerson() {
		return expressPerson;
	}
	public void setExpressPerson(String expressPerson) {
		this.expressPerson = expressPerson;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Date getExpressTime() {
		return expressTime;
	}
	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public int getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(int commentStatus) {
		this.commentStatus = commentStatus;
	}
	
}
