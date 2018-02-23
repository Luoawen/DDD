package cn.m2c.scm.port.adapter.messaging.rabbitmq.order.log;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
/**
 * 订单日志操作事件监听器
 * @author fanjc
 * created date 2017年10月14日
 * copyrighted@m2c
 */
public class OrderOptLogListener extends ExchangeListener {
	
	@Autowired
	private SupportJdbcTemplate jdbcTemplate;
	
	public OrderOptLogListener(RabbitmqConfiguration rabConfig,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabConfig, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void filteredDispatch(String aType, String msgBody) throws Exception {
		// TODO Auto-generated method stub
		NotificationReader reader = new NotificationReader(msgBody);
		
		String orderId = reader.eventStringValue("orderId");
		String dealerOrderId = reader.eventStringValue("dealerOrderId");
		String optContent = reader.eventStringValue("optContent");
		String optUser = reader.eventStringValue("optUser");
		Date occurredOn = reader.eventDateValue("occurredOn");
		Integer type = reader.eventIntegerValue("type");
		if (type == null)
			type = 1;
		if (StringUtils.isEmpty(dealerOrderId)) {
			dealerOrderId = "";
		}
		//String eventVersion = reader.eventStringValue("eventVersion");
		StringBuilder sqlBuild = new StringBuilder(200);
		sqlBuild.append("INSERT INTO t_scm_order_opt_log(order_no, dealer_order_no, opt_content, opt_user, created_date, _type) VALUES(?,?,?,?,?,?)");
		jdbcTemplate.jdbcTemplate().update(sqlBuild.toString(), new Object[] {orderId,
				dealerOrderId, optContent, optUser, occurredOn, type});
		sqlBuild = null;
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.scm.domain.model.order.log.event.OrderOptLogEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}

}
