package cn.m2c.scm.domain.model.comment;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;
import cn.m2c.scm.domain.IDGenerator;

/**
 * 商品回评
 */
public class GoodsReplyComment extends IdentifiedValueObject {
    /**
     *
     */
    private GoodsComment goodsComment;

    /**
     * 回评编号
     */
    private String replyId;

    /**
     * 回复
     */
    private String replyContent;

    public GoodsReplyComment() {
        super();
    }

    public GoodsReplyComment(GoodsComment goodsComment, String replyContent) {
        this.replyId = IDGenerator.get(IDGenerator.SCM_GOODS_REPLY_COMMENT_PREFIX_TITLE);
        this.goodsComment = goodsComment;
        this.replyContent = replyContent;
    }
}