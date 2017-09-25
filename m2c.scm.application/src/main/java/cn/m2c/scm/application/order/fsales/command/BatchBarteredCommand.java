package cn.m2c.scm.application.order.fsales.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

public class BatchBarteredCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 57909621930228741L;
	private List<String> orderIdList;		//订单编码
	private String handManId;			//处理人Id
	private String handManName;			//处理人名称
	
	public BatchBarteredCommand(List<String> orderIdList,String handManId,String handManName){
		assertArgumentNotEmpty(orderIdList, "orderIdList is not be null.");
		this.orderIdList = orderIdList;
		this.handManId = handManId;
		this.handManName = handManName;
	}

	public List<String> getOrderIdList() {
		return orderIdList;
	}

	public String getHandManId() {
		return handManId;
	}

	public String getHandManName() {
		return handManName;
	}
	
	

}
