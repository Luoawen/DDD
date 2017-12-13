package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.dealer.DealerApplication;
import cn.m2c.scm.application.dealer.command.DealerDayReportCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        NotificationReader reader = new NotificationReader(aTextMessage);
        String dealerId = reader.eventStringValue("dealerId");
        String type = reader.eventStringValue("type");
        Integer num = reader.eventIntegerValue("num");
        Long money = reader.eventLongValue("money");
        Date time = reader.eventDateValue("time");
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        Integer day = Integer.parseInt(df.format(time));
        DealerDayReportCommand command = new DealerDayReportCommand(dealerId, type, num, money, day);
        dealerApplication.saveDealerDayReport(command);
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.scm.domain.model.dealer.event.DealerReportStatisticsEvent"};
    }
}
