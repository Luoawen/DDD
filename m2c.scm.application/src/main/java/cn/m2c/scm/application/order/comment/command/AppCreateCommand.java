package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: AppCreateCommand
 * @Description: APP评论命令
 * @author moyj
 * @date 2017年7月12日 下午2:05:58
 *
 */
public class AppCreateCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = 8023202864294591497L;
	private String commentId;		//评论编号
	private String orderId;			//订单编号
	private String buyerId;			//买家编号
	//private Integer commentLevel;	//评论级别  1好  2中  3差
	private Integer starLevel;		//星级
	private String commentContent;	//评论内容
	
	public AppCreateCommand(String commentId,String orderId,String buyerId,Integer starLevel,String commentContent){
		assertArgumentNotEmpty(commentId, "评论ID不能为空");
		assertArgumentLength(commentId,36,"评论ID长度超出限制");
		assertArgumentNotEmpty(orderId, "订单ID不能为空");
		assertArgumentNotEmpty(buyerId, "买家Id不能为空");	
		assertArgumentLength(buyerId,36,"买家ID长度超出限制");	
		assertArgumentNotNull(starLevel, "星级不能为空");
		assertArgumentRange(starLevel,1,5,"星级范围必须是（ 1 2 3 4 5）");	
		assertArgumentNotEmpty(commentContent, "评论内容不能为空");
		assertArgumentLength(commentContent,256,"评论内容长度超出限制");
		
		this.commentId = commentId;
		this.orderId = orderId;
		this.buyerId = buyerId;
		this.starLevel = starLevel;
		this.commentContent = commentContent;
	}
	
	public String getCommentId() {
		return commentId;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public String getCommentContent() {
		return commentContent;
	}

	public Integer getStarLevel() {
		return starLevel;
	}


	
	
	
	
	
}
