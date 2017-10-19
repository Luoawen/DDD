package cn.m2c.scm.application.order.data.bean;

import java.util.Date;
import java.util.List;

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
	private Integer orderStatus;
	/**
	 * 平台订单Id
	 */
	private String orderId;
	/**
	 * 下单时间
	 */
	private Date createdDate;
	/**
	 * 支付方式
	 */
	private Integer payWay;
	/**
	 * 支付时间
	 */
	private Date payDate;
	/**
	 * 支付单号
	 */
	private String payNo;
	/**
	 * 收货人姓名
	 */
	private String revPerson;
	/**
	 * 收货人电话
	 */
	private String revPhone;
	/**
	 * 收货地址省
	 */
	private String province;
	/**
	 * 收货地址市
	 */
	private String city;
	/**
	 * 区或县镇
	 */
	private String areaCounty;
	/**
	 * 街道详细地址
	 */
	private String streetAddr;
	/**
	 * 商家名
	 */
	private String dealerName;
	/**
	 * 商家类型
	 */
	private String dealerClassify;
	/**
	 * 商家Id
	 */
	private String dealerInfo;
	/**
	 * 业务员姓名
	 */
	private String sellerName;
	/**
	 * 业务员手机
	 */
	private String sellerPhone;
	/**
	 * 商品信息列表
	 */
	private List<GoodsInfoBean> goodsInfoBeans;
	/**
	 * 订单商品总额(Sum(totalPrice))
	 */
	private Integer totalOrderPrice;
	/**
	 * 总运费
	 */
	private Integer totalFreight;
	/**
	 * 平台优惠券金额
	 */
	private Integer plateformDiscount;
	/**
	 * 商家优惠券金额
	 */
	private Integer dealerDiscount;
	/**
	 * 订单总额(订单商品总额 - 平台优惠券 - 商家优惠券)
	 */
	private Integer orderPrice;


	public Integer getOrderStatus() {
		return orderStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Integer getTotalOrderPrice() {
		return totalOrderPrice;
	}

	public Integer getTotalFreight() {
		return totalFreight;
	}

	public Integer getPlateformDiscount() {
		return plateformDiscount;
	}

	public Integer getDealerDiscount() {
		return dealerDiscount;
	}

	public Integer getOrderPrice() {
		return orderPrice;
	}

	public void setTotalOrderPrice(Integer totalOrderPrice) {
		this.totalOrderPrice = totalOrderPrice;
	}

	public void setTotalFreight(Integer totalFreight) {
		this.totalFreight = totalFreight;
	}

	public void setPlateformDiscount(Integer plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(Integer dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setOrderPrice(Integer orderPrice) {
		this.orderPrice = orderPrice;
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

	public List<GoodsInfoBean> getGoodsInfoBeans() {
		return goodsInfoBeans;
	}

	public void setGoodsInfoBeans(List<GoodsInfoBean> goodsInfoBeans) {
		this.goodsInfoBeans = goodsInfoBeans;
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

	public String getRevPhone() {
		return revPhone;
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
	
	

}
