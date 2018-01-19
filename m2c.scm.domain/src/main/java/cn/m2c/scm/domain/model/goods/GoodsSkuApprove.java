package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品规格审核
 */
public class GoodsSkuApprove extends IdentifiedValueObject {

    /**
     * 商品审核主键id
     */
    private GoodsApprove goodsApprove;

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

    /**
     * 市场价
     */
    private Long marketPrice;

    /**
     * 供货价
     */
    private Long supplyPrice;

    /**
     * 商品编码
     */
    private String goodsCode;

    /**
     * 是否对外展示，1：不展示，2：展示
     */
    private Integer showStatus;

    /**
     * 是否删除，1：正常，2：删除
     */
    private Integer delStatus;

    public GoodsSkuApprove() {
        super();
    }

    public GoodsSkuApprove(GoodsApprove goodsApprove, String skuId, String skuName, Integer availableNum,
                           Float weight, Long photographPrice, Long marketPrice, Long supplyPrice, String goodsCode,
                           Integer showStatus) {
        this.goodsApprove = goodsApprove;
        this.skuId = skuId;
        this.skuName = skuName;
        this.availableNum = availableNum;
        this.weight = weight;
        this.photographPrice = photographPrice;
        this.marketPrice = marketPrice;
        this.supplyPrice = supplyPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
    }

    public Map convertToMap() {
        Map map = new HashMap<>();
        map.put("skuId", skuId);
        map.put("skuName", skuName);
        map.put("availableNum", availableNum);
        map.put("weight", weight);
        map.put("photographPrice", photographPrice);
        map.put("marketPrice", marketPrice);
        map.put("supplyPrice", supplyPrice);
        map.put("goodsCode", goodsCode);
        map.put("showStatus", showStatus);
        return map;
    }

    public GoodsSkuApprove getGoodsSKUApprove(String skuId) {
        if (this.skuId.equals(skuId)) {
            return this;
        } else {
            return null;
        }
    }

    public void modifyGoodsSkuApprove(String skuName, Integer availableNum, Float weight, Long photographPrice,
                                      Long marketPrice, Long supplyPrice, String goodsCode, Integer showStatus) {
        this.skuName = skuName;
        this.availableNum = availableNum;
        this.weight = weight;
        this.photographPrice = photographPrice;
        this.marketPrice = marketPrice;
        this.supplyPrice = supplyPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
    }

    public void remove() {
        this.delStatus = 2;
        this.skuId = new StringBuffer("DEL_").append(this.id()).append("_").append(this.skuId).toString();
    }

    public boolean isModifyPhotographPrice(Long photographPrice) {
        return !this.photographPrice.equals(photographPrice);
    }

    public boolean isModifySupplyPrice(Long supplyPrice) {
        return null != this.supplyPrice && !this.supplyPrice.equals(supplyPrice);
    }

    public Map getChangePhotographPrice(Long photographPrice) {
        if (!this.photographPrice.equals(photographPrice)) {
            Map temp = new HashMap<>();
            temp.put("oldPhotographPrice", this.photographPrice);
            temp.put("newPhotographPrice", photographPrice);
            temp.put("skuId", this.skuId);
            temp.put("skuName", this.skuName);
            return temp;
        }
        return null;
    }

    public Map getChangeSupplyPrice(Long supplyPrice) {
        if (null != this.supplyPrice && !this.supplyPrice.equals(supplyPrice)) {
            Map temp = new HashMap<>();
            temp.put("oldSupplyPrice", this.supplyPrice);
            temp.put("newSupplyPrice", supplyPrice);
            temp.put("skuId", this.skuId);
            temp.put("skuName", this.skuName);
            return temp;
        }
        return null;
    }

}