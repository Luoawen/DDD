package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: ModStatusCommand
 * @Description: 修改订单状态命令
 * @author moyj
 * @date 2017年7月1日 下午5:32:33
 *
 */
public class ModStatusCommand extends AssertionConcern implements Serializable{
	
	private static final long serialVersionUID = 4835005423232101507L;
	
	private String orderId;				//订单编号
	private Integer orderStatus;		//订单状态 1正常 2取消
	private Integer payStatus;			//支付状态 1待支付 2已支付
	private Integer logisticsStatus;	//物流状态 1待发货 2待收货 3 已收货
	private Integer afterSalesStatus;	//售后状态 1待申请 2待审核 3 退换中 4.已退款
	
	public ModStatusCommand(String orderId,Integer orderStatus,Integer payStatus,Integer logisticsStatus,Integer afterSalesStatus){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		if(orderStatus != null){
			assertArgumentRange(orderStatus,1,2,"orderStatus is not in 1 2.");
		}
		if(payStatus != null){
			assertArgumentRange(payStatus,1,2,"invoiceType is not in 1 2 .");
		}
		if(logisticsStatus != null){
			assertArgumentRange(logisticsStatus,1,3,"logisticsStatus is not in 1 2 3.");
		}
		if(afterSalesStatus != null){
			assertArgumentRange(afterSalesStatus,1,4,"afterSalesStatus is not in 1 2 3 4 5 6 7.");
		}		
		this.orderId = orderId;
		this.orderStatus = orderStatus;
		this.payStatus = payStatus;
		this.logisticsStatus = logisticsStatus;
		this.afterSalesStatus = afterSalesStatus;	
	}
	
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Integer getLogisticsStatus() {
		return logisticsStatus;
	}
	public String getOrderId() {
		return orderId;
	}
	public Integer getAfterSalesStatus() {
		return afterSalesStatus;
	}
	
	

}
