package cn.m2c.scm.application.dealerorder.data.bean;
/***
 * 售后已经退的运费
 * @author 89776
 * created date 2017年11月26日
 * copyrighted@m2c
 */
public class SaleFreightBean {

	private Long costFt;

	public Long getCostFt() {
		if (costFt == null)
			costFt = 0l;
		return costFt;
	}

	public void setCostFt(Long costFt) {
		this.costFt = costFt;
	}
}
