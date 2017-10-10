package cn.m2c.scm.application.brand.command;

/**
 * 同意
 */
public class BrandApproveCommand {
    /**
     * 商户ID
     */
    private String dealerId;

    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌审批id
     */
    private String brandApproveId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    public BrandApproveCommand(String dealerId, String brandId, String brandApproveId) {
        this.dealerId = dealerId;
        this.brandId = brandId;
        this.brandApproveId = brandApproveId;
    }

    public BrandApproveCommand(String brandApproveId, String rejectReason) {
        this.brandApproveId = brandApproveId;
        this.rejectReason = rejectReason;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getBrandApproveId() {
        return brandApproveId;
    }

    public String getRejectReason() {
        return rejectReason;
    }
}
