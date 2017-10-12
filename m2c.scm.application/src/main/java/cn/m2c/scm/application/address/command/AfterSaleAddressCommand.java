package cn.m2c.scm.application.address.command;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 运费模板
 */
public class AfterSaleAddressCommand extends AssertionConcern implements Serializable {
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

    public AfterSaleAddressCommand(String addressId, String dealerId, String proCode, String cityCode, String areaCode,
                                   String proName, String cityName, String areaName, String address, String contactName, String contactNumber) throws NegativeException {

        if (StringUtils.isEmpty(addressId)) {
            throw new NegativeException(MCode.V_1, "售后地址ID为空");
        }
        if (StringUtils.isEmpty(dealerId)) {
            throw new NegativeException(MCode.V_1, "商家ID为空");
        }
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

    public String getAddressId() {
        return addressId;
    }

    public String getDealerId() {
        return dealerId;
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

    public String getProName() {
        return proName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getAreaName() {
        return areaName;
    }

    public String getAddress() {
        return address;
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactNumber() {
        return contactNumber;
    }
}
