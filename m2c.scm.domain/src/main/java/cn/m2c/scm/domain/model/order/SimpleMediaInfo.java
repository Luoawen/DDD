package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ValueObject;
/***
 * 简单媒体对象
 * @author 89776
 * created date 2017年10月25日
 * copyrighted@m2c
 */
public class SimpleMediaInfo extends ValueObject {

	/**媒体ID*/
	private String mediaId;
	/**广告位分成比例*/
	private String resRate = "0";
	/**BD专员的分成串*/
	private String bdsRate;
	/**媒体资源ID*/
	private String mediaResId;
	/**促销员ID*/
	private String salerUserId;
	/**促销员分成比例*/
	private String salerUserRate;
	
	public SimpleMediaInfo() {
		super();
	}
	
	public SimpleMediaInfo(String mediaId, String resRate,
			String bdsRate, String mediaResId, String salerUserId, String salerUserRate) {
		super();
		
		this.mediaId = mediaId;
		this.resRate = resRate;
		this.bdsRate = bdsRate;
		this.mediaResId = mediaResId;
		this.salerUserId = salerUserId;
		this.salerUserRate = salerUserRate;
	}
	
	String getMediaResId() {
		return mediaResId;
	}
	
	String getBdsRate() {
		return bdsRate;
	}
	
	String getMediaId() {
		return mediaId;
	}
	
	String getSalerUserId() {
		return salerUserId;
	}
}
