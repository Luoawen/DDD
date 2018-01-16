package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.List;

import cn.m2c.scm.application.utils.Utils;

/***
 * 商家订单BEAN
 * @author 89776
 * created date 2017年10月27日
 * copyrighted@m2c
 */
public class DealerOrderQB {
	/**
	 * 商品信息
	 */
	private List<DealerGoodsBean> goodsList;
	/**
	 * 商品总额
	 */
	private Long goodsMoney;
	/**生成日期*/
	private Long createdDate;
	/**
	 * 收货人姓名
	 */
	private String revPerson;
	/**
	 * 收货人电话
	 */
	private String revPhone;

	private String revAddress;

	/**
	 * 订货单状态
	 */
	private Integer orderStatus;
	
	private String dealerOrderId;
	
	private String dealerId;
	
	private String payNo;

	/**
	 * 支付时间
	 */
	private Long payTime;
	
	/**运费 */
	private Long orderFreight;
	/**平台优惠 */
	private Long plateDiscount;
	/**商家优惠 */
	private Long dealerDiscount;
	
	private String orderId;

	/**
	 * 售后期望(退货、换货、仅退款)
	 */
	private Integer afterOrderType;
	/**
	 * 售后单号
	 */
	private String afterSellDealerOrderId;
	/**
	 * 售后总额
	 */
	private Long afterMoney;
	/**
	 * 售后数量
	 */
	private Integer afterNum;
	/**
	 * 售后状态
	 */
	private Integer afterStatus;
	
	private long couponDiscount;
	
	private Long ddCouponDiscount;
	
	public Long getDdCouponDiscount() {
		return ddCouponDiscount;
	}

	public void setDdCouponDiscount(Long ddCouponDiscount) {
		this.ddCouponDiscount = ddCouponDiscount;
	}

	public Integer getAfterStatus() {
		return afterStatus;
	}

	public void setAfterStatus(Integer afterStatus) {
		this.afterStatus = afterStatus;
	}

	public String getOrderId() {
		return orderId;
	}

	
	public long getCouponDiscount() {
		return couponDiscount/10000;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	
	public String getStrCouponDiscount() {
		return Utils.moneyFormatCN(couponDiscount);
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Long getGoodsMoney() {
		if (null == goodsMoney)
			goodsMoney = 0l;
		return goodsMoney/100;
	}
	
	public String getStrGoodsMoney() {
		return Utils.moneyFormatCN(goodsMoney);
	}

	public void setGoodsMoney(long goodsMoney) {
		this.goodsMoney = goodsMoney;
	}

	public Long getOrderFreight() {
		return orderFreight/100;
	}
	
	public String getStrOrderFreight() {
		return Utils.moneyFormatCN(orderFreight);
	}

	public void setOrderFreight(long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public Long getPlateDiscount() {
		return plateDiscount/100;
	}
	
	public String getStrPlateDiscount() {
		return Utils.moneyFormatCN(plateDiscount);
	}

	public void setPlateDiscount(long plateDiscount) {
		this.plateDiscount = plateDiscount;
	}

	public Long getDealerDiscount() {
		return dealerDiscount/100;
	}
	
	public String getStrDealerDiscount() {
		return Utils.moneyFormatCN(dealerDiscount);
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

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}

	public String getPayNo() {
		return payNo;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public Long getCreatedDate() {
		return createdDate;
	}

	public String getRevPerson() {
		return revPerson;
	}

	public String getRevPhone() {
		return revPhone;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}


	public List<DealerGoodsBean> getGoodsList() {
		return goodsList;
	}

	public void setGoodsList(List<DealerGoodsBean> goodsInfoList) {
		this.goodsList = goodsInfoList;
	}

	public void setCreatedDate(Long createdDate) {
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

	@Override
	public String toString() {
		return "DealerOrderBean [goodsInfoList=" + goodsList + ", createdDate="
				+ createdDate + ", revPerson=" + revPerson + ", revPhone=" + revPhone + ", orderStatus=" + orderStatus
				+ "]";
	}

	public Long getPayTime() {
		return payTime;
	}

	public void setPayTime(Long payTime) {
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
		return afterMoney/100;
	}
	
	public String getStrAfterMoney() {
		return Utils.moneyFormatCN(afterMoney);
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
