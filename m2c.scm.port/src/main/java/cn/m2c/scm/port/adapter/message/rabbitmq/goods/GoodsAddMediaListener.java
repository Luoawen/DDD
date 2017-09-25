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
import cn.m2c.scm.application.goods.goods.command.GoodsAddMediaCommand;

public class GoodsAddMediaListener  extends ExchangeListener{
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsAddMediaListener.class);
	
	@Autowired
	GoodsApplication goodsApplication;
	
	public GoodsAddMediaListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return "cn.m2c.media.domain.mresources.MResBdAdvEvent";
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		System.out.println(aTextMessage+"---------------");
		 NotificationReader reader = new NotificationReader(aTextMessage);
		try {
			String goodsId = reader.eventStringValue("goodsId");
			String preGoodsId = reader.eventStringValue("preGoodsId");
			String mediaId = reader.eventStringValue("mediaId");
			String mediaName = reader.eventStringValue("mediaName");
			String mresId = reader.eventStringValue("mresId");
			String mresName = reader.eventStringValue("mresName");
			System.out.println("goodsId:"+goodsId+"---mediaId:"+mediaId+"---mediaName"+mediaName+"-----mresId"+mresId+"----mresName"+mresName+"====preGoodsId:"+preGoodsId);
			GoodsAddMediaCommand command = new GoodsAddMediaCommand(goodsId,mediaId,mediaName,mresId,mresName);
			goodsApplication.getMediaInfo(command,preGoodsId);
		} catch (Exception e) {
			LOGGER.error("消费媒体资源新增失败",e);
		}
	}

	@Override
	protected String[] listensTo() {
		
		return new String[] {"cn.m2c.media.domain.mresources.MResBdAdvEvent"};
	}

}
