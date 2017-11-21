package cn.m2c.scm.application.dealer.data.representation;

import java.util.ArrayList;
import java.util.List;

import cn.m2c.scm.application.dealer.data.bean.DealerBean;

public class DealerDetailRepresentation {

	    	
	    	
	    private String dealerName;
		 
		 private String dealerId;
		 
		 private String userName;
		 
		 private String userPhone;
		 
		 private String userId;
		 
		 private List<String> dealerClassifyIds = new ArrayList<String>();
		 
		 private String dealerClassify;
		 private String dealerFirstClassify;
		 
		 private String dealerFristClassifyName;
		 
		 private String dealerSecondClassifyName;
		 
		 private Integer cooperationMode;
		 
		 private Integer countMode;

		 private String dealerProvince;
		 
		 private String dealerCity;
		 
		private String dealerArea;
		 
		private String dealerPcode;
		 
		private String dealerCcode;
		 
		private String dealerAcode;
		private String dealerDetailAddress;
		
		private String sellerId;
		private String sellerName;
		
		private String sellerPhone;
		
		private String startSignDate;
		private String endSignDate;
		
		 private Integer isPayDeposit;
		 private Long deposit;
		private String managerName;//商家负责人姓名
		private String managerPhone;//商家负责人手机
		private String managerqq;//商家负责人qq
		private String managerWechat;//商家负责人微信
		private String managerEmail;//商家负责人邮箱
		private String managerDepartment;//商家负责人部门
		public DealerDetailRepresentation(DealerBean model) {
			this.dealerId = model.getDealerId();
			this.dealerName = model.getDealerName();
			this.userId = model.getUserId();
			this.userName = model.getUserName();
			this.userPhone = model.getUserPhone();
			this.dealerClassify = model.getDealerClassify();
			if(null != model.getDealerClassifyBean()){
				this.dealerFirstClassify = model.getDealerClassifyBean().getDealerClassifyId();
				this.dealerFristClassifyName = model.getDealerClassifyBean().getDealerFirstClassifyName();
				this.dealerSecondClassifyName = model.getDealerClassifyBean().getDealerSecondClassifyName();
				this.dealerClassifyIds.add(this.dealerFirstClassify);
				this.dealerClassifyIds.add(this.dealerClassify);
			}
			this.cooperationMode = model.getCooperationMode();
			this.countMode = model.getCountMode();
			this.dealerProvince = model.getDealerProvince();
			this.dealerCity = model.getDealerCity();
			this.dealerArea = model.getDealerArea();
			this.dealerPcode = model.getDealerPcode();
			this.dealerCcode = model.getDealerCcode();
			this.dealerAcode = model.getDealerAcode();
			this.dealerDetailAddress = model.getDealerDetailAddress();
			this.sellerId = model.getSellerId();
			this.sellerName = model.getSellerName();
			this.sellerPhone = model.getSellerPhone();
			this.managerName = model.getManagerName();
			this.managerPhone = model.getManagerPhone();
			this.managerqq = model.getManagerqq();
			this.managerWechat = model.getManagerWechat();
			this.managerEmail = model.getManagerEmail();
			this.managerDepartment = model.getManagerDepartment();
			this.startSignDate = model.getStartSignDate();
			this.endSignDate = model.getEndSignDate();
			this.isPayDeposit = model.getIsPayDeposit();
			this.deposit = model.getDeposit();
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
		public String getUserId() {
			return userId;
		}
		public void setUserId(String userId) {
			this.userId = userId;
		}
		public String getDealerClassify() {
			return dealerClassify;
		}
		public void setDealerClassify(String dealerClassify) {
			this.dealerClassify = dealerClassify;
		}
		public String getDealerFirstClassify() {
			return dealerFirstClassify;
		}
		public void setDealerFirstClassify(String dealerFirstClassify) {
			this.dealerFirstClassify = dealerFirstClassify;
		}
		public String getDealerFristClassifyName() {
			return dealerFristClassifyName;
		}
		public void setDealerFristClassifyName(String dealerFristClassifyName) {
			this.dealerFristClassifyName = dealerFristClassifyName;
		}
		public String getDealerSecondClassifyName() {
			return dealerSecondClassifyName;
		}
		public void setDealerSecondClassifyName(String dealerSecondClassifyName) {
			this.dealerSecondClassifyName = dealerSecondClassifyName;
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
		public List<String> getDealerClassifyIds() {
			return dealerClassifyIds;
		}
		public void setDealerClassifyIds(List<String> dealerClassifyIds) {
			this.dealerClassifyIds = dealerClassifyIds;
		}
		
		
		
		
}
