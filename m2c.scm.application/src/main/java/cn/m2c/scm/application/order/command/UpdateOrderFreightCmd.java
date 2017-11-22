package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.scm.domain.NegativeException;

public class UpdateOrderFreightCmd {
	/**
	 * 商家订单号
	 */
	private String dealerOrderId;
	
	private String userId;
	
	/**
	 * 订单运费
	 */
	private long orderFreight;

	public UpdateOrderFreightCmd(String dealerOrderId, long orderFreight, String userId) throws NegativeException {
		this.dealerOrderId = dealerOrderId;
		this.orderFreight = orderFreight;
		this.userId = userId;
		
		if (StringUtils.isEmpty(dealerOrderId)) {
			throw new NegativeException(MCode.V_1, "dealerOrderId参数为空");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "userId参数为空");
		}
		
		if (orderFreight < 0) {
			throw new NegativeException(MCode.V_1, "orderFreight参数不能小于0");
		}
	}

	public String getUserId() {
		return userId;
	}

	public UpdateOrderFreightCmd() {
		super();
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public long getOrderFreight() {
		return orderFreight;
	}



}
