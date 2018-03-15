package cn.m2c.scm.domain.model.order.event;

import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 下单时媒体广告位媒体事件 
 */
public class MediaOrderCreateEvent implements DomainEvent {
	
	/**
	 * key 平台订单号,val 为订单中商品与媒体数据
	 */
	private Map<String, List<MediaGoods>> data = null;
	
	private Date occurredOn;
    private int eventVersion;
    
    public MediaOrderCreateEvent() {
    	super();
    	occurredOn = new Date();
        eventVersion = 1;
    }
	
    public MediaOrderCreateEvent(Map<String, List<MediaGoods>> datas) {
    	this();
    	data = datas;
    }
    
	@Override
	public int eventVersion() {
		return this.eventVersion;
	}

	@Override
	public Date occurredOn() {
		return this.occurredOn;
	}
	
	public Map<String, List<MediaGoods>> getData() {
		return data;
	}
}
