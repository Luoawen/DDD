package cn.m2c.scm.application.order;

import cn.m2c.scm.application.utils.Utils;

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
		return goodsMoney/100;
	}
	
	public String getStrGoodsMoney() {
		return Utils.moneyFormatCN(goodsMoney);
	}

	public void setGoodsMoney(int goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public int getFreight() {
		return freight/100;
	}
	
	public String getStrFreight() {
		return Utils.moneyFormatCN(freight);
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public int getPlateformDiscount() {
		return plateformDiscount/100;
	}
	
	public String getStrPlateformDiscount() {
		return Utils.moneyFormatCN(plateformDiscount);
	}

	public void setPlateformDiscount(int plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public int getDealerDiscount() {
		return dealerDiscount/100;
	}
	
	public String getStrDealerDiscount() {
		return Utils.moneyFormatCN(dealerDiscount);
	}

	public void setDealerDiscount(int dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
}
