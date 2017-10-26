package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.IdentifiedDomainObject;
/***
 * 简单营销策略
 * @author fanjc
 *
 */
public class SimpleMarketing extends IdentifiedDomainObject {

	/**订单ID*/
	private String orderId;
	//private MainOrder order;
	private SimpleMarketInfo marketInfo;
	/**1可用，0不可用*/
	private Integer status = 1;
	
	public SimpleMarketing() {
		super();
	}
	
	public SimpleMarketing(String order, SimpleMarketInfo marketInfo) {
		super();
		this.orderId = order;
		this.marketInfo = marketInfo;
	}
	
	public void disabled() {
		status = 0;
	}
	
	String getMarketingId() {
		if (status > 0 && marketInfo != null)
			return marketInfo.getMarketingId();
		return null;
	}
}
