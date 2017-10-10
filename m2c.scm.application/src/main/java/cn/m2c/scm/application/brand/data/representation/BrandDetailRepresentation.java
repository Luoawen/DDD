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


    public BrandDetailRepresentation(BrandBean brandBean) {
        this.brandId = brandBean.getBrandId();
        this.brandName = brandBean.getBrandName();
        this.firstAreaName = brandBean.getFirstAreaName();
        this.twoAreaName = brandBean.getTwoAreaName();
        this.threeAreaName = brandBean.getThreeAreaName();
        this.brandNameEn = brandBean.getBrandNameEn();
        this.brandLogo = brandBean.getBrandLogo();
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
}
