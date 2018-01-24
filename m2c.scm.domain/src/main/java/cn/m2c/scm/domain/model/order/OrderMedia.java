package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.Entity;

/**
 * 媒体广告位订单 
 */
public class OrderMedia extends Entity{
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
	
	/**
	 * sort no商家在订单中的插入位置
	 */
	private Integer sortNo;
	
	public OrderMedia(String orderId, String dealerOrderId, String mediaCate, Integer mediaNo, String mediaName, 
			Integer mresCate, Integer formId, Long mresNo, Integer level, Integer sortNo) {
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.mediaCate = mediaCate;
		this.mediaNo = mediaNo;
		this.mediaName = mediaName;
		this.mresCate = mresCate;
		this.formId = formId;
		this.mresNo = mresNo;
		this.level = level;
		this.sortNo = sortNo;
	}
	
}
