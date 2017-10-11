package cn.m2c.scm.application.dealer.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerBean {

	 @ColumnAlias(value = "dealer_name")
	 private String dealerName;
	 
	 @ColumnAlias(value = "dealer_id")
	 private String dealerId;
	 
	 @ColumnAlias(value = "user_name")
	 private String userName;
	 
	 @ColumnAlias(value = "user_phone")
	 private String userPhone;
	 
	 @ColumnAlias(value = "user_id")
	 private String userId;
	 
	 @ColumnAlias(value = "dealer_classify")
	 private String dealerClassify;
	 
	 @ColumnAlias(value = "cooperation_mode")
	 private Integer cooperationMode;
	 
	 @ColumnAlias(value = "count_mode")
	 private Integer countMode;

	 @ColumnAlias(value = "dealer_province")
	 private String dealerProvince;
	 
	 @ColumnAlias(value = "dealer_city")
	 private String dealerCity;
	 
	@ColumnAlias(value = "dealer_area")
	private String dealerArea;
	 
	@ColumnAlias(value = "dealer_pcode")
	private String dealerPcode;
	 
	@ColumnAlias(value = "dealer_ccode")
	private String dealerCcode;
	 
	@ColumnAlias(value = "dealer_acode")
	private String dealerAcode;
	
	@ColumnAlias(value = "seller_name")
	private String sellerName;
	
	@ColumnAlias(value = "seller_phone")
	private String sellerPhone;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;

	@ColumnAlias(value = "shop_name")
	private String shopName;
	
	@ColumnAlias(value = "shop_icon")
	private String shopIcon;
	
	@ColumnAlias(value = "shop_introduce")
	private String shopIntroduce;
	
	@ColumnAlias(value = "customer_service_tel")
	private String customerServiceTel;
	
	
	private DealerClassifyNameBean dealerClassifyBean;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getDealerClassify() {
		return dealerClassify;
	}

	public void setDealerClassify(String dealerClassify) {
		this.dealerClassify = dealerClassify;
	}

	public Integer getCooperationMode() {
		return cooperationMode;
	}

	public void setCooperationMode(Integer cooperationMode) {
		this.cooperationMode = cooperationMode;
	}

	public Integer getCountMode() {
		return countMode;
	}

	public void setCountMode(Integer countMode) {
		this.countMode = countMode;
	}

	public String getDealerProvince() {
		return dealerProvince;
	}

	public void setDealerProvince(String dealerProvince) {
		this.dealerProvince = dealerProvince;
	}

	public String getDealerCity() {
		return dealerCity;
	}

	public void setDealerCity(String dealerCity) {
		this.dealerCity = dealerCity;
	}

	public String getDealerArea() {
		return dealerArea;
	}

	public void setDealerArea(String dealerArea) {
		this.dealerArea = dealerArea;
	}

	public String getDealerPcode() {
		return dealerPcode;
	}

	public void setDealerPcode(String dealerPcode) {
		this.dealerPcode = dealerPcode;
	}

	public String getDealerCcode() {
		return dealerCcode;
	}

	public void setDealerCcode(String dealerCcode) {
		this.dealerCcode = dealerCcode;
	}

	public String getDealerAcode() {
		return dealerAcode;
	}

	public void setDealerAcode(String dealerAcode) {
		this.dealerAcode = dealerAcode;
	}

	public String getSellerName() {
		return sellerName;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getUserId() {
		return userId;
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

	public DealerClassifyNameBean getDealerClassifyBean() {
		return dealerClassifyBean;
	}

	public void setDealerClassifyBean(DealerClassifyNameBean dealerClassifyBean) {
		this.dealerClassifyBean = dealerClassifyBean;
	}
	
	
}
