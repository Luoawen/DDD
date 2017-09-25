package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class SellerAddOrUpdateCommand  extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 4062908245560654020L;
	private String sellerId;
	private String roleId;//角色id
	private String userName;//用户名
	private String accNo;//编号
	private String sellerPhone;//手机号
	private String sellerName;//名字
	private String mail;//邮箱
	private String userPwd;//密码
	private String remarks;
	private Integer sex;
	private Integer age;
	private String province;
	private String city;
	private String area;
	
	
	
	
	public SellerAddOrUpdateCommand() {
		super();
	}
	
	
	public SellerAddOrUpdateCommand(String sellerId, String roleId,
			String userName, String accNo, String sellerPhone,
			String sellerName, String mail, String userPwd, String remarks,
			Integer sex, Integer age, String province, String city, String area) {
		super();
		this.sellerId = sellerId;
		this.roleId = roleId;
		this.userName = userName;
		this.accNo = accNo;
		this.sellerPhone = sellerPhone;
		this.sellerName = sellerName;
		this.mail = mail;
		this.userPwd = userPwd;
		this.remarks = remarks;
		this.sex = sex;
		this.age = age;
		this.province = province;
		this.city = city;
		this.area = area;
	}


	public String getSellerId() {
		return sellerId;
	}
	public String getUserName() {
		return userName;
	}
	public String getAccNo() {
		return accNo;
	}
	public String getSellerPhone() {
		return sellerPhone;
	}
	public String getSellerName() {
		return sellerName;
	}
	public String getMail() {
		return mail;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public String getRemarks() {
		return remarks;
	}
	public Integer getSex() {
		return sex;
	}
	public Integer getAge() {
		return age;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getArea() {
		return area;
	}

	public String getRoleId() {
		return roleId;
	}
	
	
}
