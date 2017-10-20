package cn.m2c.scm.application.goods.query.data.representation.app;

import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

/**
 * sku
 */
public class AppGoodsSkuRepresentation {
    /**
     * 规格id
     */
    private String skuId;

    /**
     * 规格名称
     */
    private String skuName;

    /**
     * 可用库存
     */
    private Integer availableNum;

    /**
     * 拍获价
     */
    private Long photographPrice;

    public AppGoodsSkuRepresentation(GoodsSkuBean bean) {
        this.skuId = bean.getSkuId();
        this.skuName = bean.getSkuName();
        this.availableNum = bean.getAvailableNum();
        this.photographPrice = bean.getPhotographPrice();
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getAvailableNum() {
        return availableNum;
    }

    public void setAvailableNum(Integer availableNum) {
        this.availableNum = availableNum;
    }

    public Long getPhotographPrice() {
        return photographPrice;
    }

    public void setPhotographPrice(Long photographPrice) {
        this.photographPrice = photographPrice;
    }
}
