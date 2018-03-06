package cn.m2c.scm.application.order.mini.data.representation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.m2c.scm.application.order.data.bean.AppOrderBean;
import cn.m2c.scm.application.order.data.bean.OrderDetailBean;
import cn.m2c.scm.application.utils.Utils;
/***
 * 主订单bean, mini订单列表展示
 * @author fanjc
 * created date 2017年11月4日
 * copyrighted@m2c
 */
public class MiniOrderRepresentation implements Serializable {

	/**
	 * 主订单号
	 */
	private String orderId;
	/**
	 * 支付单号
	 */
	private String payNo;
	/**
	 * 下单时间
	 */
	private Date createDate;
	/**
	 * 订单商品金额
	 */
	private String strGoodAmount;
	/**
	 * 订单运费
	 */
	private String strOrderFreight;
	/**
	 * 平台优惠
	 */
	private String strPlateFormDiscount;
	/**
	 * 商家优惠
	 */
	private String strDealerDiscount;
	
	private String dealerId;
	
	private String dealerName;
	
	private Integer status;
	
	private String strCouponDiscount;
	/**
	 * 商品列表
	 */
	private List<OrderDtlRepresentation> goodses;
	
	private String dealerOrderId;
	/**优惠券优惠金额*/
	private long mainCouponDiscount;
	
	public MiniOrderRepresentation(AppOrderBean bean) {
		orderId = bean.getOrderId();
		payNo = bean.getPayNo();
		createDate = bean.getCreateDate();
		strGoodAmount = bean.getStrGoodAmount();
		strOrderFreight = bean.getStrOrderFreight();
		strPlateFormDiscount = bean.getStrPlateFormDiscount();
		strDealerDiscount = bean.getStrDealerDiscount();
		dealerId = bean.getDealerId();
		dealerName = bean.getDealerName();
		status = bean.getStatus();
		strCouponDiscount = bean.getStrCouponDiscount();
		goodses = toGoodsRepresentations(bean.getGoodses());
		dealerOrderId = bean.getDealerOrderId();
		mainCouponDiscount = bean.getMainCouponDiscount();
	}
	
	private List<OrderDtlRepresentation> toGoodsRepresentations(List<OrderDetailBean> beans) {
		if (beans == null || beans.size() < 1)
			return null;
		
		List<OrderDtlRepresentation> orderDtls = new ArrayList<OrderDtlRepresentation>();
		for (OrderDetailBean bean : beans) {
			orderDtls.add(new OrderDtlRepresentation(bean));
		}
		return orderDtls;
	}
	
	public void setMainCouponDiscount(long couponDiscount) {
		this.mainCouponDiscount = couponDiscount;
	}
	
	public long getMainCouponDiscount() {
		return mainCouponDiscount;
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

	public String getStrCouponDiscount(){
		return strCouponDiscount;
	}
	
	public String getPayNo() {
		return payNo;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getStrGoodAmount() {
		return strGoodAmount;
	}

	public String getStrOrderFreight() {
		return strOrderFreight;
	}

	public String getStrPlateFormDiscount() {
		return strPlateFormDiscount;
	}

	public String getStrDealerDiscount() {
		return strDealerDiscount;
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

	public List<OrderDtlRepresentation> getGoodses() {
		return goodses;
	}

	public void setGoodses(List<OrderDtlRepresentation> goodses) {
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
		return "mainOrderBean [orderId=" + orderId + ", payNo=" + payNo + ", createDate=" + createDate + ", goodAmount="
				+ strGoodAmount + ", oderFreight=" + strOrderFreight + ", plateFormDiscount=" + strPlateFormDiscount
				+ ", dealerDiscount=" + strDealerDiscount + ", goodses=" + goodses + "]";
	}
	
}
