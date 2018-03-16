package cn.m2c.scm.port.adapter.messaging.rabbitmq.postage;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.postage.PostageModelApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

/**
 * 监听商品上架，运费模板增加商品使用数
 */
public class PostageModelAddGoodsNumListener extends ExchangeListener {
    public PostageModelAddGoodsNumListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
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
        if (aType.contains("GoodsAddEvent")) {
            Integer goodsShelves = reader.eventIntegerValue("goodsShelves");
            if (goodsShelves == 2) {
                postageModelApplication.addGoodsUserNum(goodsPostageId);
            }
        } else {
            postageModelApplication.addGoodsUserNum(goodsPostageId);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsUpShelfEvent", "cn.m2c.scm.domain.model.goods.event.GoodsAddEvent"};
    }
}
