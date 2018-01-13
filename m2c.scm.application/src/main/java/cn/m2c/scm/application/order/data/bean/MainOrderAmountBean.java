package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

public class MainOrderAmountBean {
	
	@ColumnAlias(value = "goods_amount")
	private long goodsAmount;
	
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;
	
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	
	@ColumnAlias(value = "coupon_discount")
	private long couponDiscount;

	public long getGoodsAmount() {
		return goodsAmount;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public long getCouponDiscount() {
		return couponDiscount;
	}

	public void setGoodsAmount(long goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	public String getStrAmount() {
		return Utils.moneyFormatCN(this.goodsAmount - this.dealerDiscount - this.plateformDiscount - this.couponDiscount);
	}

}
