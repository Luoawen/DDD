package cn.m2c.scm.domain.model.brand;

/**
 * 品牌
 */
public interface BrandRepository {
    /**
     * 查询品牌信息
     *
     * @param brandId
     * @return
     */
    public Brand getBrandByBrandId(String brandId);

    /**
     * 查询品牌信息
     *
     * @param brandName
     * @return
     */
    public boolean brandNameIsRepeat(String brandId,String brandName);

    /**
     * 保存品牌信息
     *
     * @param brand
     */
    public void save(Brand brand);
}
