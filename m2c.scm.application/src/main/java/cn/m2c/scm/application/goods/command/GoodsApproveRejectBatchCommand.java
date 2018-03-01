package cn.m2c.scm.application.goods.command;

import java.util.List;

/**
 * 批量拒绝
 */
public class GoodsApproveRejectBatchCommand {
	/**商品id*/
    private List goodsIds;

    /**拒绝原因*/
    private String rejectReason;

    public GoodsApproveRejectBatchCommand(List goodsIds, String rejectReason) {
        this.goodsIds = goodsIds;
        this.rejectReason = rejectReason;
    }

    public List getGoodsIds() {
        return goodsIds;
    }

    public String getRejectReason() {
        return rejectReason;
    }
}
