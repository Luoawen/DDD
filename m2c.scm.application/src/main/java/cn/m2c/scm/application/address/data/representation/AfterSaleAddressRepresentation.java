package cn.m2c.scm.application.address.data.representation;

import cn.m2c.scm.application.address.data.bean.AfterSaleAddressBean;

/**
 * 运费模板列表查询
 */
public class AfterSaleAddressRepresentation {
    /**
     * 售后地址编号
     */
    private String addressId;

    /**
     * 经销商id
     */
    private String dealerId;

    /**
     * 省编码
     */
    private String proCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    /**
     * 省名称
     */
    private String proName;

    /**
     * 市名称
     */
    private String cityName;

    /**
     * 区名称
     */
    private String areaName;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 联系人名称
     */
    private String contactName;

    /**
     * 联系电话
     */
    private String contactNumber;

    public AfterSaleAddressRepresentation(AfterSaleAddressBean bean) {
        this.addressId = bean.getAddressId();
        this.dealerId = bean.getDealerId();
        this.proCode = bean.getProCode();
        this.cityCode = bean.getCityCode();
        this.areaCode = bean.getAreaCode();
        this.proName = bean.getProName();
        this.cityName = bean.getCityName();
        this.areaName = bean.getAreaName();
        this.address = bean.getAddress();
        this.contactName = bean.getContactName();
        this.contactNumber = bean.getContactNumber();
    }

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
