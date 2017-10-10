package cn.m2c.scm.application.brand.command;

/**
 * 同意
 */
public class BrandApproveAgreeCommand {
    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌审批id
     */
    private String brandApproveId;

    public BrandApproveAgreeCommand(String brandId, String brandApproveId) {
        this.brandId = brandId;
        this.brandApproveId = brandApproveId;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getBrandApproveId() {
        return brandApproveId;
    }
}
