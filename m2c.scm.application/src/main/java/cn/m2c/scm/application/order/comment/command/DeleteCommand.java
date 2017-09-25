package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: DeleteCommand
 * @Description: 批量创建评论命令
 * @author moyj
 * @date 2017年7月12日 下午2:06:59
 *
 */
public class DeleteCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 8717381129374635249L;
	
	private List<String> commentIdList;				//订单编号
	
	public DeleteCommand(List<String> commentIdList){
		assertArgumentNotEmpty(commentIdList, "orderId is not be null.");
		this.commentIdList = commentIdList;
	}

	public List<String> getCommentIdList() {
		return commentIdList;
	}
	
	

	
}
