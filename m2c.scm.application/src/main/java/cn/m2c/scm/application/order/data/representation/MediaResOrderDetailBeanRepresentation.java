package cn.m2c.scm.application.order.data.representation;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.m2c.scm.application.order.data.bean.MediaResOrderDetailBean;
import cn.m2c.scm.application.utils.Utils;

public class MediaResOrderDetailBeanRepresentation {
	private String orderId;            //订单号
	private String userMessage;        //用户名/手机号
	private String payStatus;          //支付状态
	private String payWay;             //支付方式
	private String afterSellOrderType; //售后方式
	private String mediaId;            //媒体id
	private String mediaResId;         //广告位id
	private String goodsName;          //商品名
	private String skuId;              //商家平台sku编号
	private String dealerName;         //商家名
	private Integer sellNum;           //下单数量
	private String orderMoney;         //支付金额
	private String goodsAmount;        //销售金额
	private String createTime;         //下单日期
	
	public MediaResOrderDetailBeanRepresentation (MediaResOrderDetailBean bean) {
		this.orderId = bean.getOrderId();
		
		if(null != bean.getPayStatus()) {
			if(bean.getPayStatus() == -1) {
				this.payStatus = "已取消";
			}
			if(bean.getPayStatus() == 0) {
				this.payStatus = "待支付";
			}
			if(bean.getPayStatus() >= 1 && bean.getPayStatus() <= 5) {
				this.payStatus = "已付款";
			}
		}
		
		if(null != bean.getPayWay()) {
			if(bean.getPayWay() == 1) {
				this.payWay = "支付宝";
			}
			if(bean.getPayWay() == 2) {
				this.payWay = "微信";
			}
			if(bean.getPayWay() == 3) {
				this.payWay = "其他";
			}
		}
		if(null != bean.getAfterSellOrderType()) {
			if(bean.getAfterSellOrderType() == 0) {
				this.afterSellOrderType = "换货";
			}
			if(bean.getAfterSellOrderType() == 1) {
				this.afterSellOrderType = "退货退款";
			}
			if(bean.getAfterSellOrderType() == 2) {
				this.afterSellOrderType = "仅退款";
			}
		}else {
			this.afterSellOrderType = "无售后";
		}
		this.mediaId = null != bean.getMediaId() ? bean.getMediaId() : "";
		this.mediaResId = null != bean.getMediaResId() ? bean.getMediaResId() : "";
		this.goodsName = bean.getGoodsName();
		this.skuId = bean.getSkuId();
		this.dealerName = bean.getDealerName();
		this.sellNum = bean.getSellNum();
		
		this.goodsAmount = Utils.moneyFormatCN(bean.getGoodsAmount());//销售金额(/10000)
		this.orderMoney = Utils.moneyFormatCN(bean.getGoodsAmount() - bean.getPlateformDiscount() - bean.getDealerDiscount() - bean.getCouponDiscount() + bean.getFreight());//支付金额(销售金额 - 营销优惠 + 运费)
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.createTime = null != bean.getCreateTime() ? df.format(bean.getCreateTime()) : "";
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getAfterSellOrderType() {
		return afterSellOrderType;
	}

	public void setAfterSellOrderType(String afterSellOrderType) {
		this.afterSellOrderType = afterSellOrderType;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaResId() {
		return mediaResId;
	}

	public void setMediaResId(String mediaResId) {
		this.mediaResId = mediaResId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getSkuId() {
		return skuId;
	}

	public void setSkuId(String skuId) {
		this.skuId = skuId;
	}

	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	public Integer getSellNum() {
		return sellNum;
	}

	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}

	public String getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public String getGoodsAmount() {
		return goodsAmount;
	}

	public void setGoodsAmount(String goodsAmount) {
		this.goodsAmount = goodsAmount;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
