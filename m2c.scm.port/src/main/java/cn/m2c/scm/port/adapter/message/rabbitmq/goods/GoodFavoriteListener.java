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
import cn.m2c.scm.domain.NegativeException;

public class GoodFavoriteListener  extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodFavoriteListener.class);
	
	@Autowired
	GoodsApplication goodsApplication;
	
	public GoodFavoriteListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return "cn.m2c.users.domain.conduct.FavoriteCountEvent";  
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("商品收藏事件 参数"+aTextMessage);
		System.out.println(aTextMessage+"---------------");
		 NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			Integer favoriteCountChange = reader.eventIntegerValue("favoriteCountChange");
			System.out.println("goodsId:"+goodsId+"---favoriteCountChange:"+favoriteCountChange);
			goodsApplication.countFavorite(goodsId,favoriteCountChange);
		}catch (NegativeException e1) {
			LOGGER.error("商品不存在",e1);
		}catch (Exception e) {
			LOGGER.error("消费媒体资源变更失败",e);
		}
		LOGGER.info("消费收藏事件成功");
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.users.domain.conduct.FavoriteCountEvent"};
	}

}
