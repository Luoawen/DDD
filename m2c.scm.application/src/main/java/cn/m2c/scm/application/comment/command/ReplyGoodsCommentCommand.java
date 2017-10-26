package cn.m2c.scm.application.comment.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 增加商品评论
 */
public class ReplyGoodsCommentCommand extends AssertionConcern implements Serializable {
    private String commentId;
    private String replyContent;

    public ReplyGoodsCommentCommand(String commentId, String replyContent) {
        this.commentId = commentId;
        this.replyContent = replyContent;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getReplyContent() {
        return replyContent;
    }
}
