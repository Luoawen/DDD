package cn.m2c.scm.application.order.fsales.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: BackGoodsCommand
 * @Description: 已退货命令
 * @author moyj
 * @date 2017年7月1日 下午5:06:46
 *
 */
public class BackedGoodsCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String orderId;				//订单编号
	private Long refundAmount;			//退款金额
	private String refbaredReason;		//退/换理由
	private String handManId;			//处理人Id
	private String handManName;			//处理人名称
	
	public BackedGoodsCommand(String orderId,Long refundAmount,String refbaredReason,String handManId,String handManName){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		assertArgumentNotNull(refundAmount, "refundAmount is not be null.");
		assertArgumentNotEmpty(refbaredReason, "refbaredReason is not be null.");
		assertArgumentLength(refbaredReason,64,"refbaredReason is longer than 64.");
		this.orderId = orderId;
		this.refundAmount = refundAmount;
		this.refbaredReason = refbaredReason;
		this.handManId = handManId;
		this.handManName = handManName;
	}

	public String getHandManId() {
		return handManId;
	}

	public String getHandManName() {
		return handManName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getRefbaredReason() {
		return refbaredReason;
	}

	public Long getRefundAmount() {
		return refundAmount;
	}
	
	
	
}
