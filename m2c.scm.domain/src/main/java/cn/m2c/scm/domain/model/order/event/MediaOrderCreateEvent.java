package cn.m2c.scm.domain.model.order.event;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 下单时媒体广告位媒体事件 
 */
public class MediaOrderCreateEvent implements DomainEvent {
	/**
	 * 平台订单号
	 */
	private String orderId;
	/**
	 * 商家订单号
	 */
	private String dealerOrderId;
	/**
	 * 媒体id
	 */
	private String mediaId;
	/**
	 * 广告位id
	 */
	private String mediaResId;
	/**sort no商家在订单中的插入位置*/
	private int sortNo;
	
	private Date occurredOn;
    private int eventVersion;
	
    public MediaOrderCreateEvent(String orderId, String dealerOrderId, String mediaId, String mediaResId
    		, int sortNo) {
    	this.orderId = orderId;
    	this.dealerOrderId = dealerOrderId;
    	this.mediaId = mediaId;
    	this.mediaResId = mediaResId;
    	this.occurredOn = new Date();
        this.eventVersion = 1;
        this.sortNo = sortNo;
    }
    
	@Override
	public int eventVersion() {
		return this.eventVersion;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}

}
