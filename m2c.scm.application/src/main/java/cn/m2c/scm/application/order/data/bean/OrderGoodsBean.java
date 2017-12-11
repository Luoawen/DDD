package cn.m2c.scm.application.order.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 订单中的商品信息 
 * @author fanjc
 *
 */
public class OrderGoodsBean {
	
	/**
	 * 商品信息<名称>
	 */
	@ColumnAlias(value = "goods_name")
	private String goodsName;
	/** 规格名称 */
	@ColumnAlias(value = "sku_name")
	private String skuName;
	/** 规格ID **/
	@ColumnAlias(value = "sku_id")
	private String skuId;
	/**
	 * 广告位信息
	 */
	@ColumnAlias(value = "media_res_id")
	private String mediaResId;
	/**
	 * 商品数量
	 */
	@ColumnAlias(value = "sell_num")
	private Integer sellNum;
	/**
	 * 单价
	 */
	@ColumnAlias(value = "discount_price")
	private long price;
	
	@ColumnAlias(value = "supply_price")
	private long supplyPrice;
	
	@ColumnAlias(value = "goods_id")
	private String goodsId;
	/**
	 * 运费
	 */
	@ColumnAlias(value = "freight")
	private long freight;
	
	@ColumnAlias(value = "goods_amount")
	private long goodsAmount;
	
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;
	
	@ColumnAlias(value = "rate")
	private Float rate;
	
	@ColumnAlias(value = "bds_rate")
	private String bdsRate;
	@ColumnAlias(value = "media_id")
	private String mediaId;
	@ColumnAlias(value = "saler_user_id")
	private String salerUserId;
	@ColumnAlias(value = "saler_user_rate")
	private String salerUserRate;
	
	@ColumnAlias(value="dealer_order_id")
	private String dealerOrderId;

	@ColumnAlias(value="change_price")
	private long changePrice;
	@ColumnAlias(value="is_change")
	private Integer isChange;
	@ColumnAlias(value="res_rate")
	private Float resRate;
	@ColumnAlias(value="marketing_id")
	private String marketId;
	@ColumnAlias(value="market_level")
	private Integer level;
	
	@ColumnAlias(value="is_special")
	private Integer isSpecial;
	@ColumnAlias(value="special_price")
	private Long specialPrice;
	@ColumnAlias(value="sort_no")
	private Integer sortNo;
	
	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Long getSpecialPrice() {
		return specialPrice;
	}

	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getMarketId() {
		return marketId;
	}

	public void setMarketId(String marketId) {
		this.marketId = marketId;
	}

	public Float getResRate() {
		return resRate;
	}

	public void setResRate(Float resRate) {
		this.resRate = resRate;
	}
	
	public long getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(long changePrice) {
		this.changePrice = changePrice;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getMediaResId() {
		return mediaResId;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public long getPrice() {
		return price;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public long getFreight() {
		return freight;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setMediaResId(String mediaResId) {
		this.mediaResId = mediaResId;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public long getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(long supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public long getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(long goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public Float getRate() {
		return rate;
	}

	public void setRate(Float rate) {
		this.rate = rate;
	}

	public String getBdsRate() {
		return bdsRate;
	}

	public void setBdsRate(String bdsRate) {
		this.bdsRate = bdsRate;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getSalerUserId() {
		return salerUserId;
	}

	public void setSalerUserId(String salerUserId) {
		this.salerUserId = salerUserId;
	}

	public String getSalerUserRate() {
		return salerUserRate;
	}

	public void setSalerUserRate(String salerUserRate) {
		this.salerUserRate = salerUserRate;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	@Override
	public String toString() {
		return "OrderGoodsBean [goodsImage=, goodsName=" + goodsName + ", goodsSubTitle="
				+ ", freight=" + freight + "]";
	}

}
