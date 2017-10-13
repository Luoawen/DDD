package cn.m2c.scm.application.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;
/***
 * 订单提交命令
 * @author fanjc
 *
 */
public class OrderAddCommand extends AssertionConcern implements Serializable {
	
	private String orderId;
	
	
	public OrderAddCommand(String orderId, String userId, String noted
			,String goodses, String invoice, String addr, String coupons) {
		// 检验必传参数
	}
	
	
}
