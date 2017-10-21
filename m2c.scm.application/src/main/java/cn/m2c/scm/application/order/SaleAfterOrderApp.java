package cn.m2c.scm.application.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.order.command.AddSaleAfterCmd;
import cn.m2c.scm.application.order.command.AproveSaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterShipCmd;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.SaleAfterOrder;
import cn.m2c.scm.domain.model.order.SaleAfterOrderRepository;

/***
 * 售后应用层服务
 * @author 89776
 * created date 2017年10月21日
 * copyrighted@m2c
 */
@Service
public class SaleAfterOrderApp {
	private final static Logger LOGGER = LoggerFactory.getLogger(SaleAfterOrderApp.class);
	@Autowired
	SaleAfterOrderRepository saleAfterRepository;
	
	/***
	 * 创建售后单
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void createSaleAfterOrder(AddSaleAfterCmd cmd) throws NegativeException {
		// 获取订单SKU详情看是否满足售后申请
		DealerOrderDtl itemDtl = saleAfterRepository.getDealerOrderDtlBySku(cmd.getDealerOrderId(), 
				cmd.getSkuId());
		
		if (!itemDtl.canApplySaleAfter()) {
			throw new NegativeException(MCode.V_100, "商品处于不可申请售后状态！");
		}
		// 生成售后单保存
		long money = 1000;
		SaleAfterOrder afterOrder = new SaleAfterOrder(cmd.getSaleAfterNo(), cmd.getUserId(), cmd.getOrderId(),
				cmd.getDealerOrderId(), cmd.getDealerId(), cmd.getGoodsId(), cmd.getSkuId(), cmd.getReason()
				, cmd.getBackNum(), 0, cmd.getType(), money, cmd.getReasonCode());
		
		saleAfterRepository.save(afterOrder);
		LOGGER.info("新增加售后申请成功！");
	}
	/***
	 * 申请售后退货或退款提示
	 * @param cmd
	 */
	public void applySaleAfter(AddSaleAfterCmd cmd) {
		// 获取订单SKU详情看是否满足售后申请
		// 计算需要退的金额
	}
	/***
	 * 同意售后申请
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void agreeApply(AproveSaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo(), cmd.getDealerId());
		if (order == null) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		order.agreeApply(cmd.getUserId());
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	/***
	 * 客户退货发货
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void userShipGoods(SaleAfterShipCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.clientShip(cmd.getExpressInfo(), cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行发货操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	/***
	 * 商家换货发货
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void dealerShipGoods(SaleAfterShipCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.dealerShip(cmd.getSdExpressInfo(), cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行发货操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	
	/***
	 * 确认退款
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void approveReturnMoney(SaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.confirmBackMoney(cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行发货操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	/***
	 * 同意退款
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void agreeBackMoney(SaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.agreeBackMoney(cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行此操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	/***
	 * 用户确认收货
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void userConfirmRev(SaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.userConfirmRev(cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行收货操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
	
	/***
	 * 商家确认收货
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void dealerConfirmRev(SaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo());
		if (order == null || !order.isSame(cmd.getSkuId())) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		if (!order.dealerConfirmRev(cmd.getUserId())) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行收货操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
	}
}