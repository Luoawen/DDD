package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.application.goods.GoodsApproveApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 修改供应商名称
 */
public class GoodsModifyDealerNameListener extends ExchangeListener {
    public GoodsModifyDealerNameListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    GoodsApplication goodsApplication;
    @Autowired
    GoodsApproveApplication goodsApproveApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String dealerId = reader.eventStringValue("dealerId");
        String dealerName = reader.eventStringValue("dealerName");
        goodsApplication.modifyGoodsDealerName(dealerId, dealerName);
        goodsApproveApplication.modifyGoodsApproveDealerName(dealerId, dealerName);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.dealer.event.DealerUpdateEvent"};
    }
}
