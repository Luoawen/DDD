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
 * 特惠商品，更新商品名称
 */
public class GoodsSpecialGoodsNameUpdateListener extends ExchangeListener {
    public GoodsSpecialGoodsNameUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSpecialGoodsNameUpdateListener.class);

    @Autowired
    GoodsSpecialApplication goodsSpecialApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        LOGGER.info("GoodsSpecialGoodsNameUpdateListener aTextMessage =>" + aTextMessage);
        NotificationReader reader = new NotificationReader(aTextMessage);
        String goodsId = reader.eventStringValue("goodsId");
        String goodsName = reader.eventStringValue("goodsName");
        goodsSpecialApplication.modifyGoodsSpecialGoodsName(goodsId, goodsName);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsChangedEvent"};
    }
}
