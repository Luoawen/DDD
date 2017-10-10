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
	@ColumnAlias(value = "seller_acode")
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
	
}
