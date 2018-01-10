package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 退换货物流信息
 * 
 * @author lqwen
 *
 */
public class AftreSellLogisticsBean {

	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	/**
	 * 物流公司
	 */
	@ColumnAlias(value = "back_express_name")
	private String backExpressName;
	/**
	 * 用户寄回物流公司编号
	 */
	@ColumnAlias(value = "back_express_code")
	private String backExpressCode;

	/**
	 * 物流单号
	 */
	@ColumnAlias(value = "back_express_no")
	private String backExpressNo;
	
	@ColumnAlias(value = "express_name")
	private String expressName;
	/**
	 * 物流单号
	 */
	@ColumnAlias(value = "express_no")
	private String expressNo;
	
	@ColumnAlias(value = "order_type")
	private Integer orderType;
	
	/**
	 * 配送方式 0:物流，1自有物流 
	 */
	@ColumnAlias(value = "express_way")
	private Integer expressWay;

	/**
	 * 送货人姓名
	 */
	@ColumnAlias(value = "express_person")
	private String expressPerson;
	
	/**
	 * 送货人电话
	 */
	@ColumnAlias(value = "express_phone")
	private String expressPhone;
	
	/**
	 * 说明
	 */
	@ColumnAlias(value = "express_note")
	private String expressNote;
	
	/**
	 * 发货时间
	 */
	@ColumnAlias(value = "express_time")
	private String expressTime;
	
	
	/**
	 * 商品信息
	 */
	private GoodsInfoBean goodsInfo;

	public String getBackExpressName() {
		return backExpressName;
	}

	public void setBackExpressName(String backExpressName) {
		this.backExpressName = backExpressName;
	}

	public String getBackExpressNo() {
		return backExpressNo;
	}

	public void setBackExpressNo(String backExpressNo) {
		this.backExpressNo = backExpressNo;
	}

	public Integer getOrderType() {
		return orderType;
	}
	
	public String getBackExpressCode() {
		return backExpressCode;
	}

	public void setBackExpressCode(String backExpressCode) {
		this.backExpressCode = backExpressCode;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
	}

	
	public Integer getExpressWay() {
		return expressWay;
	}

	public String getExpressPerson() {
		return expressPerson;
	}

	public String getExpressPhone() {
		return expressPhone;
	}

	public String getExpressNote() {
		return expressNote;
	}

	public String getExpressTime() {
		return expressTime;
	}

	public void setExpressWay(Integer expressWay) {
		this.expressWay = expressWay;
	}

	public void setExpressPerson(String expressPerson) {
		this.expressPerson = expressPerson;
	}

	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}

	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}

	public void setExpressTime(String expressTime) {
		this.expressTime = expressTime;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public String getExpressName() {
		return expressName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public GoodsInfoBean getGoodsInfo() {
		return goodsInfo;
	}

	public void setGoodsInfo(GoodsInfoBean goodsInfo) {
		this.goodsInfo = goodsInfo;
	}

}
