package cn.m2c.scm.application.goods.query.data.representation.domain;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;

import java.util.List;

/**
 * 商品详情
 */
public class GoodsDetailRepresentation {
    private String goodsId;
    private String goodsName;
    private String mainImageUrl;
    private Integer stockNum;
    /**
     * 1：仓库中，2：出售中，3：已售罄 4.已删除
     */
    private Integer status;

    public GoodsDetailRepresentation(GoodsBean bean) {
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        List<String> images = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.mainImageUrl = null != images && images.size() > 0 ? images.get(0) : null;
        List<GoodsSkuBean> goodsSkuBeans = bean.getGoodsSkuBeans();
        if (null != goodsSkuBeans && goodsSkuBeans.size() > 0) {
            Integer stockNum = 0;
            for (GoodsSkuBean skuBean : goodsSkuBeans) {
                stockNum = stockNum + skuBean.getAvailableNum();
            }
            this.stockNum = stockNum;
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

    public Integer getStockNum() {
        return stockNum;
    }

    public void setStockNum(Integer stockNum) {
        this.stockNum = stockNum;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
