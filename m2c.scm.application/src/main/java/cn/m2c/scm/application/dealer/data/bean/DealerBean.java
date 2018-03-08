package cn.m2c.scm.application.dealer.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

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
	 @ColumnAlias(value = "is_pay_deposit")
	 private Integer isPayDeposit;
	 @ColumnAlias(value = "deposit")
	 private Long deposit;

	 
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
	@ColumnAlias(value = "dealer_detail_address")
	private String dealerDetailAddress;
	
	@ColumnAlias(value = "manager_name")
	private String managerName;
	@ColumnAlias(value = "manager_phone")
	private String managerPhone;
	@ColumnAlias(value = "manager_qq")
	private String managerqq;
	@ColumnAlias(value = "manager_wechat")
	private String managerWechat;
	@ColumnAlias(value = "manager_email")
	private String managerEmail;
	@ColumnAlias(value = "manager_department")
	private String managerDepartment;
	@ColumnAlias(value = "start_sign_date")
	private String startSignDate;
	@ColumnAlias(value = "end_sign_date")
	private String endSignDate;
	
	@ColumnAlias(value = "seller_id")
	private String sellerId;
	
	private String strDeposit;
	
	@ColumnAlias(value = "seller_name")
	private String sellerName;
	
	
	@ColumnAlias(value = "seller_phone")
	private String sellerPhone;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	 @ColumnAlias(value = "shop_name")
	 private String shopName;
	
	
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

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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


	public DealerClassifyNameBean getDealerClassifyBean() {
		return dealerClassifyBean;
	}

	public void setDealerClassifyBean(DealerClassifyNameBean dealerClassifyBean) {
		this.dealerClassifyBean = dealerClassifyBean;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public void setManagerPhone(String managerPhone) {
		this.managerPhone = managerPhone;
	}

	public String getManagerqq() {
		return managerqq;
	}

	public void setManagerqq(String managerqq) {
		this.managerqq = managerqq;
	}

	public String getManagerWechat() {
		return managerWechat;
	}

	public void setManagerWechat(String managerWechat) {
		this.managerWechat = managerWechat;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public String getManagerDepartment() {
		return managerDepartment;
	}

	public void setManagerDepartment(String managerDepartment) {
		this.managerDepartment = managerDepartment;
	}

	public String getStartSignDate() {
		return startSignDate;
	}

	public void setStartSignDate(String startSignDate) {
		this.startSignDate = startSignDate;
	}

	public String getEndSignDate() {
		return endSignDate;
	}

	public void setEndSignDate(String endSignDate) {
		this.endSignDate = endSignDate;
	}

	public Integer getIsPayDeposit() {
		return isPayDeposit;
	}

	public void setIsPayDeposit(Integer isPayDeposit) {
		this.isPayDeposit = isPayDeposit;
	}

	public Long getDeposit() {
		return deposit;
	}

	public void setDeposit(Long deposit) {
		this.deposit = deposit;
	}

	public String getDealerDetailAddress() {
		return dealerDetailAddress;
	}

	public void setDealerDetailAddress(String dealerDetailAddress) {
		this.dealerDetailAddress = dealerDetailAddress;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	public String getStrDeposit() {
		if (deposit == null)
			deposit = 0l;
		return Utils.moneyFormatCN(deposit);
	}
	
}
