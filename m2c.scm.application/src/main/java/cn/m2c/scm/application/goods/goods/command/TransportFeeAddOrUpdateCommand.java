package cn.m2c.scm.application.goods.goods.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class TransportFeeAddOrUpdateCommand  extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 2936721769410400243L;

	private String transportFeeId;
	private String modelName;
	private Long fee;
	private String dealerId;
	public TransportFeeAddOrUpdateCommand() {
		super();
	}
	
	public TransportFeeAddOrUpdateCommand(String transportFeeId,
			String modelName, Long fee, String dealerId) {
		super();
		assertArgumentNotNull(modelName, "模板名不能为空");
		assertArgumentLength(modelName, 50, "模板名过长");
		
		assertArgumentNotNull(fee, "运费金额不能为空");
		assertArgumentRange(fee,0,1000000,"运费金额不合法");
		
		
		assertArgumentNotNull(dealerId, "经销商不能为空");
		this.transportFeeId = transportFeeId;
		this.modelName = modelName;
		this.fee = fee;
		this.dealerId = dealerId;
	}


	public String getDealerId() {
		return dealerId;
	}
	public String getTransportFeeId() {
		return transportFeeId;
	}
	public String getModelName() {
		return modelName;
	}

	public Long getFee() {
		return fee;
	}
	
}
