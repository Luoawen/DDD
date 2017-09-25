package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: CannelOrderCommand
 * @Description: 取消订单命令
 * @author moyj
 * @date 2017年4月18日 下午4:32:25
 *
 */
public class CancelOrderCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderId;				//订单编号
	
	public CancelOrderCommand(String orderId){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
}
