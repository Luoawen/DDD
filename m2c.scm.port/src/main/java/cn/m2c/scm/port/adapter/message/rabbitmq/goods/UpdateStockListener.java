package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.google.gson.Gson;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;

public class UpdateStockListener  extends ExchangeListener{

private static final Logger logger = LoggerFactory.getLogger(UpdateStockListener.class);
	
    
	public UpdateStockListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.goods.domain.event.UpdateStockEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage)
			throws Exception {
		
		logger.info("accept user updateStock msg, {}", aTextMessage);
		
	}

	@Override
	protected String[] listensTo() {
		return new String[] {"cn.m2c.goods.domain.event.UpdateStockEvent"};
	}


}
