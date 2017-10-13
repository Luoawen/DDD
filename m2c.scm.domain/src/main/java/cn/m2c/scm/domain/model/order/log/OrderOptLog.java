package cn.m2c.scm.domain.model.order.log;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;
/***
 * 订单操作日志实体
 * @author fanjc
 *
 */
public class OrderOptLog extends IdentifiedValueObject {
	
	private String orderId;
	
	private String dealerOrderId;
	/**操作内容*/
	private String optContent;
	/**操作人*/
	private String optUser;
	
	public OrderOptLog(String orderId, String dealerOrderId,
			String optContent, String optUser) {
		super();
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.optContent = optContent;
		this.optUser = optUser;
	}
	
	public OrderOptLog() {
		super();
	}
}
