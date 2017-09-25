package cn.m2c.scm.application.order.fsales.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: SaledAuditCommand
 * @Description: 售后审核命令
 * @author moyj
 * @date 2017年7月1日 下午5:25:53
 *
 */
public class AuditAfterSalesCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 6507875408718616407L;
	
	private List<String> orderIdList;		//订单编码
	private Integer auditFlag;				//1审核通过	2审核不通过
	private String unauditReason;			//审核不通过理由
	private String handManId;				//处理人Id
	private String handManName;				//处理人名称
	
	public AuditAfterSalesCommand(List<String> orderIdList,Integer auditFlag,String unauditReason,String handManId,String handManName){
		assertArgumentNotEmpty(orderIdList, "orderIdList is not be null.");
		assertArgumentNotNull(auditFlag, "auditFlag is not be null.");
		assertArgumentRange(auditFlag, 1, 2, "auditFlag is not be limit");
		if(auditFlag == 2){
			assertArgumentNotEmpty(unauditReason, "unauditReason is not be null.");
			assertArgumentLength(unauditReason,64,"unauditReason is longer than 64.");
		}
		this.orderIdList = orderIdList;
		this.auditFlag = auditFlag;
		this.unauditReason = unauditReason;
		this.handManId = handManId;
		this.handManName = handManName;
	}
	public Integer getAuditFlag() {
		return auditFlag;
	}
	public List<String> getOrderIdList() {
		return orderIdList;
	}
	public String getUnauditReason() {
		return unauditReason;
	}
	public String getHandManId() {
		return handManId;
	}
	public String getHandManName() {
		return handManName;
	}
	
	

}
