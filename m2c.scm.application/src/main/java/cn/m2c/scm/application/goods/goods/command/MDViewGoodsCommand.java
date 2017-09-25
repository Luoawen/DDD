package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class MDViewGoodsCommand  extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = -4779357210480022017L;
	
	
	private String sn;
	private String os;
	private String appVersion;
	private String osVersion;
	private Long triggerTime;
	private Long lastTime;
	private String userId;
	private String userName;
	private String areaProvince;
	private String areaDistrict;
	private String provinceCode;
	private String districtCode;
	private String goodsId;
	private String goodsName;
	private String mediaId;
	private String mediaName;
	private String mresId;
	private String mresName;
	public MDViewGoodsCommand() {
		super();
	}
	public MDViewGoodsCommand(String sn, String os, String appVersion,
			String osVersion, Long triggerTime, Long lastTime, String userId,
			String userName, String areaProvince,String areaDistrict,String provinceCode, String districtCode,String goodsId, String goodsName, String mediaId,
			String mediaName, String mresId, String mresName) {
		super();
		this.sn = sn;
		this.os = os;
		this.appVersion = appVersion;
		this.osVersion = osVersion;
		this.triggerTime = triggerTime;
		this.lastTime = lastTime;
		this.userId = userId;
		this.userName = userName;
		this.areaProvince = areaProvince;
		this.areaDistrict = areaDistrict;
		this.provinceCode = provinceCode;
		this.districtCode = districtCode;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mresId = mresId;
		this.mresName = mresName;
	}
	
	
	public MDViewGoodsCommand(String sn, String os, String appVersion,
			String osVersion, Long triggerTime, String userId, String userName,
			String goodsId, String goodsName, String mediaId, String mediaName,
			String mresId, String mresName) {
		super();
		this.sn = sn;
		this.os = os;
		this.appVersion = appVersion;
		this.osVersion = osVersion;
		this.triggerTime = triggerTime;
		this.userId = userId;
		this.userName = userName;
		this.goodsId = goodsId;
		this.goodsName = goodsName;
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mresId = mresId;
		this.mresName = mresName;
	}
	public String getSn() {
		return sn;
	}
	public String getOs() {
		return os;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public Long getTriggerTime() {
		return triggerTime;
	}
	public Long getLastTime() {
		return lastTime;
	}
	public String getUserId() {
		return userId;
	}
	public String getUserName() {
		return userName;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getMediaId() {
		return mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public String getMresId() {
		return mresId;
	}
	public String getMresName() {
		return mresName;
	}
	
	public String getAreaProvince() {
		return areaProvince;
	}
	public String getAreaDistrict() {
		return areaDistrict;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public String getDistrictCode() {
		return districtCode;
	}
	@Override
	public String toString() {
		return "MDViewGoodsCommand [sn=" + sn + ", os=" + os + ", appVersion="
				+ appVersion + ", osVersion=" + osVersion + ", triggerTime="
				+ triggerTime + ", lastTime=" + lastTime + ", userId=" + userId
				+ ", userName=" + userName + ", areaProvince=" + areaProvince
				+ ", areaDistrict=" + areaDistrict + ", provinceCode="
				+ provinceCode + ", districtCode=" + districtCode
				+ ", goodsId=" + goodsId + ", goodsName=" + goodsName
				+ ", mediaId=" + mediaId + ", mediaName=" + mediaName
				+ ", mresId=" + mresId + ", mresName=" + mresName + "]";
	}
	
	
}
