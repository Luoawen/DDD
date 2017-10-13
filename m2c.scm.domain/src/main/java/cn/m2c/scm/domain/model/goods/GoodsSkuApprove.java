package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

/**
 * 商品规格审核
 */
public class GoodsSkuApprove extends IdentifiedValueObject {

    /**
     * 商品审核主键id
     */
    private GoodsApprove goodsApprove;

    /**
     * 规格审核id
     */
    private String skuApproveId;

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

    public GoodsSkuApprove() {
        super();
    }
}