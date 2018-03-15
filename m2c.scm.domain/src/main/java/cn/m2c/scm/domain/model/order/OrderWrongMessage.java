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

	
	public OrderWrongMessage(String dealerOrderId, String expressName, String expressNo, String importWrongMessage,
			Date createdDate) {
		super();
		this.dealerOrderId = dealerOrderId;
		this.expressName = expressName;
		this.expressNo = expressNo;
		this.importWrongMessage = importWrongMessage;
		this.createdDate = new Date();
	}
	
	
	
	

}
