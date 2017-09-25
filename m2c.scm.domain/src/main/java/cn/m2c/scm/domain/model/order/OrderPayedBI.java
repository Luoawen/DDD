package cn.m2c.scm.domain.model.order;

import java.util.Date;
import java.util.Map;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

/**
 * 
 * @ClassName: OrderPayed
 * @Description: 已付款事件
 * @author moyj
 * @date 2017年7月27日 下午5:05:13
 *
 */
public class OrderPayedBI extends AssertionConcern implements  DomainEvent {

	private String orderId;			//订单ID
	private Long orderPrice;		//订单金额
	private Long payPrice;			//付款金额
	private String goodsId;			//商品ID
	private String goodsName;		//商品名称
	private Long goodsUnitPrice;	//货品单价
	private Integer goodsNum;		//商品购买数
	private String mresId;			//资源ID
	private String mresNo;			//资源编号
	private String mresName;		//资源名称
	private String mediaId;			//媒体ID
	private String mediaName;		//媒体名称
	private Long commitTime;		//下单时间
	private Long payEndTime;		//支付成功时间
	private String buyerId;			//支付人ID
	private String dealerId;		//卖家ID
	private String sn;
	private String os;
	private String osVersion;
	private String appVersion;
	
	public OrderPayedBI(
			String orderId,
			Long orderPrice,
			Long payPrice,
			String goodsId,
			String goodsName,
			Long goodsUnitPrice,
			Integer goodsNum,
			String mresId,
			String mresNo,
			String mresName,
			String mediaId,
			String mediaName,
			Long commitTime,
			Long payEndTime,
			String buyerId,
			String dealerId,
			Map<String,Object> appMsgMap
			){
		this.orderId = orderId;
		this.orderPrice = orderPrice;
		this.payPrice = payPrice;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.goodsUnitPrice = goodsUnitPrice;
		this.goodsNum = goodsNum;
		this.mresId = mresId;
		this.mresNo = mresNo;
		this.mresName = mresName;
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.commitTime = commitTime;
		this.payEndTime = payEndTime;
		this.buyerId = buyerId;
		this.dealerId = dealerId;
		if(appMsgMap != null){
			this.sn = appMsgMap.get("sn")==null?null:(String)appMsgMap.get("sn");
			this.os = appMsgMap.get("os")==null?null:(String)appMsgMap.get("os");
			this.osVersion = appMsgMap.get("osVersion")==null?null:(String)appMsgMap.get("osVersion");
			this.appVersion = appMsgMap.get("appVersion")==null?null:(String)appMsgMap.get("appVersion");
		}
		
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

	public Long getOrderPrice() {
		return orderPrice;
	}

	public Long getPayPrice() {
		return payPrice;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public String getMresId() {
		return mresId;
	}

	public String getMresName() {
		return mresName;
	}

	public String getMediaId() {
		return mediaId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public Long getCommitTime() {
		return commitTime;
	}

	public Long getPayEndTime() {
		return payEndTime;
	}

	public String getBuyerId() {
		return buyerId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public Integer getGoodsNum() {
		return goodsNum;
	}

	public String getMresNo() {
		return mresNo;
	}

	public Long getGoodsUnitPrice() {
		return goodsUnitPrice;
	}

	public String getSn() {
		return sn;
	}

	public String getOs() {
		return os;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public String getAppVersion() {
		return appVersion;
	}

	
	
	
}
