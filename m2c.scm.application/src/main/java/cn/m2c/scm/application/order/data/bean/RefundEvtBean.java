package cn.m2c.scm.application.order.data.bean;
/***
 * 退款成功事件bean
 * @author fanjc
 * created date 2017年11月13日
 * copyrighted@m2c
 */
public class RefundEvtBean {
	
	private String orderRefundId;			//退款单号
	private String orderPayId;				//订单支付
	private String afterSellOrderId;		//售后单
	private String dealerId;				//商家Id
	private String userId;					//用户Id
	private Integer payWay;					//支付方式 1支付宝支付 2微信支付
	private Long refundAmount;				//退款金额
	private String tradeNo;					//外部交易号
	private Long refundTime;				//退款时间
	
	
	public String getOrderRefundId() {
		return orderRefundId;
	}
	public void setOrderRefundId(String orderRefundId) {
		this.orderRefundId = orderRefundId;
	}
	public String getOrderPayId() {
		return orderPayId;
	}
	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}
	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}
	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(Long refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public Long getRefundTime() {
		return refundTime;
	}
	public void setRefundTime(Long refundTime) {
		this.refundTime = refundTime;
	}	
}
