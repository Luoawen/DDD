package cn.m2c.scm.port.adapter.messaging.rabbitmq.special;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.special.GoodsSpecialApplication;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.util.List;
import java.util.Map;

/**
 * 商品修改了规格，更新商品特惠价
 */
public class GoodsSkuSpecialUpdateListener extends ExchangeListener {
    public GoodsSkuSpecialUpdateListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GoodsSkuSpecialUpdateListener.class);

    @Autowired
    GoodsSpecialApplication goodsSpecialApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        LOGGER.info("GoodsSkuSpecialUpdateListener aTextMessage =>" + aTextMessage);
        JSONObject jsonObject = JSONObject.parseObject(aTextMessage);
        JSONObject object = jsonObject.getJSONObject("event");
        String goodsId = object.getString("goodsId");
        JSONArray array = object.getJSONArray("addSkuList");
        List<Map> addSkuList = array.toJavaList(Map.class);
        if (null != addSkuList && addSkuList.size() > 0) {
            goodsSpecialApplication.modifyGoodsSkuSpecial(goodsId, addSkuList);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsModifyApproveSkuEvent"};
    }
}
