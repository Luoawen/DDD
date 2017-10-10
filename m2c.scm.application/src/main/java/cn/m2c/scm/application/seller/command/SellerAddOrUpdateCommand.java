package cn.m2c.scm.application.seller.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class SellerAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 8142409278133098063L;
	
	/**
	 * 业务员id
	 */
	private String sellerId;
	
	/**
	 * 业务员名字
	 */
	private String sellerName;
	
	/**
	 * 业务员手机
	 */
	private String sellerPhone;
	
	/**
	 * 业务员性别
	 */
	private Integer sellerSex;
	/**
	 * 业务员编号
	 */
	private String sellerNo;
	/**
	 * 业务员密码
	 */
	private String sellerPass;
	/**
	 * 确认密码
	 */
	private String sellerConfirmPass;
	/**
	 * 业务员所在省
	 */
	private String sellerProvince;
	/**
	 * 业务员所在市
	 */
	private String sellerCity;
	/**
	 * 业务员所在区
	 */
	private String sellerArea;
	/**
	 * 业务员所在省code
	 */
	private String sellerPcode;
	/**
	 * 业务员所在市code
	 */
	private String sellerCcode;
	/**
	 * 业务员所在区code
	 */
	private String sellerAcode;
	/**
	 * 业务员qq
	 */
	private String sellerqq;
	
	/**
	 * 业务员微信
	 */
	private String sellerWechat;
	/**
	 * 业务员备注
	 */
	private String sellerRemark;
	
	public SellerAddOrUpdateCommand() {
		super();
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public Integer getSellerSex() {
		return sellerSex;
	}

	public String getSellerNo() {
		return sellerNo;
	}


	public String getSellerConfirmPass() {
		return sellerConfirmPass;
	}

	public String getSellerProvince() {
		return sellerProvince;
	}

	public String getSellerCity() {
		return sellerCity;
	}

	public String getSellerArea() {
		return sellerArea;
	}

	public String getSellerPcode() {
		return sellerPcode;
	}

	public String getSellerCcode() {
		return sellerCcode;
	}

	public String getSellerAcode() {
		return sellerAcode;
	}

	public String getSellerqq() {
		return sellerqq;
	}

	public String getSellerWechat() {
		return sellerWechat;
	}

	public String getSellerRemark() {
		return sellerRemark;
	}

	public String getSellerPass() {
		return sellerPass;
	}

	public SellerAddOrUpdateCommand(String sellerId, String sellerName,
			String sellerPhone, Integer sellerSex, String sellerNo,
			String sellerPass, String sellerConfirmPass, String sellerProvince,
			String sellerCity, String sellerArea, String sellerPcode,
			String sellerCcode, String sellerAcode, String sellerqq,
			String sellerWechat, String sellerRemark) {
		super();
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerSex = sellerSex;
		this.sellerNo = sellerNo;
		this.sellerPass = sellerPass;
		this.sellerConfirmPass = sellerConfirmPass;
		this.sellerProvince = sellerProvince;
		this.sellerCity = sellerCity;
		this.sellerArea = sellerArea;
		this.sellerPcode = sellerPcode;
		this.sellerCcode = sellerCcode;
		this.sellerAcode = sellerAcode;
		this.sellerqq = sellerqq;
		this.sellerWechat = sellerWechat;
		this.sellerRemark = sellerRemark;
	}


	@Override
	public String toString() {
		return "SellerAddOrUpdateCommand [sellerId=" + sellerId
				+ ", sellerName=" + sellerName + ", sellerPhone=" + sellerPhone
				+ ", sellerSex=" + sellerSex + ", sellerNo=" + sellerNo
				+ ", sellerPass=" + sellerPass + ", sellerConfirmPass="
				+ sellerConfirmPass + ", sellerProvince=" + sellerProvince
				+ ", sellerCity=" + sellerCity + ", sellerArea=" + sellerArea
				+ ", sellerPcode=" + sellerPcode + ", sellerCcode="
				+ sellerCcode + ", sellerAcode=" + sellerAcode + ", sellerqq="
				+ sellerqq + ", sellerWechat=" + sellerWechat
				+ ", sellerRemark=" + sellerRemark + "]";
	}


	
	
}
