package cn.m2c.scm.application.order.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m2c.ddd.common.event.annotation.EventListener;
import cn.m2c.media.interfaces.dubbo.MediaService;
import cn.m2c.pay.interfaces.dubbo.SettleRuleService;
import cn.m2c.scm.application.order.order.command.CancelOrderCommand;
import cn.m2c.scm.application.order.order.command.CommitOrderCommand;
import cn.m2c.scm.application.order.order.command.CommitWaybillCommand;
import cn.m2c.scm.application.order.order.command.ConfirmReceiptCommand;
import cn.m2c.scm.application.order.order.command.DelForAppCommand;
import cn.m2c.scm.application.order.order.command.ModReceiverAddrCommand;
import cn.m2c.scm.application.order.order.command.ModStatusCommand;
import cn.m2c.scm.application.order.order.command.PayedCommand;
import cn.m2c.scm.application.order.order.command.SettledCommand;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.Order;
import cn.m2c.scm.domain.model.order.OrderRepository;
import cn.m2c.scm.goods.interfaces.GoodService;
import cn.m2c.support.interfaces.dubbo.PushService;
import cn.m2c.users.interfaces.dubbo.UserService;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

/**
 * 
 * @ClassName: OrderApplication
 * @Description: 订单
 * @author moyj
 * @date 2017年4月18日 下午5:50:58
 *
 */
@Service
@Transactional
public class OrderApplication {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(OrderApplication.class);

	@Autowired
	private OrderRepository oderRepository;
	@Autowired
	private GoodService goodsService;
	@Autowired
	private MediaService mediaService;
	@Autowired
	private UserService userService;
	@Autowired
	private SettleRuleService settleRuleService;
	@Autowired
	private PushService pushService;

