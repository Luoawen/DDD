package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
/***
 * 主订单bean, 用于查询模式，APP订单详情
 * @author fanjc
 * created date 2017年11月6日
 * copyrighted@m2c
 */
public class AppOrderDtl extends AssertionConcern implements Serializable {

	/**主订单号 */
	@ColumnAlias(value = "order_id")
	private String orderId;
	/**支付单号*/
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/**下单时间 */
	@ColumnAlias(value = "created_date")
	private Date createDate;
	/*** 订单商品金额 */
	@ColumnAlias(value = "goods_amount")
	private int goodAmount;
	/**订单运费 */
	@ColumnAlias(value = "order_freight")
	private int oderFreight;
	/*** 平台优惠 */
	@ColumnAlias(value = "plateform_discount")
	private int plateFormDiscount;
	/***商家优惠*/
	@ColumnAlias(value = "dealer_discount")
	private int dealerDiscount;
	
	@ColumnAlias(value = "dealer_id")
	private String dealerId;
	
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	
	@ColumnAlias(value = "_status")
	private Integer status;
	/**商品列表 */
	private List<OrderDetailBean> goodses;
	
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	
	@ColumnAlias(value = "province_code")
	private String provinceCode;
	@ColumnAlias(value = "province")
	private String province;
	@ColumnAlias(value = "city")
	private String city;
	@ColumnAlias(value = "city_code")
	private String cityCode;
	@ColumnAlias(value = "area_code")
	private String areaCode;
	@ColumnAlias(value = "area_county")
	private String area;
	@ColumnAlias(value = "street_addr")
	private String streetAddr;
	
	@ColumnAlias(value = "invoice_code")
	private String invoiceCode;
	@ColumnAlias(value = "invoice_header")
	private String invoiceHeader;
	@ColumnAlias(value = "invoice_name")
	private String invoiceName;
	@ColumnAlias(value = "invoice_type")
	private String invoiceType;
	
	@ColumnAlias(value = "rev_phone")
	private String revPhone;
	@ColumnAlias(value = "rev_person")
	private String revPerson;
	
	@ColumnAlias(value = "pay_way")
	private Integer payWay;

	public Integer getPayWay() {
		return payWay;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public String getRevPhone() {
		return revPhone;
	}

	public void setRevPhone(String revPhone) {
		this.revPhone = revPhone;
	}

	public String getRevPerson() {
		return revPerson;
	}

	public void setRevPerson(String revPerson) {
		this.revPerson = revPerson;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getStreetAddr() {
		return streetAddr;
	}

	public void setStreetAddr(String streetAddr) {
		this.streetAddr = streetAddr;
	}

	public String getInvoiceCode() {
		return invoiceCode;
	}

	public void setInvoiceCode(String invoiceCode) {
		this.invoiceCode = invoiceCode;
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

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public int getGoodAmount() {
		return goodAmount;
	}

	public int getOderFreight() {
		return oderFreight;
	}

	public int getPlateFormDiscount() {
		return plateFormDiscount;
	}

	public int getDealerDiscount() {
		return dealerDiscount;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setGoodAmount(int goodAmount) {
		this.goodAmount = goodAmount;
	}

	public void setOderFreight(int oderFreight) {
		this.oderFreight = oderFreight;
	}

	public void setPlateFormDiscount(int plateFormDiscount) {
		this.plateFormDiscount = plateFormDiscount;
	}

	public void setDealerDiscount(int dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
	
	public List<OrderDetailBean> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<OrderDetailBean> goodses) {
		this.goodses = goodses;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return " AppOrderDtl [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ goodAmount + ", oderFreight=" + oderFreight + ", plateFormDiscount=" + plateFormDiscount
				+ ", dealerDiscount=" + dealerDiscount + ", goodses=" + goodses + "]";
	}
	
}
