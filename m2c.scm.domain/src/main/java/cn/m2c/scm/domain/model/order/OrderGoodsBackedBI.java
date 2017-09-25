package cn.m2c.scm.domain.model.order;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 
 * @ClassName: OrderGoodsBackedBI
 * @Description: 订单退货时间
 * @author moyj
 * @date 2017年8月15日 上午11:07:06
 *
 */
public class OrderGoodsBackedBI implements  DomainEvent{
	private String orderId;			//订单ID
	private String goodsId;			//货品ID
	private String goodsName;		//货品名称
	private Integer goodsNum;		//货品数量
	private String dealerId;		//供应商ID
	private String dealerName;		//供应商 名称
	private String mresId;			//媒体资源ID
	private String mresName;		//媒体资源名称
	private String buyerId;			//买家ID(APP用户)
	private String buyerName;		//买家ID(APP名称)
	
	private Long payPrice;
	private Long orderPrice;
	private Long freightPrice;
	
	public OrderGoodsBackedBI(
			String orderId,
			String goodsId,
			String goodsName,
			Integer goodsNum,
			String dealerId,
			String dealerName,
			String mresId,
			String mresName,
			String buyerId,
			String buyerName,
			Long payPrice,
			Long orderPrice,
			Long freightPrice
			){
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsNum = goodsNum;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.mresId = mresId;
		this.mresName = mresName;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.payPrice = payPrice;
		this.orderPrice = orderPrice;
		this.freightPrice = freightPrice;
	}
	@Override
	public int eventVersion() {
		return 0;
	}
	@Override
	public Date occurredOn() {
		return new Date(System.currentTimeMillis());
	}
	public String getOrderId() {
		return orderId;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public String getDealerId() {
		return dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public String getMresId() {
		return mresId;
	}
	public String getMresName() {
		return mresName;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public Long getPayPrice() {
		return payPrice;
	}
	public Long getOrderPrice() {
		return orderPrice;
	}
	public Long getFreightPrice() {
		return freightPrice;
	}
	
}
