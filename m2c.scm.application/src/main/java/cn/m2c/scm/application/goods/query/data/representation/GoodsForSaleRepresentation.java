package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;

import java.util.List;

/**
 * 在售商品
 */
public class GoodsForSaleRepresentation {
    private String goodsId;
    private String goodsName;
    private String mainImageUrl;

    public GoodsForSaleRepresentation(GoodsBean bean) {
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        List<String> images = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.mainImageUrl = null != images && images.size() > 0 ? images.get(0) : null;
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
}
