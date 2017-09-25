package cn.m2c.scm.domain.model.goods.dealer;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

public class Dealer extends ConcurrencySafeEntity{

	private static final long serialVersionUID = -3654423729243982756L;
	
	
//	private String userGroupId;
//	private String mobile;
//	private String username;
//	private Integer sex;
//	private Integer age;
//	private String areaProvince;
//	private String areaDistrict;
//	private String icon;
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
	private String sellerName;
	private String sellerMobile;
	private Date createdDate;
	private Date lastUpdatedDate;
	private Integer dealerStatus = 1;
	private String userId;
	private String username;
	public String getSellerId() {
		return sellerId;
	}
	public Dealer() {
		super();
	}
	
	
	public String getUserId() {
		return userId;
	}
	public String getSellerName() {
		return sellerName;
	}
	public String getSellerMobile() {
		return sellerMobile;
	}
	public String getDealerId() {
		return dealerId;
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

	public Date getCreatedDate() {
		return createdDate;
	}
	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public String getMobile() {
		return mobile;
	}
	public String getDealerArea() {
		return dealerArea;
	}

	public Integer getDealerStatus() {
		return dealerStatus;
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
	/**
	 * 添加经销商操作
	 * @param dealerId
	 * @param mobile
	 * @param dealerName
	 * @param firstClassification
	 * @param secondClassification
	 * @param cooperationMode
	 * @param dealerProvince
	 * @param dealerCity
	 * @param dealerarea
	 * @param detailAddress
	 * @param dealerMobile
	 */
	public void add(String dealerId, String mobile, String dealerName,
			String firstClassification, String secondClassification,
			Integer cooperationMode, String dealerProvince,
			String dealerCity, String dealerArea, String provinceCode,String cityCode,String areaCode,String detailAddress,
			String dealerMobile,String sellerId,String userId,String username,String sellerMobile,String sellerName) {
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
		this.username= username;
		this.sellerMobile = sellerMobile;
		this.sellerName = sellerName;
	}


	public void update(String dealerId, String mobile, String dealerName,
			String firstClassification, String secondClassification,
			Integer cooperationMode, String dealerProvince,
			String dealerCity, String dealerArea,String provinceCode,String cityCode,String areaCode, String detailAddress,
			String dealerMobile, String sellerId,String userId,String username,String sellerMobile,String sellerName) {
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
		this.username= username;
		this.sellerMobile = sellerMobile;
		this.sellerName = sellerName;
	}
	/**
	 * 删除操作
	 */
	public void del() {
		this.dealerStatus = 2;
	}
	/**
	 * 解绑用户操作
	 */
	public void unbind() {
		this.userId="";
		this.mobile="";
		this.username="";
	}
	/**
	 * 
	 * @param userName2
	 */
	public void updateUserInfo(String userName) {
		this.username = userName;
	}
	
}
