package cn.m2c.scm.application.address.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 运费模板
 */
public class AfterSaleAddressBean {
    /**
     * 售后地址编号
     */
    @ColumnAlias(value = "address_id")
    private String addressId;

    /**
     * 经销商id
     */
    @ColumnAlias(value = "dealer_id")
    private String dealerId;

    /**
     * 省编码
     */
    @ColumnAlias(value = "pro_code")
    private String proCode;

    /**
     * 市编码
     */
    @ColumnAlias(value = "city_code")
    private String cityCode;

    /**
     * 区编码
     */
    @ColumnAlias(value = "area_code")
    private String areaCode;

    /**
     * 省名称
     */
    @ColumnAlias(value = "pro_name")
    private String proName;

    /**
     * 市名称
     */
    @ColumnAlias(value = "city_name")
    private String cityName;

    /**
     * 区名称
     */
    @ColumnAlias(value = "area_name")
    private String areaName;

    /**
     * 详细地址
     */
    @ColumnAlias(value = "address")
    private String address;

    /**
     * 联系人名称
     */
    @ColumnAlias(value = "contact_name")
    private String contactName;

    /**
     * 联系电话
     */
    @ColumnAlias(value = "contact_number")
    private String contactNumber;

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getProCode() {
        return proCode;
    }

    public void setProCode(String proCode) {
        this.proCode = proCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
