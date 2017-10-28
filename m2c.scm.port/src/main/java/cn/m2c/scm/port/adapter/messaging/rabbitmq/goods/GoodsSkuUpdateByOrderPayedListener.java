package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.util.Iterator;
import java.util.Map;

/**
 * 订单支付完成，加商品销量、减实际库存
 */
public class GoodsSkuUpdateByOrderPayedListener extends ExchangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSkuUpdateByOrderPayedListener.class);

    public GoodsSkuUpdateByOrderPayedListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        JSONObject json = JSONObject.parseObject(aTextMessage);
        JSONArray sales = json.getJSONArray("sales");
        Iterator<Object> it = sales.iterator();
        while (it.hasNext()) {
            Map jo = (Map) it.next();
        }

    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.order.event.OrderPayedEvent"};
    }
}
