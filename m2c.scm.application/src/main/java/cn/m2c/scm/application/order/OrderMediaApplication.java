package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.order.command.OrderMediaCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.OrderMedia;
import cn.m2c.scm.domain.model.order.OrderMediaRepository;

/**
 * 媒体广告位订单 
 */
@Service
@Transactional
public class OrderMediaApplication {
	 private static final Logger LOGGER = LoggerFactory.getLogger(OrderMediaApplication.class);
	 
	 @Autowired
	 OrderMediaRepository orderMediaRepository; 
	 
	 /**
	  * 保存广告位订单信息
	  * @param command
	  */
	 @Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	 public void addOrderMedia(OrderMediaCommand command) {
		 LOGGER.info("addOrderMedia command >>{}", command);
		 OrderMedia orderMedia = new OrderMedia(command.getOrderId(), command.getDealerOrderId(), command.getMediaCate(), command.getMediaNo(), command.getMediaName(), command.getMresCate(), command.getFormId(), command.getMresNo(), command.getLevel());
		 orderMediaRepository.save(orderMedia);
	 }
	 
}
