package cn.m2c.scm.domain.model.order.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import com.google.gson.Gson;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/***
 * 订单支付成功时的商品销量事件
 * @author 89776
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class OrderPayedEvent implements DomainEvent {
	/**销量*/
	private Map<String, Integer> sales;
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String orderNo;
    
    private List<SimpleMediaRes> reses;
    
    private Map<String, Object> markets;
	
    private Date payTime;
    
	public Date getPayTime() {
		return payTime;
	}

	public OrderPayedEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public OrderPayedEvent(String orderNo, Map<String, Integer> s, List<SimpleMediaRes> reses, Map<String, Object> markets
			, Date payTime) {
		this();
		sales = s;
		this.orderNo = orderNo;
		this.reses = reses;
		this.markets = markets;
		this.payTime = payTime;
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
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public List<SimpleMediaRes> getReses() {
		return reses;
	}
	
	public Map<String, Object> getMarkets() {
		return markets;
	}
	
	public static void main(String[] args) {
		/*Gson gson = new Gson();
		Map<String, Integer> s = new HashMap<String, Integer>();
		s.put("2222", 2);
		OrderPayedEvent a = new OrderPayedEvent("123456",  s, null, null, null);
		System.out.print(gson.toJson(a));*/
		
		/*for(int i=0; i<8; i++) {
			System.out.println(UUID.randomUUID().toString().replace("-", ""));
		}*/
	}
}
