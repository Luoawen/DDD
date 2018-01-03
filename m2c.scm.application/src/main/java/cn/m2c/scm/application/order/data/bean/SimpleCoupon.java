package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 简单优惠券实体
 * @author fanjc
 * created date 2018年1月3日
 * copyrighted@m2c
 */
public class SimpleCoupon {
	
	@ColumnAlias(value = "coupon_id")
	private String couponId;
	
	@ColumnAlias(value = "coupon_form")
	private int couponForm;
	
	@ColumnAlias(value = "coupon_type")
	private Integer couponType;//营销形式，1：减钱，2：打折，3：换购
	
	@ColumnAlias(value = "threshold")
	private long threshold;
	
	@ColumnAlias(value = "threshold_type")
	private Integer thresholdType;//1：金额，2：件数
	
	@ColumnAlias(value = "discount")
	private Integer discount;
	
	@ColumnAlias(value = "share_percent")
	private String sharePercent;
	
	@ColumnAlias(value = "coupon_name")
	private String couponName;
	
	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getSharePercent() {
		return sharePercent;
	}

	public void setSharePercent(String sharePercent) {
		this.sharePercent = sharePercent;
	}

	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public long getThreshold() {
		return threshold;
	}
	public void setThreshold(long threshold) {
		this.threshold = threshold;
	}
	public Integer getThresholdType() {
		return thresholdType;
	}
	public void setThresholdType(Integer thresholdType) {
		this.thresholdType = thresholdType;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public int getCouponForm() {
		return couponForm;
	}

	public void setCouponForm(int couponForm) {
		this.couponForm = couponForm;
	}

	public Integer getCouponType() {
		return couponType;
	}

	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	
}
