package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;

/***
 * 简单营销值对象
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class SimpleMarketInfo extends ValueObject {
	/**营销ID*/
	private String marketingId;
	/**营销层级*/
	private Integer marketLevel;
	/**营销层级门槛*/
	private Integer threshold;
	
	public SimpleMarketInfo() {
		super();
	}
	
	public SimpleMarketInfo(String marketingId, int marketLevel, int threshold) {
		super();
		this.marketingId = marketingId;
		this.marketLevel = marketLevel;
		this.threshold = threshold;
	}
	
	String getMarketingId() {
		return marketingId;
	}
	
	void setMarketId(String mid) {
		marketingId = mid;
	}
}
