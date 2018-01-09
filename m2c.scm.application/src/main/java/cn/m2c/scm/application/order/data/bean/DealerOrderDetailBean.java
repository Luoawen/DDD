package cn.m2c.scm.application.order.data.bean;

import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

/**
 * 商家订单详情
 * 
 * @author lqwen
 *
 */
public class DealerOrderDetailBean {
	/**
	 * 订单状态
	 */
	@ColumnAlias(value = "_status")
	private Integer orderStatus;
	/**
	 * 平台订单Id
	 */
	@ColumnAlias(value = "order_id")
	private String orderId;
	/**
	 * 下单时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 支付方式
	 */
	@ColumnAlias(value = "pay_way")
	private Integer payWay;
	/**
	 * 支付时间
	 */
	@ColumnAlias(value ="payTime")
	private Date payDate;
	/**
	 * 支付单号
	 */
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/**
	 * 收货人姓名
	 */
	@ColumnAlias(value = "rev_person")
	private String revPerson;
	/**
	 * 收货人电话
	 */
	@ColumnAlias(value ="rev_phone")
	private String revPhone;
	/**
	 * 收货地址省
	 */
	@ColumnAlias(value = "province")
	private String province;
	/**
	 * 收货地址市
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
	 * 商家名
	 */
	@ColumnAlias(value = "dealerName")
	private String dealerName;
	/**
	 * 商家类型
	 */
	@ColumnAlias(value = "dealer_classify")
	private String dealerClassify;
	/**
	 * 商家Id
	 */
	@ColumnAlias(value = "dealer_id")
	private String dealerInfo;
	/**
	 * 业务员姓名
	 */
	@ColumnAlias(value = "seller_name")
	private String sellerName;
	/**
	 * 业务员手机
	 */
	@ColumnAlias(value = "seller_phone")
	private String sellerPhone;
	/**
	 * 商品信息列表
	 */
	private List<GoodsInfoBean> goodsInfoBeans;
	/**
	 * 订单商品总额(Sum(totalPrice))
	 */
	@ColumnAlias(value = "goods_amount")
	private long totalOrderPrice;
	/**
	 * 总运费
	 */
	@ColumnAlias(value = "order_freight")
	private long totalFreight;
	/**
	 * 平台优惠券金额
	 */@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;
	/**
	 * 商家优惠券金额
	 */
	 @ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/**
	 * 订单总额(订单商品总额 - 平台优惠券 - 商家优惠券 + 运费)
	 */
	private long orderPrice;
	
	@ColumnAlias(value = "invoice_header")
	private String invoiceHeader;
	@ColumnAlias(value = "invoice_name")
	private String invoiceName;
	@ColumnAlias(value = "invoice_code")
	private String invoiceCode;
	/***0 个人， 1公司， -1 不开发票*/
	@ColumnAlias(value = "invoice_type")
	private int invoiceType;
	/**订单备注*/
	@ColumnAlias(value = "noted")
	private String  noted;
	
	@ColumnAlias(value = "provinceCode")
	private String  privinceCode;
	@ColumnAlias(value = "cityCode")
	private String  cityCode;
	@ColumnAlias(value = "areaCode")
	private String  areaCode;
	
	private String dealerOrderId;
	
	private Integer isShowShip;      //是否展示发货 0 不展示，1 展示
	
	public Integer getIsShowShip() {
		return 0;
	}

	public void setIsShowShip(Integer isShowShip) {
		this.isShowShip = isShowShip;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}
	
	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getInvoiceHeader() {
		return invoiceHeader;
	}

	public void setInvoiceHeader(String invoiceHeader) {
		this.invoiceHeader = invoiceHeader;
	}

	public String getInvoiceName() {
		return invoiceName;
	}

	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
	}

	public int getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(int invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getNoted() {
		return noted;
	}

	public void setNoted(String noted) {
		this.noted = noted;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public Date getPayDate() {
		return payDate;
	}

	public String getPayNo() {
		return payNo;
	}

	public String getRevPerson() {
		return revPerson;
	}

	public String getRevPhone() {
		return revPhone;
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

	public String getDealerName() {
		return dealerName;
	}

	public String getDealerClassify() {
		return dealerClassify;
	}

	public String getDealerInfo() {
		return dealerInfo;
	}

	public String getSellerName() {
		return sellerName;
	}

	public String getSellerPhone() {
		return sellerPhone;
	}

	public List<GoodsInfoBean> getGoodsInfoBeans() {
		return goodsInfoBeans;
	}

	public long getTotalOrderPrice() {
		return totalOrderPrice/100;
	}

	public long getTotalFreight() {
		return totalFreight/100;
	}

	public long getPlateformDiscount() {
		return plateformDiscount/100;
	}

	public long getDealerDiscount() {
		return dealerDiscount/100;
	}
	
	public String getStrTotalOrderPrice() {
		return Utils.moneyFormatCN(totalOrderPrice);
	}

	public String getStrTotalFreight() {
		return Utils.moneyFormatCN(totalFreight);
	}

	public String getStrPlateformDiscount() {
		return Utils.moneyFormatCN(plateformDiscount);
	}

	public String getStrDealerDiscount() {
		return Utils.moneyFormatCN(dealerDiscount);
	}

	public String getOrderPrice() {
		orderPrice = totalOrderPrice + totalFreight - plateformDiscount - dealerDiscount;
		return Utils.moneyFormatCN(orderPrice);
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}

	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
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

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public void setDealerClassify(String dealerClassify) {
		this.dealerClassify = dealerClassify;
	}

	public void setDealerInfo(String dealerInfo) {
		this.dealerInfo = dealerInfo;
	}

	public void setSellerName(String sellerName) {
		this.sellerName = sellerName;
	}

	public void setSellerPhone(String sellerPhone) {
		this.sellerPhone = sellerPhone;
	}

	public void setGoodsInfoBeans(List<GoodsInfoBean> goodsInfoBeans) {
		this.goodsInfoBeans = goodsInfoBeans;
	}

	public void setTotalOrderPrice(long totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}

	public void setTotalFreight(long totalFreight) {
		this.totalFreight = totalFreight;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	@Override
	public String toString() {
		return "DealerOrderDetailBean [orderStatus=" + orderStatus + ", orderId=" + orderId + ", createdDate="
				+ createdDate + ", payWay=" + payWay + ", payDate=" + payDate + ", payNo=" + payNo + ", revPerson="
				+ revPerson + ", revPhone=" + revPhone + ", province=" + province + ", city=" + city + ", areaCounty="
				+ areaCounty + ", streetAddr=" + streetAddr + ", dealerName=" + dealerName + ", dealerClassify="
				+ dealerClassify + ", dealerInfo=" + dealerInfo + ", sellerName=" + sellerName + ", sellerPhone="
				+ sellerPhone + ", goodsInfoBeans=" + goodsInfoBeans + ", totalOrderPrice=" + totalOrderPrice
				+ ", totalFreight=" + totalFreight + ", plateformDiscount=" + plateformDiscount + ", dealerDiscount="
				+ dealerDiscount + ", orderPrice=" + orderPrice + "]";
	}

	public String getPrivinceCode() {
		return privinceCode;
	}

	public void setPrivinceCode(String privinceCode) {
		this.privinceCode = privinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	
}
