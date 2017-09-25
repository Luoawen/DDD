package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class StaffAddOrUpdateCmd extends AssertionConcern implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6913449081945936297L;
	private String userId;
	private String accNo;
	private String staffPhone;
	private String staffName;
	private String mail;
	private Integer sex;
	private Integer age;
	private String proCode;
	private String cityCode;
	private String areaCode;
	
	private String proName;
	private String cityName;
	private String areaName;
	private Long regisDate;
	
	public StaffAddOrUpdateCmd(String userId, String accNo, String staffPhone, String staffName,
							   String mail, Integer sex, Integer age, String proCode, String cityCode, String areaCode, String proName, String cityName, String areaName, Long regisDate) {
		super();
		
		
		this.userId = userId;
		this.accNo = accNo;
		this.staffPhone = staffPhone;
		this.staffName = staffName;
		this.mail = mail;
		this.sex = sex;
		this.age = age;
		this.proCode = proCode;
		this.cityCode = cityCode;
		this.areaCode = areaCode;
		this.proName = proName;
		this.cityName = cityName;
		this.areaName = areaName;
		this.regisDate = regisDate;
	}

	public StaffAddOrUpdateCmd(String userId, String accNo, String staffPhone, String staffName, String mail, Integer sex,
							   Integer age, String proCode, String cityCode, String areaCode, String proName, String cityName, String areaName) {
		this.userId = userId;
		this.accNo = accNo;
		this.staffPhone = staffPhone;
		this.staffName = staffName;
		this.mail = mail;
		this.sex = sex;
		this.age = age;
		this.proCode = proCode;
		this.cityCode = cityCode;
		this.areaCode = areaCode;
		this.proName = proName;
		this.cityName = cityName;
		this.areaName = areaName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAccNo() {
		return accNo;
	}

	public String getUserId() {
		return userId;
	}

	public Integer getSex() {
		return sex;
	}

	public Integer getAge() {
		return age;
	}

	public String getStaffPhone() {
		return staffPhone;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getMail() {
		return mail;
	}

	
	public String getProCode() {
		return proCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public  Long getRegisDate() {
		return regisDate;
	}

	public String getProName() {
		return proName;
	}

	public String getCityName() {
		return cityName;
	}

	public String getAreaName() {
		return areaName;
	}

	@Override
	public String toString() {
		return "StaffAddOrUpdateCmd{" +
				"userId='" + userId + '\'' +
				", accNo='" + accNo + '\'' +
				", staffPhone='" + staffPhone + '\'' +
				", staffName='" + staffName + '\'' +
				", mail='" + mail + '\'' +
				", sex=" + sex +
				", age=" + age +
				", proCode='" + proCode + '\'' +
				", cityCode='" + cityCode + '\'' +
				", areaCode='" + areaCode + '\'' +
				", proName='" + proName + '\'' +
				", cityName='" + cityName + '\'' +
				", areaName='" + areaName + '\'' +
				", regisDate=" + regisDate +
				'}';
	}
}
