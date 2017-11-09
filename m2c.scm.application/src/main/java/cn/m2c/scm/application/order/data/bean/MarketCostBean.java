package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
/***
 * 营销成本分摊bean
 * @author fanjc
 * created date 2017年11月9日
 * copyrighted@m2c
 */
public class MarketCostBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String dealerId;
	
	private Integer platformPercent;
	
	private Integer dealerPercent;

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public Integer getPlatformPercent() {
		return platformPercent;
	}

	public void setPlatformPercent(Integer platformPercent) {
		this.platformPercent = platformPercent;
	}

	public Integer getDealerPercent() {
		return dealerPercent;
	}

	public void setDealerPercent(Integer dealerPercent) {
		this.dealerPercent = dealerPercent;
	}
}
