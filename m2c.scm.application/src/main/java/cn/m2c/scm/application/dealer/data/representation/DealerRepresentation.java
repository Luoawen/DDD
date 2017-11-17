package cn.m2c.scm.application.dealer.data.representation;

import java.util.Date;

import cn.m2c.scm.application.dealer.data.bean.DealerBean;

public class DealerRepresentation {

	private String dealerName;

	private String dealerId;

	private String userName;

	private String userPhone;

	private String userId;

	private String dealerClassify;

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

	private String sellerName;

	private String sellerPhone;

	private String sellerId;

	private Date createdDate;

	public DealerRepresentation() {
		super();
	}

	public DealerRepresentation(DealerBean model) {
		this.sellerId = model.getSellerId();
		this.dealerId = model.getDealerId();
		this.dealerName = model.getDealerName();
		this.userName = model.getUserName();
		this.userPhone = model.getUserPhone();
		this.userId = model.getUserId();
		this.cooperationMode = model.getCooperationMode();
		this.countMode = model.getCountMode();
		this.dealerProvince = model.getDealerProvince();
		this.dealerCity = model.getDealerCity();
		this.dealerArea = model.getDealerArea();
		this.dealerPcode = model.getDealerPcode();
		this.dealerCcode = model.getDealerCcode();
		this.dealerAcode = model.getDealerAcode();
		this.sellerName = model.getSellerName();
		this.sellerPhone = model.getSellerPhone();
		this.createdDate = model.getCreatedDate();
		this.dealerClassify = model.getDealerClassify();
		if(model.getDealerClassifyBean()!=null){
			this.dealerFristClassifyName = model.getDealerClassifyBean().getDealerFirstClassifyName();
			this.dealerSecondClassifyName = model.getDealerClassifyBean().getDealerSecondClassifyName();
		}
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getSellerId() {
		return sellerId;
	}

	public void setSellerId(String sellerId) {
		this.sellerId = sellerId;
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

}
