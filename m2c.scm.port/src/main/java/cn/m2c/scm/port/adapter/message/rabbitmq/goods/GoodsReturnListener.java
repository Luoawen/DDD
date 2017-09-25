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

/**
 * 商品退货监听
 * @author yezp
 *
 */
public class GoodsReturnListener extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsReturnListener.class);
	@Autowired
	GoodsApplication goodsApplication;

	public GoodsReturnListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.order.domain.order.OrderGoodsBacked";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		System.out.println(aTextMessage+"---------------");
		NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			Integer goodsNum = reader.eventIntegerValue("goodsNum");
			System.out.println("goodsId:"+goodsId+"---goodsNum:"+goodsNum);  
			goodsApplication.consumeGoodsreturn(goodsId,goodsNum);
		} catch (Exception e) {
			LOGGER.error("消费订单换货事件失败",e);
		}
		LOGGER.info("ok---");
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.order.domain.order.OrderGoodsBacked"};
	}

}
