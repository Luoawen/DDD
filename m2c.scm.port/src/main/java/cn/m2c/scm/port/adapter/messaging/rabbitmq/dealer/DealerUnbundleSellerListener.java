package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.domain.model.dealer.Dealer;
import cn.m2c.scm.domain.model.dealer.DealerRepository;

/**
 * 商家解绑业务员事件监听
 * @author lqwen
 *
 */
public class DealerUnbundleSellerListener extends ExchangeListener{
	
	private static final Logger log = LoggerFactory.getLogger(DealerUnbundleSellerListener.class);
	
	@Autowired
	DealerApplication dealerApplication;

	public DealerUnbundleSellerListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		try {
			log.info("消费商家管理员用户事件======>"+aTextMessage);
			NotificationReader reader = new NotificationReader(aTextMessage);
			String userId = reader.eventStringValue("userId");
			Integer oldGroupType = reader.eventIntegerValue("oldGroupType");
			Integer newGroupType = reader.eventIntegerValue("newGroupType");
			if (oldGroupType == 4) {
				if (oldGroupType != newGroupType) {
					dealerApplication.unbundleUser(userId);
				}
			}
			log.info("消费商家管理员用户事件ok!!!");
		} catch (Exception e) {
			log.info("解绑商家管理员",e);
			throw new Exception();
		}
	}

	@Override
	protected String[] listensTo() {
		 return new String[]{"cn.m2c.users.domain.user.UserUnbind"};
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

}
