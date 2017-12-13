package cn.m2c.scm.domain.util;

/**
 * 商家报表统计操作类型
 */
public interface DealerReportType {
    /**
     * 订单已付款，统计订单数和销售金额
     */
    String ORDER_PAY = "ORDER_PAY";

    /**
     * 订单退款（申请仅退款、退货退款且商家已同意的售后单）,统计退款单数和退款金额
     */
    String ORDER_REFUND = "ORDER_REFUND";

    /**
     * 商品新增，统计商品数量
     */
    String GOODS_ADD = "GOODS_ADD";

    /**
     * 商品评论，增加商品评论数量
     */
    String GOODS_COMMENT = "GOODS_COMMENT";
}
