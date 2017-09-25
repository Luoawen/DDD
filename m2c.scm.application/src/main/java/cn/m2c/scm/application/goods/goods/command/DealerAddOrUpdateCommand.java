package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.common.ArgumentUtils;
import cn.m2c.ddd.common.AssertionConcern;

public class DealerAddOrUpdateCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = -3792882791135763870L;
	private String dealerId;
	private String mobile;//用户账号
	private String dealerName;
	private String firstClassification;//一级分类
	private String secondClassification;//二级分类
	private Integer cooperationMode;//合作方式: 1技术型 2平台型 3运营型
	private String dealerProvince;//经销商省信息
	private String dealerCity;//经销商市信息
	private String dealerArea;//经销商区信息
	private String provinceCode;//省code
	private String cityCode;//市code
	private String areaCode;//区code
	private String detailAddress;
	private String dealerMobile;
	private String sellerId;
	private String userId;
	private String username;
	public DealerAddOrUpdateCommand() {
		super();
	}
	
	public DealerAddOrUpdateCommand(String dealerId, String mobile,
			String dealerName, String firstClassification,
			String secondClassification, Integer cooperationMode,
			String dealerProvince, String dealerCity, String dealerArea,
			String provinceCode,String cityCode,String areaCode,
			String detailAddress, String dealerMobile,String sellerId,String userId,String username) {
		super();
		assertArgumentNotNull(dealerId, "经销商不能为空");
		assertArgumentLength(dealerId, 36, "经销商id过长");
		
		
		assertArgumentNotNull(firstClassification, "分类不能为空");
		assertArgumentNotNull(secondClassification, "分类不能为空");
		
		assertArgumentNotNull(cooperationMode, "请选择合作方式");
		
		
		this.dealerId = dealerId;
		this.mobile = mobile;
		this.dealerName = dealerName;
		this.firstClassification = firstClassification;
		this.secondClassification = secondClassification;
		this.cooperationMode = cooperationMode;
		this.dealerProvince = dealerProvince;
		this.dealerCity = dealerCity;
		this.dealerArea = dealerArea;
		this.provinceCode = provinceCode;
		this.cityCode = cityCode;
		this.areaCode = areaCode;
		this.detailAddress = detailAddress;
		this.dealerMobile = dealerMobile;
		this.sellerId = sellerId;
		this.userId = userId;
		this.username = username;
	}


	public String getDealerId() {
		return dealerId;
	}
	public String getMobile() {
		return mobile;
	}
	public String getDealerName() {
		return dealerName;
	}
	public String getFirstClassification() {
		return firstClassification;
	}
	public String getSecondClassification() {
		return secondClassification;
	}
	public Integer getCooperationMode() {
		return cooperationMode;
	}
	public String getDealerProvince() {
		return dealerProvince;
	}
	public String getDealerCity() {
		return dealerCity;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public String getDealerMobile() {
		return dealerMobile;
	}
	public String getDealerArea() {
		return dealerArea;
	}

	public String getSellerId() {
		return sellerId;
	}

	public String getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

}
