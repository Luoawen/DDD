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
	private String sell_num;
	
}
