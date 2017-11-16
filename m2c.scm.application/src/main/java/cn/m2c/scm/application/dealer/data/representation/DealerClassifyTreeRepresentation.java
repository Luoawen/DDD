package cn.m2c.scm.application.dealer.data.representation;

import java.util.ArrayList;
import java.util.List;

import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;

public class DealerClassifyTreeRepresentation {

	private String dealerClassifyName;
	
	private String dealerClassifyId;

	private List<DealerClassifyChild> child;

	
	public DealerClassifyTreeRepresentation() {
		super();
	}

	public DealerClassifyTreeRepresentation(
			DealerClassifyBean dealerClassifyBean,
			List<DealerClassifyBean> secondClassifyBeanList) {
		this.setDealerClassifyId(dealerClassifyBean.getDealerClassifyId());
		this.setDealerClassifyName(dealerClassifyBean.getDealerClassifyName());
		this.setChild(secondClassifyBeanList);
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

	public List<DealerClassifyChild> getChild() {
		return child;
	}

	public void setChild(List<DealerClassifyBean> child) {
		List<DealerClassifyChild> mchild = new ArrayList<DealerClassifyChild>();
		for (DealerClassifyBean dealerClassifyBean : child) {
			mchild.add(new DealerClassifyChild(dealerClassifyBean.getDealerClassifyId(),dealerClassifyBean.getDealerClassifyName()));
		}
		this.child = mchild;
	}

	
	
	
}
