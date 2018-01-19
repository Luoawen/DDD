package cn.m2c.scm.application.order.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 广告位订单 
 */
public class OrderMediaCommand extends AssertionConcern implements Serializable{
	/**
	 * 平台订单
	 */
	private String orderId;
	/**
	 * 商家订单
	 */
	private String dealerOrderId;
	/**
	 * 媒体分类
	 */
	private String mediaCate;
	/**
	 * 媒体编号
	 */
	private Integer mediaNo;
	/**
	 * 媒体名
	 */
	private String mediaName;
	/**
	 * 广告位位置
	 */
	private Integer mresCate;
	/**
	 * 广告位形式
	 */
	private Integer formId;
	/**
	 * 广告位条码
	 */
	private Long mresNo;
	/**
	 * 媒体等级
	 */
	private Integer level;
	
	public OrderMediaCommand (String orderId, String dealerOrderId, String mediaCate, Integer mediaNo, String mediaName, 
			Integer mresCate, Integer formId, Long mresNo, Integer level) {
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.mediaCate = mediaCate;
		this.mediaNo = mediaNo;
		this.mediaName = mediaName;
		this.mresCate = mresCate;
		this.formId = formId;
		this.mresNo = mresNo;
		this.level = level;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getMediaCate() {
		return mediaCate;
	}

	public Integer getMediaNo() {
		return mediaNo;
	}

	public String getMediaName() {
		return mediaName;
	}

	public Integer getMresCate() {
		return mresCate;
	}

	public Integer getFormId() {
		return formId;
	}

	public Long getMresNo() {
		return mresNo;
	}

	public Integer getLevel() {
		return level;
	}
	
}
