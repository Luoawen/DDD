package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

public class DeleteByAdminCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 4832548024576323612L;
	
	private List<String> commentIdList;				//订单编号
	
	public DeleteByAdminCommand(List<String> commentIdList){
		assertArgumentNotEmpty(commentIdList, "commentIdList is not be null.");
		this.commentIdList = commentIdList;
	}
	public List<String> getCommentIdList() {
		return commentIdList;
	}
}
