package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsActInventoryApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 活动商品冻结状态更新
 */
public class GoodsActFreezeStatusUpdateListener extends ExchangeListener {
    @Autowired
    GoodsActInventoryApplication goodsActInventoryApplication;


    public GoodsActFreezeStatusUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String ruleId = reader.eventStringValue("ruleId");
        if (aType.contains("UnfreezeStockToFlashsaleEvent")) {  // 活动创建成功
            goodsActInventoryApplication.actCreateSuccess(ruleId);
        } else if (aType.contains("EndFlashsaleEvent")) { // 活动终止
            goodsActInventoryApplication.actEnd(ruleId);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.market.domain.model.flashsale.event.UnfreezeStockToFlashsaleEvent", "cn.m2c.market.domain.model.flashsale.event.EndFlashsaleEvent"};
    }
}
