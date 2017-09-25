package cn.m2c.scm.domain.model.order.logger;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 
 * @ClassName: OperLogger
 * @Description: 操作日志
 * @author moyj
 * @date 2017年7月7日 下午3:47:44
 *
 */
public class OperLogger extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 6363903666944455647L;
	private String loggerId;		//操作日志ID
	private String businessId;		//业务ID
	private Integer businessType;	//业务类型
	private String operName;		//操作名称
	private String operDes;			//操作描述	
	private String operResult;		//操作结果
	private Long operTime;			//操作时间
	private String operUserId;		//操作人
	private String operUserName;	//操作人名称
	
	public void create(
			String loggerId,
			String businessId,
			Integer businessType,
			String operName,
			String operDes,
			String operResult,
			Long operTime,String operUserId,String operUserName){
		this.loggerId = loggerId;
		this.businessId = businessId;
		this.businessType = businessType;
		this.operName = operName;
		this.operDes = operDes;
		this.operResult = operResult;
		this.operUserId = operUserId;
		this.operUserName = operUserName;
		this.operTime = operTime;
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
