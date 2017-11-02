package cn.m2c.scm.application.dealer.command;

import java.io.Serializable;

import org.springframework.util.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class DealerAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 70567638935920867L;
	private String dealerId;
	private String userId;
	private String userName;
	private String userPhone;
	private String dealerName;
	private String dealerClassify;
	private Integer cooperationMode;
	private String startSignDate;
	private String endSignDate;
	private String dealerProvince;
	private String dealerCity;
	private String dealerArea;
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
	private String sellerName;
	private String sellerPhone;
	
	public DealerAddOrUpdateCommand() {
		super();
	}

	



	public DealerAddOrUpdateCommand(String dealerId, String userId,
			String userName, String userPhone, String dealerName,
			String dealerClassify, Integer cooperationMode,
			String startSignDate, String endSignDate, String dealerProvince,
			String dealerCity, String dealerArea, String dealerPcode,
			String dealerCcode, String dealerAcode, String dealerDetailAddress,
			Integer countMode, Long deposit, Integer isPayDeposit,
			String managerName, String managerPhone, String managerqq,
			String managerWechat, String managerEmail,
			String managerDepartment, String sellerId, String sellerName,
			String sellerPhone) throws NegativeException {
		if (StringUtils.isEmpty(userPhone)) {
			throw new NegativeException(MCode.V_1,"请输入用户电话");
		}
		if (StringUtils.isEmpty(dealerName)) {
			throw new NegativeException(MCode.V_1,"请输入用户名");
		}
		if (!StringUtils.isEmpty(managerPhone) && managerPhone.length() > 11) {
			throw new NegativeException(MCode.V_1,"请输入11位电话号码");
		}
		if(dealerName.length() > 20) {
			throw new NegativeException(MCode.V_1,"商家名称不能超过20个字符");
		}
		if (null == countMode) {
			throw new NegativeException(MCode.V_1,"请选择结算方式");
		}
		this.dealerId = dealerId;
		this.userId = userId;
		this.userName = userName;
		this.userPhone = userPhone;
		this.dealerName = dealerName;
		this.dealerClassify = dealerClassify;
		this.cooperationMode = cooperationMode;
		this.startSignDate = startSignDate;
		this.endSignDate = endSignDate;
		this.dealerProvince = dealerProvince;
		this.dealerCity = dealerCity;
		this.dealerArea = dealerArea;
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
		this.sellerName = sellerName;
		this.sellerPhone = sellerPhone;
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


	public String getDealerArea() {
		return dealerArea;
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
	

	public String getUserName() {
		return userName;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	@Override
	public String toString() {
		return "DealerAddOrUpdateCommand [dealerId=" + dealerId + ", userId="
				+ userId + ", dealerName=" + dealerName + ", dealerClassify="
				+ dealerClassify + ", cooperationMode=" + cooperationMode
				+ ", startSignDate=" + startSignDate + ", endSignDate="
				+ endSignDate + ", dealerProvince=" + dealerProvince
				+ ", dealerCity=" + dealerCity + ", dealerArea=" + dealerArea
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
