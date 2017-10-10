package cn.m2c.scm.domain.model.seller;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class Seller extends ConcurrencySafeEntity{

	private static final long serialVersionUID = -8009932911222584529L;
	
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
	
	
	private Date createdDate;
	private Date lastUpdatedDate;
	
	private Integer sellerStatus=1;
	
	public Seller() {
		super();
	}

	public void add(String sellerId, String sellerName, String sellerPhone,
			Integer sellerSex, String sellerNo, String sellerConfirmPass,
			String sellerProvince, String sellerCity, String sellerArea, String sellerPcode, String sellerCcode,
			String sellerAcode, String sellerqq, String sellerWechat,
			String sellerRemark) {
		this.sellerId = sellerId;
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerSex = sellerSex;
		this.sellerNo = sellerNo;
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

	public void update(String sellerName, String sellerPhone,
			Integer sellerSex, String sellerNo, String sellerConfirmPass,
			String sellerProvince, String sellerCity, String sellerArea,
			String sellerPcode, String sellerCcode, String sellerAcode,
			String sellerqq, String sellerWechat, String sellerRemark) {
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
		this.sellerSex = sellerSex;
		this.sellerNo = sellerNo;
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
	
	

}
