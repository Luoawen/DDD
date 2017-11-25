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

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getStatus() {
		return status;
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
