package cn.m2c.scm.port.adapter.message.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.SellerApplication;

public class GoodsAddListener  extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsAddListener.class);
	
	@Autowired
	 SellerApplication staffApplication;
	
	
	public GoodsAddListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		return "cn.m2c.goods.domain.goods.GoodsAddEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("----------消费商品数据业务员业绩-----------"+aTextMessage);
		try {
			LOGGER.info("商品业务员业绩 {}", aTextMessage);
			
            NotificationReader reader = new NotificationReader(aTextMessage);
            String goodsId = reader.eventStringValue("goodsId");
            String staffId = reader.eventStringValue("staffId");
            Long regisDate = reader.eventLongValue("regisDate");
            LOGGER.info("消费商品业务员业绩数据--------goodsId"+goodsId+"--------------staffId"+staffId+"--------------regisDate"+regisDate);
            staffApplication.addGoodsNum(goodsId,staffId,regisDate);
		} catch (Exception e) {
			LOGGER.error("消费商品数据业务员业绩失败", e.getMessage());
		}
		
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.goods.domain.goods.GoodsAddEvent"};
	}

}
