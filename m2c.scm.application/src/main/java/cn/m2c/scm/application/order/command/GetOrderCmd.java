package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
/***
 * 获取订单详情命令
 * @author fanjc
 * created date 2017年11月6日
 * copyrighted@m2c
 */
public class GetOrderCmd extends AssertionConcern {

	private String userId;
	
	private String orderId;
	
	private String dealerOrderId;
	
	public GetOrderCmd(String userId, String orderId, String dealerOrderId) throws NegativeException {
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "userId为空");
		}
		
		if (StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "orderId为空");
		}
		
		this.userId = userId;
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}	
}
