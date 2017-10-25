package cn.m2c.scm.application.order.command;

public class UpdateAddrCommand {
	/**
	 * 商家订单号
	 */
	private String dealerOrderId;
	/**
	 * 省
	 */
	private String province;
	/**
	 * 省编码
	 */
	private String provCode;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 市编码
	 */
	private String cityCode;
	/**
	 * 地区
	 */
	private String area;
	/**
	 * 地区编码
	 */
	private String areaCode;
	/**
	 * 街道详细地址
	 */
	private String street;
	/** 收货联系人 */
	private String revPerson;
	/** 联系电话 */
	private String phone;

	public UpdateAddrCommand(String dealerOrderId, String province, String provCode, String city, String cityCode,
			String area, String areaCode, String street,String revPerson,String phone) {
		super();
		this.dealerOrderId = dealerOrderId;
		this.province = province;
		this.provCode = provCode;
		this.city = city;
		this.cityCode = cityCode;
		this.area = area;
		this.areaCode = areaCode;
		this.street = street;
		this.revPerson = revPerson;
		this.phone = phone;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getProvince() {
		return province;
	}

	public String getProvCode() {
		return provCode;
	}

	public String getCity() {
		return city;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getArea() {
		return area;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public String getStreet() {
		return street;
	}

	public String getRevPerson() {
		return revPerson;
	}

	public String getPhone() {
		return phone;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setProvCode(String provCode) {
		this.provCode = provCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
