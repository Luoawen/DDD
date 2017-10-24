package cn.m2c.scm.application.order.command;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
/***
 * 支付完成
 * @author fanjc
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class OrderPayedCmd extends AssertionConcern {
	
	private String orderId;
	
	private String userId;
	
	private String payNo;
	/**1 支付宝，2 微信， 3*/
	private int payWay;
	
	private Date payTime;
	
	public OrderPayedCmd(String orderNo, String userId
			,String payNo, int payWay, Date payTime) throws NegativeException {
		
		orderId = orderNo;
		
		this.userId = userId;
		
		if (StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空(orderId)！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtils.isEmpty(payNo)) {
			throw new NegativeException(MCode.V_1, "支付单号参数为空(payNo)！");
		}
		
		if (payWay < 1) {
			throw new NegativeException(MCode.V_1, "支付方式参数为空(payWay)！");
		}
		
		if (payTime == null) {
			throw new NegativeException(MCode.V_1, "支付时间参数为空(payTime)！");
		}
		
		
		this.payNo = payNo;
		this.payWay = payWay;
		this.payTime = payTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getUserId() {
		return userId;
	}

	public String getPayNo() {
		return payNo;
	}

	public int getPayWay() {
		return payWay;
	}

	public Date getPayTime() {
		return payTime;
	}	
}
