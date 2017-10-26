package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;
import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

public class DealerOrderBean {
	/**
	 * 商品信息
	 */
	private List<DealerOrderGoodsInfo> goodsInfoList;
	/**
	 * 订单总额
	 */
	private long totalMoney;
	/**
	 * 下单时间
	 */
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	/**
	 * 收货人姓名
	 */
	@ColumnAlias(value = "rev_person")
	private String revPerson;
	/**
	 * 收货人电话
	 */
	@ColumnAlias(value = "rev_phone")
	private String revPhone;
	/**
	 * 订货单状态
	 */
	@ColumnAlias(value = "_status")
	private Integer orderStatus;

	/**
	 * 商品信息
	 * @return
	 */

	public long getTotalMoney() {
		return totalMoney;
	}

	public Date getCreatedDate() {
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


	public List<DealerOrderGoodsInfo> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setGoodsInfoList(List<DealerOrderGoodsInfo> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	public void setTotalMoney(long totalMoney) {
		this.totalMoney = totalMoney;
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

	@Override
	public String toString() {
		return "DealerOrderBean [goodsInfoList=" + goodsInfoList + ", totalMoney=" + totalMoney + ", createdDate="
				+ createdDate + ", revPerson=" + revPerson + ", revPhone=" + revPhone + ", orderStatus=" + orderStatus
				+ "]";
	}

}
