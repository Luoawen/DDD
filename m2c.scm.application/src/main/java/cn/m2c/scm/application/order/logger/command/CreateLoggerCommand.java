package cn.m2c.scm.application.order.logger.command;

import java.io.Serializable;

import cn.m2c.ddd.common.AssertionConcern;

public class CreateLoggerCommand extends AssertionConcern implements Serializable{
	private static final long serialVersionUID = 3124399947726759303L;
	
	private String loggerId;		//操作日志ID
	private String businessId;		//业务ID
	private Integer businessType;	//业务类型
	private String operName;		//操作名称
	private String operDes;			//操作描述	
	private String operResult;		//操作结果
	private Long operTime;			//操作时间
	private String operUserId;		//操作人
	private String operUserName;	//操作人名称
	
	public CreateLoggerCommand(
				String loggerId,
				String businessId,
				Integer businessType,
				String operName,
				String operDes,
				String operResult,
				Long operTime,
				String operUserId,
				String operUserName
			){
		assertArgumentNotEmpty(loggerId, "loggerId is not be null.");
		assertArgumentNotEmpty(businessId, "businessId is not be null.");
		assertArgumentNotNull(businessType, "businessType is not be null.");
		assertArgumentNotEmpty(operName, "operName is not be null.");
		assertArgumentNotEmpty(operResult, "operResult is not be null.");
		assertArgumentNotNull(operTime, "operTime is not be null.");
		this.loggerId = loggerId;
		this.businessId = businessId;
		this.businessType = businessType;
		this.operName = operName;
		this.operDes = operDes;
		this.operResult = operResult;
		this.operTime = operTime;
		this.operUserId = operUserId;
		this.operUserName = operUserName;
	}
	
	
	public String getLoggerId() {
		return loggerId;
	}
	public String getBusinessId() {
		return businessId;
	}
	public Integer getBusinessType() {
		return businessType;
	}
	public String getOperName() {
		return operName;
	}
	public String getOperDes() {
		return operDes;
	}
	public String getOperResult() {
		return operResult;
	}
	public Long getOperTime() {
		return operTime;
	}
	public String getOperUserId() {
		return operUserId;
	}
	public String getOperUserName() {
		return operUserName;
	}
	
	
	
}
