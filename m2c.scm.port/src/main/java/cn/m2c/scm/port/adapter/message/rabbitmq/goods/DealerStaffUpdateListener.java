package cn.m2c.scm.port.adapter.message.rabbitmq.goods;


import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.SellerApplication;
import cn.m2c.scm.application.goods.goods.command.StaffAddOrUpdateCmd;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

public class DealerStaffUpdateListener extends ExchangeListener {

    public DealerStaffUpdateListener(RabbitmqConfiguration rabbitmqConfiguration,
                                     HibernateTransactionManager hibernateTransactionManager,
                                     ConsumedEventStore consumedEventStore) {
        super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
        // TODO Auto-generated constructor stub
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerStaffUpdateListener.class);
    @Autowired
    private SellerApplication staffApplication;


    @Override
    protected String packageName() {
        return "cn.m2c.system.domain.SalesManUpdated";
    }

    @Override
    @SuppressWarnings("all")
    protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
        try {
            LOGGER.info("----------------------DealerStaffUpdateListener msg, {}", aTextMessage);
            NotificationReader reader = new NotificationReader(aTextMessage);
            String roleName = reader.eventStringValue("roleName");
            if (roleName != null && roleName.equals("经销商业务员")) {
                //userId
                String userId = reader.eventStringValue("salersId");
                //用户名
                String userName = reader.eventStringValue("name");
                //编号
                String userNo = reader.eventStringValue("userNo");
                //联系电话
                String telNo = reader.eventStringValue("telNo");
                //邮件
                String email = reader.eventStringValue("email");
                //性别
                Integer sex = reader.eventStringValue("sex").equals("MAN") ? 1 : 0;
                //年龄
                Integer age = reader.eventIntegerValue("age");
                //区域
                String proCode = reader.eventStringValue("proCode");
                String cityCode = reader.eventStringValue("cityCode");
                String areaCode = reader.eventStringValue("areaCode");
                String proName = reader.eventStringValue("proName");
                String cityName = reader.eventStringValue("cityName");
                String areaName = reader.eventStringValue("areaName");

                StaffAddOrUpdateCmd cmd = new StaffAddOrUpdateCmd(userId, userNo, telNo, userName, email, sex,
                        age, proCode, cityCode, areaCode, proName, cityName, areaName);
                staffApplication.updateStaff(cmd);

            } else {
                LOGGER.info("不是经销商业务员，不消费");
            }
        } catch (Exception e) {
            LOGGER.error("消费业务员修改失败", e);
        }
    }

    @Override
    protected String[] listensTo() {
        return new String[]{"cn.m2c.system.domain.SalesManUpdated"};
    }

}
