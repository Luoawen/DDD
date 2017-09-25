package cn.m2c.scm.domain.model.order;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 
 * @ClassName: OrderCommited
 * @Description: 已下单事件
 * @author moyj
 * @date 2017年7月27日 下午5:01:04
 *
 */
public class OrderCommited extends AssertionConcern implements  DomainEvent{
	
	private String orderId;			//订单ID
	private String goodsId;			//货品ID
	private String goodsName;		//货品名称
	private Integer goodsNum;		//货品数量
	private String dealerId;		//供应商ID
	private String dealerName;		//供应商 名称
	private String mresId;			//媒体资源ID
	private String mresNo;			//媒体资源编号
	private String mresName;		//媒体资源名称
	private String buyerId;			//买家ID(APP用户)
	private String buyerName;		//买家ID(APP名称)
	
	public OrderCommited(
			String orderId,
			String goodsId,
			String goodsName,
			Integer goodsNum,
			String dealerId,
			String dealerName,
			String mresId,
			String mresNo,
			String mresName,
			String buyerId,
			String buyerName
			){
		this.orderId = orderId;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsNum = goodsNum;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.mresId = mresId;
		this.mresNo = mresNo;
		this.mresName = mresName;
		this.buyerId = buyerId;
		this.buyerName = buyerName;
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

	public String getMresNo() {
		return mresNo;
	}
	
	
	

}
