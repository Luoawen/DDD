package cn.m2c.scm.application.order.data.bean;

import cn.m2c.scm.domain.model.order.SimpleMediaInfo;

/***
 * 媒体资源 简单bean
 * @author fanjc
 * created date 2017年10月23日
 * copyrighted@m2c
 */
public class MediaResBean {

	private String mresId; //广告位ID
	//媒体ID
	private String mediaId;
	// 媒体分成
	private String mediaRatio;
	// 促销员id
	private String salesmanId;
	// 促销员分成
	private String salesmanRatio;
	// 销售专员及分成
	private String bdDeductInfoList;
	
	
	public String getMresId() {
		return mresId;
	}
	public void setMresId(String mresId) {
		this.mresId = mresId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}
	public String getMediaRatio() {
		return mediaRatio;
	}
	public void setMediaRatio(String mediaRatio) {
		this.mediaRatio = mediaRatio;
	}
	public String getSalesmanId() {
		return salesmanId;
	}
	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
	}
	public String getSalesmanRatio() {
		return salesmanRatio;
	}
	public void setSalesmanRatio(String salesmanRatio) {
		this.salesmanRatio = salesmanRatio;
	}
	public String getBdStaffRatio() {
		return bdDeductInfoList;
	}
	public void setBdStaffRatio(String bdStaffRatio) {
		this.bdDeductInfoList = bdStaffRatio;
	}
	
	public SimpleMediaInfo toMediaInfo() {
		return new SimpleMediaInfo(mediaId, mediaRatio, bdDeductInfoList, mresId, salesmanId, salesmanRatio);
	}
}
