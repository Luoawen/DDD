package cn.m2c.scm.application.config.command;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

/**
 * 修改配置 
 */
public class ConfigModifyCommand  extends AssertionConcern implements Serializable {
	private String configKey;
	private String configValue;
	private String configDescribe;
	private Integer configStatus;
	private Date createdDate;
	private Date lastUpdatedDate;
	
	public ConfigModifyCommand(String configKey, String configValue, String configDescribe, Integer configStatus) throws NegativeException {
		if(StringUtils.isEmpty(configKey) && StringUtils.isEmpty(configKey.trim())) {
			throw new NegativeException(MCode.V_1, "未获取到配置key");
		}
		if(StringUtils.isEmpty(configValue) && StringUtils.isEmpty(configValue.trim())) {
			throw new NegativeException(MCode.V_1, "配置内容不能为空");
		}
		if(null == configStatus) {
			throw new NegativeException(MCode.V_1, "配置状态不能为空");
		}
		this.configKey = configKey;
		this.configValue = configValue;
		this.configDescribe = configDescribe.trim();
		this.configStatus = configStatus;
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
	
}
