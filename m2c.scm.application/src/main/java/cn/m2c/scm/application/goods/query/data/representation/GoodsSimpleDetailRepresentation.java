package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.utils.Utils;

import java.util.List;

/**
 * 詳情结果展示
 */
public class GoodsSimpleDetailRepresentation {
    private String dealerId;
    private String dealerName;
    private String goodsName;
    private String goodsSubTitle;
    private String goodsId;
    private Long goodsPrice;
    private String strGoodsPrice;
    private String goodsImageUrl;
    private String goodsClassifyId;
    private String goodsClassifyName;


    public GoodsSimpleDetailRepresentation(GoodsBean bean, String classifyName) {
        this.dealerId = bean.getDealerId();
        this.dealerName = bean.getDealerName();
        this.goodsName = bean.getGoodsName();
        this.goodsSubTitle = bean.getGoodsSubTitle();
        this.goodsId = bean.getGoodsId();
        this.goodsClassifyId = bean.getGoodsClassifyId();
        this.goodsClassifyName = classifyName;
        List<String> mainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        if (null != mainImages && mainImages.size() > 0) {
            this.goodsImageUrl = mainImages.get(0);
        }
        if (null != bean.getGoodsSkuBeans() && bean.getGoodsSkuBeans().size() > 0) {
            this.goodsPrice = bean.getGoodsSkuBeans().get(0).getPhotographPrice();
            this.strGoodsPrice = Utils.moneyFormatCN(this.goodsPrice);
        }
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

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public String getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(String goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getGoodsSubTitle() {
        return goodsSubTitle;
    }

    public void setGoodsSubTitle(String goodsSubTitle) {
        this.goodsSubTitle = goodsSubTitle;
    }

    public String getGoodsClassifyName() {
        return goodsClassifyName;
    }

    public void setGoodsClassifyName(String goodsClassifyName) {
        this.goodsClassifyName = goodsClassifyName;
    }

    public String getStrGoodsPrice() {
        return strGoodsPrice;
    }

    public void setStrGoodsPrice(String strGoodsPrice) {
        this.strGoodsPrice = strGoodsPrice;
    }
}
