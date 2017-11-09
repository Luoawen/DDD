package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.command.OrderPayedCmd;
/***
 * 监听订单支付完成消息
 * @author 89776
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class OrderPayedListener extends ExchangeListener {

	//@Autowired
	//private SupportJdbcTemplate jdbcTemplate;
	
	@Autowired
	private OrderApplication orderApp;
	
	public OrderPayedListener(RabbitmqConfiguration rabbitmqConfig,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfig, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void filteredDispatch(String aType, String msgBody) throws Exception {
		// TODO Auto-generated method stub
		NotificationReader reader = new NotificationReader(msgBody);
		
		String payNo = reader.eventStringValue("orderPayId");
		String orderNo = reader.eventStringValue("orderId");
		String userId = reader.eventStringValue("userId");
		Integer payWay = reader.eventIntegerValue("payWay");
		Long time = reader.eventLongValue("tradeTime");
		Date payTime = new Date(time.longValue());
		
		OrderPayedCmd cmd = new OrderPayedCmd(orderNo, userId, payNo, payWay, payTime);
		orderApp.orderPayed(cmd);
		cmd = null;
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.trading.domain.model.payment.OrderPayedEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}

}
