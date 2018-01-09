package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 售后流程记录
 * @author lqwen
 *
 */
public class AfterSellFlowBean {
	
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	
	@ColumnAlias(value = "_status")
	private Integer status;
	
	@ColumnAlias(value = "user_id")
	private String userId;
	
	@ColumnAlias(value = "reject_reason")
	private String rejectReason;
	
	@ColumnAlias(value = "reject_reason_code")
	private Integer rejectReasonCode;
	
	@ColumnAlias(value = "apply_reason")
	private String applyReason;
	
	@ColumnAlias(value = "apply_reason_code")
	private Integer applyReasonCode;
	
	@ColumnAlias(value = "express_no")
	private String expressNo;
	
	@ColumnAlias(value = "express_name")
	private String expressName;
	
	@ColumnAlias(value = "back_express_no")
	private String backExpressNo;
	
	@ColumnAlias(value = "back_express_name")
	private String backExpressName;

	public String getExpressNo() {
		return expressNo;
	}

	public String getExpressName() {
		return expressName;
	}

	public String getBackExpressNo() {
		return backExpressNo;
	}

	public String getBackExpressName() {
		return backExpressName;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public void setBackExpressNo(String backExpressNo) {
		this.backExpressNo = backExpressNo;
	}

	public void setBackExpressName(String backExpressName) {
		this.backExpressName = backExpressName;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Integer getStatus() {
		return status;
	}

	public String getUserId() {
		return userId;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public Integer getRejectReasonCode() {
		return rejectReasonCode;
	}

	public String getApplyReason() {
		return applyReason;
	}

	public Integer getApplyReasonCode() {
		return applyReasonCode;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public void setRejectReasonCode(Integer rejectReasonCode) {
		this.rejectReasonCode = rejectReasonCode;
	}

	public void setApplyReason(String applyReason) {
		this.applyReason = applyReason;
	}

	public void setApplyReasonCode(Integer applyReasonCode) {
		this.applyReasonCode = applyReasonCode;
	}
	
	

}
