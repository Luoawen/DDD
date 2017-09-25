package cn.m2c.scm.domain.model.order.comment;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 
 * @ClassName: Commented
 * @Description: 已评论事件
 * @author moyj
 * @date 2017年7月27日 下午3:58:02
 *
 */
public class Commented  extends AssertionConcern implements  DomainEvent {
	
	private String commentId;		//评论编号
	private String goodsId;			//货品编号
	private String buyerId;			//买家编号
	
	public Commented(String commentId,String goodsId,String buyerId){
		this.commentId = commentId;
		this.goodsId = goodsId;
		this.buyerId = buyerId;
	}
	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return new Date(System.currentTimeMillis());
	}

	public String getCommentId() {
		return commentId;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public String getBuyerId() {
		return buyerId;
	}
	
	
	

}
