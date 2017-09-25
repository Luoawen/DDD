package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: HideCommand
 * @Description: 批量隐藏评论命令
 * @author moyj
 * @date 2017年7月12日 下午2:07:34
 *
 */
public class HideCommand extends AssertionConcern implements Serializable{
private static final long serialVersionUID = 8717381129374635249L;
	
	private List<String> commentIdList;				//订单编号
	private Integer hideFlag;						//隐藏标志 1隐藏  2取消隐藏
	
	public HideCommand(List<String> commentIdList,Integer hideFlag){
		assertArgumentNotEmpty(commentIdList, "orderId is not be null.");
		assertArgumentNotNull(hideFlag, "invoiceType is not be null.");
		assertArgumentRange(hideFlag,1,2,"invoiceType is not in 1 2.");
		this.commentIdList = commentIdList;
		this.hideFlag = hideFlag;
	}

	public List<String> getCommentIdList() {
		return commentIdList;
	}

	public Integer getHideFlag() {
		return hideFlag;
	}

}
