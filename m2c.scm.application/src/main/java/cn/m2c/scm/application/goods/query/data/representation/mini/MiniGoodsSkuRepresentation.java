package cn.m2c.scm.application.goods.query.data.representation.mini;

import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.Utils;

/**
 * 微信小程序商品sku 
 */
public class MiniGoodsSkuRepresentation {
	/**规格id*/
    private String skuId;

    /**规格名称*/
    private String skuName;

    /**可用库存*/
    private Integer availableNum;

    /**拍获价*/
    private Long photographPrice;

    private Float weight;

    /**特惠价*/
    //private Long specialPrice;

    /**新增字段，拍获价/10000*/
    private String strPhotographPrice;

    /**新增字段，特惠价/10000*/
    //private String strSpecialPrice;

    /**市场价/10000*/
    private String strMarketPrice;

    public MiniGoodsSkuRepresentation(GoodsSkuBean bean) {
        this.skuId = bean.getSkuId();
        this.skuName = "".equals(bean.getSkuName()) ? "默认" : bean.getSkuName();
        this.availableNum = bean.getAvailableNum();
        this.photographPrice = bean.getPhotographPrice() / 100;
        this.strPhotographPrice = Utils.moneyFormatCN(bean.getPhotographPrice());
        this.weight = bean.getWeight();
        this.strMarketPrice = null != bean.getMarketPrice() ? Utils.moneyFormatCN(bean.getMarketPrice()) : null;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getStrPhotographPrice() {
        return strPhotographPrice;
    }

    public void setStrPhotographPrice(String strPhotographPrice) {
        this.strPhotographPrice = strPhotographPrice;
    }

    public String getStrMarketPrice() {
        return strMarketPrice;
    }

    public void setStrMarketPrice(String strMarketPrice) {
        this.strMarketPrice = strMarketPrice;
    }
    
    /*public Long getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Long specialPrice) {
        this.specialPrice = specialPrice;
    }
     
    public String getStrSpecialPrice() {
        return strSpecialPrice;
    }

    public void setStrSpecialPrice(String strSpecialPrice) {
        this.strSpecialPrice = strSpecialPrice;
    }*/
}