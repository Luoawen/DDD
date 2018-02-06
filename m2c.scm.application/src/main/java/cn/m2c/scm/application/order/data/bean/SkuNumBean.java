package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 订单中的商品SKU及数量对应
 * @author fanjc
 * created date 2017年10月19日
 * copyrighted@m2c
 */
public class SkuNumBean {
	
	@ColumnAlias(value = "sku_id")
	private String skuId; //skuId
	
	@ColumnAlias(value = "sell_num")
	private Integer num; // 数量
	
	@ColumnAlias(value = "is_change")
	private Integer isChange; // 是否为换货商品，0 否， 1是
	
	@ColumnAlias(value = "goods_amount")
	private long goodsAmount; // 商品金额
	
	@ColumnAlias(value = "marketing_id")
	private String marketId; // 营销活动
	@ColumnAlias(value = "coupon_id")
	private String couponId; // 优惠券
	
	private long couponDiscount = 0;
	
	@ColumnAlias(value = "change_price")
	private Long changePrice; // 换购价
	/**优惠金额*/
	@ColumnAlias(value = "discountMoney")
	private long discountMoney = 0;
	
	@ColumnAlias(value = "sort_no")
	private Integer sortNo;
	// 满减状态
	@ColumnAlias(value = "_status")
	private int status = 0;
	@ColumnAlias(value = "market_type")
	private Integer marketType = 0;
	
	
	public Integer getMarketType() {
		return marketType;
	}

	public void setMarketType(Integer marketType) {
		this.marketType = marketType;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getChangePrice() {
		if (changePrice == null)
			changePrice = 0l;
		return changePrice;
	}

	public void setChangePrice(Long changePrice) {
		this.changePrice = changePrice;
	}
	
	public long getDiscountMoney() {
		return discountMoney;
	}

	public void setDiscountMoney(long discountMoney) {
		this.discountMoney = discountMoney;
	}

	public String getSkuId() {
		return skuId;
	}
	
	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}
	
	public Integer getNum() {
		return num;
	}
	
	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public long getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(long goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public long getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	} 
	
	
}
