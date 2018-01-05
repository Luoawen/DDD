package cn.m2c.scm.domain.model.config;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 配置(特惠价图片) 
 */
public class Config  extends ConcurrencySafeEntity {
	private String configKey;
	private String configValue;
	private String configDescribe;
	private Integer configStatus;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public Config() {
		super();
	}

	public Config(String configKey, String configValue, String configDescribe, Integer configStatus) {
		this.configKey = configKey;
		this.configValue = configValue;
		this.configDescribe = configDescribe;
		this.configStatus = configStatus;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	public void modifyConfig(String configValue, String configDescribe, Integer configStatus) {
		this.configValue = configValue;
		this.configDescribe = configDescribe;
		this.configStatus = configStatus;
		this.lastUpdatedDate = new Date();
	}
	
}
