package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.DealerApplication;

public class UserInfoChangeListerner extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserInfoChangeListerner.class);
	@Autowired
	DealerApplication application;
	public UserInfoChangeListerner(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return "cn.m2c.users.domain.user.UserAddorUpdateEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage)
			throws Exception {
		LOGGER.info("修改用户名字, {}", aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String userId = reader.eventStringValue("userId");
			String userName = reader.eventStringValue("userName");
			Integer groupType = reader.eventIntegerValue("groupType");
			System.out.println("userId:"+userId+"---userName:"+userName);  
			LOGGER.info("修改经销商用户名字参数-------------userId:"+userId+"-----userName:"+userName+"--groupType"+groupType);
			if(groupType==4){
				application.consumeUserName(userId,userName);
			}
		}catch (Exception e) {
			LOGGER.error("修改经销商用户名字失败",e);
		}
		LOGGER.info("ok");
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.users.domain.user.UserAddorUpdateEvent"};
	}

}
