package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.SellerApplication;

public class DealerAddListener extends ExchangeListener{

	private static final Logger LOGGER = LoggerFactory.getLogger(DealerAddListener.class);
	
	@Autowired
	SellerApplication staffApplication;
	
	public DealerAddListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		return "cn.m2c.goods.domain.dealer.StaffAEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("accept dealer add msg,  ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！{}", aTextMessage);
		
		try {
			
            NotificationReader reader = new NotificationReader(aTextMessage);
            String dealerId = reader.eventStringValue("dealerId");
            String staffId = reader.eventStringValue("staffId");
            Long regisDate = reader.eventLongValue("createDate");
			LOGGER.info("accept dealer add msg, ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！ {}", regisDate);

            //do business
            staffApplication.addDealerNum(dealerId,staffId,regisDate);
		} catch (Exception e) {
			LOGGER.error("Dealer add event listener Exception e:", e);
			e.printStackTrace();
		}
		LOGGER.error("经销商事件消费ok ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！");
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.goods.domain.dealer.StaffAEvent"};
	}

}
