package cn.m2c.scm.application.order.data.bean;

import javax.persistence.Column;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品信息
 * 
 * @author lqwen
 *
 */
public class GoodsInfoBean {
	/**
	 * 商品信息<图片>
	 */
	@ColumnAlias(value = "goods_icon")
	private String goodsImage;
	/**
	 * 商品信息<名称>
	 */
	@ColumnAlias(value = "goods_name")
	private String goodsName;
	/** 商品副标题 */
	@ColumnAlias(value = "goods_sub_title")
	private String goodsSubTitle;
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
	/**
	 * 单位
	 */
	@ColumnAlias(value = "goods_unit")
	private String unitName;
	/**
	 * 商品金额(单价 * 数量)
	 */
	@ColumnAlias(value = "goods_amount")
	private long totalPrice;
	
	@ColumnAlias(value = "afNum")
	private Integer afNum;
	/**
	 * 运费
	 */
	@ColumnAlias(value = "freight")
	private long freight;
	
	@ColumnAlias(value = "is_change")
	private Integer isChange;
	
	@ColumnAlias(value = "change_price")
	private Long changePrice;
	
	@ColumnAlias(value = "special_price")
	private long specialPrice;
	
	@ColumnAlias(value = "is_special")
	private Integer isSpecial;
	
	@ColumnAlias(value="sort_no")
	private Integer sortNo;
	
	public Integer getSortNo() {
		return sortNo;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	
	public long getSpecialPrice() {
		return specialPrice;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setSpecialPrice(long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Long getChangePrice() {
		return changePrice;
	}

	public void setChangePrice(Long changePrice) {
		this.changePrice = changePrice;
	}

	public Integer getIsChange() {
		return isChange;
	}

	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}

	public Integer getAfNum() {
		if (afNum == null)
			afNum = 0;
		return afNum;
	}

	public void setAfNum(Integer afNum) {
		this.afNum = afNum;
	}

	public String getGoodsImage() {
		return goodsImage;
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

	public String getUnitName() {
		return unitName;
	}

	/*public long getTotalPrice() {
		if (isChange != null && isChange == 1)
			totalPrice = changePrice * sellNum;
		else
			totalPrice = price * sellNum;
		return totalPrice;
	}*/
	public long getTotalPrice() {
		return totalPrice;
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

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
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

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setFreight(long freight) {
		this.freight = freight;
	}

	public String getGoodsSubTitle() {
		return goodsSubTitle;
	}

	public void setGoodsSubTitle(String goodsSubTitle) {
		this.goodsSubTitle = goodsSubTitle;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	@Override
	public String toString() {
		return "GoodsInfoBean [goodsImage=" + goodsImage + ", goodsName=" + goodsName + ", goodsSubTitle="
				+ goodsSubTitle + ", skuName=" + skuName + ", skuId=" + skuId + ", mediaResId=" + mediaResId
				+ ", sellNum=" + sellNum + ", price=" + price + ", unitName=" + unitName + ", totalPrice=" + totalPrice
				+ ", freight=" + freight + "]";
	}

}
