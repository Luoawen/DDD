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

public class GoodsUMediaListener  extends ExchangeListener{
private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUMediaListener.class);
	
	@Autowired
	GoodsApplication goodsApplication;
	public GoodsUMediaListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.media.domain.mresources.MResUpdSucEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		System.out.println(aTextMessage+"---------------");
		 NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			String preGoodsId = reader.eventStringValue("preGoodsId");
			System.out.println("goodsId:"+goodsId+"---preGoodsId:"+preGoodsId);
			goodsApplication.getMediaInfo(goodsId,preGoodsId);
		} catch (Exception e) {
			LOGGER.error("消费媒体资源变更失败",e);
		}
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.media.domain.mresources.MResUpdSucEvent"};
	}

}
