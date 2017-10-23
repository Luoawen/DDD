package cn.m2c.scm.application.dealerclassify.data.representation;

import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;

public class DealerSecondClassifyRepresentation {
	
	 private String dealerClassify;
	 private String dealerSecondClassifyName;
     private Integer dealerLevel;

	public DealerSecondClassifyRepresentation(
			DealerClassifyBean dealerClassifyBean) {
		this.dealerClassify = dealerClassifyBean.getDealerClassifyId();
		this.dealerSecondClassifyName = dealerClassifyBean.getDealerClassifyName();
		this.dealerLevel = dealerClassifyBean.getDealerLevel();
	}


	public Integer getDealerLevel() {
		return dealerLevel;
	}

	public void setDealerLevel(Integer dealerLevel) {
		this.dealerLevel = dealerLevel;
	}


	public String getDealerClassify() {
		return dealerClassify;
	}


	public void setDealerClassify(String dealerClassify) {
		this.dealerClassify = dealerClassify;
	}


	public String getDealerSecondClassifyName() {
		return dealerSecondClassifyName;
	}


	public void setDealerSecondClassifyName(String dealerSecondClassifyName) {
		this.dealerSecondClassifyName = dealerSecondClassifyName;
	}

}
