package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.shop.data.bean.ShopBean;

import java.util.List;
import java.util.Map;

/**
 * 商品sku
 */
public class GoodsSkuInfoRepresentation {
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商家ID
     */
    private String dealerId;

    /**
     * 商家名称
     */
    private String dealerName;

    /**
     * 店铺名称
     */
    private String shopId;

    /**
     * 店铺名称
     */
    private String shopName;

    /**
     * 商品分类id
     */
    private String goodsClassifyId;
    /**
     * 运费模板id
     */
    private String goodsPostageId;
    /**
     * 商品状态，1：仓库中，2：出售中，3：已售罄; 4:删除
     */
    private Integer goodsStatus;

    /**
     * sku状态是否可用0：不可用，1：可用
     */
    private Integer skuAvailableStatus;

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
     * 重量
     */
    private Float weight;

    /**
     * 拍获价
     */
    private Long photographPrice;

    private String goodsImageUrl;

    /**
     * 最小起订量
     */
    private Integer goodsMinQuantity;

    /**
     * 特惠价
     */
    private Long specialPrice;

    public GoodsSkuInfoRepresentation(GoodsBean goodsBean, GoodsSkuBean bean, ShopBean shopBean, Map specialMap) {
        this.goodsId = goodsBean.getGoodsId();
        this.goodsName = goodsBean.getGoodsName();
        this.dealerId = goodsBean.getDealerId();
        this.dealerName = goodsBean.getDealerName();
        this.goodsClassifyId = goodsBean.getGoodsClassifyId();
        this.goodsPostageId = goodsBean.getGoodsPostageId();
        this.goodsStatus = goodsBean.getGoodsStatus();
        if (goodsBean.getDelStatus() == 2) {
            this.goodsStatus = 4;
        }
        this.skuId = bean.getSkuId();
        this.skuName = bean.getSkuName();
        this.availableNum = bean.getAvailableNum();
        this.weight = bean.getWeight();
        this.photographPrice = bean.getPhotographPrice();
        List<String> mainImages = JsonUtils.toList(goodsBean.getGoodsMainImages(), String.class);
        if (null != mainImages && mainImages.size() > 0) {
            this.goodsImageUrl = mainImages.get(0);
        }

        // 是否对外展示，1：不展示，2：展示
        if (this.goodsStatus == 2 && this.availableNum > 0 && bean.getShowStatus() == 2) {
            this.skuAvailableStatus = 1;
        } else {
            this.skuAvailableStatus = 0;
        }
        this.goodsMinQuantity = goodsBean.getGoodsMinQuantity();
        this.shopId = null != shopBean ? shopBean.getShopId() : "";
        this.shopName = null != shopBean ? shopBean.getShopName() : "";
        this.specialPrice = null != specialMap && specialMap.containsKey(this.getSkuId()) ? Long.parseLong(String.valueOf(specialMap.get(this.getSkuId()))) : null;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(String goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getGoodsPostageId() {
        return goodsPostageId;
    }

    public void setGoodsPostageId(String goodsPostageId) {
        this.goodsPostageId = goodsPostageId;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
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

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Long getPhotographPrice() {
        return photographPrice;
    }

    public void setPhotographPrice(Long photographPrice) {
        this.photographPrice = photographPrice;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public Integer getSkuAvailableStatus() {
        return skuAvailableStatus;
    }

    public void setSkuAvailableStatus(Integer skuAvailableStatus) {
        this.skuAvailableStatus = skuAvailableStatus;
    }

    public Integer getGoodsMinQuantity() {
        return goodsMinQuantity;
    }

    public void setGoodsMinQuantity(Integer goodsMinQuantity) {
        this.goodsMinQuantity = goodsMinQuantity;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Long getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(Long specialPrice) {
        this.specialPrice = specialPrice;
    }
}
