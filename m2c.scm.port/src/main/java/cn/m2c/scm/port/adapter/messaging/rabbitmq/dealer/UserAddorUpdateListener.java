package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.util.StringUtils;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.dealer.DealerApplication;

public class UserAddorUpdateListener  extends ExchangeListener{
	
	private static final Logger log = LoggerFactory.getLogger(UserAddorUpdateListener.class);
	
	@Autowired
	DealerApplication dealerApplication;

	public UserAddorUpdateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		log.info("消费商家管理员用户事件======>"+aTextMessage);
		try {
			NotificationReader reader = new NotificationReader(aTextMessage);
			String userId = reader.eventStringValue("userId");
			Integer GroupType = reader.eventIntegerValue("groupType");
			String userName = reader.eventStringValue("userName");
			String dealerId = reader.eventStringValue("dealerId");
			String userPhone = reader.eventStringValue("mobile");
			
			if (!StringUtils.isEmpty(dealerId)) {
				if (GroupType == 4) {
					if (StringUtils.isEmpty(userId)) {
						log.info("用户Id为空");
						throw new Exception();
					}
					dealerApplication.addOrUpdateUser(dealerId, userId, userName, userPhone);
				}
			}
		} catch (Exception e) {
			log.info("更新业务员事件失败",e.getMessage());
			throw new Exception();
		}
		
	}

	@Override
	protected String[] listensTo() {
		 return new String[]{"cn.m2c.users.domain.user.UserAddorUpdateEvent"};
	}

}
