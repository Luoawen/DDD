package cn.m2c.scm.domain.model.order.log.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单操作日志实体
 * @author fanjc
 *
 */
public class OrderOptLogEvent implements DomainEvent {
	
	private String orderId;
	
	private String dealerOrderId;
	/**操作内容*/
	private String optContent;
	/**操作人*/
	private String optUser;
	
	
	private Date occurredOn;
	
    private int eventVersion;
	
	public OrderOptLogEvent(String orderId, String dealerOrderId,
			String optContent, String optUser ) {
		this();
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.optContent = optContent;
		this.optUser = optUser;
	}
	
	public OrderOptLogEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return eventVersion;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return occurredOn;
	}
}
