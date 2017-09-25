package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class AppModCommentCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 9095500770814888179L;
	private String commentId;
	private String commentContent;
	private Integer starLevel;
	private Long deadline;
	
	public AppModCommentCommand(String commentId,Integer starLevel,String commentContent ,Long deadline){
		assertArgumentNotEmpty(commentId, "评论ID不能为空");
		if(starLevel != null){
			assertArgumentRange(starLevel,1,5,"星级范围必须是（ 1 2 3 4 5）");	
		}
		this.commentId = commentId;
		this.commentContent = commentContent;
		this.starLevel = starLevel;
		this.deadline = deadline;
		
	}
	public String getCommentContent() {
		return commentContent;
	}

	public Integer getStarLevel() {
		return starLevel;
	}
	public String getCommentId() {
		return commentId;
	}
	public Long getDeadline() {
		return deadline;
	}
	
	
	
	
}
