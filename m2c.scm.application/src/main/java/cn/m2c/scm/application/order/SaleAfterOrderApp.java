package cn.m2c.scm.application.order;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.scm.application.order.command.AddSaleAfterCmd;
import cn.m2c.scm.application.order.command.AproveSaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterShipCmd;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.RefundEvtBean;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;
import cn.m2c.scm.application.order.query.AfterSellOrderQuery;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.DealerOrderDtl;
import cn.m2c.scm.domain.model.order.SaleAfterOrder;
import cn.m2c.scm.domain.model.order.SaleAfterOrderRepository;
import cn.m2c.scm.domain.util.GetDisconfDataGetter;

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
	
	@Autowired
	AfterSellOrderQuery saleOrderQuery;
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
		
		if (itemDtl == null) {
			throw new NegativeException(MCode.V_1, "申请售后的商品不存在！");
		}
		
		int ij = saleAfterRepository.getSaleAfterOrderBySkuId(cmd.getDealerOrderId(), 
				cmd.getSkuId());
		if (ij > 0) {
			throw new NegativeException(MCode.V_100, "此商品已有售后还在处理中！");
		}
		
		if (!itemDtl.canApplySaleAfter()) {
			throw new NegativeException(MCode.V_100, "商品处于不可申请售后状态！");
		}
		// 生成售后单保存, 计算售后需要退的钱
		String mkId = itemDtl.getMarketId(); 
		long discountMoney = 0;
		long money = itemDtl.sumGoodsMoney();
		if (mkId != null) {//计算售后需要退的钱
			SimpleMarket marketInfo = saleOrderQuery.getMarketById(mkId, cmd.getOrderId());
			List<SkuNumBean> skuBeanLs = saleOrderQuery.getOrderDtlByMarketId(mkId, cmd.getOrderId());
			
			discountMoney = OrderMarketCalc.calcReturnMoney(marketInfo, skuBeanLs, cmd.getSkuId());
			if (marketInfo != null && marketInfo.isFull())
				money = itemDtl.changePrice();
		}
		//long ft = itemDtl.isDeliver() ? 0 : itemDtl.getFreight();
		long ft = 0;
		
		money = money - discountMoney;
		if (money<0) {			
			throw new NegativeException(MCode.V_103, "不能申请售后，因已不符合条件！");
			// money = 0;
		}
		int orderType = cmd.getType() == 3 ? 0 : cmd.getType(); //0换货， 1退货，2仅退款                  app传 1退货，2退款，3换货
		int status = 2; //0申请退货,1申请换货,2申请退款          订单类型，0换货， 1退货，2仅退款
		switch (orderType) {
			case 0:
				status = 1;
				break;
			case 1:
				status = 0;
				break;
			case 2:
				status = 2;
				break;
		}
		int num = cmd.getBackNum();
		if (num > itemDtl.sellNum())
			num = itemDtl.sellNum();
		
		SaleAfterOrder afterOrder = new SaleAfterOrder(cmd.getSaleAfterNo(), cmd.getUserId(), cmd.getOrderId(),
				cmd.getDealerOrderId(), cmd.getDealerId(), cmd.getGoodsId(), cmd.getSkuId(), cmd.getReason()
				, num, status, orderType, money, cmd.getReasonCode(), ft);
		afterOrder.addApply();
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
		
		DealerOrderDtl itemDtl = saleAfterRepository.getDealerOrderDtlBySku(order.dealerOrderId(), 
				order.skuId());
		SimpleMarket marketInfo = saleOrderQuery.getMarketBySkuIdAndOrderId(order.skuId(), order.orderId());
		long discountMoney = 0;
		long money = itemDtl.sumGoodsMoney();
		if (marketInfo != null) {//计算售后需要退的钱
			List<SkuNumBean> skuBeanLs = saleOrderQuery.getOrderDtlByMarketId(marketInfo.getMarketingId(), order.orderId());
			discountMoney = OrderMarketCalc.calcReturnMoney(marketInfo, skuBeanLs, order.skuId());
			if (marketInfo != null && marketInfo.isFull())
				money = itemDtl.changePrice();
		}
		
		if (marketInfo != null && !marketInfo.isFull()) {
			// 更新已使用营销 为不可用状态
			saleAfterRepository.disabledOrderMarket(order.orderId(), marketInfo.getMarketingId());
		}
		money = money - discountMoney;
		if (money<0) {
			throw new NegativeException(MCode.V_103, "不能申请售后，因已不符合条件！");
			//money = 0;
		}
		order.updateBackMoney(money);
		float frt = cmd.getRtFreight();
		if (order.isOnlyRtMoney()) {
			OrderDealerBean odb = saleOrderQuery.getDealerOrderById(order.dealerOrderId());
			if (odb != null && odb.getStatus() == 1) {
				if(frt * 100 > odb.getOderFreight())
					frt = (int)odb.getOderFreight() / 100;
			}
			else
				frt = 0;
		}
		if (order.agreeApply(cmd.getUserId(), (int)frt)) {
			itemDtl.returnInventory(cmd.getSaleAfterNo(), order.getBackNum(), order.orderType());
			saleAfterRepository.updateSaleAfterOrder(order);
		}
		else {
			throw new NegativeException(MCode.V_101, "售后单状态不正确或已经同意过了！");
		}
	}
	/**
	 * 拒绝售后申请
	 * @param cmd
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void rejectApply(AproveSaleAfterCmd cmd) throws NegativeException {
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(cmd.getSaleAfterNo(), cmd.getDealerId());
		if (order == null) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		order.rejectSute(cmd.getRejectReason(), cmd.getRejectReasonCode(), cmd.getUserId());
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
	 * 退款成功
	 * @param cmd
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	@EventListener(isListening=true)
	public void refundSuccess(RefundEvtBean bean) throws NegativeException {
		LOGGER.info("===fanjc==afterSellOrderId==" + bean.getAfterSellOrderId());
		SaleAfterOrder order = saleAfterRepository.getSaleAfterOrderByNo(bean.getAfterSellOrderId());
		if (order == null) {
			throw new NegativeException(MCode.V_101, "无此售后单！");
		}
		Date d = new Date(bean.getRefundTime());
		if (!order.updateRefound(bean.getOrderRefundId(), d)) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行退款操作！");
		}
		saleAfterRepository.updateSaleAfterOrder(order);
		//检查本单的完成状态
		saleAfterRepository.scanDtlGoods(bean.getAfterSellOrderId());
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
		String payNo = saleOrderQuery.getMainOrderPayNo(order.orderId());
		if (StringUtils.isEmpty(payNo)) {
			throw new NegativeException(MCode.V_101, "售后单状态不正确！");
		}
		if (!order.agreeBackMoney(cmd.getUserId(), payNo)) {
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
	
	/**
	 * 商家同意退款或是换货商家已发出态下7天变更为交易完成
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void updataStatusAgreeAfterSale() throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.dealer.agree"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		List<SaleAfterOrder> saleAfterOrders = saleAfterRepository.getSaleAfterOrderStatusAgree(hour);
		
		if (saleAfterOrders.size() == 0)
			throw new NegativeException(MCode.V_1, "没有满足条件的商家订单.");
		
		for (SaleAfterOrder afterOrder : saleAfterOrders) {
			jobUpdateSaleAfter(afterOrder);
		}
	}
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	private void jobUpdateSaleAfter(SaleAfterOrder afterOrder) {
		afterOrder.updateStatusAgreeAfterSale();
		saleAfterRepository.save(afterOrder);
	}
	
	/**
	 * 申请的售后3天未来同意，则需要取消
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void cancelApply(String userId) throws NegativeException {
		int hour = 72;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.cancel.apply"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		List<SaleAfterOrder> saleAfterOrders= saleAfterRepository.getSaleAfterApplyed(hour);
		if (saleAfterOrders.size() == 0)
			return ;
		
		for (SaleAfterOrder afterOrder : saleAfterOrders) {
			jobCancelAfterOrder(afterOrder);
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	private void jobCancelAfterOrder(SaleAfterOrder afterOrder) {
		if (afterOrder.cancel())
			saleAfterRepository.save(afterOrder);
	}
	
	/**
	 * 售后申请同意后，七天没有确认退款的则直接退款
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void afterAgreed(String userId) throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.apply.agreed"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		List<SaleAfterOrder> saleAfterOrders = saleAfterRepository.getAgreeRtMoney(hour);
		if (saleAfterOrders.size() == 0)
			return ;
		try {
			for (SaleAfterOrder afterOrder : saleAfterOrders) {
				jobAfterAgreed(afterOrder, userId);
			}
		} catch (NegativeException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	private void jobAfterAgreed(SaleAfterOrder afterOrder, String userId) throws NegativeException {
		String payNo = saleOrderQuery.getMainOrderPayNo(afterOrder.orderId());
		if (StringUtils.isEmpty(payNo)) {
			throw new NegativeException(MCode.V_101, "售后单状态不正确！");
		}
		if (!afterOrder.agreeBackMoney(userId, payNo)) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行此操作！");
		}
		
		saleAfterRepository.save(afterOrder);
	}
	
	/**
	 * 当商家同意售后， 退货类型且用户发货， 过七天需要自动收货商家
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void dealerAutoRec(String userId) throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.dealer.autoRec"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		List<SaleAfterOrder> saleAfterOrders = saleAfterRepository.getUserSend(hour);
		if (saleAfterOrders.size() == 0)
			return ;
		try {
			for (SaleAfterOrder afterOrder : saleAfterOrders) {
				jobDealerAutoRec(afterOrder, userId);
			}
		} catch (NegativeException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	private void jobDealerAutoRec(SaleAfterOrder afterOrder, String userId) throws NegativeException {
		
		if (!afterOrder.dealerConfirmRev(userId)) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行此操作！");
		}
		
		saleAfterRepository.save(afterOrder);
	}
	
	/**
	 * 当商家同意售后， 换货类型且商家发货， 过七天需要用户自动收货
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void userAutoRec(String userId) throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.user.autoRec"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		List<SaleAfterOrder> saleAfterOrders = saleAfterRepository.getDealerSend(hour);
		if (saleAfterOrders.size() == 0)
			return ;
		try {
			for (SaleAfterOrder afterOrder : saleAfterOrders) {
				jobUserAutoRec(afterOrder, userId);
			}
		} catch (NegativeException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,NegativeException.class }, propagation = Propagation.REQUIRES_NEW)
	private void jobUserAutoRec(SaleAfterOrder afterOrder, String userId) throws NegativeException {
		
		if (!afterOrder.userConfirmRev(userId)) {
			throw new NegativeException(MCode.V_103, "状态不正确，不能进行此操作！");
		}		
		// 查询出订单详情中的对应记录 标记为完成状态
		// adfsaf;
		saleAfterRepository.save(afterOrder);
	}
	
	/**
	 * 当售后单完成其对应的商品也应该是完成状态。
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void afterSaleCompleteUpdated(String userId) throws NegativeException {
		int hour = 168;
		try {
			hour = Integer.parseInt(GetDisconfDataGetter.getDisconfProperty("sale.after.complete.upstream"));
			if (hour < 1)
				hour = 1;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		List<Long> list = saleAfterRepository.getSpecifiedDtlGoods(hour);
		LOGGER.info("=fanjc=处于完成售后的条数为==" + (list == null? 0 : list.size()));
		return ;
	}
	
	/**
	 * 当售后单完成其对应的商品也应该是完成状态。
	 * @throws NegativeException 
	 */
	@Transactional(rollbackFor = {Exception.class, RuntimeException.class, NegativeException.class})
	public void scanOrderDtlUpdated(String userId, String saleOrderId) throws NegativeException {
		saleAfterRepository.scanDtlGoods(saleOrderId);
		return ;
	}
}
