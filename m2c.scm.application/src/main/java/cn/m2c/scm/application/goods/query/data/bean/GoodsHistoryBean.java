package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.Date;

/**
 * 商品记录变更历史（商品库，修改需审核字段，通过审核记录）
 */
public class GoodsHistoryBean {
    /**
     * 记录编号
     */
    @ColumnAlias(value = "history_id")
    private String historyId;
    /**
     * 操作批次编号
     */
    @ColumnAlias(value = "history_no")
    private String historyNo;

    /**
     * 商品id
     */
    @ColumnAlias(value = "goods_id")
    private String goodsId;

    /**
     * 变更类型，1：修改商品分类，2：修改拍获价，3：修改供货价，4：增加sku
     */
    @ColumnAlias(value = "change_type")
    private Integer changeType;

    /**
     * 变更前内容
     */
    @ColumnAlias(value = "before_content")
    private String beforeContent;

    /**
     * 变更后内容
     */
    @ColumnAlias(value = "after_content")
    private String afterContent;

    /**
     * 变更原因
     */
    @ColumnAlias(value = "change_reason")
    private String changeReason;

    /**
     * 创建时间
     */
    @ColumnAlias(value = "create_time")
    private Date createTime;

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }

    public String getHistoryNo() {
        return historyNo;
    }

    public void setHistoryNo(String historyNo) {
        this.historyNo = historyNo;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getChangeType() {
        return changeType;
    }

    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }

    public String getBeforeContent() {
        return beforeContent;
    }

    public void setBeforeContent(String beforeContent) {
        this.beforeContent = beforeContent;
    }

    public String getAfterContent() {
        return afterContent;
    }

    public void setAfterContent(String afterContent) {
        this.afterContent = afterContent;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
