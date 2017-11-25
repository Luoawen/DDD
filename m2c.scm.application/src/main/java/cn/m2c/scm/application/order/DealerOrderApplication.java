package cn.m2c.scm.application.order;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.order.command.SendOrderCommand;
import cn.m2c.scm.application.order.command.UpdateAddrCommand;
import cn.m2c.scm.application.order.command.UpdateAddrFreightCmd;
import cn.m2c.scm.application.order.command.UpdateOrderFreightCmd;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrder;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.DealerOrderDtlRepository;
import cn.m2c.scm.domain.model.order.DealerOrderRepository;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.domain.model.order.ReceiveAddr;
import cn.m2c.scm.domain.util.GetDisconfDataGetter;

@Service
@Transactional
public class DealerOrderApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(DealerOrderApplication.class);

	
	@Autowired
	DealerOrderRepository dealerOrderRepository;
	
	@Autowired
	DealerOrderDtlRepository orderDtlRepository;

	@Autowired
    OrderRepository orderRepository;
	/**
	 * 更新物流信息
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void updateExpress(SendOrderCommand command) throws NegativeException {

		LOGGER.info("更新物流信息");
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在.");
		if (!dealerOrder.updateExpress(command.getExpressName(), command.getExpressNo(), command.getExpressNote(),
				command.getExpressPerson(), command.getExpressPhone(), command.getExpressWay(),
				command.getExpressCode(), command.getUserId())) {
			throw new NegativeException(MCode.V_300, "订单处于不可发货状态");
		}
		dealerOrderRepository.save(dealerOrder);
	}

	/**
	 * 更新收货地址
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void updateAddress(UpdateAddrCommand command) throws NegativeException {
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在.");
		ReceiveAddr addr = dealerOrder.getAddr();
		addr.updateAddr(command.getProvince(), command.getProvCode(), command.getCity(), command.getCityCode(),
				command.getArea(), command.getAreaCode(), command.getStreet(), command.getRevPerson(),
				command.getPhone());
		dealerOrder.updateAddr(addr, command.getUserId());
		dealerOrderRepository.save(dealerOrder);
	}

	/**
	 * 更新订单运费
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void updateOrderFreight(UpdateOrderFreightCmd command) throws NegativeException {
		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(command.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在.");
		dealerOrder.updateOrderFreight(command.getOrderFreight(), command.getUserId());
		dealerOrderRepository.save(dealerOrder);
	}

	/**
	 * 更新收货地址及运费
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void updateAddrFreight(UpdateAddrFreightCmd cmd) throws NegativeException {

		DealerOrder dealerOrder = dealerOrderRepository.getDealerOrderById(cmd.getDealerOrderId());
		if (dealerOrder == null)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "此商家订单不存在.");

		if (!dealerOrder.canUpdateFreight())
			throw new NegativeException(MCode.V_1, "此商家订单处于不能修改状态.");

		ReceiveAddr addr = dealerOrder.getAddr();
		boolean updatedAddr = addr.updateAddr(cmd.getProvince(), cmd.getProvCode(), cmd.getCity(), cmd.getCityCode(),
				cmd.getArea(), cmd.getAreaCode(), cmd.getStreet(), cmd.getRevPerson(), cmd.getPhone());
		
		MainOrder mOrder = orderRepository.getOrderById(dealerOrder.getOrderNo());
		if (updatedAddr) {
			dealerOrder.updateAddr(addr, cmd.getUserId());
		}
		boolean updatedFreight = dealerOrder.updateOrderFreight(cmd.getFreights(), cmd.getUserId());
		if (mOrder.updateAddr(addr) || updatedFreight) {
			mOrder.updateFreight(dealerOrder);
			orderRepository.updateMainOrder(mOrder);
		}
		mOrder = null;
		if (updatedFreight || updatedAddr)
			dealerOrderRepository.updateFreight(dealerOrder);
	}

	/**
	 * 更新订单状态<将待收货改为已完成>
	 * 
	 * @param beanList
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void orderDtlToFinished() throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("order.cinfirm"));
			if (hour < 1)
				hour = 1;
		} catch (Exception e) {
			
		}
		List<DealerOrderDtl> dealerOrders = orderDtlRepository.getOrderDtlStatusQeury(hour, 2);

		for (DealerOrderDtl orderDtl : dealerOrders) {
			jobFinishiedOrder(orderDtl);
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	@EventListener(isListening = true)
	private void jobFinishiedOrder(DealerOrderDtl orderDtl) {
		orderDtl.finished();
		orderDtlRepository.save(orderDtl);
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void orderDtlToDealFinished() throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("order.cinfirm"));
			if (hour < 1)
				hour = 1;
		} catch (Exception e) {
			
		}
		List<DealerOrderDtl> dealerOrders = orderDtlRepository.getOrderDtlStatusQeury(hour, 3);

		for (DealerOrderDtl orderDtl : dealerOrders) {
			jobOrderDealFinishied(orderDtl);
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	@EventListener(isListening = true)
	private void jobOrderDealFinishied(DealerOrderDtl orderDtl) {
		orderDtl.dealFinished();
		orderDtlRepository.save(orderDtl);
	}
	

	/**
	 * 公用方法
	 * @param dealerOrders
	 * @return
	 * @throws NegativeException
	 */
	public List<DealerOrder> commondMethod(List<DealerOrder> dealerOrders, long hours) throws NegativeException {
		List<DealerOrder> list = new ArrayList<DealerOrder>();
		if (dealerOrders.size() == 0)
			throw new NegativeException(NegativeCode.DEALER_ORDER_IS_NOT_EXIST, "没有满足条件的商家订单.");
		/** 
		 * 计算出超过7天的订单
		 */
		for (DealerOrder bean : dealerOrders) {
			if (((System.currentTimeMillis() - bean.dateToLong()) / (1000 * 60 * 60)) > hours)
				list.add(bean);
		}
		return list;
	}

	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void commentSku(String orderId, String skuId, int flag) {
		dealerOrderRepository.updateComment(orderId, skuId, flag);
	}
	/***
	 * 检测商家订单下的子单完成
	 * @param userId
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void dtlCompleteUpdated(String userId) {
		dealerOrderRepository.getSpecifiedDtlStatus(1);
	}
}
