package cn.m2c.scm.application.brand.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;
import java.util.Date;

/**
 * 品牌审核
 */
public class BrandApproveCommand extends AssertionConcern implements Serializable {
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
     * 商家名称
     */
    private String dealerName;

    /**
     * 0:商家申请，需审批，1:商家管理平台添加，无需审批
     */
    private Integer isSysAdd;

    public BrandApproveCommand(String brandApproveId, String brandId, String brandName, String brandNameEn, String brandLogo, String firstAreaCode,
                               String twoAreaCode, String threeAreaCode, String firstAreaName, String twoAreaName,
                               String threeAreaName, String dealerId, String dealerName) {
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
        this.dealerName=dealerName;
    }

    public BrandApproveCommand(String brandApproveId, String brandName, String brandNameEn, String brandLogo,
                               String firstAreaCode, String twoAreaCode, String threeAreaCode, String firstAreaName,
                               String twoAreaName, String threeAreaName) {
        this.brandApproveId = brandApproveId;
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

    public String getDealerName() {
        return dealerName;
    }
}
