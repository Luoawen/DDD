package cn.m2c.scm.application.config.command;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.scm.domain.NegativeException;

/**
 * 修改配置 
 */
public class ConfigModifyCommand {
	private String configKey;
	private String configValue;    //新配置url
	private String configOldValue; //原配置url
	private String configDescribe;
	private Integer configStatus;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public ConfigModifyCommand(String configKey, String configValue, String configOldValue, String configDescribe) throws NegativeException {
		if(StringUtils.isEmpty(configKey) && StringUtils.isEmpty(configKey.trim())) {
			throw new NegativeException(MCode.V_1, "未获取到配置key");
		}
		if(StringUtils.isEmpty(configValue) && StringUtils.isEmpty(configValue.trim())) {
			if(StringUtils.isEmpty(configOldValue) && StringUtils.isEmpty(configOldValue.trim())) {
				throw new NegativeException(MCode.V_1, "未获取到原配置路径");
			}
		}
		if(StringUtils.isNotEmpty(configDescribe) && StringUtils.isNotEmpty(configDescribe.trim())) {
			this.configDescribe = configDescribe.trim();
		}else {
			this.configDescribe = null;
		}
		this.configKey = configKey;
		this.configValue = configValue;
		this.configOldValue = configOldValue;
		this.configStatus = 1;
	}

	public String getConfigKey() {
		return configKey;
	}

	public String getConfigValue() {
		return configValue;
	}

	public String getConfigOldValue() {
		return configOldValue;
	}

	public String getConfigDescribe() {
		return configDescribe;
	}

	public Integer getConfigStatus() {
		return configStatus;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public Date getLastUpdatedDate() {
		return lastUpdatedDate;
	}

	@Override
	public String toString() {
		return "ConfigModifyCommand [configKey=" + configKey + ", configValue=" + configValue + ", configOldValue="
				+ configOldValue + ", configDescribe=" + configDescribe + ", configStatus=" + configStatus
				+ ", createdDate=" + createdDate + ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
	
}
