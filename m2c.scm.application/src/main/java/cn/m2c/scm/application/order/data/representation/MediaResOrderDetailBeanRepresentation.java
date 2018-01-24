package cn.m2c.scm.application.order.data.representation;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.scm.application.order.data.bean.MediaResOrderDetailBean;
import cn.m2c.scm.application.utils.OrderUtils;
import cn.m2c.scm.application.utils.Utils;

/**
 * 媒体广告位订单明细表述对象 
 */
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
	private String mediaName;          //来源媒体(媒体名称)
	private String mediaNo;            //媒体编号
	private String mediaCate;          //媒体分类
	private String level;             //媒体等级
	private String mresNo;             //来源广告位条码
	private String mresCate;           //广告位位置
	private String formId;             //广告位形式
	
	
	
	public MediaResOrderDetailBeanRepresentation (MediaResOrderDetailBean bean) {
		this.orderId = bean.getOrderId();
		
		this.payStatus = OrderUtils.getPayStatusStr2(bean.getPayStatus());
		this.payWay = OrderUtils.getPayWayStr(bean.getPayWay());
		
		if(null != bean.getAfterSellOrderType()) {
			this.afterSellOrderType = OrderUtils.getAfterType(bean.getAfterSellOrderType());
		}else {
			this.afterSellOrderType = "无售后";
		}
		this.mediaId = null != bean.getMediaId() ? bean.getMediaId() : "";
		this.mediaResId = null != bean.getMediaResId() ? bean.getMediaResId() : "";
		if(StringUtils.isEmpty(bean.getSkuName())) {
			this.goodsName = bean.getGoodsName();
		}else {
			this.goodsName = bean.getGoodsName() + "(" + bean.getSkuName() + ")";
		}
		this.skuId = bean.getSkuId();
		this.dealerName = bean.getDealerName();
		this.sellNum = bean.getSellNum();
		
		this.goodsAmount = Utils.moneyFormatCN(bean.getGoodsAmount());//销售金额(/10000)
		this.orderMoney = Utils.moneyFormatCN(bean.getGoodsAmount() - bean.getPlateformDiscount() - bean.getDealerDiscount() - bean.getCouponDiscount() + bean.getFreight());//支付金额(销售金额 - 营销优惠 + 运费)
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.createTime = null != bean.getCreateTime() ? df.format(bean.getCreateTime()) : "";
		this.mediaNo = null != bean.getMediaNo() ? bean.getMediaNo().toString() : "";
		this.mediaName = null != bean.getMediaName() ? bean.getMediaName() : "";
		this.mresNo = null != bean.getMresNo() ? bean.getMresNo().toString() : "";
		this.level = null != bean.getLevel() ? (bean.getLevel() == 1 ? "A级" : bean.getLevel() == 2 ? "B级" : bean.getLevel() == 3 ? "C级" : "") : "";
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

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMediaNo() {
		return mediaNo;
	}

	public void setMediaNo(String mediaNo) {
		this.mediaNo = mediaNo;
	}

	public String getMediaCate() {
		return mediaCate;
	}

	public void setMediaCate(String mediaCate) {
		this.mediaCate = mediaCate;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getMresNo() {
		return mresNo;
	}

	public void setMresNo(String mresNo) {
		this.mresNo = mresNo;
	}

	public String getMresCate() {
		return mresCate;
	}

	public void setMresCate(String mresCate) {
		this.mresCate = mresCate;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}
	
}
