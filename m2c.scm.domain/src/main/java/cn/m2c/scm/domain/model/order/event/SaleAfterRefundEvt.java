package cn.m2c.scm.domain.model.order.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import com.google.gson.Gson;

import java.util.Date;

/***
 * 售后单同意退款需要发事件通知退款
 * @author fanjc
 * created date 2017年11月14日
 * copyrighted@m2c
 */
public class SaleAfterRefundEvt implements DomainEvent {
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String afterSellOrderId;
    /**原订单支付单号*/
    private String orderPayId;
    
    private String dealerId;
    
    private long refundMoney;
    
	public SaleAfterRefundEvt() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public SaleAfterRefundEvt(String saleAfterNo, String dealerId, long money, String payNo) {
		this();
		this.afterSellOrderId = saleAfterNo;
		this.dealerId = dealerId;
		this.refundMoney = money;
		this.orderPayId = payNo;
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

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public String getOrderPayId() {
		return orderPayId;
	}

	public void setOrderPayId(String orderPayId) {
		this.orderPayId = orderPayId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public long getRefundMoney() {
		return refundMoney;
	}

	public void setRefundMoney(long refundMoney) {
		this.refundMoney = refundMoney;
	}

	public static void main(String[] args) {
		Gson gson = new Gson();
		SaleAfterRefundEvt a = new SaleAfterRefundEvt("123456", "AFAA3333", 3000, "adfa866555aaa");
		System.out.print(gson.toJson(a));
	}
	
}
