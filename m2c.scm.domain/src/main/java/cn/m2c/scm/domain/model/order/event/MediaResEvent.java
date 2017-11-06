package cn.m2c.scm.domain.model.order.event;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单支付成功时的商品媒体事件
 * @author fanjc
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class MediaResEvent implements DomainEvent {
	/**媒体资源及专员*/
	private List<SimpleMediaRes> reses;
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String orderNo;
	
	public MediaResEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public MediaResEvent(String orderNo, List<SimpleMediaRes> s) {
		this();
		reses = s;
		this.orderNo = orderNo;
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

	public List<SimpleMediaRes> getReses() {
		return reses;
	}

	public Date getOccurredOn() {
		return occurredOn;
	}

	public int getEventVersion() {
		return eventVersion;
	}

	public String getOrderNo() {
		return orderNo;
	}
	
	public static void main(String[] args) {
		List<SimpleMediaRes> s = new ArrayList<SimpleMediaRes>();
		s.add(new SimpleMediaRes("2222", "{'id':'1','rate':'2'}", "skuId", 6000, "aaa", "ddd"));
		MediaResEvent a = new MediaResEvent("11111", s);
		System.out.print(JSONObject.toJSONString(a));
	}
}
