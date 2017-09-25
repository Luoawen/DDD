package cn.m2c.scm.application.order.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.order.logger.command.CreateLoggerCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.logger.OperLogger;
import cn.m2c.scm.domain.model.order.logger.OperLoggerRepository;

/**
 * 
 * @ClassName: LoggerApplication
 * @Description: 操作日志
 * @author moyj
 * @date 2017年7月14日 下午12:50:14
 *
 */
@Service
public class OperLoggerApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(OperLoggerApplication.class);
	
	@Autowired
	private OperLoggerRepository operLoggerRepository;
	
	/** 创建操作日志
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void create(CreateLoggerCommand command) throws NegativeException{
		LOGGER.info(" operLogger create command >>{}", command);
		OperLogger operLogger = new OperLogger();
		operLogger.create(command.getLoggerId(), 
				command.getBusinessId(), 
				command.getBusinessType(),
				command.getOperName(), 
				command.getOperDes(),
				command.getOperResult(), 
				command.getOperTime(), 
				command.getOperUserId(), command.getOperUserName());
		operLoggerRepository.save(operLogger);
	}
}
