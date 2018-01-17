package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.Entity;

import java.util.Date;

/**
 * 商品审核记录变更历史(商品审核库，修改需审核字段记录)
 */
public class GoodsApproveHistory extends Entity {
    /**
     * 记录编号
     */
    private String historyId;
    /**
     * 操作批次编号
     */
    private String historyNo;

    /**
     * 商品审核id
     */
    private Integer approveId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 变更类型，1：修改商品分类，2：修改拍获价，3：修改供货价，4：增加sku
     */
    private Integer changeType;

    /**
     * 变更前内容
     */
    private String beforeContent;

    /**
     * 变更后内容
     */
    private String afterContent;

    /**
     * 创建时间
     */
    private Date createTime;

    public GoodsApproveHistory(String historyId, String historyNo, Integer approveId, String goodsId,
                               Integer changeType, String beforeContent,
                               String afterContent, Date createTime) {
        this.historyId = historyId;
        this.historyNo = historyNo;
        this.approveId = approveId;
        this.goodsId = goodsId;
        this.changeType = changeType;
        this.beforeContent = beforeContent;
        this.afterContent = afterContent;
        this.createTime = createTime;
    }
}
