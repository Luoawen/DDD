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
	
	@ColumnAlias(value = "change_price")
	private Long changePrice; // 换购价
	/**优惠金额*/
	@ColumnAlias(value = "discountMoney")
	private long discountMoney = 0;
	
	@ColumnAlias(value = "sort_no")
	private Integer sortNo;
	// 满减状态
	@ColumnAlias(value = "_status")
	private Integer status = 0;
	
	@ColumnAlias(value = "special_price")
	private Long specialPrice;
	
	@ColumnAlias(value = "discount_price")
	private Long discountPrice;
	
	@ColumnAlias(value = "is_special")
	private Integer isSpecial;
	/**优惠券优惠金额*/
	private long couponMoney = 0;
	
	public Long getSpecialPrice() {
		if (specialPrice == null)
			specialPrice = 0l;
		return specialPrice;
	}

	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Long getDiscountPrice() {
		if (discountPrice == null)
			discountPrice = 0l;
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getIsSpecial() {
		if (isSpecial == null)
			isSpecial = 0;
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public long getCouponMoney() {
		return couponMoney;
	}

	public void setCouponMoney(long couponMoney) {
		this.couponMoney = couponMoney;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getStatus() {
		if (status == null)
			status = 0;
		return status;
	}

	public void setStatus(Integer status) {
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
		if (null == num)
			num = 0;
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
		return getThePrice() * getNum();
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
	/***
	 * 获取执行价格
	 * @return
	 */
	public long getThePrice() {
		if (isSpecial != null && isSpecial == 1)
			return getSpecialPrice();
		else
		    return getDiscountPrice();
	}
	@Override
	public SkuNumBean clone() {
		SkuNumBean newBean = new SkuNumBean();
		newBean.setChangePrice(changePrice);
		newBean.setCouponId(couponId);
		newBean.setDiscountPrice(discountPrice);
		newBean.setIsChange(isChange);
		newBean.setIsSpecial(isSpecial);
		newBean.setMarketId(marketId);
		newBean.setNum(num);
		newBean.setSkuId(skuId);
		newBean.setSortNo(sortNo);
		newBean.setSpecialPrice(specialPrice);
		newBean.setStatus(status);
		return newBean;
	}
}
