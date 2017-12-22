package cn.m2c.scm.application.goods.query.data.representation.app;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * app商品搜索
 */
public class AppGoodsSearchRepresentation {
    private String goodsId;
    private String goodsName;
    private String goodsImageUrl;
    private Long goodsPrice;
    private List<Map> goodsTags;
    private List<Map> goodsClassifies;
    /**
     * 新增字段，价格/10000
     */
    private String strGoodsPrice;

    public AppGoodsSearchRepresentation(GoodsBean bean, List<Map> goodsTags) {
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        List<String> mainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        if (null != mainImages && mainImages.size() > 0) {
            this.goodsImageUrl = mainImages.get(0);
        }
        List<GoodsSkuBean> goodsSkuBeans = bean.getGoodsSkuBeans();
        if (null != goodsSkuBeans && goodsSkuBeans.size() > 0) {
            this.goodsPrice = goodsSkuBeans.get(0).getPhotographPrice()/100;
            this.strGoodsPrice = Utils.moneyFormatCN(goodsSkuBeans.get(0).getPhotographPrice());
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
}
