package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;

/***
 * 订单应用服务类
 * @author fanjc
 *
 */
@Service
public class OrderApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderApplication.class);
	
	@Autowired
	OrderRepository orderRepository;
	
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void submitOrder() {
		// 判断库存
		// 锁定库存
		// 拆单
		// 优惠券
		// 营销
		// 计算
		// 组织保存
		MainOrder order = new MainOrder();
		order.add();
		orderRepository.save(order);
	}
}
