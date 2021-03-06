package cn.m2c.scm.application.dealerclassify.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerClassifyBean {

	@ColumnAlias("dealer_classify_id")
	private String dealerClassifyId;
	
	@ColumnAlias("dealer_level")
	private Integer dealerLevel;
	
	@ColumnAlias("dealer_classify_name")
	private String dealerClassifyName;

	public String getDealerClassifyId() {
		return dealerClassifyId;
	}

	public void setDealerClassifyId(String dealerClassifyId) {
		this.dealerClassifyId = dealerClassifyId;
	}

	public Integer getDealerLevel() {
		return dealerLevel;
	}

	public void setDealerLevel(Integer dealerLevel) {
		this.dealerLevel = dealerLevel;
	}

	public String getDealerClassifyName() {
		return dealerClassifyName;
	}

	public void setDealerClassifyName(String dealerClassifyName) {
		this.dealerClassifyName = dealerClassifyName;
	}

	
	
	
}
