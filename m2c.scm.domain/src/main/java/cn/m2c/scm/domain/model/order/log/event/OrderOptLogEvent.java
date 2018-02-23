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
    /**日志类型 1为订单日志，2为商家订货单日志，3为用户私有日志（暂不用）*/
    private int type = 1;
    /**日志类型 1为订单日志，2为商家订货单日志，3为用户私有日志（暂不用）*/
	public OrderOptLogEvent(String orderId, String dealerOrderId,
			String optContent, String optUser, int type) {
		this();
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.optContent = optContent;
		this.optUser = optUser;
		this.type = type;
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
