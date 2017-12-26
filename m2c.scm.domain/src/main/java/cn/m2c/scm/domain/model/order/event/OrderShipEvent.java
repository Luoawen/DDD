package cn.m2c.scm.domain.model.order.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 商家发货事件
 * 
 * @author lqwen
 *
 */
public class OrderShipEvent implements DomainEvent {

	private String orderId;

	private String shopName;

	private String com;

	private String nu;

	private Date occurredOn;
	private int eventVersion;

	public OrderShipEvent(String orderId, String shopName, String com, String nu) {
		this();
		this.orderId = orderId;
		this.shopName = shopName;
		this.com = com;
		this.nu = nu;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getShopName() {
		return shopName;
	}

	public String getCom() {
		return com;
	}

	public String getNu() {
		return nu;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setCom(String com) {
		this.com = com;
	}

	public void setNu(String nu) {
		this.nu = nu;
	}

	public OrderShipEvent() {
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
