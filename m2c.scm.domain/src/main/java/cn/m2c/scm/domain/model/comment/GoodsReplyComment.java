package cn.m2c.scm.domain.model.comment;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

import java.math.BigInteger;

/**
 * 商品回评
 */
public class GoodsReplyComment extends IdentifiedValueObject {
    private Integer commentId;

    /**
     * 回评编号
     */
    private String replyId;

    /**
     * 回复
     */
    private String replyContent;

    /**
     * 回复时间
     */
    private BigInteger replyTime;

    public GoodsReplyComment() {
        super();
    }
}