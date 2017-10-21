package cn.m2c.scm.domain.model.order;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
/***
 * 售后订单实体
 * @author fanjc
 * created date 2017年10月20日
 * copyrighted@m2c
 */
public class SaleAfterOrder extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 1L;
	/**申请售后人*/
	private String userId;
	/**客户发送退换货单号信息*/
	private ExpressInfo backExpress;
	/**商家发送换货单号信息*/
	private ExpressInfo sendExpress;
	/**售后单号*/
	private String saleAfterNo;
	/**主订单号*/
	private String orderId;
	/**商家订单id*/
	private String dealerOrderId;
	/**商家 Id*/
	private String dealerId;
	/**商品 Id*/
	private String goodsId;
	/**SKU Id*/
	private String skuId;
	/**退货数*/
	private Integer backNum;
	/**退货原因*/
	private String reason;
	/**拒绝原因*/
	private String rejectReason;
	/**状态，0申请退货,1申请换货,2申请退款,3拒绝,4同意(退换货),5客户寄出,6商家收到,7商家寄出,8客户收到,9同意退款, 10确认退款*/
	private Integer status;
	/**售后单类型*/
	private Integer orderType;
	/**退款金额*/
	private Long backMoney;
	
	/**退货原因*/
	private Integer reasonCode;
	/**拒绝原因*/
	private Integer rejectReasonCode;
	
	public SaleAfterOrder() {
		super();
	}
	
	public SaleAfterOrder(String saleAfterNo, String userId, String orderId
			,String dealerOrderId, String dealerId, String goodsId, String skuId
			,String reason, int backNum, int status, int orderType, long backMoney
			, int reasonCode) {
		this.reason = reason;
		this.saleAfterNo = saleAfterNo;
		this.userId = userId;
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.goodsId = goodsId;
		this.skuId = skuId;
		this.backNum = backNum;
		this.status = status;
		this.orderType = orderType;
		this.backMoney = backMoney;
		this.reasonCode = reasonCode;
		
		this.dealerId = dealerId;
	}
	/***
	 * 同意售后申请
	 */
	public void agreeApply() {
		if (status < 4 && status != 3)
			status = 4;
	}
	/***
	 * 拒绝申请
	 */
	public boolean rejectSute(String r, int rCode) {
		
		if (status < 3)
			status = 3;
		else
			return false;
		rejectReason = r;
		rejectReasonCode = rCode;
		return true;
	}
	/**
	 * 客户退货的发货
	 * @param e
	 * @return
	 */
	public boolean clientShip(ExpressInfo e) {
		if (status != 4)
			return false;
		backExpress = e;
		return true;
	}
	
	/**
	 * 商家发货
	 * @param e
	 * @return
	 */
	public boolean dealerShip(ExpressInfo e) {
		if (status < 5)
			return false;
		sendExpress = e;
		return true;
	}
	/***
	 * 商家确认收货
	 */
	public boolean dealerConfirmRev() {
		if (status < 5)
			return false;
		status = 6;
		return true;
	}
	
	/***
	 * 用户确认收货
	 */
	public boolean userConfirmRev() {
		if (status < 7)
			return false;
		status = 8;
		return true;
	}
	
	/***
	 * 同意退款
	 */
	public boolean agreeBackMoney() {
		if (status < 4)
			return false;
		status = 9;
		return true;
	}
	
	/***
	 * 确认退款
	 */
	public boolean confirmBackMoney() {
		if (status < 9)
			return false;
		status = 10;
		return true;
	}
}
