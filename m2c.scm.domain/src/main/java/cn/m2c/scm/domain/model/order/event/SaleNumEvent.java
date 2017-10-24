package cn.m2c.scm.domain.model.order.event;

import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.domain.model.DomainEvent;
/***
 * 订单支付成功时的商品销量事件
 * @author 89776
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class SaleNumEvent implements DomainEvent {
	/**销量*/
	private List<SimpleSale> sales;
	
	private Date occurredOn;
	
    private int eventVersion;
	
	public SaleNumEvent() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public SaleNumEvent(List<SimpleSale> s) {
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

	public List<SimpleSale> getSales() {
		return sales;
	}
	
	/*public static void main(String[] args) {
		Gson gson = new Gson();
		List<SimpleSale> s = new ArrayList<SimpleSale>();
		s.add(new SimpleSale("2222", 2));
		SaleNumEvent a = new SaleNumEvent(s);
		System.out.print(gson.toJson(a));
	}*/
}
