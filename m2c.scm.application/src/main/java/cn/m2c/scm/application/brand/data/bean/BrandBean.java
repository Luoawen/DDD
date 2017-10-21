package cn.m2c.scm.application.brand.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.Date;

/**
 * 品牌
 */
public class BrandBean {
    /**
     * 品牌id
     */
    @ColumnAlias(value = "brand_id")
    private String brandId;

    /**
     * 品牌名称
     */
    @ColumnAlias(value = "brand_name")
    private String brandName;

    /**
     * 英文名称
     */
    @ColumnAlias(value = "brand_name_en")
    private String brandNameEn;

    /**
     * 品牌logo
     */
    @ColumnAlias(value = "brand_logo")
    private String brandLogo;

    /**
     * 一级区域编号
     */
    @ColumnAlias(value = "first_area_code")
    private String firstAreaCode;

    /**
     * 二级区域编号
     */
    @ColumnAlias(value = "two_area_code")
    private String twoAreaCode;

    /**
     * 三级区域编号
     */
    @ColumnAlias(value = "three_area_code")
    private String threeAreaCode;

    /**
     * 一级区域名称
     */
    @ColumnAlias(value = "first_area_name")
    private String firstAreaName;

    /**
     * 二级区域名称
     */
    @ColumnAlias(value = "two_area_name")
    private String twoAreaName;

    /**
     * 三级区域名称
     */
    @ColumnAlias(value = "three_area_name")
    private String threeAreaName;

    /**
     * 商户ID
     */
    @ColumnAlias(value = "dealer_id")
    private String dealerId;

    /**
     * 商家名称
     */
    @ColumnAlias(value = "dealer_name")
    private String dealerName;

    /**
     * 0:商家申请，需审批，1:商家管理平台添加，无需审批
     */
    @ColumnAlias(value = "is_sys_add")
    private Integer isSysAdd;

    /**
     * 创建时间
     */
    @ColumnAlias(value = "created_date")
    private Date createDate;

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandNameEn() {
        return brandNameEn;
    }

    public void setBrandNameEn(String brandNameEn) {
        this.brandNameEn = brandNameEn;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        this.brandLogo = brandLogo;
    }

    public String getFirstAreaCode() {
        return firstAreaCode;
    }

    public void setFirstAreaCode(String firstAreaCode) {
        this.firstAreaCode = firstAreaCode;
    }

    public String getTwoAreaCode() {
        return twoAreaCode;
    }

    public void setTwoAreaCode(String twoAreaCode) {
        this.twoAreaCode = twoAreaCode;
    }

    public String getThreeAreaCode() {
        return threeAreaCode;
    }

    public void setThreeAreaCode(String threeAreaCode) {
        this.threeAreaCode = threeAreaCode;
    }

    public String getFirstAreaName() {
        return firstAreaName;
    }

    public void setFirstAreaName(String firstAreaName) {
        this.firstAreaName = firstAreaName;
    }

    public String getTwoAreaName() {
        return twoAreaName;
    }

    public void setTwoAreaName(String twoAreaName) {
        this.twoAreaName = twoAreaName;
    }

    public String getThreeAreaName() {
        return threeAreaName;
    }

    public void setThreeAreaName(String threeAreaName) {
        this.threeAreaName = threeAreaName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public Integer getIsSysAdd() {
        return isSysAdd;
    }

    public void setIsSysAdd(Integer isSysAdd) {
        this.isSysAdd = isSysAdd;
    }
}
