package cn.m2c.scm.application.goods.query.data.representation.app;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 随机取商品
 */
public class AppGoodsByIdsRepresentation {
    private String goodsId;
    private String goodsName;
    private String goodsImageUrl;
    private Long goodsPrice;
    private List<Map> goodsTags;
    private List<AppGoodsSkuRepresentation> goodsSKUs;

    public AppGoodsByIdsRepresentation(GoodsBean bean, List<Map> goodsTags) {
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        List<String> mainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        if (null != mainImages && mainImages.size() > 0) {
            this.goodsImageUrl = mainImages.get(0);
        }
        List<GoodsSkuBean> goodsSkuBeans = bean.getGoodsSkuBeans();
        if (null != goodsSkuBeans && goodsSkuBeans.size() > 0) {
            this.goodsPrice = goodsSkuBeans.get(0).getPhotographPrice();
            if (null == this.goodsSKUs) {
                this.goodsSKUs = new ArrayList<>();
            }
            for (GoodsSkuBean sukBean : bean.getGoodsSkuBeans()) {
                this.goodsSKUs.add(new AppGoodsSkuRepresentation(sukBean));
            }
        }
        if (null != goodsTags && goodsTags.size() > 0) {
            this.goodsTags = goodsTags;
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

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public Long getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Long goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public List<Map> getGoodsTags() {
        return goodsTags;
    }

    public void setGoodsTags(List<Map> goodsTags) {
        this.goodsTags = goodsTags;
    }

    public List<AppGoodsSkuRepresentation> getGoodsSKUs() {
        return goodsSKUs;
    }

    public void setGoodsSKUs(List<AppGoodsSkuRepresentation> goodsSKUs) {
        this.goodsSKUs = goodsSKUs;
    }
}
