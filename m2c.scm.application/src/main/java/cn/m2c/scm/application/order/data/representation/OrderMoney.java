package cn.m2c.scm.application.order.data.representation;
/**
 * 订单号及订单金额bean
 * @author fanjc
 *
 */
public class OrderMoney {
	/**主(平台)订单号*/
	private String orderNo;
	
	private long amountOfMoney;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderId) {
		this.orderNo = orderId;
	}

	public long getAmountOfMoney() {
		return amountOfMoney;
	}

	public void setAmountOfMoney(long amountOfMoney) {
		this.amountOfMoney = amountOfMoney;
	}
}
