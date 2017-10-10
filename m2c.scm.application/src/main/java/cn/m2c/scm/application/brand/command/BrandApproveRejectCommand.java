package cn.m2c.scm.application.brand.command;

/**
 * 拒绝
 */
public class BrandApproveRejectCommand {
    /**
     * 品牌审批id
     */
    private String brandApproveId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    public BrandApproveRejectCommand(String brandApproveId, String rejectReason) {
        this.brandApproveId = brandApproveId;
        this.rejectReason = rejectReason;
    }

    public String getBrandApproveId() {
        return brandApproveId;
    }

    public String getRejectReason() {
        return rejectReason;
    }
}
