package cn.m2c.scm.application.brand.data.representation;

import cn.m2c.scm.application.brand.data.bean.BrandBean;

import java.text.SimpleDateFormat;

/**
 * 品牌
 */
public class BrandRepresentation {
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
     * 审核通过时间
     */
    private String createTime;

    public BrandRepresentation(BrandBean brandBean) {
        this.brandId = brandBean.getBrandId();
        this.brandName = brandBean.getBrandName();
        this.dealerName = brandBean.getDealerName();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.createTime = format.format(brandBean.getCreateDate());
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
}
