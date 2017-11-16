package cn.m2c.scm.application.dealer.data.representation;

public class DealerClassifyChild {
	
	private String dealerClassifyName;
	
	private String dealerClassifyId;
	
	public DealerClassifyChild() {
		super();
	}

	public DealerClassifyChild(String dealerClassifyId,
			String dealerClassifyName) {
		this.setDealerClassifyId(dealerClassifyId);
		this.setDealerClassifyName(dealerClassifyName);
	}


	public String getDealerClassifyName() {
		return dealerClassifyName;
	}

	public void setDealerClassifyName(String dealerClassifyName) {
		this.dealerClassifyName = dealerClassifyName;
	}

	public String getDealerClassifyId() {
		return dealerClassifyId;
	}

	public void setDealerClassifyId(String dealerClassifyId) {
		this.dealerClassifyId = dealerClassifyId;
	}
	
}
