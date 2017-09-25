package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: ReplyCommand
 * @Description: 评论回复命令
 * @author moyj
 * @date 2017年7月12日 下午2:07:52
 *
 */
public class ReplyCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 3206498790356243673L;
	
	private String commentId;		//评论ID
	private String replyContent;	//回复内容
	
	public ReplyCommand(String commentId,String replyContent){
		
		assertArgumentNotEmpty(commentId, "commentId is not be null.");
		assertArgumentLength(commentId,36,"commentId is longer than 36.");
		assertArgumentNotEmpty(commentId, "commentContent is not be null.");
		assertArgumentLength(replyContent,256,"commentContent is longer than 256.");
		
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
