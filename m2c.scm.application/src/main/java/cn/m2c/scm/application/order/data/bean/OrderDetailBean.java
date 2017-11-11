package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

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
	
	public Integer getAfterStatus() {
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
		return discountPrice;
	}
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	public long getFreight() {
		return freight;
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
