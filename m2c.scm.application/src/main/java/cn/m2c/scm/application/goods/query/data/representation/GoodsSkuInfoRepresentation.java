package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

import java.util.List;

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

    public GoodsSkuInfoRepresentation(GoodsBean goodsBean, GoodsSkuBean bean) {
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
}
