package cn.m2c.scm.application.order.data.bean;

/***
 * 原始金额数据
 * @author 89776
 * created date 2017年12月21日
 * copyrighted@m2c
 */
public class DealerOrderMoneyBean {

	/**运费*/
	private Long orderFreight;
	
	/**状态*/
	private Integer status;

	public Long getOrderFreight() {
		return orderFreight;
	}

	public void setOrderFreight(Long orderFreight) {
		this.orderFreight = orderFreight;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
}
