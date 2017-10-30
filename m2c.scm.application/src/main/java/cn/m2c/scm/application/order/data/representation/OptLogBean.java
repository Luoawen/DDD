package cn.m2c.scm.application.order.data.representation;

import java.util.Date;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/***
 * 订单实体类,用于数据展示
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
public class OptLogBean {
	@ColumnAlias(value= "order_no")
	private String orderId;
	@ColumnAlias(value= "dealer_order_no")
	private String dealerOrderId;
	@ColumnAlias(value= "opt_content")
	private String optContent;
	@ColumnAlias(value= "opt_user")
	private String optUser;
	@ColumnAlias(value= "created_date")
	private Date optTime;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getOptContent() {
		return optContent;
	}

	public void setOptContent(String optContent) {
		this.optContent = optContent;
	}

	public String getOptUser() {
		return optUser;
	}

	public void setOptUser(String optUser) {
		this.optUser = optUser;
	}

	public Date getOptTime() {
		return optTime;
	}

	public void setOptTime(Date optTime) {
		this.optTime = optTime;
	}

}
