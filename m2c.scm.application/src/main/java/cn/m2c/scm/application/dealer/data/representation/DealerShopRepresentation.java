package cn.m2c.scm.application.dealer.data.representation;

import cn.m2c.scm.application.dealer.data.bean.DealerBean;

public class DealerShopRepresentation {
	private String dealerName;
	 
	 private String dealerId;
	 private String shopName;
	 
	public DealerShopRepresentation() {
		super();
	}
	public DealerShopRepresentation(DealerBean model) {
		this.dealerId = model.getDealerId();
		this.dealerName = model.getDealerName();
		this.shopName = model.getShopName();
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
	 
	 
}
