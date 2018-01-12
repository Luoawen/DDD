package cn.m2c.scm.domain.model.order;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.ddd.common.domain.model.ValueObject;

/***
 * 收货地址值对象
 *
 * @author fanjc
 *         created date 2017年10月17日
 *         copyrighted@m2c
 */
public class ReceiveAddr extends ValueObject {

    private String province;

    private String provinceCode;

    private String city;

    private String cityCode;

    private String area;

    private String areaCode;

    private String street;
    /**
     * 收货联系人
     */
    private String revPerson;
    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮编
     */
    private String postCode;

    public ReceiveAddr() {
        super();
    }

    public ReceiveAddr(String prov, String provCode, String city,
                       String cityCode, String area, String areaCode,
                       String street, String pName, String phone, String postCode) {
        this.province = prov;
        provinceCode = provCode;
        this.city = city;
        this.cityCode = cityCode;
        this.area = area;
        this.areaCode = areaCode;
        this.street = street;
        revPerson = pName;
        this.phone = phone;

        this.postCode = postCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public boolean isModifyAddress(String province, String provCode, String city, String cityCode, String area, String areaCode,
                                   String street, String revPerson, String phone) {
        return (StringUtils.isNotEmpty(province) && !province.equals(this.province)) ||
                (StringUtils.isNotEmpty(provCode) && !province.equals(this.provinceCode)) ||
                (StringUtils.isNotEmpty(city) && !province.equals(this.city)) ||
                (StringUtils.isNotEmpty(cityCode) && !province.equals(this.cityCode)) ||
                (StringUtils.isNotEmpty(area) && !province.equals(this.area)) ||
                (StringUtils.isNotEmpty(areaCode) && !province.equals(this.areaCode)) ||
                (StringUtils.isNotEmpty(street) && !province.equals(this.street)) ||
                (StringUtils.isNotEmpty(revPerson) && !province.equals(this.revPerson)) ||
                (StringUtils.isNotEmpty(phone) && !province.equals(this.phone));
    }


    /**
     * 修改地址
     *
     * @param province
     * @param city
     * @param area
     * @param street
     */
    public boolean updateAddr(String province, String provCode, String city, String cityCode, String area, String areaCode,
                              String street, String revPerson, String phone, String postCode) {
        if (StringUtils.isEmpty(provCode))
            return false;
        if (StringUtils.isEmpty(province))
            return false;
        if (StringUtils.isEmpty(city))
            return false;
        if (StringUtils.isEmpty(cityCode))
            return false;

        if (StringUtils.isEmpty(street))
            return false;
        if (StringUtils.isEmpty(revPerson) || StringUtils.isEmpty(phone))
            return false;

        this.province = province;
        this.provinceCode = provCode;
        this.city = city;
        this.cityCode = cityCode;
        this.area = area;
        this.areaCode = areaCode;
        this.street = street;
        this.revPerson = revPerson;
        this.phone = phone;
        this.postCode = postCode;

        return true;
    }
}