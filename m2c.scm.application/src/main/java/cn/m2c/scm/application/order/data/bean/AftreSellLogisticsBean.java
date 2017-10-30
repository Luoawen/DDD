package cn.m2c.scm.application.order.data.bean;

import java.util.List;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 退换货物流信息
 * 
 * @author lqwen
 *
 */
public class AftreSellLogisticsBean {

	/**
	 * 售后状态
	 */
	@ColumnAlias(value = "_status")
	private Integer status;
	/**
	 * 售后单号
	 */
	@ColumnAlias(value = "after_sell_order_id")
	private String afterSellOrderId;
	/**
	 * 物流公司
	 */
	@ColumnAlias(value = "back_express_name")
	private String expressName;
	/**
	 * 物流单号
	 */
	@ColumnAlias(value = "back_express_no")
	private String expressNo;

	/**
	 * 商品信息
	 */
	private List<GoodsInfoBean> goodsInfoList;

	public Integer getStatus() {
		return status;
	}

	public String getAfterSellOrderId() {
		return afterSellOrderId;
	}

	public List<GoodsInfoBean> goodsInfoList() {
		return goodsInfoList;
	}

	public String getExpressName() {
		return expressName;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setStatus(Integer status) {
		this.status = status;
		
	}

	public void setAfterSellOrderId(String afterSellOrderId) {
		this.afterSellOrderId = afterSellOrderId;
	}

	public void setAfterSellGoodsInfoBeans(List<GoodsInfoBean> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public List<GoodsInfoBean> getGoodsInfoList() {
		return goodsInfoList;
	}

	public void setGoodsInfoList(List<GoodsInfoBean> goodsInfoList) {
		this.goodsInfoList = goodsInfoList;
	}

	@Override
	public String toString() {
		return "AftreSellLogisticsBean [status=" + status + ", afterSellOrderId=" + afterSellOrderId
				+ ", afterSellGoodsInfoBeans=" + goodsInfoList + ", expressName=" + expressName + ", expressNo="
				+ expressNo + "]";
	}

}
