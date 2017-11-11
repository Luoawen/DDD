package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

public class CancelOrderCmd extends AssertionConcern {
	
	private String orderId;
	
	private String userId;
	
	private String dealerOrderId;
	
	public CancelOrderCmd(String orderNo, String userId) throws NegativeException {
		orderId = orderNo;
		
		this.userId = userId;
		
		if (StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空(orderId)！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
	}
	
	public CancelOrderCmd(String orderNo, String userId, String dealerOrderId) throws NegativeException {
		
		orderId = orderNo;
		
		this.userId = userId;
		
		if (StringUtils.isEmpty(orderId) || StringUtils.isEmpty(dealerOrderId)) {
			throw new NegativeException(MCode.V_1, "订单号或订货号不能同时为空！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		this.dealerOrderId = dealerOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}	
	
}
