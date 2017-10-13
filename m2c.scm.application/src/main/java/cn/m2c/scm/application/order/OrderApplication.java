package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.order.data.representation.OrderNo;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.OrderIDGenernor;
import cn.m2c.scm.domain.model.order.OrderIDRepository;

/***
 * 订单应用服务类
 * @author fanjc
 *
 */
@Service
public class OrderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);
	
	@Autowired
	OrderIDRepository orderIdRepository;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public OrderNo getOrderNo() {
		OrderNo orderNo = new OrderNo();
		OrderIDGenernor orderNoGen = new OrderIDGenernor();
		orderNo.setOrderId(orderNoGen.getOrder());
		orderIdRepository.save(orderNoGen.getTime(), orderNoGen.getStr());
		return orderNo;
	}
}
