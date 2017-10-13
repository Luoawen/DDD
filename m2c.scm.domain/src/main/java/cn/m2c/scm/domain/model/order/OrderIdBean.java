package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.IdentifiedDomainObject;
/***
 * 订单ID Bean
 * @author fanjc
 *
 */
public class OrderIdBean extends IdentifiedDomainObject {

	private static final long serialVersionUID = 1L;
	
	private String time;
	
	private String suffix;
	
	private String orderNo;
	
	public OrderIdBean() {
		super();
	}
	
	public OrderIdBean(String time, String suffix) {
		super();
		this.time = time;
		this.suffix = suffix;
		orderNo = time + suffix;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	
}
