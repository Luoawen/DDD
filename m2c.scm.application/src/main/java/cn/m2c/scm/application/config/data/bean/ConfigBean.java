package cn.m2c.scm.application.config.data.bean;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 配置 
 */
public class ConfigBean {
	/**
	 * 主键id
	 */
	@ColumnAlias(value = "id")
	private Integer id;
	
	/**
	 * 配置名
	 */
	@ColumnAlias(value = "config_key")
	private String configKey;
	
	/**
	 * 配置值
	 */
	@ColumnAlias(value = "config_value")
	private String configValue;
	
	/**
	 * 配置说明
	 */
	@ColumnAlias(value = "config_describe")
	private String configDescribe;
	
	/**
	 * 配置状态（1正常，2不可用）
	 */
	@ColumnAlias(value = "config_status")
	private Integer configStatus;
	
	@ColumnAlias(value = "created_date")
	private Date createdDate;
	
	@ColumnAlias(value = "last_updated_date")
	private Date lastUpdatedDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
