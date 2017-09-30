package cn.m2c.scm.application.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.dealer.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeException;


@Service
@Transactional
public class DealerApplication {
	private static final Logger log = LoggerFactory.getLogger(DealerApplication.class);

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addDealer(DealerAddOrUpdateCommand command) {
		
	}
	
	
	
}
