package cn.m2c.scm.application.goods.query.data.representation.domain;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 */
public class GoodsDetailRepresentation {
    private String goodsId;
    private String goodsName;
    private String mainImageUrl;
    /**
     * 1：仓库中，2：出售中，3：已售罄 4.已删除
     */
    private Integer status;

    /**
     * 规格
     */
    private List<Map> goodsSkus;

    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    private Integer skuFlag;

    public GoodsDetailRepresentation(GoodsBean bean) {
        this.skuFlag = bean.getSkuFlag();
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        List<String> images = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.mainImageUrl = null != images && images.size() > 0 ? images.get(0) : null;
        List<GoodsSkuBean> goodsSkuBeans = bean.getGoodsSkuBeans();
        this.goodsSkus = new ArrayList<>();
        if (null != goodsSkuBeans && goodsSkuBeans.size() > 0) {
            for (GoodsSkuBean skuBean : goodsSkuBeans) {
                Map skuMap = new HashMap<>();
                skuMap.put("skuId", skuBean.getSkuId());
                skuMap.put("skuName", skuBean.getSkuName());
                skuMap.put("availableNum", skuBean.getAvailableNum());
                skuMap.put("showStatus", skuBean.getShowStatus()); // 是否对外展示，1：不展示，2：展示
                this.goodsSkus.add(skuMap);
            }
        }
        this.status = bean.getGoodsStatus();
        if (bean.getDelStatus() == 2) {  //是否删除，1:正常，2：已删除
            this.status = 4;
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

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<Map> getGoodsSkus() {
        return goodsSkus;
    }

    public void setGoodsSkus(List<Map> goodsSkus) {
        this.goodsSkus = goodsSkus;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }
}
