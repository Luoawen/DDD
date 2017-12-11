package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 售后申请理由
 * @author lqwen
 *
 */
public class AfterSellApplyReason {

	/**
	 * 理由Id
	 */
	@ColumnAlias(value = "reason_id")
	private String reasonId;

	/**
	 * 申请订单状态：1.发货前，申请仅退款原因;2.发货后，申请仅退款原因;
	 * 				3.发货后，申请退货退款原因;4.换货原因 
	 */
	@ColumnAlias(value = "apply_order_status")
	private Integer applyOrderStatus;

	/**
	 * 理由描述
	 */
	@ColumnAlias(value = "apply_desc")
	private String applyDesc;

	public String getReasonId() {
		return reasonId;
	}

	public Integer getApplyOrderStatus() {
		return applyOrderStatus;
	}

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}

	public void setApplyOrderStatus(Integer applyOrderStatus) {
		this.applyOrderStatus = applyOrderStatus;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}

	public AfterSellApplyReason() {
		super();
	}
	
	

}
