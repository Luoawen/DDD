package cn.m2c.scm.port.adapter.messaging.rabbitmq.postage;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.postage.PostageModelApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 监听商品下架和商品删除，运费模板减少商品使用数
 */
public class PostageModelOutGoodsNumListener extends ExchangeListener {
    public PostageModelOutGoodsNumListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    PostageModelApplication postageModelApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        NotificationReader reader = new NotificationReader(aTextMessage);
        String goodsPostageId = reader.eventStringValue("goodsPostageId");
        if (aType.contains("GoodsDeleteEvent")) {
            Integer goodsStatus = reader.eventIntegerValue("goodsStatus");
            if (goodsStatus != 1) {
                postageModelApplication.outGoodsUserNum(goodsPostageId);
            }
        } else {
            postageModelApplication.outGoodsUserNum(goodsPostageId);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsOffShelfEvent",
                "cn.m2c.scm.domain.model.goods.event.GoodsDeleteEvent"};
    }
}
