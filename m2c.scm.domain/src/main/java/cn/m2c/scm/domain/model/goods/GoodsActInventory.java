package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

import java.util.Date;

/**
 * 商品活动库存
 */
public class GoodsActInventory extends ConcurrencySafeEntity {
    /**
     * 编号
     */
    private String inventoryId;
    /**
     * 活动id
     */
    private String activityId;

    /**
     * 活动类型
     */
    private Integer activityType;

    /**
     * 规格id
     */
    private String skuId;

    /**
     * 期望冻结库存数量
     */
    private Integer expectFreezeNum;

    /**
     * 实际冻结库存数量
     */
    private Integer realFreezeNum;

    /**
     * 可用库存数量
     */
    private Integer availableNum;

    /**
     * 活动价格
     */
    private Long price;

    /**
     * 状态,0.冻结状态，1.可用状态，2.活动创建失败
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    public GoodsActInventory() {
        super();
    }

    public GoodsActInventory(String inventoryId, String activityId, Integer activityType, String skuId,
                             Integer expectFreezeNum, Integer realFreezeNum, Integer availableNum, Long price,
                             Integer status, Date createTime) {
        this.inventoryId = inventoryId;
        this.activityId = activityId;
        this.activityType = activityType;
        this.skuId = skuId;
        this.expectFreezeNum = expectFreezeNum;
        this.realFreezeNum = realFreezeNum;
        this.availableNum = availableNum;
        this.price = price;
        this.status = status;
        this.createTime = createTime;
    }
}