package cn.m2c.scm.domain.model.brand;

/**
 * 品牌审批
 */
public interface BrandApproveRepository {
    /**
     * 根据审批id查询品牌审批信息
     *
     * @param approveId
     * @return
     */
    BrandApprove getBrandApproveByApproveId(String approveId);

    /**
     * 根据品牌id查询品牌审批信息
     *
     * @param brandId
     * @return
     */
    BrandApprove getBrandApproveByBrandId(String brandId);

    /**
     * 保存品牌审批信息
     *
     * @param brandApprove
     */
    void save(BrandApprove brandApprove);

    void remove(BrandApprove brandApprove);
}
