package cn.m2c.scm.domain.model.order.logger;

import java.util.Date;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.ddd.common.domain.model.DomainEvent;
/**
 * 
 * @ClassName: Opered
 * @Description: 操作日志事件
 * @author moyj
 * @date 2017年7月14日 上午10:47:36
 *
 */
public class Opered extends AssertionConcern implements  DomainEvent{

	private String businessId;		//业务ID
	private Integer businessType;	//业务类型
	private String operName;		//操作名称
	private String operDes;			//操作描述	
	private String operResult;		//操作结果
	private Long operTime;			//操作时间
	private String operUserId;		//操作人
	private String operUserName;	//操作人名称
	
	public Opered(String businessId,
			Integer businessType,
			String operName,
			String operDes,
			String operResult,
			String operUserId,
			String operUserName){
		this.businessId = businessId;
		this.businessType = businessType;
		this.operName = operName;
		this.operDes = operDes;
		this.operResult = operResult;
		this.operTime = System.currentTimeMillis();
		this.operUserId = operUserId;
		this.operUserName = operUserName;
		
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

	@Override
	public int eventVersion() {
		return 0;
	}

	@Override
	public Date occurredOn() {
		return new Date(System.currentTimeMillis());
	}
	


}
