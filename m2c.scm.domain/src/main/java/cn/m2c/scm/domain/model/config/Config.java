package cn.m2c.scm.domain.model.config;

import java.util.Date;

import cn.m2c.ddd.common.domain.model.Entity;

/**
 * 配置(特惠价图片) 
 */
public class Config  extends Entity {
	private String configKey;      //配置名
	private String configValue;    //配置值
	private String configDescribe; //配置说明
	private Integer configStatus;  //配置状态（1正常，2不可用）
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

	/**
	 * 修改配置(配置内容，配置说明)
	 * @param configValue
	 * @param configDescribe
	 */
	public void modifyConfig(String configValue, String configDescribe) {
		this.configValue = configValue;
		this.configDescribe = configDescribe;
		this.lastUpdatedDate = new Date();
	}
	
}
