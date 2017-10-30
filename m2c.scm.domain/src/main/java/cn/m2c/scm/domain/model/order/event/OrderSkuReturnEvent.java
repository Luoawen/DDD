package cn.m2c.scm.domain.model.order.event;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单退货事件
 * @author fanjc
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class OrderSkuReturnEvent implements DomainEvent {
	/**退货商品及数量*/
	private Map<String, Integer> skus;
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String orderNo;
    
    private String dealerOrderNo;
    
    private String afterSaleOrderNo;
    /**实际退货金额*/
    private long goodsMoney = 0;
    
	public String getDealerOrderNo() {
		return dealerOrderNo;
	}

	public String getAfterSaleOrderNo() {
		return afterSaleOrderNo;
	}

	public long getGoodsMoney() {
		return goodsMoney;
	}

	public OrderSkuReturnEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public OrderSkuReturnEvent(String orderNo, Map<String, Integer> s, String dealerOrderNo, String afterSaleNo) {
		this();
		skus = s;
		this.orderNo = orderNo;
		this.dealerOrderNo = dealerOrderNo;
		this.afterSaleOrderNo = afterSaleNo;
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
		return skus;
	}
	
	public String getOrderNo() {
		return orderNo;
	}
	
	public static void main(String[] args) {
		/*Gson gson = new Gson();
		Map<String, Integer> s = new HashMap<String, Integer>();
		s.put("2222", 2);
		OrderPayedEvent a = new OrderPayedEvent(s);
		System.out.print(gson.toJson(a));*/
	}
}
