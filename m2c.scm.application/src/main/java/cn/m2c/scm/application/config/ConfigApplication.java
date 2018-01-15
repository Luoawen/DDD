package cn.m2c.scm.application.config;

import java.text.ParseException;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.logger.OperationLogManager;
import cn.m2c.scm.application.config.command.ConfigCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.config.Config;
import cn.m2c.scm.domain.model.config.ConfigRepository;

/**
 * 配置 
 */
@Service
@Transactional
public class ConfigApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigApplication.class);

	@Autowired
	ConfigRepository configRepository;
	
	@Resource
    private OperationLogManager operationLogManager;
	
	/**
	 * 添加配置
	 * @param configCommand
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void saveConfig(ConfigCommand command) throws NegativeException {
		LOGGER.info("saveConfig command >>{}", command);
		Config config = configRepository.queryConfigByKey(command.getConfigKey());
		if(config != null) {
			throw new NegativeException(MCode.V_300, "配置已存在");
		}
		config = new Config(command.getConfigKey(), command.getConfigValue(), command.getConfigDescribe(), command.getConfigStatus());
		configRepository.save(config);
	}

	/**
	 * 修改配置
	 * @param ConfigCommand
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void modifyConfig(ConfigCommand command, String _attach) throws NegativeException {
		LOGGER.info("modifyConfig command >>{}", command);
		Config config = configRepository.queryConfigByKey(command.getConfigKey());
		if(null == config) {
			throw new NegativeException(MCode.V_300, "配置不存在");
		}
		if (StringUtils.isNotEmpty(_attach))
        	operationLogManager.operationLog("修改配置", _attach, config);
		config.modifyConfig(command.getConfigValue(), command.getConfigDescribe());
	}
	
	
}
