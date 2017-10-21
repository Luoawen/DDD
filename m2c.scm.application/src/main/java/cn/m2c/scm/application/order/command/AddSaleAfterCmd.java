package cn.m2c.scm.application.order.command;

import cn.m2c.common.MCode;
import cn.m2c.common.StringUtil;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
/**
 * 增加售后单命令
 * @author fanjc
 * created date 2017年10月21日
 * copyrighted@m2c
 */
public class AddSaleAfterCmd extends AssertionConcern {

	private String saleAfterNo;
	
	private String userId;
	
	private String orderId;
	
	private String dealerOrderId;
	
	private String skuId;
	
	private int type;
	
	private String goodsId;
	
	private String dealerId;
	
	private String reason;
	
	private int backNum;
	
	private int reasonCode;
	
	public AddSaleAfterCmd(String userId, String orderId, String dealerOrderId,
			String skuId, String saleAfterNo, int type, String dealerId,
			String goodsId, int backNum, String reason, int reasonCode) throws NegativeException {
		
		if (StringUtil.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空(orderId)！");
		}
		
		if (StringUtil.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtil.isEmpty(dealerOrderId)) {
			throw new NegativeException(MCode.V_1, "商家订单号参数为空(dealerOrderId)！");
		}
		
		if (StringUtil.isEmpty(skuId)) {
			throw new NegativeException(MCode.V_1, "申请售后商品sku参数为空(skuId)！");
		}
		
		if (StringUtil.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (StringUtil.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (type < 0 || type > 2) {
			throw new NegativeException(MCode.V_1, "售后单类型参数不正确(type)！");
		}
		
		this.userId = userId;
		this.orderId = orderId;
		this.dealerOrderId = dealerOrderId;
		this.skuId = skuId;
		this.saleAfterNo = saleAfterNo;
		this.type = type;
		
		this.dealerId = dealerId;
		this.goodsId = goodsId;
		this.backNum = backNum;
		this.reason = reason;
		this.reasonCode = reasonCode;
	}

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public String getUserId() {
		return userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public String getSkuId() {
		return skuId;
	}

	public int getType() {
		return type;
	}

	public String getGoodsId() {
		return goodsId;
	}

	public String getDealerId() {
		return dealerId;
	}

	public String getReason() {
		return reason;
	}

	public int getBackNum() {
		return backNum;
	}

	public int getReasonCode() {
		return reasonCode;
	}
	
}