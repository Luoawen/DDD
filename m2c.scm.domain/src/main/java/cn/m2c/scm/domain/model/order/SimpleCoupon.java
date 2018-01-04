package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.IdentifiedDomainObject;
/***
 * 简单优惠券
 * @author fanjc
 *
 */
public class SimpleCoupon extends IdentifiedDomainObject {

	/**订单ID*/
	private String orderId;
	/**1可用，0不可用*/
	private Integer status = 1;
	
	private CouponInfo couponInfo;
	
	public SimpleCoupon() {
		super();
	}
	
	public SimpleCoupon(String orderId, CouponInfo couponInfo) {
		super();
		this.orderId = orderId;
		this.couponInfo = couponInfo;
	}
	
	public void disenabled() {
		status = 0;
	}
}
