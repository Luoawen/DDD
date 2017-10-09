package cn.m2c.scm.domain.model.address;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 售后地址
 */
public class AfterSaleAddress extends ConcurrencySafeEntity {
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

    public AfterSaleAddress() {
        super();
    }

    public AfterSaleAddress(String addressId, String dealerId, String proCode, String cityCode, String areaCode,
                            String proName, String cityName, String areaName, String address, String contactName, String contactNumber) {
        this.addressId = addressId;
        this.dealerId = dealerId;
        this.proCode = proCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
        this.proName = proName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }

    public void modifyAfterSaleAddress(String proCode, String cityCode, String areaCode,
                                       String proName, String cityName, String areaName, String address, String contactName, String contactNumber) {
        this.proCode = proCode;
        this.cityCode = cityCode;
        this.areaCode = areaCode;
        this.proName = proName;
        this.cityName = cityName;
        this.areaName = areaName;
        this.address = address;
        this.contactName = contactName;
        this.contactNumber = contactNumber;
    }
}
