package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

public class DealerOrderAfterSellDetailBean {

	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 售后期望(退货、换货、仅退款)
	 */
	@ColumnAlias(value = "order_type")
	private Integer orderType;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSelldealerOrderId;
	/**
	 * 售后总额
	 */
	@ColumnAlias(value = "back_money")
	private long backMoney;
	/**
	 * 申请原因
	 */
	@ColumnAlias(value = "reason")
	private String reason;
	/**
	 * 申请时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 关联订单号
	 */
	@ColumnAlias(value = "dealer_order_id")
	private String dealerdealerOrderId;
	/**
	 * 订单总额
	 */
	private long orderTotalMoney;
	/**
	 * 商品信息
	 */
	private List<GoodsInfoBean> goodsInfoList;

	public Integer getStatus() {
		return status;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public String getAfterSelldealerOrderId() {
		return afterSelldealerOrderId;
	}

	public long getBackMoney() {
		return backMoney;
	}

	public String getReason() {
		return reason;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public String getDealerdealerOrderId() {
		return dealerdealerOrderId;
	}

	public long getOrderTotalMoney() {
		return orderTotalMoney;
	}

	public List<GoodsInfoBean> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public void setAfterSelldealerOrderId(String afterSelldealerOrderId) {
		this.afterSelldealerOrderId = afterSelldealerOrderId;
	}

	public void setBackMoney(long backMoney) {
		this.backMoney = backMoney;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setDealerdealerOrderId(String dealerdealerOrderId) {
		this.dealerdealerOrderId = dealerdealerOrderId;
	}

	public void setOrderTotalMoney(long orderTotalMoney) {
		this.orderTotalMoney = orderTotalMoney;
	}

	public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	@Override
	public String toString() {
		return "DealerOrderAfterSellDetailBean [status=" + status + ", orderType=" + orderType
				+ ", afterSelldealerOrderId=" + afterSelldealerOrderId + ", backMoney=" + backMoney + ", reason="
				+ reason + ", createdDate=" + createdDate + ", dealerdealerOrderId=" + dealerdealerOrderId
				+ ", orderTotalMoney=" + orderTotalMoney + ", goodsInfoList=" + goodsInfoList + "]";
	}

}
