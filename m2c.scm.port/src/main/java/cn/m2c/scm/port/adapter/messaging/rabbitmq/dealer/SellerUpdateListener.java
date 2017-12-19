package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.seller.SellerApplication;
import cn.m2c.scm.application.seller.command.SellerCommand;

/**
 * 业务员更新事件消费
 * @author lqwen
 *
 */
public class SellerUpdateListener extends ExchangeListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(SellerUpdateListener.class);
	
	@Autowired
	private SellerApplication sellerApplication;
	
	public SellerUpdateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}


	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("更新业务员事件消费");
		
		NotificationReader reader = new NotificationReader(aTextMessage);
		String sellerId = reader.eventStringValue("userId");
		String sellerPhone = reader.eventStringValue("userName");
		String sellerName = reader.eventStringValue("name");
		String remark = reader.eventStringValue("remark");
		
		SellerCommand command = new SellerCommand(sellerId, sellerName, sellerPhone, remark);
		sellerApplication.updateSeller(command);
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.operate.domain.model.system.SellerUpdateEvent"};
	}
	
	

}
