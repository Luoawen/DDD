package cn.m2c.scm.application.dealer.data.representation;

import cn.m2c.scm.application.dealer.data.bean.DealerBean;

public class DealerNameListRepresentation {
	private String dealerName;
	 
	 private String dealerId;

	public DealerNameListRepresentation(DealerBean model) {
		this.dealerId = model.getDealerId();
		this.dealerName = model.getDealerName();
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	 
	 
}
