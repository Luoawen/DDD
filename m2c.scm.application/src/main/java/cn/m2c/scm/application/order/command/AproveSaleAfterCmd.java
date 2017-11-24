package cn.m2c.scm.application.order.command;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

/***
 * 售后申请商家审核
 * 
 * @author fanjc created date 2017年10月21日 copyrighted@m2c
 */
public class AproveSaleAfterCmd extends AssertionConcern {

	private String userId;

	private String saleAfterNo;

	private String dealerId;

	private String rejectReason;

	private int rejectReasonCode;
	
	private float rtFreight;

	public float getRtFreight() {
		return rtFreight;
	}

	public AproveSaleAfterCmd(String userId, String saleAfterNo, String dealerId, String rejectReason,
			int rejectReasonCode) throws NegativeException {

		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空(userId)！");
		}

		if (StringUtils.isEmpty(saleAfterNo)) {
			throw new NegativeException(MCode.V_1, "售后单号参数为空(saleAfterNo)！");
		}

		if (StringUtils.isEmpty(dealerId)) {
			throw new NegativeException(MCode.V_1, "商家id参数为空(dealerId)！");
		}
		
		if (rejectReason != null && rejectReason.length() > 200) {
			throw new NegativeException(MCode.V_1, "原因不能大于200字！");
		}

		this.userId = userId;
		this.saleAfterNo = saleAfterNo;
		this.dealerId = dealerId;
		this.rejectReason = rejectReason;
		this.rejectReasonCode = rejectReasonCode;
	}

	public AproveSaleAfterCmd(String userId, String saleAfterNo, String dealerId, float rtFreight) throws NegativeException {
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
		this.rtFreight = rtFreight;
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

	public String getRejectReason() {
		return rejectReason;
	}

	public int getRejectReasonCode() {
		return rejectReasonCode;
	}

}
