package cn.m2c.scm.domain.model.order;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 售后流程记录
 * @author lqwen
 *
 */
public class AfterSellFlow extends ConcurrencySafeEntity{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(AfterSellFlow.class);

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String afterSellOrderId;
	
	private Integer status;
	
	private String statusName;
	
	private Date createdDate;
	
	private String userId;
	
	private String rejectReason;
	
	private Integer rejectReasonCode;
	
	private String applyReason;
	
	private Integer applyReasonCode;

	public AfterSellFlow(String afterSellOrderId, Integer status, String statusName, Date createdDate, String userId) {
		super();
		this.afterSellOrderId = afterSellOrderId;
		this.status = status;
		this.statusName = statusName;
		this.createdDate = createdDate;
		this.userId = userId;
	}
	
	public AfterSellFlow() {
		super();
	}

	public void add(String afterSellOrderId, Integer status, String userId,String applyReason,Integer applyReasonCode,String rejectReason,Integer rejectReasonCode) {
		
		LOGGER.info("售后记录...");
		
		this.afterSellOrderId = afterSellOrderId;
		this.status = status;
		this.userId = userId;
		this.createdDate = new Date();
		this.applyReason = applyReason;
		this.applyReasonCode = applyReasonCode;
		this.rejectReason = rejectReason;
		this.rejectReasonCode = rejectReasonCode;
		
		switch (status) {
		case -1:
			this.statusName = "售后已取消";
			break;
		case 0:
			this.statusName = "发起售后申请";
			break;
		case 3:
			this.statusName = "拒绝售后申请";
			break;
		case 4:
			this.statusName = "同意退换货";
			break;
		case 5:
			this.statusName = "客户寄出";
			break;
		case 7:
			this.statusName = "商家重新发货";
			break;
		case 8:
			this.statusName = "客户收到";
			break;
		case 9:
			this.statusName = "商家同意退款";
			break;
		case 10:
			this.statusName = "确认退款";
			break;
		case 11:
			this.statusName = "交易完成";
			break;
		case 12:
			this.statusName = "交易关闭";
			break;
		}
	}

	@Override
	public String toString() {
		return "AfterSellFlow [afterSellOrderId=" + afterSellOrderId + ", status=" + status + ", statusName="
				+ statusName + ", createdDate=" + createdDate + ", userId=" + userId + ", rejectReason=" + rejectReason
				+ ", rejectReasonCode=" + rejectReasonCode + ", applyReason=" + applyReason + ", applyReasonCode="
				+ applyReasonCode + "]";
	}

	
	
}
