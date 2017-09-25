package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: TopCommand
 * @Description: 评论批量置顶命令
 * @author moyj
 * @date 2017年7月12日 下午2:08:05
 *
 */
public class TopCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = -1090685198294877480L;
	
	private List<String> commentIdList;				//订单编号
	private Integer topFlag;						//置顶标志 1置顶 2取消置顶
	
	public TopCommand(List<String> commentIdList,Integer topFlag){
		assertArgumentNotEmpty(commentIdList, "orderId is not be null.");
		assertArgumentNotNull(topFlag, "topFlag is not be null.");
		assertArgumentRange(topFlag,1,2,"topFlag is not in 1 2.");
		this.commentIdList = commentIdList;
		this.topFlag = topFlag;
	}

	public List<String> getCommentIdList() {
		return commentIdList;
	}

	public Integer getTopFlag() {
		return topFlag;
	}
	
}
