package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ImportFailedOrderBean {
	
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	@ColumnAlias(value = "express_name")
	private String expressName;
	@ColumnAlias(value = "express_no")
	private String expressNo;
	@ColumnAlias(value = "import_wrong_message")
	private String failedReason;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getExpressName() {
		return expressName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public String getFailedReason() {
		return failedReason;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason;
	}
	
	

}
