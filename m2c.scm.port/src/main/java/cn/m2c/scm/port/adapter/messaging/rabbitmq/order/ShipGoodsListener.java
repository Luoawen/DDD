package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.util.StringUtils;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.command.SendOrderSMSCommand;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.domain.NegativeException;

public class ShipGoodsListener extends ExchangeListener{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ShipGoodsListener.class);
	
	@Autowired
	OrderApplication orderapplication;
	
	
	@Autowired
	OrderQuery orderQuery;

	public ShipGoodsListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws NegativeException {
		
		LOGGER.info("发货事件监听...");
		
		NotificationReader reader = new NotificationReader(aTextMessage);
		String shopName = reader.eventStringValue("shopName");
		String orderId = reader.eventStringValue("orderId");
		String nu = reader.eventStringValue("nu");
		String com = reader.eventStringValue("com");
		
		if(StringUtils.isEmpty(orderId)) {
			throw new NegativeException(MCode.V_401,"订单ID为空！");
		}
		if(StringUtils.isEmpty(shopName)) {
			throw new NegativeException(MCode.V_401,"店铺名为空！");
		}
		if(StringUtils.isEmpty(nu)) {
			throw new NegativeException(MCode.V_401,"物流公司单号为空！");
		}
		if(StringUtils.isEmpty(com)) {
			throw new NegativeException(MCode.V_401,"物流公司编码为空！");
		}
		String userId = orderQuery.getOrderUserId(orderId);
		SendOrderSMSCommand cmd = new SendOrderSMSCommand(userId,shopName);
		orderapplication.sendOrderSMS(cmd);
		
		orderapplication.registExpress(com,nu);
		
		
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.scm.domain.model.order.event.OrderShipEvent"};
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

}
