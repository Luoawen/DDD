package cn.m2c.scm.application.brand.data.representation;

import cn.m2c.scm.application.brand.data.bean.BrandBean;

/**
 * 品牌
 */
public class BrandChoiceRepresentation {
    /**
     * 品牌id
     */
    private String brandId;

    /**
     * 品牌名称
     */
    private String brandName;


    public BrandChoiceRepresentation(BrandBean brandBean) {
        this.brandId = brandBean.getBrandId();
        this.brandName = brandBean.getBrandName();
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
}
