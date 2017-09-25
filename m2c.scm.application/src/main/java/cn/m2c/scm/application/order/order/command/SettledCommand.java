package cn.m2c.scm.application.order.order.command;

import java.io.Serializable;
import java.util.List;

import cn.m2c.ddd.common.AssertionConcern;

/**
 * 
 * @ClassName: SettledCommand
 * @Description: 批量结算命令
 * @author moyj
 * @date 2017年7月12日 下午2:06:59
 *
 */
public class SettledCommand extends AssertionConcern implements Serializable{

	private static final long serialVersionUID = 8717381129374635249L;
	
	private List<String> orderIdList;				//订单编号
	
	public SettledCommand(List<String> orderIdList){
		assertArgumentNotEmpty(orderIdList, "orderIdList is not be null.");
		this.orderIdList = orderIdList;
	}


	public List<String> getOrderIdList() {
		return orderIdList;
	}

	

	
}
