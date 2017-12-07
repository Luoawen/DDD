package cn.m2c.scm.port.adapter.messaging.rabbitmq.special;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 特惠商品，更新商家名称
 */
public class GoodsSpecialDealerNameUpdateListener extends ExchangeListener {
    public GoodsSpecialDealerNameUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialDealerNameUpdateListener.class);

    @Autowired
    GoodsSpecialApplication goodsSpecialApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        LOGGER.info("GoodsSpecialDealerNameUpdateListener aTextMessage =>" + aTextMessage);
        NotificationReader reader = new NotificationReader(aTextMessage);
        String dealerId = reader.eventStringValue("dealerId");
        String dealerName = reader.eventStringValue("dealerName");
        goodsSpecialApplication.modifyGoodsSpecialDealerName(dealerId, dealerName);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.dealer.event.DealerUpdateEvent"};
    }
}
