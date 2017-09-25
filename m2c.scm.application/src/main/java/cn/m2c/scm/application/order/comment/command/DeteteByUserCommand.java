package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class DeteteByUserCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = -1155891750747670747L;
	
	private String commentId;

	public DeteteByUserCommand(String commentId){
		assertArgumentNotEmpty(commentId, "commentId is not be null.");
		this.commentId = commentId;
	}
	
	public String getCommentId() {
		return commentId;
	}
	
	

}
