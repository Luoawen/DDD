package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.GoodsApplication;

public class GoodsUDealerNameListener extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUDealerNameListener.class);
	@Autowired
	GoodsApplication goodsApplication;
	public GoodsUDealerNameListener(
			RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	protected String packageName() {
		return "cn.m2c.goods.domain.dealer.UorADealerEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("accept dealer add msg,  ！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！{}", aTextMessage);
		
		try {
			
            NotificationReader reader = new NotificationReader(aTextMessage);
            String dealerId = reader.eventStringValue("dealerId");
            String dealerName = reader.eventStringValue("dealerName");
			LOGGER.info("accept dealer add msg, ****************************** {}", dealerName);

            //do business
			goodsApplication.consumeDealerNameChange(dealerId,dealerName);
		} catch (Exception e) {
			LOGGER.error("商品更新经销商名字事件出错", e);
			e.printStackTrace();
		}
		LOGGER.error("商品更新经销商名字事件ok **********************************");
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.goods.domain.dealer.UorADealerEvent"};
	}

}
