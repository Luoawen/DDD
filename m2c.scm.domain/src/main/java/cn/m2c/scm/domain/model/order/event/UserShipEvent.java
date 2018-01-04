package cn.m2c.scm.domain.model.order.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 用户退货换货 发货事件
 * @author lqwen
 *
 */
public class UserShipEvent implements DomainEvent{
	private String com;
	private String nu;
	private Date shipGoodsTime;         //发货时间
	private Integer shipType;           //发货类型：0 商家发货物流信息 1 用户售后寄回物流信息
	
	private Date occurredOn;
	private int eventVersion;
	
	public UserShipEvent(String com,String nu) {
		this();
		this.com = com;
		this.nu = nu;
		this.shipType = 1;
		this.shipGoodsTime = new Date();
	}
	
	public UserShipEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}

	@Override
	public int eventVersion() {
		return eventVersion;
	}

	@Override
	public Date occurredOn() {
		return occurredOn;
	}

}
