package cn.m2c.scm.application.order.comment.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: CreateCommand
 * @Description: 后台新增评论命令
 * @author moyj
 * @date 2017年7月12日 下午2:06:30
 *
 */
public class AdminCreateCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = -526288654100127127L;
	private String commentId;		//评论编号
	private String goodsId;			//货品编号
	private String goodsName;		//货品名称
	private String buyerName;		//买家名称
	private String buyerIcon;		//买家头像
	private String commentContent;	//评论内容
	private Integer starLevel;		//星级
	private Long commentTime;	    //评论时间
	private Long buyingTime;		//购买时间
	
	public AdminCreateCommand(
			String commentId,
			String goodsId,
			String goodsName,
			String buyerName,
			String buyerIcon,
			String commentContent,
			Integer starLevel,
			Long commentTime,
			Long buyingTime){
		assertArgumentNotEmpty(commentId, "评论ID不能为空");
		assertArgumentLength(commentId,36,"评论ID长度不能大于36");
		assertArgumentNotEmpty(goodsId, "请选择所属商品");
		assertArgumentLength(goodsId,36,"商品ID长度不能大于36");
		assertArgumentNotEmpty(goodsName, "请选择所属商品");
		assertArgumentLength(goodsName,100,"商品名称长度不能大于100");
		assertArgumentNotEmpty(buyerName, "用户名不能为空");
		assertArgumentLength(buyerName,100,"用户名称长度不能大于100");
		assertArgumentNotEmpty(buyerIcon, "请上传头像");
		assertArgumentLength(buyerIcon,100,"头像URL长度不能大于100");
		assertArgumentNotEmpty(commentContent, "评论内容不能为空");
		assertArgumentLength(commentContent,256,"评论内容 长度不能大于1000");
		assertArgumentNotNull(commentTime, "请选择评论时间");
		assertArgumentNotNull(buyingTime, "请选择购买时间");
		assertArgumentNotNull(starLevel, "请选择星级");
		assertArgumentRange(starLevel,1,5,"星级范围必须是（ 1 2 3 4 5）");	
		
		this.commentId = commentId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.buyerName = buyerName;
		this.buyerIcon = buyerIcon;
		this.commentContent = commentContent;
		this.commentTime = commentTime;
		this.buyingTime = buyingTime;
		this.starLevel = starLevel;
	}
	public String getCommentId() {
		return commentId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerIcon() {
		return buyerIcon;
	}
	public String getCommentContent() {
		return commentContent;
	}
	public Long getCommentTime() {
		return commentTime;
	}
	public Long getBuyingTime() {
		return buyingTime;
	}
	public Integer getStarLevel() {
		return starLevel;
	}
	
	

}
