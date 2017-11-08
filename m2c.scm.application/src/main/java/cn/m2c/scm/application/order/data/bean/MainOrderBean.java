package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class MainOrderBean extends AssertionConcern implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主订单号
	 */
//	@ColumnAlias(value = "ordde_id")
	private String orderId;
	/**
	 * 支付单号
	 */
	//	@ColumnAlias(value = "pay_no")
	private String payNo;
	/**
	 * 下单时间
	 */
	//	@ColumnAlias(value = "created_date")
	private Date createDate;
	/**
	 * 订单商品金额
	 */
	//	@ColumnAlias(value = "goods_amount")
	private long goodAmount;
	/**
	 * 订单运费
	 */
	//@ColumnAlias(value = "order_freight")
	private long oderFreight;
	/**
	 * 平台优惠
	 */
	//@ColumnAlias(value = "plateform_discount")
	private long plateFormDiscount;
	/**
	 * 商家优惠
	 */
	//@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/**订单状态*/
	private int status;
	
	/**支付时间*/
	private Date payTime;
	/**下单人*/
	private String userId;
	
	/**
	 * 商家订单列表
	 */
	private List<OrderDealerBean> dealerOrderBeans;
	/**订单中的商品列表*/
	private Map<String, List<OrderGoodsBean>> goodses;
	
	private List<SimpleMarket> markets;
	
	public List<SimpleMarket> getMarkets() {
		return markets;
	}
	
	public void setMarkets(List<SimpleMarket> ges) {
		markets = ges;
	}
	
	public Map<String, List<OrderGoodsBean>> getGoodses() {
		return goodses;
	}
	
	public void setGoodses(Map<String, List<OrderGoodsBean>> ges) {
		goodses = ges;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public long getGoodAmount() {
		return goodAmount;
	}

	public long getOderFreight() {
		return oderFreight;
	}

	public long getPlateFormDiscount() {
		return plateFormDiscount;
	}

	public long getDealerDiscount() {
		return dealerDiscount;
	}

	public List<OrderDealerBean> getDealerOrderBeans() {
		return dealerOrderBeans;
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

	public void setGoodAmount(long goodAmount) {
		this.goodAmount = goodAmount;
	}

	public void setOderFreight(long oderFreight) {
		this.oderFreight = oderFreight;
	}

	public void setPlateFormDiscount(long plateFormDiscount) {
		this.plateFormDiscount = plateFormDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setDealerOrderBeans(List<OrderDealerBean> dealerOrderBeans) {
		this.dealerOrderBeans = dealerOrderBeans;
	}

	@Override
	public String toString() {
		return "MainOrderBean [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ goodAmount + ", oderFreight=" + oderFreight + ", plateFormDiscount=" + plateFormDiscount
				+ ", dealerDiscount=" + dealerDiscount + ", dealerOrderBeans=" + dealerOrderBeans + "]";
	}

	
}
