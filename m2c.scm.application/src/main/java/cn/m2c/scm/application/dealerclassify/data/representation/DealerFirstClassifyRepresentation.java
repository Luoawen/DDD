package cn.m2c.scm.application.dealerclassify.data.representation;

import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;

public class DealerFirstClassifyRepresentation {

	private String dealerFirstClassify;
	
	private String dealerFristClassifyName;
	
	private Integer dealerLevel;

	public DealerFirstClassifyRepresentation(
			DealerClassifyBean dealerClassifyBean) {
		this.dealerFirstClassify = dealerClassifyBean.getDealerClassifyId();
		this.dealerFristClassifyName = dealerClassifyBean.getDealerClassifyName();
		this.dealerLevel = dealerClassifyBean.getDealerLevel();
	}

	public String getDealerFirstClassify() {
		return dealerFirstClassify;
	}

	public void setDealerFirstClassify(String dealerFirstClassify) {
		this.dealerFirstClassify = dealerFirstClassify;
	}

	public String getDealerFristClassifyName() {
		return dealerFristClassifyName;
	}

	public void setDealerFristClassifyName(String dealerFristClassifyName) {
		this.dealerFristClassifyName = dealerFristClassifyName;
	}

	public Integer getDealerLevel() {
		return dealerLevel;
	}

	public void setDealerLevel(Integer dealerLevel) {
		this.dealerLevel = dealerLevel;
	}
	
	
}
