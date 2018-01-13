package cn.m2c.scm.application.order.data.bean;

/**
 * 营销用优惠券商家信息 
 */
public class CouponDealerBean {
	private String dealerId;
	private String dealerName;
	private String shopId;
	private String shopName;
	private String shopIcon;
	
	public CouponDealerBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CouponDealerBean(String dealerId, String dealerName, String shopId, String shopName, String shopIcon) {
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.shopId = shopId;
		this.shopName = shopName;
		this.shopIcon = shopIcon;
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

	@Override
	public String toString() {
		return "CouponDealerBean [dealerId=" + dealerId + ", dealerName=" + dealerName + ", shopId=" + shopId
				+ ", shopName=" + shopName + ", shopIcon=" + shopIcon + "]";
	}
	
}
