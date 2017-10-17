package cn.m2c.scm.application.shop.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShopBean {
	 @ColumnAlias(value = "dealer_id")
	 private String dealerId;
	 @ColumnAlias(value = "shop_id")
	 private String shopId;
	 @ColumnAlias(value = "shop_name")
	 private String shopName;
	 @ColumnAlias(value = "shop_icon")
	 private String shopIcon;
	 @ColumnAlias(value = "shop_introduce")
	 private String shopIntroduce;
	 @ColumnAlias(value = "customer_service_tel")
	 private String customerServiceTel;
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopIntroduce() {
		return shopIntroduce;
	}
	public void setShopIntroduce(String shopIntroduce) {
		this.shopIntroduce = shopIntroduce;
	}
	public String getCustomerServiceTel() {
		return customerServiceTel;
	}
	public void setCustomerServiceTel(String customerServiceTel) {
		this.customerServiceTel = customerServiceTel;
	}
	public String getShopIcon() {
		return shopIcon;
	}
	public void setShopIcon(String shopIcon) {
		this.shopIcon = shopIcon;
	}
	 
	 
}