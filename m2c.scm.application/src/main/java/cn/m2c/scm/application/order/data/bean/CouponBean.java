package cn.m2c.scm.application.order.data.bean;

public class CouponBean {
	/**优惠券ID*/
	private String couponId;
	/**优惠券形式，为分享券用*/
	private Integer couponForm;
	/**优惠券门槛*/
	private Integer threshold;
	/**优惠券形式，1：减钱，2：打折，3：换购*/
	private Integer couponType;
	/**门槛类型，1：金额，2：件数*/
	private Integer thresholdType;
	/**优惠券平摊，json串*/
	private String sharePercent;
	/**优惠券名称*/
	private String couponName;
	/**优惠券折扣或优惠*/
	private Integer discount;
	/**商家优惠和平台优惠*/
	private Integer creatorType;
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public Integer getCouponForm() {
		return couponForm;
	}
	public void setCouponForm(Integer couponForm) {
		this.couponForm = couponForm;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Integer getCouponType() {
		return couponType;
	}
	public void setCouponType(Integer couponType) {
		this.couponType = couponType;
	}
	public Integer getThresholdType() {
		return thresholdType;
	}
	public void setThresholdType(Integer thresholdType) {
		this.thresholdType = thresholdType;
	}
	public String getSharePercent() {
		return sharePercent;
	}
	public void setSharePercent(String sharePercent) {
		this.sharePercent = sharePercent;
	}
	public String getCouponName() {
		return couponName;
	}
	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}
	public Integer getDiscount() {
		return discount;
	}
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	public Integer getCreatorType() {
		return creatorType;
	}
	public void setCreatorType(Integer creatorType) {
		this.creatorType = creatorType;
	}
	@Override
	public String toString() {
		return "CouponBean 的信息[couponId=" + couponId + ", couponForm="
				+ couponForm + ", threshold=" + threshold + ", couponType="
				+ couponType + ", thresholdType=" + thresholdType
				+ ", sharePercent=" + sharePercent + ", couponName="
				+ couponName + ", discount=" + discount + ", creatorType="
				+ creatorType + "]";
	}
	
}
