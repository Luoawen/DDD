package cn.m2c.scm.application.order.fsales.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: SaledApplyCommand
 * @Description: 售后申请命令
 * @author moyj
 * @date 2017年7月1日 下午5:11:32
 *
 */
public class ApplyAfterSalesCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = -5242965180038988997L;

	private String orderId;				//订单编号
	private String applyReason;			//申请售后原因
	private Long fsaleDeadline;			//售后期
	
	public ApplyAfterSalesCommand(String orderId,String applyReason,Long fsaleDeadline){
		assertArgumentNotEmpty(orderId, "orderId is not be null.");
		assertArgumentLength(orderId,36,"orderId is longer than 36.");
		assertArgumentNotEmpty(applyReason, "saledReason is not be null.");
		assertArgumentLength(applyReason,64,"saledReason is longer than 64.");
		assertArgumentNotNull(fsaleDeadline, "saledDeadline is not be null.");
		this.orderId = orderId;
		this.applyReason = applyReason;
		this.fsaleDeadline = fsaleDeadline;
	}
	
	public String getOrderId() {
		return orderId;
	}
	public String getApplyReason() {
		return applyReason;
	}
	
	public Long getFsaleDeadline() {
		return fsaleDeadline;
	}

	

	
	
	
	
}
