package cn.m2c.scm.application.order.data.representation;

import cn.m2c.scm.application.order.data.bean.CouponDealerBean;

/**
 * 对营销提供的优惠券适用商家信息表述对象 
 */
public class CouponDealerBeanRepresentation {
	private String dealerId;
	private String dealerName;
	private String shopId;
	private String shopName;
	private String shopIcon;
	
	public CouponDealerBeanRepresentation(CouponDealerBean bean) {
		this.dealerId = bean.getDealerId();
		this.dealerName = bean.getDealerName();
		this.shopId = bean.getShopId();
		this.shopName = bean.getShopName();
		this.shopIcon = bean.getShopIcon();
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
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
}
