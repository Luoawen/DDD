package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatform;
import cn.m2c.scm.domain.model.expressPlatform.ExpressPlatformRepository;

/**
 * 用户退货换货发货 事件监听
 * @author lqwen
 *
 */
public class UserShipListener extends ExchangeListener {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(UserShipListener.class);
	
	@Autowired
    ExpressPlatformRepository expressPlatformRepository;

	public UserShipListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("用户发货物流注册消费...");
		NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String com = reader.eventStringValue("com");
			String nu = reader.eventStringValue("nu");
			
			ExpressPlatform ep = new ExpressPlatform();
			ep.save(com, nu, 1);
			
			expressPlatformRepository.saveOrUpdate(ep);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw new NegativeException(MCode.V_400,"用户发货物流消费失败");
		}
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.scm.domain.model.order.event.UserShipEvent"};
	}

}
