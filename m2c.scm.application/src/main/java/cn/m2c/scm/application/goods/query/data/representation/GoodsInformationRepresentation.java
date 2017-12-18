package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsRecognizedBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品信息
 * 商品ID,商品名，商家ID,商家名,识别图ID,识别图URL
 */
public class GoodsInformationRepresentation {
    /**
     * 商品ID
     */
    private String goodsId;
    /**
     * 商品名
     */
    private String goodsName;
    /**
     * 商家Id
     */
    private String dealerId;
    /**
     * 商家名
     */
    private String dealerName;

    /**
     * 商品识别图
     */
    List<Map> goodsRecognized;

    /**
     * 商品投放状态：0：未投放，1：投放
     */
    private Integer goodsLaunchStatus;
    /**
     * 商品品牌id
     */
    private String goodsBrandId;
    /**
     * 商品品牌名称
     */
    private String goodsBrandName;
    /**
     * 商品状态，1：仓库中，2：出售中，3：已售罄
     */
    private Integer goodsStatus;
    /**
     * 是否删除，1:正常，2：已删除
     */
    private Integer delStatus;

    public GoodsInformationRepresentation(GoodsBean bean) {
        this.goodsId = bean.getGoodsId();
        this.goodsName = bean.getGoodsName();
        this.dealerId = bean.getDealerId();
        this.dealerName = bean.getDealerName();
        this.goodsLaunchStatus = bean.getGoodsLaunchStatus();
        this.goodsBrandId = bean.getGoodsBrandId();
        this.goodsBrandName = bean.getGoodsBrandName();
        this.goodsStatus = bean.getGoodsStatus();
        this.delStatus = bean.getDelStatus();
        List<GoodsRecognizedBean> goodsRecognizedBeans = bean.getGoodsRecognizedBeans();
        if (null != goodsRecognizedBeans && goodsRecognizedBeans.size() > 0) {
            this.goodsRecognized = new ArrayList<>();
            for (GoodsRecognizedBean goodsRecognizedBean : goodsRecognizedBeans) {
                Map map = new HashMap<>();
                map.put("recognizedId", goodsRecognizedBean.getRecognizedId());
                map.put("recognizedUrl", goodsRecognizedBean.getRecognizedUrl());
                map.put("recognizedNo", goodsRecognizedBean.getRecognizedNo());
                this.goodsRecognized.add(map);
            }
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

    public List<Map> getGoodsRecognized() {
        return goodsRecognized;
    }

    public void setGoodsRecognized(List<Map> goodsRecognized) {
        this.goodsRecognized = goodsRecognized;
    }

    public Integer getGoodsLaunchStatus() {
        return goodsLaunchStatus;
    }

    public void setGoodsLaunchStatus(Integer goodsLaunchStatus) {
        this.goodsLaunchStatus = goodsLaunchStatus;
    }

    public String getGoodsBrandId() {
        return goodsBrandId;
    }

    public void setGoodsBrandId(String goodsBrandId) {
        this.goodsBrandId = goodsBrandId;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

}
