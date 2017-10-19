package cn.m2c.scm.application.order.data.bean;

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
	
	
	
}
