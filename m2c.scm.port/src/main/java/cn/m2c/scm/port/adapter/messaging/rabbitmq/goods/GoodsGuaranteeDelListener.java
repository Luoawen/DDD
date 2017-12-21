package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.GoodsApproveApplication;

/**
 * 商品保障删除后, 需删除已选保障商品的保障, 和审核中已选保障商品的保障 
 */
public class GoodsGuaranteeDelListener extends ExchangeListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsGuaranteeDelListener.class);
	
	@Autowired
    GoodsApproveApplication goodsApproveApplication;	
	
	@Autowired
    GoodsApplication goodsApplication;
	
	public GoodsGuaranteeDelListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("GoodsGuaranteeDelListener aTextMessage ==> " + aTextMessage);
		NotificationReader reader = new NotificationReader(aTextMessage);
		String dealerId = reader.eventStringValue("dealerId");
		String guaranteeId = reader.eventStringValue("guaranteeId");
		//修改商品的商品保障
		goodsApplication.modifyGoodsGuarantee(dealerId, guaranteeId);
		//修改审核中商品的商品保障
		goodsApproveApplication.modifyGoodsApproveGuarantee(dealerId, guaranteeId);
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsGuaranteeDelEvent"};
	}

}
