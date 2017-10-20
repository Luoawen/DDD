package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

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

    /**
     * 搜索条件
     */
    private GoodsSearchInfo goodsSearchInfo;

    /**
     * 是否删除，1:正常，2：已删除
     */
    private Integer delStatus;

    public GoodsSku(Goods goods, String skuId, String skuName, Integer availableNum, Integer realNum, Float weight,
                    Long photographPrice, Long marketPrice, Long supplyPrice, String goodsCode, Integer showStatus, GoodsSearchInfo goodsSearchInfo) {
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
        this.goodsSearchInfo=goodsSearchInfo;
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

    public void modifyNotApproveGoodsSku(Integer availableNum, Float weight, Long marketPrice, String goodsCode, Integer showStatus, GoodsSearchInfo goodsSearchInfo) {
        this.availableNum = availableNum;
        this.realNum = this.realNum + (availableNum - this.availableNum);
        this.weight = weight;
        this.marketPrice = marketPrice;
        this.goodsCode = goodsCode;
        this.showStatus = showStatus;
        this.goodsSearchInfo = goodsSearchInfo;
    }


    public boolean isModifyNeedApprovePrice(Long photographPrice, Long supplyPrice) {
        return !this.photographPrice.equals(photographPrice) || !this.supplyPrice.equals(supplyPrice);
    }

    public void remove() {
        this.delStatus = 2;
    }

    /**
     * 上架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void upShelf() {
        this.goodsSearchInfo.upShelf();
    }

    /**
     * 下架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void offShelf() {
        this.goodsSearchInfo.offShelf();
    }

    public Integer availableNum() {
        return availableNum;
    }

    public Goods goods() {
        return goods;
    }

    public void soldOut() {
        this.goodsSearchInfo.soldOut();
    }
}