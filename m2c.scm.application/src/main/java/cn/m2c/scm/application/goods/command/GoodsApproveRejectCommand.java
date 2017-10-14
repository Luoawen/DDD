package cn.m2c.scm.application.goods.command;

/**
 * 拒绝
 */
public class GoodsApproveRejectCommand {
    /**
     * 品牌id
     */
    private String goodsId;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    public GoodsApproveRejectCommand(String goodsId, String rejectReason) {
        this.goodsId = goodsId;
        this.rejectReason = rejectReason;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getRejectReason() {
        return rejectReason;
    }
}
