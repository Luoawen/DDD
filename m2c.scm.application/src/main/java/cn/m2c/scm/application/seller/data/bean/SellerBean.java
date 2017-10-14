package cn.m2c.scm.application.seller.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class SellerBean {
	/**
	 * 业务员id
	 */
	@ColumnAlias(value = "seller_id")
	private String sellerId;
	
	/**
	 * 业务员名字
	 */
	@ColumnAlias(value = "seller_name")
	private String sellerName;
	
	/**
	 * 业务员手机
	 */
	@ColumnAlias(value = "seller_phone")
	private String sellerPhone;
	
	@ColumnAlias(value = "seller_no")
	private String sellerNo;
	
	@ColumnAlias(value = "seller_sex")
	private Integer sellerSex;
	/**
	 * 业务员所在省
	 */
	@ColumnAlias(value = "seller_province")
	private String sellerProvince;
	/**
	 * 业务员所在市
	 */
	@ColumnAlias(value = "seller_city")
	private String sellerCity;
	/**
	 * 业务员所在区
	 */
	@ColumnAlias(value = "seller_area")
	private String sellerArea;
	/**
	 * 业务员所在省code
	 */
	@ColumnAlias(value = "seller_pcode")
	private String sellerPcode;
	/**
	 * 业务员所在市code
	 */
	@ColumnAlias(value = "seller_ccode")
	private String sellerCcode;
	/**
	 * 业务员所在区code
	 */
	@ColumnAlias(value = "seller_acode")
	private String sellerAcode;
	
	@ColumnAlias(value = "seller_qq")
	private String sellerqq;
	
	@ColumnAlias(value = "seller_wechat")
	private String sellerWechat;
	
	@ColumnAlias(value = "seller_remark")
	private String sellerRemark;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;

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

	public String getSellerProvince() {
		return sellerProvince;
	}

	public void setSellerProvince(String sellerProvince) {
		this.sellerProvince = sellerProvince;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public void setSellerCity(String sellerCity) {
		this.sellerCity = sellerCity;
	}

	public String getSellerArea() {
		return sellerArea;
	}

	public void setSellerArea(String sellerArea) {
		this.sellerArea = sellerArea;
	}

	public String getSellerPcode() {
		return sellerPcode;
	}

	public void setSellerPcode(String sellerPcode) {
		this.sellerPcode = sellerPcode;
	}

	public String getSellerCcode() {
		return sellerCcode;
	}

	public void setSellerCcode(String sellerCcode) {
		this.sellerCcode = sellerCcode;
	}

	public String getSellerAcode() {
		return sellerAcode;
	}

	public void setSellerAcode(String sellerAcode) {
		this.sellerAcode = sellerAcode;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getSellerNo() {
		return sellerNo;
	}

	public void setSellerNo(String sellerNo) {
		this.sellerNo = sellerNo;
	}


	public Integer getSellerSex() {
		return sellerSex;
	}

	public void setSellerSex(Integer sellerSex) {
		this.sellerSex = sellerSex;
	}

	public String getSellerqq() {
		return sellerqq;
	}

	public void setSellerqq(String sellerqq) {
		this.sellerqq = sellerqq;
	}

	public String getSellerWechat() {
		return sellerWechat;
	}

	public void setSellerWechat(String sellerWechat) {
		this.sellerWechat = sellerWechat;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public void setSellerRemark(String sellerRemark) {
		this.sellerRemark = sellerRemark;
	}
	
	
}
