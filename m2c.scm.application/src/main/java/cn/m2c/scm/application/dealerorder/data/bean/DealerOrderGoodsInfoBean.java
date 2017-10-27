package cn.m2c.scm.application.dealerorder.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerOrderGoodsInfoBean {
	/**
	 * 商品信息<图片>
	 */
	@ColumnAlias(value = "goods_main_images")
	private String goodsImage;
	/**
	 * 商品信息<名称>
	 */
	@ColumnAlias(value = "goods_name")
	private String goodsName;
	/**
	 * 商品信息<规格>
	 */
	@ColumnAlias(value = "goods_specifications")
	private String stantardName;
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
	 * 商品总额
	 */
	@ColumnAlias(value = "goods_amount")
	private long totalPrice;
	/**
	 * 商品金额
	 */
	private long goodsMoney;
	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer dealerOrderStatus;

	public String getGoodsImage() {
		return goodsImage;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public long getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getStantardName() {
		return stantardName;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public long getGoodsMoney() {
		return goodsMoney;
	}

	public void setGoodsMoney(long goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public long getPrice() {
		return price;
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

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public Integer getDealerOrderStatus() {
		return dealerOrderStatus;
	}

	public void setDealerOrderStatus(Integer dealerOrderStatus) {
		this.dealerOrderStatus = dealerOrderStatus;
	}

}
