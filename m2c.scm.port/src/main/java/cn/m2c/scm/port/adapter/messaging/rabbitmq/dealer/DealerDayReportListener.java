package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.application.dealer.command.DealerDayReportCommand;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * 商家数据按天报表
 */
public class DealerDayReportListener extends ExchangeListener {
    public DealerDayReportListener(RabbitmqConfiguration rabbitmqConfiguration, HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerDayReportListener.class);

    @Autowired
    DealerApplication dealerApplication;

    @Override
    protected String packageName() {
        return this.getClass().getPackage().getName();
    }

    @Override
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        LOGGER.info("DealerDayReportListener aTextMessage =>" + aTextMessage);
        JSONObject object = JSONObject.parseObject(aTextMessage);
        JSONObject eventObject = object.getJSONObject("event");
        String type = eventObject.getString("type");
        Date time = eventObject.getDate("time");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Integer day = Integer.parseInt(df.format(time));
        JSONObject infoObject = eventObject.getJSONObject("dealerInfo");
        Map<String, String> dealerInfoMap = JsonUtils.toMap(JSONObject.toJSONString(infoObject));
        for (Map.Entry<String, String> entry : dealerInfoMap.entrySet()) {
            String dealerId = entry.getKey();
            Map info = JsonUtils.toMap(entry.getValue());
            Integer num = null != info.get("num") ? Integer.parseInt(info.get("num").toString()) : 0;
            Long money = null != info.get("money") ? Long.parseLong(info.get("money").toString()) : 0l;
            DealerDayReportCommand command = new DealerDayReportCommand(dealerId, type, num, money, day);
            dealerApplication.saveDealerDayReport(command);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.dealer.event.DealerReportStatisticsEvent"};
    }
}
