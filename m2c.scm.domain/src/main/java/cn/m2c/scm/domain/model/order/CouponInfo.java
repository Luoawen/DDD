package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;

/***
 * 简单优惠券信息值对象
 * @author fanjc
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class CouponInfo extends ValueObject {
	/**优惠券ID*/
	private String couponId;
	/**优惠券形式，1 减钱 2 打折*/
	private Integer couponForm;
	/**优惠券门槛*/
	private Long threshold;
	/**优惠券类型，1：代金券，2：折扣券，3：分享券*/
	private Integer couponType;
	/**门槛类型，1：金额，2：件数，3：无条件*/
	private Integer thresholdType;
	/**优惠券平摊，json串*/
	private String sharePercent;
	/**优惠券名称*/
	private String couponName;
	/**优惠券折扣或优惠*/
	private long discount;
	/**优惠券唯一标识码*/
	private String couponUserId;
	public CouponInfo() {
		super();
	}
	
	public CouponInfo(String couponId, int couponForm, long threshold,
			int couponType, int thresholdType, String sharePercent, String couponName
			, long discount,String couponUserId) {
		super();
		this.couponId = couponId;
		this.couponForm = couponForm;
		this.threshold = threshold;
		this.couponType = couponType;
		this.thresholdType = thresholdType;
		this.sharePercent = sharePercent;
		this.couponName = couponName;
		this.discount = discount;
		this.couponUserId = couponUserId;
	}
	
	String getCouponId() {
		return couponId;
	}
	
	void setCouponId(String mid) {
		couponId = mid;
	}
	
	String getCouponName() {
		return couponName;
	}
	String getCouponUserId() {
		return couponUserId;
	}
}
