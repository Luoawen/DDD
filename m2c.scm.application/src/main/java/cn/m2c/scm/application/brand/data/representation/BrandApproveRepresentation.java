package cn.m2c.scm.application.brand.data.representation;

import cn.m2c.scm.application.brand.data.bean.BrandApproveBean;

import java.text.SimpleDateFormat;

/**
 * 品牌
 */
public class BrandApproveRepresentation {
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
     * 商家名称
     */
    private String dealerName;

    /**
     * 申请时间
     */
    private String createTime;

    /**
     * 品牌审批状态，1：审批中，2：审批不通过
     */
    private Integer approveStatus;

    public BrandApproveRepresentation(BrandApproveBean bean) {
        this.approveId=bean.getApproveId();
        this.brandId = bean.getBrandId();
        this.brandName = bean.getBrandName();
        this.dealerName = bean.getDealerName();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = format.format(bean.getCreatedDate());
        this.approveStatus=bean.getApproveStatus();
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

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
}
