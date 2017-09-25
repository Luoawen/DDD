package cn.m2c.scm.domain.model.order.service;


import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * @ClassName: AfterSalesService
 * @Description: 评论领域服务 (评论操作)
 * @author moyj
 * @date 2017年7月7日 下午4:14:30
 *
 */
public interface CommentService {
	
	/**
	 * app新增评论
	 * @param commentId			//评论编号
	 * @param orderId			//订单编号
	 * @param buyerId			//买家编号
	 * @param buyerName			//买家名称
	 * @param buyerIcon			//买家头像
	 * @param commentLevel		//评论级别  1好  2中  3差
	 * @param buyingTime		//购买时间
	 * @throws NegativeException
	 */
	public void appCreate(String commentId,String buyerId,String content,Integer commentLevel,Integer starLevel,String orderId) throws NegativeException;
	
	
	
}
