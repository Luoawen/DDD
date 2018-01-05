package cn.m2c.scm.application.config.data.representation;

import java.util.Date;

import cn.m2c.scm.application.config.data.bean.ConfigBean;

/**
 * 配置表述对象
 */
public class ConfigBeanRepresentation {
	private String configKey;
	private String configValue;
	private String configDescribe;
	private Integer configStatus;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public ConfigBeanRepresentation (ConfigBean bean) {
		this.configKey = bean.getConfigKey();
		this.configValue = bean.getConfigValue();
		this.configDescribe = bean.getConfigDescribe();
		this.configStatus = bean.getConfigStatus();
		this.createdDate = bean.getCreatedDate();
		this.lastUpdatedDate = bean.getLastUpdatedDate();
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}

	public String getConfigDescribe() {
		return configDescribe;
	}

	public void setConfigDescribe(String configDescribe) {
		this.configDescribe = configDescribe;
	}

	public Integer getConfigStatus() {
		return configStatus;
	}

	public void setConfigStatus(Integer configStatus) {
		this.configStatus = configStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}
	
}
