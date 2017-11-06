package cn.m2c.scm.domain.model.order.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单提交成功事件
 * @author fanjc
 * created date 2017年10月31日
 * copyrighted@m2c
 */
public class OrderAddedEvent implements DomainEvent {
	/**提交上来的数据*/
	private Map<String, Integer> skus;
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String userId;
    
    private String orderNo;
    
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

	public OrderAddedEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	public OrderAddedEvent(String userId, Map<String, Integer> skus, String orderNo) {
		this();
		this.userId = userId;
		this.skus = skus;
		this.orderNo = orderNo;
	}
	
	public Map<String, Integer> getSkus() {
		return skus;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		Map<String, Integer> s = new HashMap<String, Integer>();
		s.put("2222", 2);
		OrderAddedEvent a = new OrderAddedEvent("123456",  s, "114");
		System.out.print(gson.toJson(a));
	}
}
