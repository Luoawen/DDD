package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
/***
 * 售后申请商家审核 
 * @author fanjc
 * created date 2017年10月21日
 * copyrighted@m2c
 */
public class AproveSaleAfterCmd extends AssertionConcern {

	private String userId;
	
	private String saleAfterNo;
	
	private String dealerId;
	
	public AproveSaleAfterCmd(String userId, String saleAfterNo, String dealerId) throws NegativeException {
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}
		
		if (StringUtils.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}
		
		if (StringUtils.isEmpty(dealerId)) {
			throw new NegativeException(MCode.V_1, "商家id参数为空(dealerId)！");
		}
		
		this.userId = userId;
		this.saleAfterNo = saleAfterNo;
		this.dealerId = dealerId;
	}

	public String getUserId() {
		return userId;
	}

	public String getSaleAfterNo() {
		return saleAfterNo;
	}

	public String getDealerId() {
		return dealerId;
	}
	
}
