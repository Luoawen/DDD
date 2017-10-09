package cn.m2c.scm.domain.model.brand;

/**
 * 品牌审批
 */
public interface BrandApproveRepository {
    /**
     * 查询品牌审批信息
     *
     * @param approveId
     * @return
     */
    BrandApprove getBrandApproveByApproveId(String approveId);

    /**
     * 保存品牌审批信息
     *
     * @param brandApprove
     */
    void save(BrandApprove brandApprove);

    void remove(BrandApprove brandApprove);
}
