package cn.m2c.scm.application.shop.data.representation;

import cn.m2c.scm.application.shop.data.bean.ShopBean;


public class ShopInfoRepresentation {
	 private String dealerId;
	 private String dealerName;
	 private String shopId;
	 private String shopName;
	 private String shopIcon;
	 private String shopIntroduce;
	 private String shopReceipt;
	 private String customerServiceTel;
	 private Integer onSaleGoods;
	 
	public ShopInfoRepresentation() {
		super();
	}
	public ShopInfoRepresentation(ShopBean model) {
		this.dealerId = model.getDealerId();
		this.dealerName = model.getDealerName();
		this.shopId = model.getShopId();
		this.shopName = model.getShopName();
		this.shopIcon = model.getShopIcon();
		this.shopIntroduce = model.getShopIntroduce();
		this.shopReceipt = model.getShopReceipt();
		this.customerServiceTel = model.getCustomerServiceTel();
		this.onSaleGoods = model.getOnSaleGoods();
	}
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
	public String getShopIcon() {
		return shopIcon;
	}
	public void setShopIcon(String shopIcon) {
		this.shopIcon = shopIcon;
	}
	public String getShopIntroduce() {
		return shopIntroduce;
	}
	public void setShopIntroduce(String shopIntroduce) {
		this.shopIntroduce = shopIntroduce;
	}
	public String getShopReceipt() {
		return shopReceipt;
	}
	public void setShopReceipt(String shopReceipt) {
		this.shopReceipt = shopReceipt;
	}
	public String getCustomerServiceTel() {
		return customerServiceTel;
	}
	public void setCustomerServiceTel(String customerServiceTel) {
		this.customerServiceTel = customerServiceTel;
	}
	public Integer getOnSaleGoods() {
		return onSaleGoods;
	}
	public void setOnSaleGoods(Integer onSaleGoods) {
		this.onSaleGoods = onSaleGoods;
	}
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	 
	 
}
