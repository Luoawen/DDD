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
	private Integer price;
	/**重量*/
	private Float weight = 0f;
	/**供货价*/
	private Integer supplyPrice;
	/**拍获价(单价)*/
	private Integer discountPrice;
	/**商品小图url*/
	private String goodsIcon;
	
	private Float sellNum;
	
	private Integer freight;
	
	private String marketingId;
	
	public Float getSellNum() {
		return sellNum;
	}

	public Integer getFreight() {
		return freight;
	}

	public GoodsInfo() {
		super();
	}
	
	public GoodsInfo(float rate, String goodsId, String goodsName, String goodsTitle
			,String goodsType, String goodsTypeId, String goodsUnit, String skuId
			,String skuName, int price, int supplyPrice, int discountPrice, String goodsIcon
			,float weight, Float sellNum, Integer freight, String marketingId) {
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
		this.marketingId = marketingId;
	}
	
	public int getDiscountPrice() {
		return discountPrice;
	}
}
