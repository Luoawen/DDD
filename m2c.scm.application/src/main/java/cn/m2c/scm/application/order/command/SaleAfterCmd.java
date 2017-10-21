package cn.m2c.scm.application.order.command;


import cn.m2c.common.MCode;
import cn.m2c.common.StringUtil;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
/***
 * 售后 基本命令单 
 * @author fanjc
 * created date 2017年10月21日
 * copyrighted@m2c
 */
public class SaleAfterCmd extends AssertionConcern {

	private String userId;
	
	private String saleAfterNo;
	
	private String skuId;
	
	public SaleAfterCmd(String userId, String saleAfterNo, String skuId) throws NegativeException {
		
		if (StringUtil.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtil.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (StringUtil.isEmpty(skuId)) {
			throw new NegativeException(MCode.V_1, "售后商品sku参数为空(skuId)！");
		}
		
		this.userId = userId;
		this.skuId = skuId;
		this.saleAfterNo = saleAfterNo;
	}
	

	public String getUserId() {
		return userId;
	}

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public String getSkuId() {
		return skuId;
	}
}
