package cn.m2c.scm.domain.model.order;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.NegativeCode;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.logger.Opered;


/**
 * 
 * @ClassName: Order
 * @Description: 订单
 * @author: moyj
 * @date: 2017年2月17日 上午16:45:59
 */
public class Order extends ConcurrencySafeEntity {

	private static final Logger logger = LoggerFactory.getLogger(Order.class);
	private static final long serialVersionUID = -4277865299140099419L;
	   
	private String orderId;					//订单Id
	private String orderNo;					//订单编号
	private String dispatchWay;				//配送方式
	private Integer invoiceType;			//发票类型 1不开 2个人 3公司
	private String invoiceTitle;			//发票抬头
	private String taxIdenNum;				//纳税人识别号
	private Long orderPrice;				//订单价格
	private Long freightPrice;				//运费
	private Long payPrice;					//付款价格
	private Integer payWay;					//支付方式 (1支付宝,2微信)
	private Long commitTime;				//下单时间
	private Long payStartTime;				//支付生成时间
	private Long payEndTime;				//支付完成时间,即实际付款时间
	private String payTradeNo;				//外部支付交易号

	private String buyerId;					//买家编号
	private String buyerName;				//买家名称
	private String buyerPhone;				//买家电话
	private String buyerMessage;			//买家留言
	
	private String dealerUserId;			//供应商用户编号
	private String dealerId;				//商家编号
	private String dealerName;				//商家名称
	private String dealerPhone;				//商家电话
	private Integer dealerStrategy;			//商家结算策略（1、无需结算 2、按比例 3、底价）
	private Integer dealerPercent;			//商家结算比例
	private String goodsId;					//货品编号
	private String goodsNo;					//货品编码
	private String goodsName;				//货品名称
	private String goodsIcon;				//货品图标
	private String propertyId;				//货品规格属性
	private String propertyDesc;			//规格描述
	private Long goodsUnitPrice;			//货品单价
	private Long goodsMarketPrice;			//货品市场价格
	private Integer goodsNum;				//货品订购数量
	private Integer goodsType;				//货品类型(1、实体货品  2服务类型  3 虚拟商品)
	
	private String mresId;					//媒体资源ID
	private String mresNo;					//媒体资源编号				
	private String mresName;				//媒体资源名称
	private String mediaUserId;				//媒体用户编号
	private String mediaId;					//媒体编号
	private String mediaName;				//媒体名称
	private String mediaPhone;				//媒体电话
	private Integer mediaStrategy;			//媒体结算策略   （1、入驻<无需结算> 2<按比例>）
	private Integer mediaPercent;			//媒体结算比例
	private String salerUserId;				//促销员用户编号
	private String salerId;					//促销员编号
	private String salerName;				//促销员名称
	private String salerPhone;				//促销员电话
	private Integer salerStrategy;			//促销员结算策略   （1、按比例）
	private Integer salerPercent;			//促销员结算比例
	
	private String receiverId;				//收货地址编号
	private String receiverName;			//收货人
	private String provinceCode;			//省Code
	private String provinceName;			//省
	private String cityCode;				//市code
	private String cityName;				//市
	private String areaCode;				//区code
	private String areaName;				//区
	private String receiverAddr;			//收获地址
	private String receiverPhone;			//收货人电话
	private String receiverZipCode;			//邮编
	
	private String expCompanyCode;			//快递公司代码
	private String expCompanyName;			//快递公司名称
	private String waybillNo;				//运单号
	private Long commitWaybTime;			//提交运单时间
	private Long receiverTime;				//收货时间
	private Long afterSalesValid;			//售后有效期
	
	private Integer orderStatus;			//订单状态 1正常 2取消
	private Integer payStatus;				//支付状态 1待支付 2已支付
	private Integer logisticsStatus;		//物流状态 1待发货 2待收货 3 已收货
	private Integer settleStatus;			//结算状态 1待结算 2已结算
	private Integer afterSalesStatus;		//售后状态 1待申请 2待审核 3 退换中 4.已退款
	private Integer commentStatus;			//评论状态 1待评论,2已经评论
	
	private Integer platformStrategy;		//平台结算策略   （1、按比例）
	private Integer platformPercent;		//平台结算比例
	
	private Long refundAmount;				//退款金额
	private Long refundTime;				//退/换时间
	
