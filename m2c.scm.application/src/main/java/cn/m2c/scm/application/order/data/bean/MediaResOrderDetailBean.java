package cn.m2c.scm.application.order.data.bean;

import java.util.Date;

/**
 * 媒体广告位订单明细 
 */
public class MediaResOrderDetailBean {

	private String orderId; //订单号
	private String userId;  //用户id
	private Integer payStatus; //支付状态
	private Integer payWay;    //支付方式
	private Integer afterSellOrderType; //售后
	private String mediaId; //媒体编号
	private String mediaResId; //广告位id
	private String goodsName;//购买商品名
	private String skuId;//商品平台sku编号
	private String dealerName;//来源商家
	private Integer sellNum;//购买数量
	
	private Date createTime;//下单日期
	
	//支付金额    销售金额
	private Long discountPrice;//拍获价(单价)
	private Long goodsAmount;//订单商品金额 (销售金额 = 拍获价/特惠价 * 数量)
	private Integer isSpecial;//是否特惠价
	private Long specialPrice;//特惠价
	private Long couponDiscount;//优惠券金额
	private Long plateformDiscount;//平台优惠金额
	private Long dealerDiscount;//商家优惠金额
	private Long freight;//订单运费
	private String mediaCate;//媒体分类
	private Integer mediaNo;//媒体编号
	private String mediaName;//媒体名
	private Integer mresCate;//广告位位置
	private Integer formId;//广告位形式
	private Long mresNo;//广告位条码
	private Integer level;//媒体等级
	
	public Long getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Long discountPrice) {
		this.discountPrice = discountPrice;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	public Integer getAfterSellOrderType() {
		return afterSellOrderType;
	}
	public void setAfterSellOrderType(Integer afterSellOrderType) {
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getGoodsAmount() {
		return goodsAmount;
	}
	public void setGoodsAmount(Long goodsAmount) {
		this.goodsAmount = goodsAmount;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}
	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}
	public Long getSpecialPrice() {
		return specialPrice;
	}
	public void setSpecialPrice(Long specialPrice) {
		this.specialPrice = specialPrice;
	}
	public Long getCouponDiscount() {
		return couponDiscount;
	}
	public void setCouponDiscount(Long couponDiscount) {
		this.couponDiscount = couponDiscount;
	}
	public Long getPlateformDiscount() {
		return plateformDiscount;
	}
	public void setPlateformDiscount(Long plateformDiscount) {
		this.plateformDiscount = plateformDiscount;
	}
	public Long getDealerDiscount() {
		return dealerDiscount;
	}
	public void setDealerDiscount(Long dealerDiscount) {
		this.dealerDiscount = dealerDiscount;
	}
	public Long getFreight() {
		return freight;
	}
	public void setFreight(Long freight) {
		this.freight = freight;
	}
	public String getMediaCate() {
		return mediaCate;
	}
	public void setMediaCate(String mediaCate) {
		this.mediaCate = mediaCate;
	}
	public Integer getMediaNo() {
		return mediaNo;
	}
	public void setMediaNo(Integer mediaNo) {
		this.mediaNo = mediaNo;
	}
	public String getMediaName() {
		return mediaName;
	}
	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}
	public Integer getMresCate() {
		return mresCate;
	}
	public void setMresCate(Integer mresCate) {
		this.mresCate = mresCate;
	}
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public Long getMresNo() {
		return mresNo;
	}
	public void setMresNo(Long mresNo) {
		this.mresNo = mresNo;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	
}
