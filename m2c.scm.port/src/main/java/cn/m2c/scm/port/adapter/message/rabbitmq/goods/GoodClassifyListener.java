package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.GoodsClassifyApplication;

public class GoodClassifyListener extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodClassifyListener.class);
	@Autowired
	GoodsClassifyApplication goodsClassifyApplication;

	public GoodClassifyListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return "cn.m2c.goods.domain.classify.GCountEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		System.out.println(aTextMessage+"---------------");
		 NotificationReader reader = new NotificationReader(aTextMessage);
		 try {
				String goodsClassifyId = reader.eventStringValue("goodsClassifyId");
				Integer changeCount = reader.eventIntegerValue("changeCount");
				System.out.println("goodsClassifyId:"+goodsClassifyId+"---changeCount:"+changeCount);
				goodsClassifyApplication.consumeCountEvent(goodsClassifyId,changeCount);
			} catch (Exception e) {
				LOGGER.error("消费用户组的用户数变更失败",e);
			}
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.goods.domain.classify.GCountEvent"};
	}

}
