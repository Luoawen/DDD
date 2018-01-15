package cn.m2c.scm.application.order.data.bean;

import java.io.Serializable;
import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.utils.Utils;

public class AllOrderBean extends AssertionConcern implements Serializable {
	
	private static final long serialVersionUID = 1L;
	/** 商家订单号 **/
	@ColumnAlias(value = "dealer_order_id")
	private String dealerOrderId;
	/** 平台订单号 **/
	@ColumnAlias(value = "order_id")
	private String orderId;
	/** 支付编号 **/
	@ColumnAlias(value = "pay_no")
	private String payNo;
	/** 支付方式 **/
	@ColumnAlias(value = "pay_way")
	private Integer payWay; // 1:支付宝 2：微信
	/** 创建时间 **/
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/** 平台订单商品总额 **/
	@ColumnAlias(value = "goods_amount")
	private long mainGoodsAmount;
	/** 平台订单运费 **/
	@ColumnAlias(value = "order_freight")
	private long mainOrderFreight;
	/** 商家订单运费 **/
	@ColumnAlias(value = "orderFreight")
	private long dealerOrderFreight;
	/** 平台优惠金额 **/
	@ColumnAlias(value = "plateform_discount")
	private long plateformDiscount;
	/** 商家优惠金额 **/
	@ColumnAlias(value = "dealer_discount")
	private long dealerDiscount;
	/** 商家订单商品总额 **/
	@ColumnAlias(value = "dealerAmount")
	private long dealerGoodsAmount;
	/** 订单状态 **/
	@ColumnAlias(value = "_status")
	private Integer status;
	/** 商家名 **/
	@ColumnAlias(value = "dealer_name")
	private String dealerName;
	/**主订单中的平台优惠*/
	@ColumnAlias(value = "pDiscount")
	private long ppDiscount;   
	/**主订单中的商家优惠*/
    @ColumnAlias(value = "pDealerDiscount")
	private long ppDealerDiscount;
    
    @ColumnAlias(value = "dCouponDiscount")
    private long ddCouponDiscount;
    
    @ColumnAlias(value = "coupon_discount")
    private long couponDiscount;
    
	public long getPpDiscount() {
		return ppDiscount/100;
	}
	
	public String getStrPpDiscount() {
		return Utils.moneyFormatCN(ppDiscount);
	}

	public long getCouponDiscount() {
		return couponDiscount;
	}

	public void setCouponDiscount(long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}

	public long getDdCouponDiscount() {
		return ddCouponDiscount;
	}

	public void setDdCouponDiscount(long ddCouponDiscount) {
		this.ddCouponDiscount = ddCouponDiscount;
	}

	public void setPpDiscount(long ppDiscount) {
		this.ppDiscount = ppDiscount;
	}

	public long getPpDealerDiscount() {
		return ppDealerDiscount/100;
	}
	
	public String getStrPpDealerDiscount() {
		return Utils.moneyFormatCN(ppDealerDiscount);
	}
	
	public void setPpDealerDiscount(long ppDealerDiscount) {
		this.ppDealerDiscount = ppDealerDiscount;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getPayNo() {
		return payNo;
	}

	public Integer getPayWay() {
		return payWay;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public long getMainGoodsAmount() {
		return mainGoodsAmount/100;
	}
	
	public String getGoodsAmount() {
		return Utils.moneyFormatCN(mainGoodsAmount);
	}

	public long getMainOrderFreight() {
		return mainOrderFreight/100;
	}
	
	public String getStrMainOrderFreight() {
		return Utils.moneyFormatCN(mainOrderFreight);
	}
	
	public long getDealerOrderFreight() {
		return dealerOrderFreight/100;
	}
	
	public String getStrDealerOrderFreight() {
		return Utils.moneyFormatCN(dealerOrderFreight);
	}
	
	public long getPlateformDiscount() {
		return plateformDiscount/100;
	}
	
	public String getStrPlateformDiscount() {
		return Utils.moneyFormatCN(plateformDiscount);
	}
	
	public long getDealerDiscount() {
		return dealerDiscount/100;
	}
	
	public String getStrDealerDiscount() {
		return Utils.moneyFormatCN(dealerDiscount);
	}
	
	public long getDealerGoodsAmount() {
		return dealerGoodsAmount/100;
	}
	
	public String getStrDealerGoodsAmount() {
		return Utils.moneyFormatCN(dealerGoodsAmount);
	}
	
	public Integer getStatus() {
		return status;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}

	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setMainGoodsAmount(long mainGoodsAmount) {
		this.mainGoodsAmount = mainGoodsAmount;
	}

	public void setMainOrderFreight(long mainOrderFreight) {
		this.mainOrderFreight = mainOrderFreight;
	}

	public void setDealerOrderFreight(long dealerOrderFreight) {
		this.dealerOrderFreight = dealerOrderFreight;
	}

	public void setPlateformDiscount(long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}

	public void setDealerDiscount(long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}

	public void setDealerGoodsAmount(long dealerGoodsAmount) {
		this.dealerGoodsAmount = dealerGoodsAmount;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Override
	public String toString() {
		return "AllOrderBean [dealerOrderId=" + dealerOrderId + ", orderId=" + orderId + ", payNo=" + payNo
				+ ", payWay=" + payWay + ", createdDate=" + createdDate + ", mainGoodsAmount=" + mainGoodsAmount
				+ ", mainOrderFreight=" + mainOrderFreight + ", dealerOrderFreight=" + dealerOrderFreight
				+ ", plateformDiscount=" + plateformDiscount + ", dealerDiscount=" + dealerDiscount
				+ ", dealerGoodsAmount=" + dealerGoodsAmount + ", status=" + status + ", dealerName=" + dealerName
				+ "]";
	}

}