	public Order() {
		super();	
	}
	/**
	 * 提交订单
	 */
	public void commitOrder(	
			String orderId ,				//订单编号
			String dispatchWay,				//配送方式
			Integer invoiceType,			//发票类型 1不开 2个人 3公司
			String invoiceTitle,			//发票抬头
			String taxIdenNum,				//纳税人识别号
			String buyerId,					//买家编号
			String buyerName,				//买家名称
			String buyerPhone,				//买家电话
			String buyerMessage,			//买家留言
			
			String dealerUserId,			//供应商用户编号
			String dealerId,				//商家编号
			String dealerName,				//商家名称
			String dealerPhone,				//商家电话
			Integer dealerStrategy,			//商家结算策略（1、无需结算 2、按比例 3、底价）
			Integer dealerPercent,			//商家结算比例
			String goodsId,					//货品编号
			String goodsNo,					//货品编码
			String goodsName,				//货品名称
			String goodsIcon,				//货品图片
			String propertyId,				//货品规格属性
			String propertyDesc,			//规格描述
			Long goodsUnitPrice,			//货品单价
			Long goodsMarketPrice,			//市场价格
			Integer goodsNum,				//货品订购数量
			Long freightPrice,				//运费
	
			String mresId,					//媒体资源Id
			String mresNo,					//媒体资源编号
			String mresName,				//媒体资源名称
			String mediaUserId,				//媒体用户编号
			String mediaId,					//媒体编号
			String mediaName,				//媒体名称
			String mediaPhone,				//媒体电话
			Integer mediaStrategy,			//媒体结算策略   （1、入驻<无需结算> 2<按比例>）
			Integer mediaPercent,			//媒体结算比例
			String salerUserId,				//媒体用户编号
			String salerId,					//促销员编号
			String salerName,				//促销员名称
			String salerPhone,				//促销员电话
			Integer salerStrategy,			//促销员结算策略   （1、按比例）
			Integer salerPercent,			//促销员结算比例
	
			String receiverId,				//收货地址编号
			String receiverName,			//收货人
			String provinceCode,			//省Code
			String provinceName,			//省
			String cityCode,				//市code
			String cityName,				//市
			String areaCode,				//区code
			String areaName,				//区
			String receiverAddr,			//收获地址详细
			String receiverPhone,			//收货人电话
			String receiverZipCode,			//邮编
			
			Integer platformStrategy,		//平台结算策略   （1、按比例）
			Integer platformPercent,			//平台结算比例
			Long afterSalesValid
			) throws NegativeException{
		this.orderId = orderId;
		this.dispatchWay = dispatchWay;
		this.invoiceType = invoiceType;
		this.invoiceTitle = invoiceTitle;
		this.taxIdenNum = taxIdenNum;
		this.platformStrategy = platformStrategy;
		this.platformPercent = platformPercent;
		this.afterSalesValid = afterSalesValid;
		//买家
		this.buyerId = buyerId;
		this.buyerName = buyerName;
		this.buyerPhone = buyerPhone;
		this.buyerMessage = buyerMessage;	
		//供应商/货品
		this.dealerUserId = dealerUserId;
		this.dealerId = dealerId;
		this.dealerName = dealerName;
		this.dealerPhone = dealerPhone;
		this.dealerStrategy = dealerStrategy;
		this.dealerPercent = dealerPercent;
		this.goodsId = goodsId;
		this.goodsNo = goodsNo;
		this.goodsName = goodsName;
		this.goodsIcon = goodsIcon;
		this.propertyId = propertyId;
		this.propertyDesc = propertyDesc;
		this.goodsUnitPrice = goodsUnitPrice;
		this.goodsMarketPrice = goodsMarketPrice;
		this.goodsNum = goodsNum;
		this.goodsType = GoodsType.MATERIAL_OBJECT.getId();
		//媒体/促销员
		this.mresId = mresId;
		this.mresNo = mresNo;
		this.mresName = mresName;
		this.mediaUserId = mediaUserId;
		this.mediaId = mediaId;
		this.mediaName = mediaName;
		this.mediaPhone = mediaPhone;
		this.mediaStrategy = mediaStrategy;
		this.mediaPercent = mediaPercent;
		this.salerUserId = salerUserId;
		this.salerId = salerId;
		this.salerName = salerName;
		this.salerPhone = salerPhone;
		this.salerStrategy = salerStrategy;
		this.salerPercent = salerPercent;
		//收货地址
		this.receiverId = receiverId;
		this.receiverName = receiverName;
		this.provinceCode = provinceCode;
		this.provinceName = provinceName;
		this.cityCode = cityCode;
		this.cityName = cityName;
		this.areaCode = areaCode;
		this.areaName = areaName;
		this.receiverAddr = receiverAddr;
		this.receiverPhone = receiverPhone;
		this.receiverZipCode = receiverZipCode;
		//
		this.orderId = orderId;
		this.freightPrice =  freightPrice;
		this.orderPrice = this.goodsUnitPrice * goodsNum;
		//初始化
		this.commitTime = System.currentTimeMillis();	
		this.orderStatus = OrderStatus.NORMAL.getId();
		this.payStatus = PayStatus.WAITING.getId();
		this.logisticsStatus = LogisticsStatus.WAIT_SEND_GOOGS.getId();
		this.settleStatus = SettleStatus.WAITING.getId();
		this.afterSalesStatus = AfterSalesStatus.WAIT_APPLY.getId();
		this.commentStatus = CommentStatus.WAIT.getId();
		this.setOrderNo();
		//已下单事件
		OrderCommited orderCommited = new OrderCommited(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, mresId, mresNo,mresName, buyerId, buyerName);
		DomainEventPublisher.instance().publish(orderCommited);
	

	}
	
