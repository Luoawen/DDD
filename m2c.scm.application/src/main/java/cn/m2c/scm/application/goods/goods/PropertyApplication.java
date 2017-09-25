package cn.m2c.scm.application.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.dealer.DealerRepository;
import cn.m2c.goods.domain.templet.Property;
import cn.m2c.goods.domain.templet.PropertyRepository;
import cn.m2c.goods.exception.NegativeCode;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.command.PropertyAddOrUpdateCommand;


@Service
@Transactional
public class PropertyApplication {
	private  Logger logger = LoggerFactory.getLogger(PropertyApplication.class);

	@Autowired
	DealerRepository dealerRepository;
	@Autowired
	PropertyRepository propertyRepository;
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void addProperty(PropertyAddOrUpdateCommand command) throws NegativeException {
		logger.info("Property add command >>{}", command);	
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Property property = new Property();
		property.add(command.getPropertyId(),command.getModelName(),command.getPropertyValue(),command.getDealerId());
		propertyRepository.save(property);
	}
	
	
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public  void updateProperty(PropertyAddOrUpdateCommand command) throws NegativeException {
		logger.info("Property update command >>{}", command);	
		Dealer dealer = dealerRepository.getDealerDetail(command.getDealerId());
		if(dealer==null)
			throw new NegativeException(NegativeCode.DEALER_IS_NOT_EXIST, "此经销商不存在.");
		Property property = propertyRepository.getDetail(command.getPropertyId());
		if(property==null)
			throw new NegativeException(NegativeCode.PROPERTY_IS_NOT_EXIST, "此属性模板不存在.");
		property.update(command.getModelName(),command.getPropertyValue());
		propertyRepository.save(property);
	}

	@Transactional(rollbackFor = {Exception.class,RuntimeException.class,NegativeException.class})
	public void delProperty(String propertyId) throws NegativeException {
		logger.info("Property delete command >>{}", propertyId);	
		Property property = propertyRepository.getDetail(propertyId);
		if(property==null)
			throw new NegativeException(NegativeCode.PROPERTY_IS_NOT_EXIST, "此属性模板不存在.");
		property.delProperty();
		propertyRepository.save(property);
	}
}
