package cn.m2c.scm.application.order.data.representation;
/**
 * 订单数bean
 * @author fanjc
 *
 */
public class OrderNums {
	/**订单数*/
	private Integer orderNum;

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public OrderNums() {
		
	}
	
	public OrderNums(Integer i) {
		orderNum = i;
	}
}
