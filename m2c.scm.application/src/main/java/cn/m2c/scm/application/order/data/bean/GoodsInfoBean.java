package cn.m2c.scm.application.order.data.bean;

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
	@ColumnAlias(value="goods_main_images")
	private String goodsImage;
	/**
	 * 商品信息<名称>
	 */
	@ColumnAlias(value="goods_name")
	private String goodsName;
	/**
	 * 商品信息<规格>
	 */
	@ColumnAlias(value = "stantard_name")
	private String stantardName;
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
	@ColumnAlias(value = "_price")
	private Integer price;
	/**
	 * 单位
	 */
	@ColumnAlias(value = "goods_unit")
	private String unitName;
	/**
	 * 商品金额(单价 * 数量)
	 */
	private Integer totalPrice;
	/**
	 * 运费
	 */
	@ColumnAlias(value = "freight")
	private Integer freight;

	public String getGoodsImage() {
		return goodsImage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getStantardName() {
		return stantardName;
	}

	public String getMediaResId() {
		return mediaResId;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public Integer getPrice() {
		return price;
	}

	public String getUnitName() {
		return unitName;
	}

	public Integer getTotalPrice() {
		return totalPrice;
	}

	public Integer getFreight() {
		return freight;
	}

	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public void setStantardName(String stantardName) {
		this.stantardName = stantardName;
	}

	public void setMediaResId(String mediaResId) {
		this.mediaResId = mediaResId;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setFreight(Integer freight) {
		this.freight = freight;
	}

	@Override
	public String toString() {
		return "GoodsInfoBean [goodsImage=" + goodsImage + ", goodsName=" + goodsName + ", stantardName=" + stantardName
				+ ", mediaResId=" + mediaResId + ", sellNum=" + sellNum + ", price=" + price + ", unitName=" + unitName
				+ ", totalPrice=" + totalPrice + ", freight=" + freight + "]";
	}

}
