package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 筛选结果展示
 */
public class GoodsChoiceRepresentation {
    private String dealerId;
    private String dealerName;
    private String goodsName;
    private String goodsId;
    private Long goodsPrice;
    private String goodsImageUrl;
    private List<Map> goodsSkuList;
    private Integer skuFlag;

    public GoodsChoiceRepresentation(GoodsBean bean) {
        this.dealerId = bean.getDealerId();
        this.dealerName = bean.getDealerName();
        this.goodsName = bean.getGoodsName();
        this.goodsId = bean.getGoodsId();
        if (null != bean.getGoodsSkuBeans() && bean.getGoodsSkuBeans().size() > 0) {
            this.goodsPrice = bean.getGoodsSkuBeans().get(0).getPhotographPrice();
            if (null == this.goodsSkuList) {
                this.goodsSkuList = new ArrayList<>();
            }
            for (GoodsSkuBean skuBean : bean.getGoodsSkuBeans()) {
                Map map = new HashMap<>();
                map.put("goodsSkuId", skuBean.getSkuId());
                map.put("goodsSkuName", skuBean.getSkuName());
                map.put("goodsSkuInventory", skuBean.getAvailableNum());
                map.put("goodsSkuPrice", skuBean.getPhotographPrice());
                this.goodsSkuList.add(map);
            }
        }
        List<String> mainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        if (null != mainImages && mainImages.size() > 0) {
            this.goodsImageUrl = mainImages.get(0);
        }
        this.skuFlag = bean.getSkuFlag();
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

    public List<Map> getGoodsSkuList() {
        return goodsSkuList;
    }

    public void setGoodsSkuList(List<Map> goodsSkuList) {
        this.goodsSkuList = goodsSkuList;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }
}
