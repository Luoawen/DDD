package cn.m2c.scm.application.goods.command;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;

/**
 * 删除商品保障
 * */
public class GoodsGuaranteeDelCommand extends AssertionConcern implements Serializable {
	
	private String guaranteeId;    //商品保障id
	private String dealerId;       //商家id
	
	public GoodsGuaranteeDelCommand(String guaranteeId, String dealerId) throws NegativeException {
		super();
		if(StringUtils.isEmpty(guaranteeId)) {
			throw new NegativeException(MCode.V_1, "商品保障ID为空");
		}
		if(StringUtils.isEmpty(dealerId)) {
			throw new NegativeException(MCode.V_1, "商品ID为空");
		}
		this.guaranteeId = guaranteeId;
		this.dealerId = dealerId;
	}

	public String getGuaranteeId() {
		return guaranteeId;
	}

	public String getDealerId() {
		return dealerId;
	}

	@Override
	public String toString() {
		return "GoodsGuaranteeDelCommand [guaranteeId=" + guaranteeId + ", dealerId=" + dealerId + "]";
	}
	
}
