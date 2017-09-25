package cn.m2c.scm.port.adapter.message.rabbitmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.common.IDGenerator;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.logger.OperLoggerApplication;
import cn.m2c.scm.application.order.logger.command.CreateLoggerCommand;
import cn.m2c.scm.domain.IDPrefix;

public class OperedListener extends ExchangeListener {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(OperedListener.class);
	@Autowired
	private OperLoggerApplication operLoggerApplication;

	public OperedListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager,
				consumedEventStore);
	}

	@Override
	protected String packageName() {
		return "cn.m2c.order.domain.logger.Opered";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage)
			throws Exception {
		LOGGER.info("accept opered msg, {}", aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		String businessId = reader.eventStringValue("businessId");
		Integer businessType = reader.eventIntegerValue("businessType");
		String operName = reader.eventStringValue("operName");
		String operDes = reader.eventStringValue("operDes");
		String operResult = reader.eventStringValue("operResult");
		Long operTime = reader.eventLongValue("operTime");
		String operUserId = reader.eventStringValue("operUserId");
		String operUserName = reader.eventStringValue("operUserName");
		CreateLoggerCommand command = new CreateLoggerCommand(
				IDGenerator.get(IDPrefix.ORDER_OPERLOGGER_PREFIX), businessId,
				businessType, operName, operDes, operResult, operTime,
				operUserId, operUserName);

		operLoggerApplication.create(command);

	}

	@Override
	protected String[] listensTo() {
		return new String[] { "cn.m2c.order.domain.logger.Opered" };
	}

}
