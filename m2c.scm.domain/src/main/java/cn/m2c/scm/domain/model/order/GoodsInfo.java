package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;
/***
 * 商品信息值对象
 * @author fanjc
 * created date 2017年10月18日
 * copyrighted@m2c
 */
public class GoodsInfo extends ValueObject {
	/**分类费率*/
	private Float rate;
	/**商品id*/
	private String goodsId;
	/**商品名称*/
	private String goodsName;
	/**商品副标题*/
	private String goodsTitle;
	/**商品分类名称*/
	private String goodsType;
	/**商品分类id*/
	private String goodsTypeId;
	/**计量单位*/
	private String goodsUnit;
	/**规格id*/
	private String skuId;
	/**规格名称*/
	private String skuName;
	/**市场价(单价)*/
	private Long price;
	/**重量*/
	private Float weight = 0f;
	/**供货价*/
	private Long supplyPrice;
	/**拍获价(单价)*/
	private Long discountPrice;
	/**商品小图url*/
	private String goodsIcon;
	
	private Integer sellNum;
	
	private Long freight;
	/**平台优惠*/
	private Long plateformDiscount;
	
	public Integer getSellNum() {
		return sellNum;
	}

	public Long getFreight() {
		return freight;
	}

	public GoodsInfo() {
		super();
	}
	
	public GoodsInfo(float rate, String goodsId, String goodsName, String goodsTitle
			,String goodsType, String goodsTypeId, String goodsUnit, String skuId
			,String skuName, long price, long supplyPrice, long discountPrice, String goodsIcon
			,float weight, int sellNum, Long freight, long plateformDiscount) {
		this.rate = rate;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsTitle = goodsTitle;
		this.goodsType = goodsType;
		this.goodsTypeId = goodsTypeId;
		this.goodsUnit = goodsUnit;
		this.skuId = skuId;
		this.skuName = skuName;
		this.price = price;
		this.supplyPrice = supplyPrice;
		this.discountPrice = discountPrice;
		this.goodsIcon = goodsIcon;
		this.weight = weight;
		this.sellNum = sellNum;
		this.freight = freight;
		this.plateformDiscount = plateformDiscount;
	}
	
	public long getDiscountPrice() {
		return discountPrice;
	}
	
	String getSkuId() {
		return skuId;
	}
	
	long getPlateformDiscount() {
		return plateformDiscount;
	}
	
	void setPlateformDiscount(long discount) {
		plateformDiscount = discount;
	}
	
	long calGoodsAmount() {
		return discountPrice * sellNum;
	}
}
