package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.IdentifiedDomainObject;
/***
 * 简单优惠券
 * @author fanjc
 *
 */
public class SimpleCoupon extends IdentifiedDomainObject {

	/**订单ID*/
	//private String orderId;
	private MainOrder order;
	/**优惠券ID*/
	private String couponId;
	/**1可用，0不可用*/
	private Integer status = 1;
	
	public SimpleCoupon() {
		super();
	}
	
	public SimpleCoupon(MainOrder order, String couponId) {
		super();
		this.order = order;
		this.couponId = couponId;
	}
	
	public void disenabled() {
		status = 0;
	}
}
