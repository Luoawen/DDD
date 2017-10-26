package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.scm.application.order.command.SendOrderCommand;
import cn.m2c.scm.application.order.command.UpdateAddrCommand;
import cn.m2c.scm.application.order.command.UpdateOrderFreightCmd;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderRepository;
import cn.m2c.scm.domain.model.order.ReceiveAddr;

@Service
@Transactional
public class DealerOrderApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DealerOrderApplication.class);

	@Autowired
	DealerOrderRepository dealerOrderRepository;

	/**
	 * 更新物流信息
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void updateExpress(SendOrderCommand command) throws NegativeException {
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在在.");
		dealerOrder.updateExpress(command.getExpressName(), command.getExpressNo(), command.getExpressNote(),
				command.getExpressPerson(), command.getExpressPhone(), command.getExpressWay(),
				command.getExpressCode());
		dealerOrderRepository.save(dealerOrder);
	}

	/**
	 * 更新收货地址
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void updateAddress(UpdateAddrCommand command) throws NegativeException {
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在在.");
		ReceiveAddr addr = dealerOrder.getAddr();
		addr.updateAddr(command.getProvince(), command.getProvCode(), command.getCity(), command.getCityCode(),
				command.getArea(), command.getAreaCode(), command.getStreet(), command.getRevPerson(),
				command.getPhone());
		dealerOrder.updateAddr(addr);
		dealerOrderRepository.save(dealerOrder);
	}
	
	/**
	 * 更新订单运费
	 * @param command
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void updateOrderFreight(UpdateOrderFreightCmd command) throws NegativeException {
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在在.");
		dealerOrder.updateOrderFreight(command.getOrderFreight());
		dealerOrderRepository.save(dealerOrder);

	}
}
