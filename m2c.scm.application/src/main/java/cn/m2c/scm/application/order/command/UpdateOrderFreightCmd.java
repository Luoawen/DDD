package cn.m2c.scm.application.order.command;

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

	public UpdateOrderFreightCmd(String dealerOrderId, long orderFreight, String userId) {
		this.dealerOrderId = dealerOrderId;
		this.orderFreight = orderFreight;
		this.userId = userId;
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
