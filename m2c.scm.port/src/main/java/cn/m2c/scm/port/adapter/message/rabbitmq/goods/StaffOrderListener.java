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
import cn.m2c.scm.domain.NegativeException;

public class StaffOrderListener  extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(StaffOrderListener.class);
	
	@Autowired
	SellerApplication staffApplication;
	
	
	public StaffOrderListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return "cn.m2c.order.domain.order.OrderPayed";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {

		LOGGER.info("监听到订单支付成功事件,text={}", aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			Integer goodsNum = reader.eventIntegerValue("goodsNum");
			System.out.println("goodsId:"+goodsId+"---goodsNum:"+goodsNum);  
			Long orderDate = reader.eventLongValue("payTime");
			LOGGER.info("订单数支付量参数-------------goodsId:"+goodsId+"-----goodsNum:"+goodsNum+"---------payTime:"+orderDate);
			staffApplication.addOrderNum(goodsId,orderDate);
		}catch (NegativeException e1) {
			LOGGER.error("商品不存在",e1);
		}catch (Exception e) {
			LOGGER.error("消费订单支付变更失败",e);
		}
		LOGGER.info("ok");
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[]{"cn.m2c.order.domain.order.OrderPayed"};
	}

}
