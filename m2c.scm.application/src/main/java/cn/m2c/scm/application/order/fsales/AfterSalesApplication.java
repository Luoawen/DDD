package cn.m2c.scm.application.order.fsales;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.order.fsales.command.ApplyAfterSalesCommand;
import cn.m2c.scm.application.order.fsales.command.AuditAfterSalesCommand;
import cn.m2c.scm.application.order.fsales.command.BackedGoodsCommand;
import cn.m2c.scm.application.order.fsales.command.BarteredCommand;
import cn.m2c.scm.application.order.fsales.command.BarteringCommand;
import cn.m2c.scm.application.order.fsales.command.BatchBarteredCommand;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.service.AfterSalesService;

@Service
public class AfterSalesApplication {
	private static final Logger LOGGER = LoggerFactory.getLogger(AfterSalesApplication.class);
	@Autowired
	private AfterSalesService afterSalesService;
	
	/** 申请售后
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	public void applyAfterSales(ApplyAfterSalesCommand command) throws NegativeException{
		LOGGER.info(" Order applyAfterSales command >>{}", command);
		afterSalesService.applyAfterSales(command.getOrderId(), command.getApplyReason(), command.getFsaleDeadline());
	}
	
	/**
	 * 售后审核
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void auditAfterSales(AuditAfterSalesCommand command) throws NegativeException{
		LOGGER.info(" Order AuditAfterSales command >>{}", command);
		afterSalesService.auditAfterSales(command.getOrderIdList(), command.getAuditFlag(),command.getUnauditReason(),command.getHandManId(),command.getHandManName());	
	}
	
	/**
	 * 退货完成
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void backedGoods(BackedGoodsCommand command) throws NegativeException{
		LOGGER.info(" Order backGoods command >>{}", command);
		afterSalesService.backedGoods(command.getOrderId(),command.getRefundAmount(),command.getRefbaredReason(),command.getHandManId(),command.getHandManName());
	}
	
	/**
	 * 换货中
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void bartering(BarteringCommand command) throws NegativeException{
		LOGGER.info(" Order bartering command >>{}", command);
		afterSalesService.bartering(command.getOrderId(),command.getRefbaredReason(),command.getHandManId(),command.getHandManName());
	}
	
	/** 换货完成
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void bartered(BarteredCommand command) throws NegativeException{
		LOGGER.info(" Order bartered command >>{}", command);
		afterSalesService.bartered(command.getOrderId(),command.getHandManId(),command.getHandManName());
	}
	/** 
	 * 批量换货完成
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class, NegativeException.class })
	@EventListener
	public void batchBartered(BatchBarteredCommand command) throws NegativeException{
		LOGGER.info(" Order bartered command >>{}", command);
		for(String orderId:command.getOrderIdList()){
			afterSalesService.bartered(orderId,command.getHandManId(),command.getHandManName());
		}
		
	}
}
