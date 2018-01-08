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
	private long threshold;
	/**营销形式，1：减钱，2：打折，3：换购*/
	private Integer marketType;
	/**门槛类型，1：金额，2：件数*/
	private Integer thresholdType;
	/**优惠平摊，json串*/
	private String sharePercent;
	/**营销名称*/
	private String marketName;
	/**活动折扣或优惠*/
	private Integer discount;
	
	public SimpleMarketInfo() {
		super();
	}
	
	public SimpleMarketInfo(String marketingId, int marketLevel, long threshold,
			int marketType, int thresholdType, String sharePercent, String marketName
			, int discount) {
		super();
		this.marketingId = marketingId;
		this.marketLevel = marketLevel;
		this.threshold = threshold;
		this.marketType = marketType;
		this.thresholdType = thresholdType;
		this.sharePercent = sharePercent;
		this.marketName = marketName;
		this.discount = discount;
	}
	
	String getMarketingId() {
		return marketingId;
	}
	
	void setMarketId(String mid) {
		marketingId = mid;
	}
	
	String getMarketName() {
		return marketName;
	}
}
