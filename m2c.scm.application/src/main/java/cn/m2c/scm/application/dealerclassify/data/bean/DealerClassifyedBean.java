package cn.m2c.scm.application.dealerclassify.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerClassifyedBean {
	@ColumnAlias(value = "dealer_classify_id")
	private String dealerClassifyId;
	
	@ColumnAlias(value = "dealer_classify_name")
	private String dealerClassifyName;

	public String getDealerClassifyId() {
		return dealerClassifyId;
	}

	public String getDealerClassifyName() {
		return dealerClassifyName;
	}

	public void setDealerClassifyId(String dealerClassifyId) {
		this.dealerClassifyId = dealerClassifyId;
	}

	public void setDealerClassifyName(String dealerClassifyName) {
		this.dealerClassifyName = dealerClassifyName;
	}
	
	
}
