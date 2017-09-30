package cn.m2c.scm.application.dealer.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class DealerAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 70567638935920867L;
	private String dealerId;
	private String userId;
	private String dealerName;
	private String dealerClassify;
	private Integer cooperationMode;
	private String startSignDate;
	private String endSignDate;
	private String dealerProvince;
	private String dealerCity;
	private String dealerarea;
	private String dealerPcode;
	private String dealerCcode;
	private String dealerAcode;
	private String dealerDetailAddress;
	private Integer countMode;
	private Long deposit;
	private Integer isPayDeposit;
	private String managerName;
	private String managerPhone;
	private String managerqq;
	private String managerWechat;
	private String managerEmail;
	private String managerDepartment;
	private String sellerId;
	
	public DealerAddOrUpdateCommand() {
		super();
	}

	
	public DealerAddOrUpdateCommand(String dealerId, String userId,
			String dealerName, String dealerClassify, Integer cooperationMode,
			String startSignDate, String endSignDate, String dealerProvince,
			String dealerCity, String dealerarea, String dealerPcode,
			String dealerCcode, String dealerAcode, String dealerDetailAddress,
			Integer countMode, Long deposit, Integer isPayDeposit,
			String managerName, String managerPhone, String managerqq,
			String managerWechat, String managerEmail,
			String managerDepartment, String sellerId) {
		super();
		this.dealerId = dealerId;
		this.userId = userId;
		this.dealerName = dealerName;
		this.dealerClassify = dealerClassify;
		this.cooperationMode = cooperationMode;
		this.startSignDate = startSignDate;
		this.endSignDate = endSignDate;
		this.dealerProvince = dealerProvince;
		this.dealerCity = dealerCity;
		this.dealerarea = dealerarea;
		this.dealerPcode = dealerPcode;
		this.dealerCcode = dealerCcode;
		this.dealerAcode = dealerAcode;
		this.dealerDetailAddress = dealerDetailAddress;
		this.countMode = countMode;
		this.deposit = deposit;
		this.isPayDeposit = isPayDeposit;
		this.managerName = managerName;
		this.managerPhone = managerPhone;
		this.managerqq = managerqq;
		this.managerWechat = managerWechat;
		this.managerEmail = managerEmail;
		this.managerDepartment = managerDepartment;
		this.sellerId = sellerId;
	}


	public String getDealerId() {
		return dealerId;
	}

	public String getUserId() {
		return userId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public String getDealerClassify() {
		return dealerClassify;
	}

	public Integer getCooperationMode() {
		return cooperationMode;
	}

	public String getStartSignDate() {
		return startSignDate;
	}

	public String getEndSignDate() {
		return endSignDate;
	}

	public String getDealerProvince() {
		return dealerProvince;
	}

	public String getDealerCity() {
		return dealerCity;
	}

	public String getDealerarea() {
		return dealerarea;
	}

	public String getDealerPcode() {
		return dealerPcode;
	}

	public String getDealerCcode() {
		return dealerCcode;
	}

	public String getDealerAcode() {
		return dealerAcode;
	}

	public String getDealerDetailAddress() {
		return dealerDetailAddress;
	}

	public Integer getCountMode() {
		return countMode;
	}

	public Long getDeposit() {
		return deposit;
	}

	public Integer getIsPayDeposit() {
		return isPayDeposit;
	}

	public String getManagerName() {
		return managerName;
	}

	public String getManagerPhone() {
		return managerPhone;
	}

	public String getManagerqq() {
		return managerqq;
	}

	public String getManagerWechat() {
		return managerWechat;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public String getManagerDepartment() {
		return managerDepartment;
	}

	public String getSellerId() {
		return sellerId;
	}

	@Override
	public String toString() {
		return "DealerAddOrUpdateCommand [dealerId=" + dealerId + ", userId="
				+ userId + ", dealerName=" + dealerName + ", dealerClassify="
				+ dealerClassify + ", cooperationMode=" + cooperationMode
				+ ", startSignDate=" + startSignDate + ", endSignDate="
				+ endSignDate + ", dealerProvince=" + dealerProvince
				+ ", dealerCity=" + dealerCity + ", dealerarea=" + dealerarea
				+ ", dealerPcode=" + dealerPcode + ", dealerCcode="
				+ dealerCcode + ", dealerAcode=" + dealerAcode
				+ ", dealerDetailAddress=" + dealerDetailAddress
				+ ", countMode=" + countMode + ", deposit=" + deposit
				+ ", isPayDeposit=" + isPayDeposit + ", managerName="
				+ managerName + ", managerPhone=" + managerPhone
				+ ", managerqq=" + managerqq + ", managerWechat="
				+ managerWechat + ", managerEmail=" + managerEmail
				+ ", managerDepartment=" + managerDepartment + ", sellerId="
				+ sellerId + "]";
	}
}
