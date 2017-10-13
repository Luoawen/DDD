package cn.m2c.scm.domain.model.order;

import cn.m2c.common.RandomUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
/***
 * 订单号生成器，时间加6位随机串
 * @author fanjc
 *
 */
public class OrderIDGenernor extends ConcurrencySafeEntity {

	private static final long serialVersionUID = 1L;
	/**订单号*/
	private String orderNo;
	/**时间串*/
	private String time;
	/**随机串*/
	private String str;
	
	public String getTime() {
		return time;
	}

	public String getStr() {
		return str;
	}
	/***
	 * 获取订单号
	 * @return
	 */
	public String getOrder() {
		time = cn.m2c.scm.domain.util.DateUtils.getDateStr(cn.m2c.scm.domain.util.DateUtils.TYPE_0);
		str = RandomUtils.toStrs4Upper(6);
		orderNo = time + str;
		return orderNo;
	}
	
	public OrderIDGenernor() {
		super();
	}
}

