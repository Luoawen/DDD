package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
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
	/**与订单详情对应的一个编号*/
	private int sortNo;
	
	public AddSaleAfterCmd(String userId, String orderId, String dealerOrderId,
			String skuId, String saleAfterNo, int type, String dealerId,
			String goodsId, int backNum, String reason, int reasonCode
			,int sortNo) throws NegativeException {
		
		if (StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空(orderId)！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtils.isEmpty(dealerOrderId)) {
			throw new NegativeException(MCode.V_1, "商家订单号参数为空(dealerOrderId)！");
		}
		
		if (StringUtils.isEmpty(skuId)) {
			throw new NegativeException(MCode.V_1, "申请售后商品sku参数为空(skuId)！");
		}
		
		if (StringUtils.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (StringUtils.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (type < 0 || type > 3) {
			throw new NegativeException(MCode.V_1, "售后单类型参数不正确(type)！");
		}
		
		if (backNum < 1) {
			throw new NegativeException(MCode.V_1, "退货数不正确(backNum)！");
		}
		
		if (sortNo < 0) {
			throw new NegativeException(MCode.V_1, "编号不正确(sortNo)！");
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
		this.sortNo = sortNo;
	}

	public int getSortNo() {
		return sortNo;
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
		if (type == 3)
			type = 0;
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
