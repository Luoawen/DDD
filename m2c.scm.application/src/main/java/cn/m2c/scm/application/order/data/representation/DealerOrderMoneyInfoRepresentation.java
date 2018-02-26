package cn.m2c.scm.application.order.data.representation;

import cn.m2c.scm.application.order.data.bean.DealerOrderMoneyInfoBean;

/**
 * 	给基础组提供,查询商家订单金额 
 */
public class DealerOrderMoneyInfoRepresentation {
	//订单总额(订单商品总额 - 平台优惠券 - 商家优惠券 - 优惠券 + 运费)
	private Long orderMoney;

	public DealerOrderMoneyInfoRepresentation(DealerOrderMoneyInfoBean bean) {
		this.orderMoney = bean.getOrderPrice();
	}
	
	public Long getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(Long orderMoney) {
		this.orderMoney = orderMoney;
	}
	
	
}
