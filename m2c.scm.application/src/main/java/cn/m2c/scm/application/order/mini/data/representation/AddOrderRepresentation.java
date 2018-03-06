package cn.m2c.scm.application.order.mini.data.representation;

import cn.m2c.scm.application.order.OrderResult;
import cn.m2c.scm.application.utils.Utils;

/***
 * 订单提交返回的结果展示
 * @author fanjc
 * created date 2018年03月06日
 * copyrighted@m2c
 */
public class AddOrderRepresentation {

	private String orderId;
	
	private long goodsMoney = 0;
	
	private long freight = 0;
	
	private long plateformDiscount;
	
	private long dealerDiscount;

	public AddOrderRepresentation() {
		
	}
	
	public AddOrderRepresentation(OrderResult bean) {
		if (bean == null)
			return;
		orderId = bean.getOrderId();
		this.goodsMoney = bean.getGoodsMoney();
		this.freight = bean.getFreight();
		this.plateformDiscount = bean.getPlateformDiscount();
		this.dealerDiscount = bean.getDealerDiscount();
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getStrGoodsMoney() {
		return Utils.moneyFormatCN(goodsMoney);
	}

	public void setGoodsMoney(int goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public String getStrFreight() {
		return Utils.moneyFormatCN(freight);
	}

	public void setFreight(int freight) {
		this.freight = freight;
	}

	public String getStrPlateformDiscount() {
		return Utils.moneyFormatCN(plateformDiscount);
	}

	public void setPlateformDiscount(int plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public String getStrDealerDiscount() {
		return Utils.moneyFormatCN(dealerDiscount);
	}

	public void setDealerDiscount(int dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
}
