package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

import java.util.List;

/**
 * 商品规格
 */
public class GoodsSku extends IdentifiedValueObject {

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
     * 商品规格
     */
    private List<GoodsSku> goodsSkuList;

    public GoodsSku() {
        super();
    }
}