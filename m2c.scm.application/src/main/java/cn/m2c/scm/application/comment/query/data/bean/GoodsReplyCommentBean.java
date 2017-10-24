package cn.m2c.scm.application.comment.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.Date;

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
    @ColumnAlias(value = "created_date")
    private Date createdDate;

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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
