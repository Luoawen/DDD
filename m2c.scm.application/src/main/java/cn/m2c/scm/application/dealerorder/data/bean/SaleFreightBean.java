package cn.m2c.scm.application.dealerorder.data.bean;

import cn.m2c.scm.application.utils.Utils;

/***
 * 售后已经退的运费
 * @author 89776
 * created date 2017年11月26日
 * copyrighted@m2c
 */
public class SaleFreightBean {

	private Long costFt;

	public Long getCostFt() {
		return costFt/100;
	}
	
	public String getStrCostFt() {
		return Utils.moneyFormatCN(costFt);
	}

	public void setCostFt(Long costFt) {
		if (costFt == null)
			costFt = 0l;
		this.costFt = costFt;
	}
}
