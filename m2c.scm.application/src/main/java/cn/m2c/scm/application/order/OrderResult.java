package cn.m2c.scm.application.order;
/***
 * 订单提交返回的结果
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
public class OrderResult {

	private String orderId;
	
	private int goodsMoney = 0;
	
	private int freight = 0;
	
	private int plateformDiscount;
	
	private int dealerDiscount;

	public OrderResult() {
		
	}
	
	public OrderResult(String orderNo, int goodsMoney, 
			int freight, int plateformDiscount, int dealerDiscount) {
		orderId = orderNo;
		this.goodsMoney = goodsMoney;
		this.freight = freight;
		this.plateformDiscount = plateformDiscount;
		this.dealerDiscount = dealerDiscount;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public int getGoodsMoney() {
		return goodsMoney;
	}

	public void setGoodsMoney(int goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public int getFreight() {
		return freight;
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public int getPlateformDiscount() {
		return plateformDiscount;
	}

	public void setPlateformDiscount(int plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public int getDealerDiscount() {
		return dealerDiscount;
	}

	public void setDealerDiscount(int dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
}
