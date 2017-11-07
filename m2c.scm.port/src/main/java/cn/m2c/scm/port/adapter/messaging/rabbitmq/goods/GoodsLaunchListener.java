package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;

/**
 * 商品投放
 */
public class GoodsLaunchListener extends ExchangeListener {

	@Autowired
	GoodsApplication goodsApplication;
	
	public GoodsLaunchListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		NotificationReader reader = new NotificationReader(aTextMessage);
        String goodsId = reader.eventStringValue("goodsId");
        goodsApplication.LaunchGoods(goodsId);
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.media.domain.mresources.AdsenseScheduleAddEvent"};
	}

}
