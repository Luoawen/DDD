package cn.m2c.scm.application.brand.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌
 */
public class BrandCommand extends AssertionConcern implements Serializable {
    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌审批id
     */
    private String brandApproveId;

    /**
     * 品牌名称
     */
    private String brandName;

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
     * 申请时间
     */
    private Date applyDate;

    /**
     * 商户ID
     */
    private String dealerId;

    /**
     * 0:商家申请，需审批，1:商家管理平台添加，无需审批
     */
    private Integer isSysAdd;

    public BrandCommand(String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                        String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                        String threeAreaName, Date applyDate, String dealerId, Integer isSysAdd) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
        this.applyDate = applyDate;
        this.dealerId = dealerId;
        this.isSysAdd = isSysAdd;
    }

    public BrandCommand(String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                        String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                        String threeAreaName) {
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
    }


    public BrandCommand(String brandApproveId, String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                        String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                        String threeAreaName, String dealerId) {
        this.brandApproveId = brandApproveId;
        this.brandId = brandId;
        this.brandName = brandName;
        this.brandNameEn = brandNameEn;
        this.brandLogo = brandLogo;
        this.firstAreaCode = firstAreaCode;
        this.twoAreaCode = twoAreaCode;
        this.threeAreaCode = threeAreaCode;
        this.firstAreaName = firstAreaName;
        this.twoAreaName = twoAreaName;
        this.threeAreaName = threeAreaName;
        this.dealerId = dealerId;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBrandNameEn() {
        return brandNameEn;
    }

    public String getBrandLogo() {
        return brandLogo;
    }

    public String getFirstAreaCode() {
        return firstAreaCode;
    }

    public String getTwoAreaCode() {
        return twoAreaCode;
    }

    public String getThreeAreaCode() {
        return threeAreaCode;
    }

    public String getFirstAreaName() {
        return firstAreaName;
    }

    public String getTwoAreaName() {
        return twoAreaName;
    }

    public String getThreeAreaName() {
        return threeAreaName;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getBrandApproveId() {
        return brandApproveId;
    }

    public Integer getIsSysAdd() {
        return isSysAdd;
    }
}
