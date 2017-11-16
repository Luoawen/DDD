package cn.m2c.scm.domain.model.order.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;
import com.google.gson.Gson;

import java.util.Date;

/***
 * 售后单同意退款成功事件
 * @author fanjc
 * created date 2017年11月14日
 * copyrighted@m2c
 */
public class AfterRefundSuccEvt implements DomainEvent {
	
	private Date occurredOn;
	
    private int eventVersion;
    
    private String afterSellOrderId;
    /**订单号*/
    private String orderId;
    /**商家订单号*/
    private String dealerOrderId;
    /**商家ID*/
    private String dealerId;
    /**退款金额*/
    private long refundMoney;
    /**商品sku ID*/
    private String skuId;
    /**数量*/
    private int num;
    /**运费退款金额*/
    private long rtFreight = 0;
    
	public AfterRefundSuccEvt() {
		super();
		occurredOn = new Date();
		eventVersion = 1;
	}
	
	public AfterRefundSuccEvt(String saleAfterNo, String orderId, String dealerOrderId, 
			String dealerId, long money, long rtFreight, int num) {
		this();
		this.afterSellOrderId = saleAfterNo;
		this.dealerId = dealerId;
		this.refundMoney = money;
		
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.dealerId = dealerId;
		
		this.rtFreight = rtFreight;
		this.num = num;
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
	
	public Date getOccurredOn() {
		return occurredOn;
	}

	public int getEventVersion() {
		return eventVersion;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public int getNum() {
		return num;
	}

	public long getRtFreight() {
		return rtFreight;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
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
		AfterRefundSuccEvt a = new AfterRefundSuccEvt("123456", "AFAA3333", "a85adfsf", "dealerId", 3000, 0, 0);
		System.out.print(gson.toJson(a));
	}
	
}
