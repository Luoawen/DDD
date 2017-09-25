package cn.m2c.scm.domain.model.order;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 
 * @ClassName: OrderRefundedBI
 * @Description: 已退款事件
 * @author moyj
 * @date 2017年8月1日 下午8:38:27
 *
 */
public class OrderRefundedBI extends AssertionConcern implements  DomainEvent {
	
	private String orderId;
	private String refundNo;
	private Long payPrice;
	private Long orderPrice;
	private Long freightPrice;
	private String buyerId;
	private Long refundAmount;
	private Long refundTime;
	
	public OrderRefundedBI(String orderId,String refundNo,Long payPrice,Long orderPrice,
			Long freightPrice,String buyerId,Long refundAmount,Long refundTime){
		this.orderId = orderId;
		this.refundNo = refundNo;
		this.payPrice = payPrice;
		this.orderPrice = orderPrice;
		this.freightPrice = freightPrice;
		this.buyerId = buyerId;
		this.refundAmount = refundAmount;
		this.refundTime = refundTime;
		
	}

	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return new Date(System.currentTimeMillis());
	}

	public String getOrderId() {
		return orderId;
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public Long getOrderPrice() {
		return orderPrice;
	}

	public Long getFreightPrice() {
		return freightPrice;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}

	public Long getRefundTime() {
		return refundTime;
	}

	public String getRefundNo() {
		return refundNo;
	}
	
	

}
