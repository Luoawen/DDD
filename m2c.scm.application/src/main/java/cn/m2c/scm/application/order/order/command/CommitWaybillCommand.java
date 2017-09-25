package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: CommitWaybillCommand
 * @Description: 提交运单命令
 * @author moyj
 * @date 2017年4月18日 下午3:09:49
 *
 */
public class CommitWaybillCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String orderId;				//订单编号
	private String waybillNo;			//运单号
	private String expCompanyCode;		//快递公司编码
	private String expCompanyName;		//快递公司名称
	private String handManId;			//处理人Id
	private String handManName;			//处理人名称
	
	public CommitWaybillCommand(String orderId,String waybillNo,String expCompanyCode,String expCompanyName,String handManId,String handManName){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		assertArgumentNotEmpty(waybillNo, "waybillNo is not be null.");
		assertArgumentLength(waybillNo,64,"waybillNo is longer than 64.");
		assertArgumentNotEmpty(expCompanyCode, "expCompanyCode is not be null.");
		assertArgumentLength(expCompanyCode,64,"expCompanyCode is longer than 64.");
		assertArgumentNotEmpty(expCompanyName, "expCompanyName is not be null.");
		assertArgumentLength(expCompanyName,64,"expCompanyName is longer than 64.");
		this.orderId = orderId;
		this.waybillNo = waybillNo;
		this.expCompanyCode = expCompanyCode;
		this.expCompanyName = expCompanyName;
		this.handManId = handManId;
		this.handManName = handManName;
	}
	public String getOrderId() {
		return orderId;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public String getExpCompanyCode() {
		return expCompanyCode;
	}
	public String getExpCompanyName() {
		return expCompanyName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getHandManId() {
		return handManId;
	}
	public String getHandManName() {
		return handManName;
	}

	
	
	

}
