package cn.m2c.scm.application.shop.data.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShopBean {
	@ColumnAlias(value = "dealer_id")
	private String dealerId;
	@ColumnAlias(value = "shop_id")
	private String shopId;
	@ColumnAlias(value = "shop_name")
	private String shopName;
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	@ColumnAlias(value = "shop_icon")
	private String shopIcon;
	@ColumnAlias(value = "shop_introduce")
	private String shopIntroduce;
	@ColumnAlias(value = "shop_receipt")
	private String shopReceipt;
	@ColumnAlias(value = "customer_service_tel")
	private String customerServiceTel;
	
	@ColumnAlias(value = "last_updated_date")
	private Date lastUpdatedTIme;
	
	private String lastUpdateDate;
	
	private Integer onSaleGoods;
	
	/** 是否已关注 0：未关注 1：已关注 **/
	private Integer isFocus;
	
	private String userId;

	public ShopBean() {
		super();
	}

	public ShopBean(String dealerId, String shopId, String shopName, String dealerName, String shopIcon,
			String shopIntroduce, String shopReceipt, String customerServiceTel, Integer onSaleGoods) {
		
		if (StringUtils.isEmpty(userId)) {
			this.isFocus = 0;
		}
		this.dealerId = dealerId;
		this.shopId = shopId;
		this.shopName = shopName;
		this.dealerName = dealerName;
		this.shopIcon = shopIcon;
		this.shopIntroduce = shopIntroduce;
		this.shopReceipt = shopReceipt;
		this.customerServiceTel = customerServiceTel;
		this.onSaleGoods = onSaleGoods;
	}

	public String getDealerId() {
		return dealerId;
	}
	

	public String getLastUpdateDate() {
		return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(lastUpdatedTIme);
	}


	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Date getLastUpdatedTIme() {
		return lastUpdatedTIme;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public Integer getIsFocus() {
		return isFocus;
	}

	public String getUserId() {
		return userId;
	}

	public void setIsFocus(Integer isFocus) {
		this.isFocus = isFocus;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getShopReceipt() {
		return shopReceipt;
	}

	public void setShopReceipt(String shopReceipt) {
		this.shopReceipt = shopReceipt;
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

	@Override
	public String toString() {
		return "ShopBean [dealerId=" + dealerId + ", shopId=" + shopId
				+ ", shopName=" + shopName + ", dealerName=" + dealerName
				+ ", shopIcon=" + shopIcon + ", shopIntroduce=" + shopIntroduce
				+ ", shopReceipt=" + shopReceipt + ", customerServiceTel="
				+ customerServiceTel + ", onSaleGoods=" + onSaleGoods
				+ ", isFocus=" + isFocus + ", userId=" + userId + "]";
	}

}
