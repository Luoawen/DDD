package cn.m2c.scm.application.order.fsales.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: SaledBarteredCommand
 * @Description: 已换货命令
 * @author moyj
 * @date 2017年7月1日 下午5:07:38
 *
 */
public class BarteredCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String orderId;				//订单编号
	private String handManId;			//处理人Id
	private String handManName;			//处理人名称
	
	public BarteredCommand(String orderId,String handManId,String handManName){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		this.orderId = orderId;
		this.handManId = handManId;
		this.handManName = handManName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getHandManId() {
		return handManId;
	}

	public String getHandManName() {
		return handManName;
	}
	
}
