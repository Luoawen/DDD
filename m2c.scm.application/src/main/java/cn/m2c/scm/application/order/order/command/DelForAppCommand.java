package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class DelForAppCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderId;				//订单编号
	
	public DelForAppCommand(String orderId){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		this.orderId = orderId;
	}

	public String getOrderId() {
		return orderId;
	}
}
