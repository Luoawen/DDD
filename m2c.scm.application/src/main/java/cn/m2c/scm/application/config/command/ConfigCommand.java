package cn.m2c.scm.application.config.command;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

/**
 * 新增配置 
 */
public class ConfigCommand  extends AssertionConcern implements Serializable {
	private String configKey;
	private String configValue;
	private String configDescribe;
	private Integer configStatus;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public ConfigCommand(String configKey, String configValue, String configDescribe) throws NegativeException {
		if(StringUtils.isEmpty(configKey) && StringUtils.isEmpty(configKey.trim())) {
			throw new NegativeException(MCode.V_1, "未获取到配置key");
		}
		if(StringUtils.isEmpty(configValue) && StringUtils.isEmpty(configValue.trim())) {
			throw new NegativeException(MCode.V_1, "配置内容不能为空");
		}
		if(StringUtils.isNotEmpty(configDescribe) && StringUtils.isNotEmpty(configDescribe.trim())) {
			this.configDescribe = configDescribe.trim();
		}else {
			this.configDescribe = null;
		}
		this.configKey = configKey;
		this.configValue = configValue;
		this.configStatus = 1;
	}

	public String getConfigKey() {
		return configKey;
	}

	public String getConfigValue() {
		return configValue;
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
		return "ConfigCommand [configKey=" + configKey + ", configValue=" + configValue + ", configDescribe="
				+ configDescribe + ", configStatus=" + configStatus + ", createdDate=" + createdDate
				+ ", lastUpdatedDate=" + lastUpdatedDate + "]";
	}
	
}
