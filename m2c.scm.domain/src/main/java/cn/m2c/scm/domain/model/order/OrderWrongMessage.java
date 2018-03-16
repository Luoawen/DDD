package cn.m2c.scm.domain.model.order;

import java.util.Date;

/**
 * 批量发货失败内容
 * @author lqwen
 *
 */
public class OrderWrongMessage {
	private String dealerOrderId;
	
	private String expressName;
	
	private String expressNo;
	
	private String importWrongMessage;
	
	private Date createdDate;
	
	private long expressFlag;
	
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
