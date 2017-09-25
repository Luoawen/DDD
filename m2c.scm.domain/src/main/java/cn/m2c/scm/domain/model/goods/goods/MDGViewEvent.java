package cn.m2c.scm.domain.model.goods.goods;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;

public class MDGViewEvent  extends AssertionConcern implements DomainEvent{
	
	
	
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
	private Date currentTime;
	
	
	
	
	
	

	public MDGViewEvent() {
		super();
	}
	
	

	public MDGViewEvent(String sn, String os, String appVersion,
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
		this.setCurrentTime(new Date());
	}



	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}

	public Long getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Long triggerTime) {
		this.triggerTime = triggerTime;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	public String getMresId() {
		return mresId;
	}

	public void setMresId(String mresId) {
		this.mresId = mresId;
	}

	public String getMresName() {
		return mresName;
	}

	public void setMresName(String mresName) {
		this.mresName = mresName;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}

	@Override
	public int eventVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Date occurredOn() {
		// TODO Auto-generated method stub
		return this.getCurrentTime();
	}

}