	/**
	 * 确认收货
	 */
	public void confirmReceipt() throws NegativeException{
		//已取消
		if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//未付款
		if(this.payStatus.intValue() != PayStatus.COMMITED.getId() ){
			throw new NegativeException(NegativeCode.UNPAY_UNABLE_RECEIPT_GOOGS,"未付款的订单不允许确认收货");
		}
		//非待收货
		if(this.logisticsStatus.intValue() != LogisticsStatus.WAIT_RECEIPT_GOOGS.getId() ){
			throw new NegativeException(NegativeCode.UNWAIT_UNABLE_RECEIPT_GOOGS,"非待收货的订单不能确认收货");
		}
		this.logisticsStatus = LogisticsStatus.RECEIPTED_GOOGS.getId();
		this.receiverTime = System.currentTimeMillis();
	}
	
	/**
	 * 取消订单
	 */
	public void cancelOrder() throws NegativeException{
		//已取消
		if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//非待付款
		if(this.payStatus.intValue() != PayStatus.WAITING.getId()){
			throw new NegativeException(NegativeCode.UNWAIT_PAY_UNABLE_CANCEL,"非待付款的订单不允许取消");
		}
		//非待发货
		if(this.logisticsStatus.intValue() != LogisticsStatus.WAIT_SEND_GOOGS.getId()){
			throw new NegativeException(NegativeCode.UNWAIT_RECGOOGS_UNABLE_CANCEL,"非待收货的订单不允许取消");
		}
		this.orderStatus = OrderStatus.CANCELED.getId();
	}
	
