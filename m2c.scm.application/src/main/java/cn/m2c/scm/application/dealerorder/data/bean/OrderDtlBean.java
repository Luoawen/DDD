package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/***
 * 商家订单详情 BEAN
 * @author fanjc
 * created date 2017年12月20日
 * copyrighted@m2c
 */
public class OrderDtlBean {
	/*** 商品总额 */
	@ColumnAlias(value = "goods_amount")
	private Long goodsMoney;
	/**生成日期*/
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/*** 收货人姓名 */
	@ColumnAlias(value = "rev_person")
	private String revPerson;
	/**
	 * 收货人电话
	 */
	@ColumnAlias(value = "rev_phone")
	private String revPhone;
	/**
	 * 收货人地址
	 */
	@ColumnAlias(value = "street_addr")
	private String revAddress;
	
	@ColumnAlias(value = "province")
	private String province;
	@ColumnAlias(value = "city")
	private String city;
	@ColumnAlias(value = "area_county")
	private String areaCounty;
	/**
	 * 订货单状态
	 */
	@ColumnAlias(value = "_status")
	private Integer orderStatus;
	
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/**
	 * 支付时间
	 */
	@ColumnAlias(value = "pay_time")
	private Date payTime;
	
	/**运费 */
	@ColumnAlias(value = "order_freight")
	private Long orderFreight;
	/**平台优惠 */
	@ColumnAlias(value = "plateform_discount")
	private Long plateDiscount;
	/**商家优惠 */
	@ColumnAlias(value = "dealer_discount")
	private Long dealerDiscount;
	
	@ColumnAlias(value = "discount_price")
	private Long discountPrice;
	/**
	 * 售后期望(退货、换货、仅退款)
	 */
	@ColumnAlias(value = "order_type")
	private Integer afterOrderType;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellDealerOrderId;
	/**
	 * 售后金额
	 */
	@ColumnAlias(value = "back_money")
	private Long afterMoney;
	
	@ColumnAlias(value = "return_freight")
	private Long returnFreight;
	/**
	 * 售后数量
	 */
	@ColumnAlias(value = "afNum")
	private Integer afterNum;
	
	/**数量*/
	@ColumnAlias(value = "sell_num")
	private Integer sellNum;
	/**是否特惠价*/
	@ColumnAlias(value = "is_special")
	private Integer isSpecial;
	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "afStatus")
	private Integer afterStatus;
	
	@ColumnAlias(value = "special_price")
	private Long specialPrice;
	
	@ColumnAlias(value = "goods_name")
	private String goodsName;
	@ColumnAlias(value = "goods_brand_name")
	private String brandName;
	@ColumnAlias(value = "goods_type_id")
	private String typeId;
	@ColumnAlias(value = "sku_id")
	private String skuId;
	@ColumnAlias(value = "sku_name")
	private String skuName;
	/**大类名*/
	private String typeName;
	/**二级类名*/
	private String secondType;
	/**三级类名*/
	private String thirdType;
	
	public String getProvince() {
		if (null == province)
			return "";
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		if (null == city)
			return "";
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAreaCounty() {
		if (null == areaCounty)
			return "";
		return areaCounty;
	}

	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}

	public String getDealerName() {
		if (null == dealerName)
			dealerName = "";
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Long getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Long getReturnFreight() {
		return returnFreight;
	}

	public void setReturnFreight(Long returnFreight) {
		this.returnFreight = returnFreight;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public Integer getIsSpecial() {
		return isSpecial;
	}

	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}

	public Long getSpecialPrice() {
		if (null == specialPrice)
			specialPrice = 0l;
		return specialPrice;
	}

	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getBrandName() {
		if (null == brandName)
			brandName = "";
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getSkuName() {
		return skuName;
	}

	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getSecondType() {
		return secondType;
	}

	public void setSecondType(String secondType) {
		this.secondType = secondType;
	}

	public String getThirdType() {
		return thirdType;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public void setGoodsMoney(Long goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public void setOrderFreight(Long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public void setPlateDiscount(Long plateDiscount) {
		this.plateDiscount = plateDiscount;
	}

	public void setDealerDiscount(Long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setAfterMoney(Long afterMoney) {
		this.afterMoney = afterMoney;
	}

	public Integer getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(Integer afterStatus) {
		this.afterStatus = afterStatus;
	}

	public Long getGoodsMoney() {
		return goodsMoney;
	}

	public void setGoodsMoney(long goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public Long getOrderFreight() {
		return orderFreight;
	}

	public void setOrderFreight(long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public Long getPlateDiscount() {
		return plateDiscount;
	}

	public void setPlateDiscount(long plateDiscount) {
		this.plateDiscount = plateDiscount;
	}

	public Long getDealerDiscount() {
		return dealerDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getRevPerson() {
		if(null == revPerson)
			revPerson = "";
		return revPerson;
	}

	public String getRevPhone() {
		return revPhone;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}


	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}

	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getRevAddress() {
		return revAddress;
	}

	public void setRevAddress(String revAddress) {
		this.revAddress = revAddress;
	}

	public Integer getAfterOrderType() {
		return afterOrderType;
	}

	public void setAfterOrderType(Integer afterOrderType) {
		this.afterOrderType = afterOrderType;
	}

	public String getAfterSellDealerOrderId() {
		return afterSellDealerOrderId;
	}

	public void setAfterSellDealerOrderId(String afterSellDealerOrderId) {
		this.afterSellDealerOrderId = afterSellDealerOrderId;
	}

	public Long getAfterMoney() {
		if (afterMoney == null)
			afterMoney = 0l;
		return afterMoney;
	}

	public void setAfterMoney(long afterMoney) {
		this.afterMoney = afterMoney;
	}

	public Integer getAfterNum() {
		return afterNum;
	}

	public void setAfterNum(Integer afterNum) {
		this.afterNum = afterNum;
	}
}