	/**
	 * 创建订单
	 * 
	 * @param command
	 * @return
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	@EventListener
	public String commitOrder(CommitOrderCommand command)
			throws NegativeException {
		LOGGER.info(" Order commitOrder command >>{}", command);
		String orderId = command.getOrderId();
		String buyerId = command.getBuyerId();
		String buyerName = command.getBuyerName();
		String buyerMessage = command.getBuyerMessage();
		String dispatchWay = command.getDispatchWay();
		Integer invoiceType = command.getInvoiceType();
		String invoiceTitle = command.getInvoiceTitle();
		String propertyId = command.getPropertyId();
		String taxIdenNum = command.getTaxIdenNum();
		String mresId = command.getMresId();
		Integer goodsNum = command.getGoodsNum();
		String goodsId = command.getGoodsId();
		String receiverId = command.getReceiverId();
		List<Map<String, Object>> propertyList = command.getPropertyList();

		// 调用用户中心 获取用户信息
		Map<String, Object> userMap = userService.getUserInfo(buyerId);
		if (userMap == null) {
			throw new NegativeException(NegativeCode.REMOTE_GET_USER_FAIL,
					"下单失败,没有匹配的用户");
		}
		String buyerPhone = userMap.get("mobile") == null ? null
				: (String) userMap.get("mobile");

		// 远程调用商家领域 获取货品信息
		Map<String, Object> goodsMap = goodsService.getPropertyInfo(goodsId,
				propertyId);
		if (goodsMap == null) {
			throw new NegativeException(NegativeCode.REMOTE_GET_GOODS_FAIL,
					"下单失败,没有匹配的商品");
		}
		String dealerUserId = goodsMap.get("userId") == null ? null
				: (String) goodsMap.get("userId");
		String dealerId = goodsMap.get("dealerId") == null ? null
				: (String) goodsMap.get("dealerId");
		String dealerName = goodsMap.get("dealerName") == null ? null
				: (String) goodsMap.get("dealerName");
		String dealerPhone = goodsMap.get("dealerMobile") == null ? null
				: (String) goodsMap.get("dealerMobile");
		String goodsNo = goodsMap.get("goodsNo") == null ? null
				: (String) goodsMap.get("goodsNo");
		String goodsName = goodsMap.get("goodsName") == null ? null
				: (String) goodsMap.get("goodsName");
		String goodsIcon = goodsMap.get("gallery") == null ? null
				: (String) goodsMap.get("gallery");
		Long goodsUnitPrice = goodsMap.get("salePrice") == null ? null
				: (Long) goodsMap.get("salePrice");
		Long goodsMarketPrice = goodsMap.get("marketPrice") == null ? null
				: (Long) goodsMap.get("marketPrice");
		Long freightPrice = goodsMap.get("transportFee") == null ? 0L
				: (Long) goodsMap.get("transportFee");
		// String propertyValue = goodsMap.get("propertyValue") ==
		// null?null:(String)goodsMap.get("propertyValue");
		String propertyDesc = getPropertyDest(propertyList);
		// if(!isExistProperty(propertyList,propertyValue)){
		// throw new
		// NegativeException(NegativeCode.REMOTE_GET_GOODS_FAIL,"下单失败,没有匹配的商品规格");
		// }
		// 远程调用媒体领域 获取媒体信息
		Map<String, Object> mediaMap = new HashMap<String, Object>();
		if (mresId != null && mresId.length() > 0) {
			mediaMap = mediaService.getMres(mresId, mresId);
			if (mediaMap == null) {
				LOGGER.info(" 下单失败,没有匹配的媒体信息 >>{}", mresId);
				throw new NegativeException(NegativeCode.REMOTE_GET_MEDIA_FAIL,
						"下单失败,没有匹配的媒体信息");
			}
		}
		mresId = mediaMap.get("mresId") == null ? null : (String) mediaMap
				.get("mresId");
		String mresNo = mediaMap.get("mresNo") == null ? null : String
				.valueOf((Long) mediaMap.get("mresNo"));
		String mresName = mediaMap.get("mresName") == null ? null
				: (String) mediaMap.get("mresName");
		String mediaUserId = mediaMap.get("mediaUserId") == null ? null
				: (String) mediaMap.get("mediaUserId");
		String mediaId = mediaMap.get("mediaId") == null ? null
				: (String) mediaMap.get("mediaId");
		String mediaName = mediaMap.get("mediaName") == null ? null
				: (String) mediaMap.get("mediaName");
		String mediaPhone = mediaMap.get("mediaPhone") == null ? null
				: (String) mediaMap.get("mediaPhone");
		String salerUserId = mediaMap.get("salerUserId") == null ? null
				: (String) mediaMap.get("salerUserId");
		String salerId = mediaMap.get("salerId") == null ? null
				: (String) mediaMap.get("salerId");
		String salerName = mediaMap.get("salerName") == null ? null
				: (String) mediaMap.get("salerName");
		String salerPhone = mediaMap.get("salerPhone") == null ? null
				: (String) mediaMap.get("salerPhone");
		// String cate = mediaMap.get("cate") ==
		// null?null:(String)mediaMap.get("cate");

		// 远程调用用户中心领域 收货地址信息
		Map<String, Object> receiverMap = userService
				.getAddressInfo(receiverId);
		if (receiverMap == null) {
			throw new NegativeException(NegativeCode.REMOTE_GET_RECEIVERR_FAIL,
					"远程获取收货地址信息失败");
		}
		String receiverName = receiverMap.get("receiptMan") == null ? null
				: (String) receiverMap.get("receiptMan");
		String provinceCode = receiverMap.get("provinceCode") == null ? null
				: (String) receiverMap.get("provinceCode");
		String provinceName = receiverMap.get("receiptProvince") == null ? null
				: (String) receiverMap.get("receiptProvince");
		String cityCode = receiverMap.get("cityCode") == null ? null
				: (String) receiverMap.get("cityCode");
		String cityName = receiverMap.get("receiptCity") == null ? null
				: (String) receiverMap.get("receiptCity");
		String areaCode = receiverMap.get("areaCode") == null ? null
				: (String) receiverMap.get("areaCode");
		String areaName = receiverMap.get("receiptArea") == null ? null
				: (String) receiverMap.get("receiptArea");
		String receiverAddr = receiverMap.get("receiptStreet") == null ? null
				: (String) receiverMap.get("receiptStreet");
		String receiverPhone = receiverMap.get("receiptMobile") == null ? null
				: (String) receiverMap.get("receiptMobile");
		String receiverZipCode = receiverMap.get("provinceCode") == null ? ""
				: (String) receiverMap.get("provinceCode");

		// 远程调用订单领域获取结算策略
		Map<String, Object> strategyMap = settleRuleService.getRule();
		if (strategyMap == null) {
			throw new NegativeException(NegativeCode.REMOTE_GET_STRATEGY_FAIL,
					"远程获取结算策略失败");
		}
		Integer dealerStrategy = 1;
		Integer dealerPercent = strategyMap.get("dealerPercent") == null ? null
				: (Integer) strategyMap.get("dealerPercent");
		Integer mediaStrategy = 1;
		Integer mediaPercent = 0;
		if (mediaId != null && mediaId.length() > 0) {
			mediaPercent = strategyMap.get("mediaPercent") == null ? null
					: (Integer) strategyMap.get("mediaPercent");
		}
		Integer salerStrategy = 1;
		Integer salerPercent = 0;
		if (salerUserId != null && salerUserId.length() > 0) {
			salerPercent = strategyMap.get("salerPercent") == null ? null
					: (Integer) strategyMap.get("salerPercent");
		}
		Integer platformStrategy = 1;
		Integer platformPercent = 100 - dealerPercent - mediaPercent
				- salerPercent;

		if (oderRepository.findT(orderId) != null) {
			throw new NegativeException(
					NegativeCode.UNABLE_REPEAT_COMMIT_ORDER, "不允许重复提交订单");
		}
		Long afterSalesValid = (Long) DisconfDataGetter.getByFileItem("constants.properties", "after.sales.deadline");
		Order order = new Order();
		order.commitOrder(orderId, dispatchWay, invoiceType, invoiceTitle,
				taxIdenNum, buyerId, buyerName, buyerPhone, buyerMessage,
				dealerUserId, dealerId, dealerName, dealerPhone,
				dealerStrategy, dealerPercent, goodsId, goodsNo, goodsName,
				goodsIcon, propertyId, propertyDesc, goodsUnitPrice,
				goodsMarketPrice, goodsNum, freightPrice, mresId, mresNo,
				mresName, mediaUserId, mediaId, mediaName, mediaPhone,
				mediaStrategy, mediaPercent, salerUserId, salerId, salerName,
				salerPhone, salerStrategy, salerPercent, receiverId,
				receiverName, provinceCode, provinceName, cityCode, cityName,
				areaCode, areaName, receiverAddr, receiverPhone,
				receiverZipCode, platformStrategy, platformPercent,
				afterSalesValid);
		oderRepository.save(order);
		return command.getOrderId();
	}

	/**
	 * 取消订单
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void cancelOrder(CancelOrderCommand command)
			throws NegativeException {
		LOGGER.info(" Order cannel command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.cancelOrder();
		oderRepository.save(order);
	}

	/**
	 * 取消订单
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void delForApp(DelForAppCommand command) throws NegativeException {
		LOGGER.info(" Order cannel command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.delForApp();
		oderRepository.save(order);
	}

	/**
	 * 确认收货
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void confirmReceipt(ConfirmReceiptCommand command)
			throws NegativeException {
		LOGGER.info(" Order confirmReceipt command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.confirmReceipt();
		oderRepository.save(order);

		// 极光推送
		try {
			// String snNo = "f87ee18f07a8786883b1ba4ecb9b2eccff374aaf";
			String snNo = "";
			Integer terminalType = null;
			String buyerId = order.getBuyerId();
			String userName = "";
			String mobile = "";
			Map<String, Object> userMap = userService.getUserInfo(buyerId);
			if (userMap != null) {
				snNo = userMap.get("appDeviceSn") == null ? ""
						: (String) userMap.get("appDeviceSn");
				terminalType = userMap.get("terminalType") == null ? null
						: (Integer) userMap.get("terminalType");
				userName = userMap.get("userName") == null ? ""
						: (String) userMap.get("userName");
				mobile = userMap.get("mobile") == null ? "" : (String) userMap
						.get("mobile");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("operType", "1502");
			map.put("orderId", order.getOrderId());
			map.put("goodsIcon", order.getGoodsIcon());
			map.put("goodsName", order.getGoodsName());
			map.put("waybillNo", order.getWaybillNo());
			map.put("expCompanyName", order.getExpCompanyName());
			pushService.pushNotify(2, terminalType, buyerId, mobile, userName,
					snNo, "您在一拍即获购买的" + order.getGoodsName()
							+ "已签收！如有疑问，请联系客服。", "您的订单已签收", map);
		} catch (Exception e) {
			LOGGER.info(" Order confirmReceipt command >>{} 极光推送 失败");
		}

	}

	/**
	 * 提交运单
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	@EventListener
	public void commitWaybill(CommitWaybillCommand command)
			throws NegativeException {
		LOGGER.info(" Order commitWaybill command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.commitWaybill(command.getExpCompanyCode(),
				command.getExpCompanyName(), command.getWaybillNo(),
				command.getHandManId(), command.getHandManName());
		oderRepository.save(order);

		// 极光推送
		try {
			// String snNo = "f87ee18f07a8786883b1ba4ecb9b2eccff374aaf";
			String snNo = "";
			Integer terminalType = null;
			String buyerId = order.getBuyerId();
			String userName = "";
			String mobile = "";
			Map<String, Object> userMap = userService.getUserInfo(buyerId);
			if (userMap != null) {
				snNo = userMap.get("appDeviceSn") == null ? ""
						: (String) userMap.get("appDeviceSn");
				terminalType = userMap.get("terminalType") == null ? null
						: (Integer) userMap.get("terminalType");
				userName = userMap.get("userName") == null ? ""
						: (String) userMap.get("userName");
				mobile = userMap.get("mobile") == null ? "" : (String) userMap
						.get("mobile");
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("operType", "1501");
			map.put("orderId", order.getOrderId());
			map.put("goodsIcon", order.getGoodsIcon());
			map.put("goodsName", order.getGoodsName());
			map.put("waybillNo", order.getWaybillNo());
			map.put("expCompanyName", order.getExpCompanyName());
			List<String> list = new ArrayList<String>();
			list.add(snNo);
			pushService.pushNotify(2, terminalType, buyerId, mobile, userName,
					snNo, "您在一拍即获购买的" + order.getGoodsName() + "已经发货啦！请注意查收。",
					"您的订单已发货", map);
		} catch (Exception e) {
			LOGGER.info(" Order commitWaybill command >>{} 极光推送 失败");
		}
	}

	/**
	 * 修改收货地址
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void modReceiverAddr(ModReceiverAddrCommand command)
			throws NegativeException {
		LOGGER.info(" Order modReceiverAddr command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.modReceiverAddr(command.getProvinceCode(),
				command.getProvinceName(), command.getCityCode(),
				command.getCityName(), command.getAreaCode(),
				command.getAreaName(), command.getReceiverAddr());
		oderRepository.save(order);
	}

	/**
	 * 修改状态
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void modStatus(ModStatusCommand command) throws NegativeException {
		LOGGER.info(" Order modStatus command >>{}", command);
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST, "订单不存在");
		}
		order.modStatus(command.getOrderStatus(), command.getPayStatus(),
				command.getLogisticsStatus(), command.getAfterSalesStatus());
		oderRepository.save(order);
	}

	/**
	 * 结算
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	public void settled(SettledCommand command) throws NegativeException {
		List<String> orderIdList = command.getOrderIdList();
		if (orderIdList == null || orderIdList.size() == 0) {
			throw new NegativeException(NegativeCode.OPER_NOT_CHOICE_ORDER,
					"请选择要操作的订单");
		}
		for (String orderId : orderIdList) {
			Order order = oderRepository.findT(orderId);
			if (order == null) {
				throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST,
						"订单不存在");
			}
			order.settled();
			oderRepository.save(order);
		}
	}

	/**
	 * 支付
	 * 
	 * @param command
	 * @throws NegativeException
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class,
			NegativeException.class })
	@EventListener
	public void payed(PayedCommand command) throws NegativeException {
		Order order = oderRepository.findT(command.getOrderId());
		if (order == null) {
			throw new NegativeException(NegativeCode.COMMENT_NOT_EXIST, "订单不存在");
		}
		order.payed(command.getPayStartTime(), command.getPayEndTime(),
				command.getPayTradeNo(), command.getPayPrice(),
				command.getPayWay(), command.getAppMsgMap());
		oderRepository.save(order);

	}

	/**
	 * 规格属性
	 * 
	 * @param propertyList
	 * @param propertyValue
	 * @return
	 */
	private String getPropertyDest(List<Map<String, Object>> propertyList) {
		String propertyDest = "";
		try {
			for (Map<String, Object> reqMap : propertyList) {
				Set<String> set = reqMap.keySet();
				for (String key : set) {
					String value = (String) reqMap.get(key);
					propertyDest = propertyDest + key + ":" + value + ";";
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (propertyDest != null && propertyDest.length() > 1) {
			propertyDest = propertyDest.substring(0, propertyDest.length() - 1);
		}
		return propertyDest;
	}

}
