package cn.m2c.scm.application.dealerorder.data.bean;

import java.util.Date;
import java.util.List;

import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

public class DealerOrderBean {
	/**
	 * 商品信息
	 */
	private List<GoodsInfoBean> goodsInfoList;
	/**
	 * 订单总额
	 */
	private Integer totalMoney;
	/**
	 * 下单时间
	 */
	private Date createdDate;
	/**
	 * 收货人姓名
	 */
	private String revPerson;
	/**
	 * 收货人电话
	 */
	private String revPhone;
	/**
	 * 订货单状态
	 */
	private Integer orderStatus;

	public List<GoodsInfoBean> getGoodsInfoList() {
		return goodsInfoList;
	}

	public Integer getTotalMoney() {
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

	public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	public void setTotalMoney(Integer totalMoney) {
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
