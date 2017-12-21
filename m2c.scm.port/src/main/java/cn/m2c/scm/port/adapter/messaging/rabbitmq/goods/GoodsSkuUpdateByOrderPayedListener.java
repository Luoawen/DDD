package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 订单支付完成，加商品销量、减实际库存
 */
public class GoodsSkuUpdateByOrderPayedListener extends ExchangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSkuUpdateByOrderPayedListener.class);

    @Autowired
    GoodsApplication goodsApplication;

    public GoodsSkuUpdateByOrderPayedListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        LOGGER.info("GoodsSkuUpdateByOrderPayedListener start...");
        LOGGER.info("GoodsSkuUpdateByOrderPayedListener aTextMessage =>" + aTextMessage);
        Map map = JsonUtils.toMap4Obj(aTextMessage);
        Map eventMap = JsonUtils.toMap4Obj(JSONObject.toJSONString(map.get("event")));
        Date time = new Date(Long.parseLong(eventMap.get("payTime").toString()));
        SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
        Integer month = Integer.parseInt(df.format(time));
        Map obj = JsonUtils.toMap4Obj(JSONObject.toJSONString(eventMap.get("sales")));
        goodsApplication.GoodsSkuUpdateByOrderPayed(obj,month);
        LOGGER.info("GoodsSkuUpdateByOrderPayedListener end...");
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.order.event.OrderPayedEvent"};
    }
}
