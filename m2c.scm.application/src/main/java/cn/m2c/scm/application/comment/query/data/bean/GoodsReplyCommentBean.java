package cn.m2c.scm.application.comment.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品评论
 */
public class GoodsReplyCommentBean {
    /**
     * 评论编号
     */
    @ColumnAlias(value = "comment_id")
    private String commentId;

    /**
     * 回复
     */
    @ColumnAlias(value = "reply_content")
    private String replyContent;

    /**
     * 回复时间
     */
    @ColumnAlias(value = "reply_time")
    private String replyTime;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyTime() {
        return replyTime;
    }

    public void setReplyTime(String replyTime) {
        this.replyTime = replyTime;
    }
}