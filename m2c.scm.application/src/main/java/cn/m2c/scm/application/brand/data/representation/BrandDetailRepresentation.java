package cn.m2c.scm.application.brand.data.representation;

import cn.m2c.scm.application.brand.data.bean.BrandBean;

/**
 * 品牌详情
 */
public class BrandDetailRepresentation {
    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;

    /**
     * 一级区域名称
     */
    private String firstAreaName;

    /**
     * 二级区域名称
     */
    private String twoAreaName;

    /**
     * 三级区域名称
     */
    private String threeAreaName;

    /**
     * 英文名称
     */
    private String brandNameEn;

    /**
     * 品牌logo
     */
    private String brandLogo;

    /**
     * 一级区域编号
     */
    private String firstAreaCode;

    /**
     * 二级区域编号
     */
    private String twoAreaCode;

    /**
     * 三级区域编号
     */
    private String threeAreaCode;

    private Integer isSysAdd;


    public BrandDetailRepresentation(BrandBean brandBean) {
        this.brandId = brandBean.getBrandId();
        this.brandName = brandBean.getBrandName();
        this.firstAreaName = brandBean.getFirstAreaName();
        this.twoAreaName = brandBean.getTwoAreaName();
        this.threeAreaName = brandBean.getThreeAreaName();
        this.brandNameEn = brandBean.getBrandNameEn();
        this.brandLogo = brandBean.getBrandLogo();
        this.firstAreaCode = brandBean.getFirstAreaCode();
        this.twoAreaCode = brandBean.getTwoAreaCode();
        this.threeAreaCode = brandBean.getThreeAreaCode();
        this.isSysAdd = brandBean.getIsSysAdd();
    }

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

    public Integer getIsSysAdd() {
        return isSysAdd;
    }

    public void setIsSysAdd(Integer isSysAdd) {
        this.isSysAdd = isSysAdd;
    }
}
