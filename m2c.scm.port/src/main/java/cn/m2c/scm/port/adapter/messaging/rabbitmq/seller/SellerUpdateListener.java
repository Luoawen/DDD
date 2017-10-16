package cn.m2c.scm.port.adapter.messaging.rabbitmq.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.application.seller.command.SellerCommand;

public class SellerUpdateListener extends ExchangeListener{
	
	@Autowired 
	DealerApplication dealerApplication;

	public SellerUpdateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String sellerId = reader.eventStringValue("sellerId");
        String sellerName = reader.eventStringValue("sellerName");
        String sellerPhone = reader.eventStringValue("sellerPhone");
        
        SellerCommand command = new SellerCommand(sellerId, sellerName, sellerPhone);
        dealerApplication.updateSeller(command);
        
	}

	@Override
	protected String[] listensTo() {
		return new String[] {"cn.m2c.scm.domain.model.seller.event.SellerAddOrUpdateEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}

}
