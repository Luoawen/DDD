package cn.m2c.scm.domain.model.order;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 批量发货失败内容
 * @author lqwen
 *
 */
public class OrderWrongMessage extends ConcurrencySafeEntity {
	private String dealerOrderId;
	
	private String expressName;
	
	private String expressNo;
	
	private String importWrongMessage;
	
	
	private long expressFlag;
	private Date createdDate;
	
	public OrderWrongMessage() {
		super();
	}

	
	public OrderWrongMessage(String dealerOrderId, String expressName,
			String expressNo, String importWrongMessage, long expressFlag) {
		super();
		this.dealerOrderId = dealerOrderId;
		this.expressName = expressName;
		this.expressNo = expressNo;
		this.importWrongMessage = importWrongMessage;
		this.expressFlag = expressFlag;
		this.createdDate = new Date();
	}


	public void add(String dealerOrderId, String expressName, String expressNo, String importWrongMessage,
			long expressFlag) {
		this.dealerOrderId = dealerOrderId;
		this.expressName = expressName;
		this.expressNo = expressNo;
		this.importWrongMessage = importWrongMessage;
		this.expressFlag = expressFlag;
	}
	

}
