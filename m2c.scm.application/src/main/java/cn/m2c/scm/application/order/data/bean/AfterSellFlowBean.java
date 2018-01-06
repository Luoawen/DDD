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
