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

public class UserUnbindDealerListener  extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(UserUnbindDealerListener.class);
	
	@Autowired
	DealerApplication dealerApplication;
	
	public UserUnbindDealerListener(
			RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.users.domain.user.UserUnbind";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("经销商解绑事件 {}", aTextMessage);
		
		try {
			
            NotificationReader reader = new NotificationReader(aTextMessage);
            Integer oldGroupType = reader.eventIntegerValue("oldGroupType");
            Integer newGroupType = reader.eventIntegerValue("newGroupType");
            String userId = reader.eventStringValue("userId");
            String mobile = reader.eventStringValue("mobile");
			LOGGER.info("经销商解绑事件参数 ****************************** oldGroupType："+oldGroupType+"-----newGroupType："+newGroupType+"---userId:"+userId+"---mobile:"+mobile);
			if(oldGroupType==4){
				//do business
				dealerApplication.consumeUnBind(userId,mobile);
			}
		} catch (Exception e) {
			LOGGER.error("经销商解绑事件出错", e);
			e.printStackTrace();
		}
		LOGGER.error("经销商解绑事件ok **********************************");
		
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.users.domain.user.UserUnbind"};
	}

}
