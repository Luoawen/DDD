package cn.m2c.scm.application.order.data.bean;

import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerOrderBean {
	@ColumnAlias(value= "order_id")
	private String orderId;
	@ColumnAlias(value= "dealer_order_id")
	private String dealerOrderId;
	@ColumnAlias(value= "dealer_id")
	private String dealerId;
	/**订单状态 0待付款，1待发货，2待收货，3完成，4交易完成，5交易关闭，-1已取消*/
	@ColumnAlias(value= "_status")
	private Integer status;
	@ColumnAlias(value= "province_code")
	private String provinceCode;
	
	@ColumnAlias(value= "province")
	private String province;
	
	@ColumnAlias(value= "city")
	private String city;
	@ColumnAlias(value= "city_code")
	private String cityCode;
	@ColumnAlias(value= "area_county")
	private String areaCounty;
	@ColumnAlias(value= "area_code")
	private String areaCode;
	@ColumnAlias(value= "street_addr")
	private String streetAddr;
	@ColumnAlias(value= "rev_person")
	private String revPerson;
	@ColumnAlias(value= "rev_phone")
	private String revPhone;

	
	/**订单明细*/
	private List<OrderDetailBean> orderDtls;
	
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCityCode() {
		return cityCode;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
	public String getAreaCounty() {
		return areaCounty;
	}
	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
	public String getStreetAddr() {
		return streetAddr;
	}
	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}
	public String getRevPerson() {
		return revPerson;
	}
	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}
	public String getRevPhone() {
		return revPhone;
	}
	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
	}
	public List<OrderDetailBean> getOrderDtls() {
		return orderDtls;
	}
	public void setOrderDtls(List<OrderDetailBean> orderDtls) {
		this.orderDtls = orderDtls;
	}
	
}
