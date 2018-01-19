package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.OrderMediaApplication;
import cn.m2c.scm.application.order.command.OrderMediaCommand;
import cn.m2c.scm.port.adapter.service.order.OrderServiceImpl;

public class MediaOrderCreateListener extends ExchangeListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediaOrderCreateListener.class);
	
	@Autowired
	OrderMediaApplication orderMediaApplication;
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	public MediaOrderCreateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("MediaOrderCreateListener aTextMessage ==> " + aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		String orderId = reader.eventStringValue("orderId");
		String dealerOrderId = reader.eventStringValue("dealerOrderId");
		String mediaId = reader.eventStringValue("mediaId");
		String mediaResId = reader.eventStringValue("mediaResId");
		//调用媒体接口查询广告位信息
		Map map = orderServiceImpl.getMediaMessageInfo(mediaId, mediaResId);
		if(null != map && map.size() > 0) {
			String mediaCate = map.get("mediaCate") == null ? null : (String) map.get("mediaCate");
			Integer mediaNo = map.get("mediaNo") == null ? null : Integer.parseInt((String) map.get("mediaNo")); 
			String mediaName = map.get("mediaName") == null ? null : (String) map.get("mediaName");
			Integer mresCate = map.get("mresCate") == null ? null : Integer.parseInt((String) map.get("mresCate"));
			Integer formId = map.get("formId") == null ? null : Integer.parseInt((String) map.get("formId"));
			Long mresNo = map.get("mresNo") == null ? null : Long.parseLong((String) map.get("mresNo"));
			Integer level = map.get("level") == null ? null : Integer.parseInt((String) map.get("level"));
			//封装，保存
			OrderMediaCommand command = new OrderMediaCommand(orderId, dealerOrderId, mediaCate, mediaNo, mediaName, mresCate, formId, mresNo, level);
			orderMediaApplication.addOrderMedia(command);
		}
		
	}

	@Override
	protected String[] listensTo() {
		return new String[] {"cn.m2c.scm.domain.model.order.event.MediaOrderCreateEvent"};
	}

}
