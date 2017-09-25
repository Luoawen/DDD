package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.goods.goods.command.TransportFeeAddOrUpdateCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.goods.dealer.Dealer;
import cn.m2c.scm.domain.model.goods.dealer.DealerRepository;
import cn.m2c.scm.domain.model.goods.templet.TransportFee;
import cn.m2c.scm.domain.model.goods.templet.TransportFeeRepository;

@Service
@Transactional
public class TransportFeeApplication {
	private  Logger logger = LoggerFactory.getLogger(TransportFeeApplication.class);
	
	@Autowired
	DealerRepository dealerRepository;
	@Autowired
	TransportFeeRepository transportFeeRepository;  
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addTransportFee(TransportFeeAddOrUpdateCommand command) throws NegativeException {
		logger.info("Property add command >>{}", command);	
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		TransportFee fee = new TransportFee();
		fee.add(command.getTransportFeeId(),command.getModelName(),command.getFee(),command.getDealerId());
		transportFeeRepository.save(fee);
	}

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void updatetransportFee(TransportFeeAddOrUpdateCommand command) throws NegativeException {
		logger.info("Property UPDATE command >>{}", command);	
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		TransportFee fee = transportFeeRepository.getDetail(command.getTransportFeeId());
		if(fee==null)
			throw new NegativeException(NegativeCode.TRANSPORTFEE_IS_NOT_EXIST, "物流模板不存在.");
		fee.update(command.getModelName(),command.getFee());		
		transportFeeRepository.save(fee);
	}
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void deltransportFee(String transportFeeId) throws NegativeException {
		logger.info("Property del command >>{}", transportFeeId);
		TransportFee fee = transportFeeRepository.getDetail(transportFeeId);
		if(fee==null)
			throw new NegativeException(NegativeCode.TRANSPORTFEE_IS_NOT_EXIST, "物流模板不存在.");
		fee.del();
		transportFeeRepository.save(fee);
	}
}
