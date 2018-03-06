package cn.m2c.scm.application.order.mini.data.representation;

import java.util.Date;

import cn.m2c.scm.application.order.data.bean.OrderDetailBean;
import cn.m2c.scm.application.utils.Utils;
/***
 * 订单详情展示层
 * @author 89776
 * created date 2018年3月5日
 * copyrighted@m2c
 */
public class OrderDtlRepresentation {

	/**商家订单号*/
	private String dealerOrderId;
	/**商品名*/
	private String goodsName;
	/**商品sku ID*/
	private String skuId;
	/**商品sku名称*/
	private String skuName;
	/**商品图组*/
	private String goodsIcon;
	/**购买数量*/
	private Integer  sellNum;
	/**快递方式*/
	private Integer  expressWay;
	/**留言备注*/
	private String expressNote;
	/**收货人电话*/
	private String expressPhone;
	/**收件人名称*/
	private String expressPerson;
	/**快递公司名称*/
	private String expressName;
	/**快递单号*/
	private String expressNo;
	/**快递公司编码*/
	private String expressCode;
	
	private Date expressTime;
	/**主订单号*/
	private String orderId;
	
	private Integer isChange;
	/**换购价*/
	private String strChangePrice;
	
	private Integer isSpecial;
	/**特惠价*/
	private String strSpecialPrice;
	
	private Integer sortNo;
	
	public OrderDtlRepresentation(OrderDetailBean bean) {
		dealerOrderId = bean.getDealerOrderId();
		goodsName = bean.getGoodsName();
		skuId = bean.getSkuId();
		skuName = bean.getSkuName();
		goodsIcon = bean.getGoodsIcon();
		sellNum = bean.getSellNum();
		expressWay = bean.getExpressWay();
		expressNote = bean.getExpressNote();
		expressPhone = bean.getExpressPhone();
		expressPerson = bean.getExpressPerson();
		expressName = bean.getExpressName();
		
		expressNo = bean.getExpressNo();
		expressCode = bean.getExpressCode();
		expressTime = bean.getExpressTime();
		orderId = bean.getOrderId();
		isChange = bean.getIsChange();
		strChangePrice = bean.getStrChangePrice();
		isSpecial = bean.getIsSpecial();
		strSpecialPrice = bean.getStrSpecialPrice();
		sortNo = bean.getSortNo();
	}
	
	public Integer getSortNo() {
		return sortNo;
	}
	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}
	public Integer getIsSpecial() {
		return isSpecial;
	}
	
	public String getStrSpecialPrice() {
		return strSpecialPrice;
	}
	public void setIsSpecial(Integer isSpecial) {
		this.isSpecial = isSpecial;
	}
	
	public Integer getIsChange() {
		return isChange;
	}
	public void setIsChange(Integer isChange) {
		this.isChange = isChange;
	}
	
	public String getStrChangePrice() {
		if (null == strChangePrice)
			return "";
		return strChangePrice;
	}
	
	
	public Integer getAfterStatus() {
		if (null == afterStatus)
			afterStatus = -2;
		return afterStatus;
	}
	public void setAfterStatus(Integer afterStatus) {
		this.afterStatus = afterStatus;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getGoodsTypeId() {
		return goodsTypeId;
	}
	public void setGoodsTypeId(String goodsTypeId) {
		this.goodsTypeId = goodsTypeId;
	}
	private long discountPrice;
	
	private long freight;
	
	private int commentStatus;
	
	private Integer afterStatus;
	
	private String goodsId;
	
	private String goodsTypeId;
	
	public long getDiscountPrice() {
		return discountPrice/100;
	}
	
	public String getStrDiscountPrice() {
		return Utils.moneyFormatCN(discountPrice);
	}
	
	public void setDiscountPrice(long discountPrice) {
		this.discountPrice = discountPrice;
	}
	
	public long getFreight() {
		return freight/100;
	}
	public String getStrFreight() {
		return Utils.moneyFormatCN(freight);
	}
	
	
	public void setFreight(long freight) {
		this.freight = freight;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
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
	public String getSkuName() {
		return skuName;
	}
	public void setSkuName(String skuName) {
		this.skuName = skuName;
	}
	public String getGoodsIcon() {
		return goodsIcon;
	}
	public void setGoodsIcon(String goodsIcon) {
		this.goodsIcon = goodsIcon;
	}
	public Integer getSellNum() {
		return sellNum;
	}
	public void setSellNum(Integer sellNum) {
		this.sellNum = sellNum;
	}
	public Integer getExpressWay() {
		return expressWay;
	}
	public void setExpressWay(Integer expressWay) {
		this.expressWay = expressWay;
	}
	public String getExpressNote() {
		return expressNote;
	}
	public void setExpressNote(String expressNote) {
		this.expressNote = expressNote;
	}
	public String getExpressPhone() {
		return expressPhone;
	}
	public void setExpressPhone(String expressPhone) {
		this.expressPhone = expressPhone;
	}
	public String getExpressPerson() {
		return expressPerson;
	}
	public void setExpressPerson(String expressPerson) {
		this.expressPerson = expressPerson;
	}
	public String getExpressName() {
		return expressName;
	}
	public void setExpressName(String expressName) {
		this.expressName = expressName;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Date getExpressTime() {
		return expressTime;
	}
	public void setExpressTime(Date expressTime) {
		this.expressTime = expressTime;
	}
	public String getExpressCode() {
		return expressCode;
	}
	public void setExpressCode(String expressCode) {
		this.expressCode = expressCode;
	}
	public int getCommentStatus() {
		return commentStatus;
	}
	public void setCommentStatus(int commentStatus) {
		this.commentStatus = commentStatus;
	}
	
}
