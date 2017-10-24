package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class ShippingOrderBean {
	/**
	 * 收货人
	 */
	@ColumnAlias(value = "rev_person")
	private String revPerson;
	/**
	 * 收货电话号码
	 */
	@ColumnAlias(value = "rev_phone")
	private String revPhone;
	/**
	 * 订单号
	 */
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	/**
	 * 下单时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 运费
	 */
	@ColumnAlias(value = "order_freight")
	private long orderFreight;
	/**
	 * 订单总额
	 */
	private long totalOrderMoney;
	/**
	 * 优惠金额
	 */
	private long discountMoney;
	/**
	 * 收货地址省
	 */
	@ColumnAlias(value = "province")
	private String province;
	/**
	 * 收货地址 市
	 */
	@ColumnAlias(value = "city")
	private String city;
	/**
	 * 区或县镇
	 */
	@ColumnAlias(value = "area_county")
	private String areaCounty;
	/**
	 * 街道详细地址
	 */
	@ColumnAlias(value = "street_addr")
	private String streetAddr;
	/**
	 * 留言
	 */
	@ColumnAlias(value = "noted")
	private String noted;
	/**
	 * 商家店铺名称
	 */
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	/**
	 * 商家优惠金额
	 */
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/**
	 * 平台优惠金额
	 */
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;

	private List<DealerOrderGoodsInfo> goodsInfos;

	public String getRevPerson() {
		return revPerson;
	}

	public String getRevPhone() {
		return revPhone;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public List<DealerOrderGoodsInfo> getGoodsInfos() {
		return goodsInfos;
	}

	public void setGoodsInfos(List<DealerOrderGoodsInfo> goodsInfos) {
		this.goodsInfos = goodsInfos;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public long getTotalOrderMoney() {
		return totalOrderMoney;
	}

	public long getOrderFreight() {
		return orderFreight;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public long getPlateformDiscount() {
		return plateformDiscount;
	}

	public void setOrderFreight(long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public long getDiscountMoney() {
		return discountMoney;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getAreaCounty() {
		return areaCounty;
	}

	public String getStreetAddr() {
		return streetAddr;
	}

	public String getNoted() {
		return noted;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}

	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setTotalOrderMoney(long totalOrderMoney) {
		this.totalOrderMoney = totalOrderMoney;
	}

	public void setDiscountMoney(long discountMoney) {
		this.discountMoney = discountMoney;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	public void setNoted(String noted) {
		this.noted = noted;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Override
	public String toString() {
		return "ShippingOrderBean [revPerson=" + revPerson + ", revPhone=" + revPhone + ", dealerOrderId="
				+ dealerOrderId + ", createdDate=" + createdDate + ", orderFreight=" + orderFreight
				+ ", totalOrderMoney=" + totalOrderMoney + ", discountMoney=" + discountMoney + ", province=" + province
				+ ", city=" + city + ", areaCounty=" + areaCounty + ", streetAddr=" + streetAddr + ", noted=" + noted
				+ ", dealerName=" + dealerName + ", dealerDiscount=" + dealerDiscount + ", plateformDiscount="
				+ plateformDiscount + "]";
	}

}
