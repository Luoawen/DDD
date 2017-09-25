package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.GoodsApplication;

public class GoodOrderInfoListener  extends ExchangeListener{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodOrderInfoListener.class);
	
	@Autowired
	GoodsApplication goodsApplication;
	
	
	public GoodOrderInfoListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.order.domain.order.OrderCommited";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {

		LOGGER.info("监听到订单事件,text={}", aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			Integer goodsNum = reader.eventIntegerValue("goodsNum");
			System.out.println("下单事件监听--------------goodsId:"+goodsId+"---goodsNum:"+goodsNum);  
			goodsApplication.consumeOrderInfo(goodsId,goodsNum);
		}catch (NegativeException e1) {
			LOGGER.error("商品不存在",e1);
		}catch (Exception e) {
			LOGGER.error("消费媒体资源变更失败",e);
		}
		LOGGER.info("ok");
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.order.domain.order.OrderCommited"};
	}

}
