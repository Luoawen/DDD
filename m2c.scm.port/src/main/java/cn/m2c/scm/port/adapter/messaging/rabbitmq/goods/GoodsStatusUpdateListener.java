package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.util.List;

/**
 * 商品扣库存，更新商品状态
 */
public class GoodsStatusUpdateListener extends ExchangeListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsStatusUpdateListener.class);

    public GoodsStatusUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    @Autowired
    GoodsApplication goodsApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String s, String aTextMessage) throws Exception {
        LOGGER.info("GoodsStatusUpdateListener aTextMessage =>" + aTextMessage);
        JSONObject jsonObject = JSONObject.parseObject(aTextMessage);
        JSONObject object = jsonObject.getJSONObject("event");
        JSONArray array = object.getJSONArray("goodsIds");
        List<Integer> list = array.toJavaList(Integer.class);
        if (null != list && list.size() > 0) {
            goodsApplication.outInventoryUpdateGoodsStatus(list);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsOutInventoryEvent"};
    }
}
