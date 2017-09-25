package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;
import java.util.Map;

import cn.m2c.ddd.common.AssertionConcern;
/**
 * 
 * @ClassName: PayedCommand
 * @Description: 支付完成命令
 * @author moyj
 * @date 2017年7月17日 下午7:27:26
 *
 */
public class PayedCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1L;
	private String orderId;					//订单ID		
	private Long payStartTime;				//支付开始时间
	private Long payEndTime;				//支付结束时间
	private String payTradeNo; 				//外部交易号
	private Long payPrice;					//支付价格
	private Integer payWay;					//支付方式
	private Map<String,Object> appMsgMap; 	//设备信息
	
	public PayedCommand(String orderId,Long payStartTime,Long payEndTime,String payTradeNo,Long payPrice,Integer payWay,Map<String,Object> appMsgMap){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		//assertArgumentNotNull(payStartTime, "payStartTime is not be null.");
		assertArgumentNotNull(payEndTime, "payEndTime is not be null.");
		assertArgumentNotEmpty(payTradeNo, "payTradeNo is not be null.");
		assertArgumentNotNull(payPrice, "payPrice is not be null.");
		assertArgumentNotNull(payWay, "payWay is not be null.");
		assertArgumentRange(payWay,1,2,"payWay is not in 1 2 .");	
		assertArgumentNotNull(appMsgMap, "payPrice is not be null.");
		this.orderId = orderId;
		this.payStartTime = payStartTime;
		this.payEndTime = payEndTime;
		this.payTradeNo = payTradeNo;
		this.payPrice = payPrice;
		this.payWay = payWay;
		this.appMsgMap = appMsgMap;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getOrderId() {
		return orderId;
	}
	public Long getPayStartTime() {
		return payStartTime;
	}
	public Long getPayEndTime() {
		return payEndTime;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public Long getPayPrice() {
		return payPrice;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public Map<String, Object> getAppMsgMap() {
		return appMsgMap;
	}
	
	

}
