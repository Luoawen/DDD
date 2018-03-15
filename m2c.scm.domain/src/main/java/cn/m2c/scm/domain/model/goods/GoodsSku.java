package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品规格
 */
public class GoodsSku extends ConcurrencySafeEntity {

    /**
     * 商品
     */
    private Goods goods;

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
     * 实际库存
     */
    private Integer realNum;

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
     * 商品销量
     */
    private Integer sellerNum;

    /**
     * 是否对外展示，1：不展示，2：展示
     */
    private Integer showStatus;

    public GoodsSku(Goods goods, String skuId, String skuName, Integer availableNum, Integer realNum, Float weight,
                    Long photographPrice, Long marketPrice, Long supplyPrice, String goodsCode, Integer showStatus) {
        this.goods = goods;
        this.skuId = skuId;
        this.skuName = skuName;
        this.availableNum = availableNum;
        this.realNum = realNum;
        this.weight = weight;
        this.photographPrice = photographPrice;
        this.marketPrice = marketPrice;
        this.supplyPrice = supplyPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
    }

    public GoodsSku() {
        super();
    }

    public GoodsSku getGoodsSKU(String skuId) {
        if (this.skuId.equals(skuId)) {
            return this;
        } else {
            return null;
        }
    }

    public void modifyApprovePrice(Long photographPrice, Long supplyPrice) {
        this.photographPrice = photographPrice;
        this.supplyPrice = supplyPrice;
    }

    public void modifyNotApproveGoodsSku(Integer availableNum, Float weight, Long marketPrice, String goodsCode, Integer showStatus) {
        this.realNum = this.realNum + (availableNum - this.availableNum);
        this.availableNum = availableNum;
        this.weight = weight;
        this.marketPrice = marketPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
    }


    public boolean isModifyNeedApprovePrice(Long photographPrice, Long supplyPrice) {
        return !this.photographPrice.equals(photographPrice) || (null != this.supplyPrice && !this.supplyPrice.equals(supplyPrice));
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

    public Integer availableNum() {
        return availableNum;
    }

    public Goods goods() {
        return goods;
    }

    /**
     * 订单支付完成
     *
     * @param num
     */
    public void orderPayed(Integer num) {
        this.sellerNum = this.sellerNum + num;
        this.realNum = this.realNum - num;
    }

    /**
     * 订单取消
     *
     * @param num
     */
    public void orderCancel(Integer num) {
        this.availableNum = this.availableNum + num;
    }

    /**
     * 退货
     */
    public void orderReturnGoods(Integer num) {
        this.availableNum = this.availableNum + num;
        this.realNum = this.realNum + num;
        this.sellerNum = this.sellerNum - num;
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

    public boolean isModifyPhotographPrice(Long photographPrice) {
        return !this.photographPrice.equals(photographPrice);
    }

    public boolean isModifySupplyPrice(Long supplyPrice) {
        return null != this.supplyPrice && !this.supplyPrice.equals(supplyPrice);
    }

    public boolean isShow() {
        return this.showStatus == 2;
    }

    public void actReturnGoods(Integer num){
        this.availableNum = this.availableNum + num;
        this.realNum = this.realNum + num;
    }
}