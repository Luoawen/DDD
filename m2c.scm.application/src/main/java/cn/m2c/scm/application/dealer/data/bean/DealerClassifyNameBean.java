package cn.m2c.scm.application.dealer.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerClassifyNameBean {

	@ColumnAlias(value = "dealerFirstClassifyName")
	private String dealerFirstClassifyName;
	
	@ColumnAlias(value = "dealerSecondClassifyName")
	private String dealerSecondClassifyName;
	
	@ColumnAlias(value = "dealerClassifyId")
	private String dealerClassifyId;

	public String getDealerFirstClassifyName() {
		return dealerFirstClassifyName;
	}

	public void setDealerFirstClassifyName(String dealerFirstClassifyName) {
		this.dealerFirstClassifyName = dealerFirstClassifyName;
	}

	public String getDealerSecondClassifyName() {
		return dealerSecondClassifyName;
	}

	public void setDealerSecondClassifyName(String dealerSecondClassifyName) {
		this.dealerSecondClassifyName = dealerSecondClassifyName;
	}

	public String getDealerClassifyId() {
		return dealerClassifyId;
	}

	public void setDealerClassifyId(String dealerClassifyId) {
		this.dealerClassifyId = dealerClassifyId;
	}
	
	
}
