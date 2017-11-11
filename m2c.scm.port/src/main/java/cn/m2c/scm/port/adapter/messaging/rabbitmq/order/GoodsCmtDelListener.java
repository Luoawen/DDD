package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.DealerOrderApplication;

public class GoodsCmtDelListener extends ExchangeListener {

	@Autowired
	private DealerOrderApplication orderApp;
	
	public GoodsCmtDelListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.scm.domain.model.comment.event.GoodsCommentDeleteEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String msgBody) throws Exception {
		// TODO Auto-generated method stub
		NotificationReader reader = new NotificationReader(msgBody);
		
		String orderNo = reader.eventStringValue("orderId");
		String skuId = reader.eventStringValue("skuId");
		
		orderApp.commentSku(orderNo, skuId, 0);
	}
}
