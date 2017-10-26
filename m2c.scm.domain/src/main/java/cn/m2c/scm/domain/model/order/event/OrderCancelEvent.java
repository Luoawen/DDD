package cn.m2c.scm.domain.model.order.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单支付成功时的商品销量事件
 * @author 89776
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class OrderCancelEvent implements DomainEvent {
	/**销量*/
	private Map<String, Integer> sales;
	
	private Date occurredOn;
	
    private int eventVersion;
	
	public OrderCancelEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public OrderCancelEvent(Map<String, Integer> s) {
		this();
		sales = s;
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

	public Map<String, Integer> getSales() {
		return sales;
	}
	
	public static void main(String[] args) {
		/*Gson gson = new Gson();
		Map<String, Integer> s = new HashMap<String, Integer>();
		s.put("2222", 2);
		OrderPayedEvent a = new OrderPayedEvent(s);
		System.out.print(gson.toJson(a));*/
	}
}
