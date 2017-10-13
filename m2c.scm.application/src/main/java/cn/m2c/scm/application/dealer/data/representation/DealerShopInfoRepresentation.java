package cn.m2c.scm.application.dealer.data.representation;

import cn.m2c.scm.application.dealer.data.bean.DealerBean;

public class DealerShopInfoRepresentation {
	private String dealerName;
	 
	private String dealerId;
	 
	private String shopName;//店铺名
	private String shopIcon;//店铺图标
	private String shopIntroduce;//店铺介绍
	private String customerServiceTel;//客服电话
	
	public DealerShopInfoRepresentation() {
		super();
	}
	
	public DealerShopInfoRepresentation(DealerBean model) {
		this.dealerName = model.getDealerName();
		this.dealerId = model.getDealerId();
		this.shopName = model.getShopName();
		this.shopIcon = model.getShopIcon();
		this.shopIntroduce = model.getShopIntroduce();
		this.customerServiceTel = model.getCustomerServiceTel();
	}

	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
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
	public String getCustomerServiceTel() {
		return customerServiceTel;
	}
	public void setCustomerServiceTel(String customerServiceTel) {
		this.customerServiceTel = customerServiceTel;
	}
	
	
	 
}
