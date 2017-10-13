package cn.m2c.scm.application.brand.data.representation;

import cn.m2c.scm.application.brand.data.bean.BrandApproveBean;

/**
 * 品牌详情
 */
public class BrandApproveDetailRepresentation {
    /**
     * 品牌审批id
     */
    private String approveId;

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
     * 品牌审批状态，1：审批中，2：审批不通过
     */
    private Integer approveStatus;

    /**
     * 审批不通过原因
     */
    private String rejectReason;

    public BrandApproveDetailRepresentation(BrandApproveBean bean) {
        this.approveId=bean.getApproveId();
        this.brandId = bean.getBrandId();
        this.brandName = bean.getBrandName();
        this.firstAreaName = bean.getFirstAreaName();
        this.twoAreaName = bean.getTwoAreaName();
        this.threeAreaName = bean.getThreeAreaName();
        this.brandNameEn = bean.getBrandNameEn();
        this.brandLogo = bean.getBrandLogo();
        this.approveStatus=bean.getApproveStatus();
        this.rejectReason=bean.getRejectReason();
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

    public String getApproveId() {
        return approveId;
    }

    public void setApproveId(String approveId) {
        this.approveId = approveId;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
}
