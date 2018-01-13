package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 商品下架更新猜你喜欢缓存
 */
public class GoodsGuessCacheUpdateListener extends ExchangeListener {
    public GoodsGuessCacheUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    GoodsQueryApplication goodsQueryApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String goodsId = reader.eventStringValue("goodsId");
        boolean isModify = false;
        if (aType.contains("GoodsModifyApproveSkuEvent")) {
            isModify = true;
        }
        goodsQueryApplication.updateGoodsGuessCache(goodsId, isModify);
        goodsQueryApplication.updateGoodsHotSellCache(goodsId, isModify);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsOffShelfEvent",
                "cn.m2c.scm.domain.model.goods.event.GoodsDeleteEvent",
                "cn.m2c.scm.domain.model.goods.event.GoodsModifyApproveSkuEvent"};
    }
}