	/**
	 * 删除订单(APP)
	 */
	public void delForApp() throws NegativeException{
		//已取消
		if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//未付款
		if(this.payStatus.intValue() != PayStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.UNPAYED_UNABLE_DELETE," 未付款的订单不允许删除");
		}
		//未确认收货
		if(this.logisticsStatus.intValue() != LogisticsStatus.RECEIPTED_GOOGS.getId()){
			throw new NegativeException(NegativeCode.UNRECED_GOOGS_UNABLE_DELETE,"未确认收货的订单不允许删除");
		}
		//售后处理中的订单
		if(this.afterSalesStatus.intValue() == AfterSalesStatus.WAIT_AUDIT.getId()
				|| this.afterSalesStatus.intValue() == AfterSalesStatus.AFTER_SALES_INHAND.getId()){
			throw new NegativeException(NegativeCode.UNRECED_GOOGS_UNABLE_DELETE,"售后处理中的订单的订单不允许删除");
		}
		this.orderStatus = OrderStatus.DELETED.getId();
	}
	
	/**
	 * 提交运单
	 * @param expCompanyCode		//快递公司编号
	 * @param expCompanyName		//快递公司名称
	 * @param waybillNo				//运单号
	 */
	public void commitWaybill(String expCompanyCode,String expCompanyName,String waybillNo,String handManId,String handManName)  throws NegativeException{
		assertArgumentNotEmpty(expCompanyCode, "expCompanyCode is not be null.");
		assertArgumentNotNull(expCompanyName, "expCompanyName is not be null.");
		assertArgumentNotNull(waybillNo, "waybillNo is not be null.");
		//已取消
		if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//非待付款
		if(this.payStatus.intValue() != PayStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.UNWAIT_PAY_UNABLE_COMMIT_WAYBILL,"未付款的订单不允许提交运单");
		}
		//非待发货
		if(this.logisticsStatus.intValue() != LogisticsStatus.WAIT_SEND_GOOGS.getId()){
			throw new NegativeException(NegativeCode.UNWAIT_RECGOOGS_UNABLE_COMMIT_WAYBILL,"非待发货的订单不允许提交运单");
		}
		this.expCompanyCode = expCompanyCode;
		this.expCompanyName = expCompanyName;
		this.waybillNo = waybillNo;	
		this.logisticsStatus = LogisticsStatus.WAIT_RECEIPT_GOOGS.getId();
		this.commitWaybTime = System.currentTimeMillis();
		
		//操作日志事件
		Opered opered = new Opered(this.orderId, 1, "绑定快递单", "", "操作成功", handManId, handManName);
		DomainEventPublisher.instance().publish(opered);
	}
	
	/**
	 * 状态变更为售后待审核
	 * 
	 * @throws NegativeException
	 */
	public void applyAfterSales(Long fsaleDeadline)throws NegativeException{
		assertArgumentNotNull(fsaleDeadline, "fsaleDeadline is not be null.");
		//已取消
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//待付款
		if(this.payStatus != PayStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.UNWAIT_PAY_UNABLE_COMMIT_WAYBILL,"未付款的订单不允许提交运单");
		}
		//未收货
		if(this.logisticsStatus != LogisticsStatus.RECEIPTED_GOOGS.getId()){
			throw new NegativeException(NegativeCode.UNRECEIPTED_UNABLE_SALED,"未收货的订单不允许申请售后");
		}
		//已结算
		if(this.settleStatus == SettleStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.SETTLED_UNABLE_APPLY_SALED,"已结算的订单不允许申请售后");
		}
		if(this.receiverTime != null && this.receiverTime.longValue() != 0){
			if(System.currentTimeMillis() - this.receiverTime.longValue() >= this.afterSalesValid.intValue()){
				throw new NegativeException(NegativeCode.PASSED_SALED_DEADLINE,"已过售后期的订单不允许申请售后");
			}
		}
		//非待申请
		if(this.afterSalesStatus != AfterSalesStatus.WAIT_APPLY.getId()){
			throw new NegativeException(NegativeCode.HANDLED_UNABLE_APPLY_SALED,"申请已处理或处理中的订单不允许申请售后");
		}	
		this.afterSalesStatus = AfterSalesStatus.WAIT_AUDIT.getId();
	}
	
	/**
	 * 售后审核
	 * @param auditFlag		审核标志		//1审核通过	2审核不通过
	 * @throws NegativeException
	 */
	public void auditAfterSales(Integer auditFlag)throws NegativeException{
		assertArgumentNotNull(auditFlag, "statusFlag is not be null.");
		assertArgumentRange(auditFlag,1,2,"statusFlag is not in 1 2.");
		//已取消
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//非待审核
		if(this.afterSalesStatus != AfterSalesStatus.WAIT_AUDIT.getId()){
			throw new NegativeException(NegativeCode.UN_APPLY_SALED_UNABLE_APPLY,"非售后待审核的订单不允许审核");
		}	
		//审核通过
		if(auditFlag == 1){
			this.afterSalesStatus = AfterSalesStatus.AFTER_SALES_INHAND.getId();
		}
		//审核不通过
		else if(auditFlag == 2){
			this.afterSalesStatus = AfterSalesStatus.FINISHED.getId();
		}
	}
	
	/**
	 * 退款
	 */
	public void backedGoods(Long refundAmount,String handManId,String handManName)throws NegativeException{
		assertArgumentNotNull(refundAmount, "refundAmount is not be null.");
		//已取消
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}	
		//非审核
		if(this.afterSalesStatus != AfterSalesStatus.AFTER_SALES_INHAND.getId()){
			throw new NegativeException(NegativeCode.UN_APPLY_PASS_UNABLE_BACK_GOODS,"非审核通过的订单不允许退货");
		}
		//退款金额大于付款金额
		if(refundAmount > this.payPrice){
			throw new NegativeException(NegativeCode.REFUNDED_MORE_PAY,"退款金额大于付款金额");
		}
		this.afterSalesStatus = AfterSalesStatus.FINISHED.getId();
		this.refundAmount = refundAmount;
		this.refundTime =  System.currentTimeMillis();
		
		//日志
		Opered opered = new Opered(this.orderId, 1, "退款", "", "操作成功", handManId, handManName);
		DomainEventPublisher.instance().publish(opered);
				
		//已退款
		OrderRefundedBI orderRefundedBI = new OrderRefundedBI(orderId,orderNo, payPrice, orderPrice, freightPrice, buyerId, refundAmount, refundTime);
		DomainEventPublisher.instance().publish(orderRefundedBI);
		//退货事件
		OrderGoodsBacked orderGoodsBacked = new OrderGoodsBacked(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, 
				mresId, mresName, buyerId, buyerName, payPrice, orderPrice, freightPrice);
		DomainEventPublisher.instance().publish(orderGoodsBacked);
		//退货事件BI
		OrderGoodsBackedBI orderGoodsBackedBI = new OrderGoodsBackedBI(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, 
				mresId, mresName, buyerId, buyerName, payPrice, orderPrice, freightPrice);
		DomainEventPublisher.instance().publish(orderGoodsBackedBI);
	}
	
	/**
	 * 换货完成
	 */
	public void bartered()throws NegativeException{
		//已取消
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}		
		if(this.afterSalesStatus != AfterSalesStatus.AFTER_SALES_INHAND.getId()){
			throw new NegativeException(NegativeCode.UN_BARTERING_UNABLE_BARTERED,"非审核通过的订单不允许换货完成");
		}
		this.afterSalesStatus = AfterSalesStatus.FINISHED.getId();
		this.refundTime = System.currentTimeMillis();
		//换货事件
		OrderGoodsBartered orderGoodsBartered = new OrderGoodsBartered(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, 
				mresId, mresName, buyerId, buyerName, payPrice, orderPrice, freightPrice);
		DomainEventPublisher.instance().publish(orderGoodsBartered);
		//换货事件BI
		OrderGoodsBarteredBI orderGoodsBarteredBI = new OrderGoodsBarteredBI(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, 
				mresId, mresName, buyerId, buyerName, payPrice, orderPrice, freightPrice);
		DomainEventPublisher.instance().publish(orderGoodsBarteredBI);
	}

	/**
	 * 修改收货地址
	 * @param receiverAreaCode	//收货地址编码
	 * @param receiverRegion	//收货地址区域	
	 * @param receiverAddr		//收获地址
	 */
	public void modReceiverAddr(String provinceCode,String provinceName, String cityCode,String cityName,
			String areaCode,String areaName,String receiverAddr)throws NegativeException{

		//已取消
		if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		if(provinceCode != null && provinceCode.length() > 0){
			this.provinceCode = provinceCode;
		}
		if(provinceName != null && provinceName.length() > 0){
			this.provinceName = provinceName;		
		}
		if(cityCode != null && cityCode.length() > 0){
			this.cityCode = cityCode;	
		}
		if(cityName != null && cityName.length() > 0){
			this.cityName = cityName;	
		}
		if(areaCode != null && areaCode.length() > 0){
			this.areaCode = areaCode;	
		}
		if(areaName != null && areaName.length() > 0){
			this.areaName = areaName;	
		}
		if(receiverAddr != null && receiverAddr.length() > 0){
			this.receiverAddr = receiverAddr;
		}
	}
	
	/**
	 * 修改状态
	 * @param orderStatus		//订单状态 1正常 2取消
	 * @param payStatus			//支付状态 1待支付 2已支付
	 * @param logisticsStatus	//物流状态 1待发货 2待收货 3 已收货
	 * @param saledStatus		//售后状态 1正常 2申请售后 3 审核通过 4审核不通过 5已退货 6已换货
	 */
	public void modStatus(Integer orderStatus,Integer payStatus,Integer logisticsStatus,Integer afterSalesStatus)throws NegativeException{
		//已取消
		//if(this.orderStatus.intValue() != OrderStatus.NORMAL.getId()){
		//	throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		//}
		//已结算
		if(this.settleStatus.intValue() == SettleStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.SETTLED_UNABLE_MOD_STATUS,"已结算的订单不允许修改状态");
		}
		if(orderStatus != null && orderStatus != 0){
			this.orderStatus = orderStatus;
		}
		if(payStatus != null && payStatus != 0){
			this.payStatus = payStatus;
		}
		if(logisticsStatus != null && logisticsStatus != 0){
			this.logisticsStatus = logisticsStatus;
		}
		if(afterSalesStatus != null && afterSalesStatus != 0){
			this.afterSalesStatus = afterSalesStatus;
		}
	}
	
	/**
	 * 变更订单状态为已评论
	 * @throws NegativeException
	 */
	public void commentOrder()throws NegativeException{
		//
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"订单不存在");
		}
		//未付款的订单不允许评论
		if(this.payStatus != PayStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"未付款的订单不允许评论");
		}
		//未确认收货的订单不允许评论
		if(this.logisticsStatus != LogisticsStatus.RECEIPTED_GOOGS.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"未确认收货的订单不允许评论");
		}
		//售后处理中的订单不允许评论
		if(this.afterSalesStatus != AfterSalesStatus.WAIT_APPLY.getId()){
			throw new NegativeException(NegativeCode.ORDER_NOT_EXIST,"售后处理中的订单不允许评论");
		}
		this.commentStatus = CommentStatus.COMMENTED.getId();
	}
	
	/**
	 * 结算
	 * @param fsaleDeadline			//售后期
	 * @throws NegativeException
	 */
	public void settled() throws NegativeException{
		
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
		}
		if(this.settleStatus == SettleStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
		}
		if(!(this.afterSalesStatus == AfterSalesStatus.WAIT_APPLY.getId() || this.afterSalesStatus == AfterSalesStatus.FINISHED.getId())){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
		}
		if(this.payStatus != PayStatus.COMMITED.getId()){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
		}
		if(!(this.logisticsStatus == LogisticsStatus.WAIT_RECEIPT_GOOGS.getId() || this.logisticsStatus == LogisticsStatus.RECEIPTED_GOOGS.getId())){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
		}
		if(this.afterSalesStatus == AfterSalesStatus.WAIT_APPLY.getId()){
			if(this.receiverTime == null || this.afterSalesValid == null || (this.receiverTime  + this.afterSalesValid > (System.currentTimeMillis()))){	
				throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合结算要求");
			}
		}
		this.settleStatus = SettleStatus.COMMITED.getId();
	
	}
	
	/**
	 * 支付
	 * @param payStartTime	//支付开始时间
	 * @param payEndTime	//支付结束时间
	 * @param payTradeNo	//外部交易号
	 * @param payPrice		//支付金额
	 * @param payWay		//支付方式
	 * @throws NegativeException
	 */
	public void payed( Long payStartTime, Long payEndTime, String payTradeNo, Long payPrice,Integer payWay,Map<String,Object> appMsgMap) throws NegativeException{
		
		if(this.orderStatus != OrderStatus.NORMAL.getId()){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_SETTLE,"订单不符合支付要求");
		}
		if(this.payStatus != PayStatus.WAITING.getId()){
			throw new NegativeException(NegativeCode.ORDER_UNACCORD_PAY,"订单不符合支付要求");
		}
		this.payStartTime = payStartTime;
		this.payEndTime = payEndTime;
		this.payTradeNo = payTradeNo;
		this.payPrice = payPrice;
		this.payWay = payWay;
		this.payStatus = PayStatus.COMMITED.getId();
		//已付款事件
		OrderPayed OrderPayed = new OrderPayed(orderId, goodsId, goodsName, goodsNum, dealerId, dealerName, mresId, mresName, buyerId, buyerName,payEndTime);
		DomainEventPublisher.instance().publish(OrderPayed);
		//已付款事件(BI)
		OrderPayedBI orderPayedBI = new OrderPayedBI(orderId, orderPrice, payPrice, goodsId, goodsName,goodsUnitPrice,goodsNum, mresId,mresNo, mresName, mediaId, mediaName, commitTime, payEndTime, buyerId, dealerId,appMsgMap);
		DomainEventPublisher.instance().publish(orderPayedBI);
		
		
	}
	
	/**
	 * 生成订单编号
	 */
	private void setOrderNo(){
    	SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String sDate = format.format(new Date());
        int ranNum = new Random().nextInt(899999) + +100000;    
        this.orderNo  = sDate + "00" + String.valueOf(ranNum);
	}
	public String getOrderId() {
		return orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public String getDispatchWay() {
		return dispatchWay;
	}
	public Integer getInvoiceType() {
		return invoiceType;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public String getTaxIdenNum() {
		return taxIdenNum;
	}
	public Long getOrderPrice() {
		return orderPrice;
	}
	public Long getFreightPrice() {
		return freightPrice;
	}
	public Long getPayPrice() {
		return payPrice;
	}
	public Integer getPayWay() {
		return payWay;
	}
	public Long getCommitTime() {
		return commitTime;
	}
	public Long getPayStartTime() {
		return payStartTime;
	}
	public Long getPayEndTime() {
		return payEndTime;
	}
	public String getPayTradeNo() {
		return payTradeNo;
	}
	public String getBuyerId() {
		return buyerId;
	}
	public String getBuyerName() {
		return buyerName;
	}
	public String getBuyerPhone() {
		return buyerPhone;
	}
	public String getBuyerMessage() {
		return buyerMessage;
	}
	public String getDealerUserId() {
		return dealerUserId;
	}
	public String getDealerId() {
		return dealerId;
	}
	public String getDealerName() {
		return dealerName;
	}
	public String getDealerPhone() {
		return dealerPhone;
	}
	public Integer getDealerStrategy() {
		return dealerStrategy;
	}
	public Integer getDealerPercent() {
		return dealerPercent;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public String getGoodsIcon() {
		return goodsIcon;
	}
	public String getPropertyId() {
		return propertyId;
	}
	public String getPropertyDesc() {
		return propertyDesc;
	}
	public Long getGoodsUnitPrice() {
		return goodsUnitPrice;
	}
	public Long getGoodsMarketPrice() {
		return goodsMarketPrice;
	}
	public Integer getGoodsNum() {
		return goodsNum;
	}
	public Integer getGoodsType() {
		return goodsType;
	}
	public String getMresId() {
		return mresId;
	}
	public String getMresName() {
		return mresName;
	}
	public String getMediaUserId() {
		return mediaUserId;
	}
	public String getMediaId() {
		return mediaId;
	}
	public String getMediaName() {
		return mediaName;
	}
	public String getMediaPhone() {
		return mediaPhone;
	}
	public Integer getMediaStrategy() {
		return mediaStrategy;
	}
	public Integer getMediaPercent() {
		return mediaPercent;
	}
	public String getSalerUserId() {
		return salerUserId;
	}
	public String getSalerId() {
		return salerId;
	}
	public String getSalerName() {
		return salerName;
	}
	public String getSalerPhone() {
		return salerPhone;
	}
	public Integer getSalerStrategy() {
		return salerStrategy;
	}
	public Integer getSalerPercent() {
		return salerPercent;
	}
	public String getReceiverId() {
		return receiverId;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public String getCityCode() {
		return cityCode;
	}
	public String getCityName() {
		return cityName;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public String getAreaName() {
		return areaName;
	}
	public String getReceiverAddr() {
		return receiverAddr;
	}
	public String getReceiverPhone() {
		return receiverPhone;
	}
	public String getReceiverZipCode() {
		return receiverZipCode;
	}
	public String getExpCompanyCode() {
		return expCompanyCode;
	}
	public String getExpCompanyName() {
		return expCompanyName;
	}
	public String getWaybillNo() {
		return waybillNo;
	}
	public Long getCommitWaybTime() {
		return commitWaybTime;
	}
	public Long getReceiverTime() {
		return receiverTime;
	}
	public Long getAfterSalesValid() {
		return afterSalesValid;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public Integer getLogisticsStatus() {
		return logisticsStatus;
	}
	public Integer getSettleStatus() {
		return settleStatus;
	}
	public Integer getAfterSalesStatus() {
		return afterSalesStatus;
	}
	public Integer getCommentStatus() {
		return commentStatus;
	}
	public Integer getPlatformStrategy() {
		return platformStrategy;
	}
	public Integer getPlatformPercent() {
		return platformPercent;
	}
	public Long getRefundAmount() {
		return refundAmount;
	}
	public Long getRefundTime() {
		return refundTime;
	}
	public String getMresNo() {
		return mresNo;
	}
	

	
	
	

	
	
	
}
