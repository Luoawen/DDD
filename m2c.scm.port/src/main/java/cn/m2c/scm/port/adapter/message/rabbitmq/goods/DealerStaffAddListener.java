package cn.m2c.scm.port.adapter.message.rabbitmq.goods;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.goods.SellerApplication;
import cn.m2c.scm.application.goods.goods.command.StaffAddOrUpdateCmd;
import cn.m2c.scm.domain.IDGenerator;

public class DealerStaffAddListener  extends ExchangeListener{
	
	public DealerStaffAddListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(DealerStaffAddListener.class);
	@Autowired
	private SellerApplication staffApplication;
	

	@Override
	protected String packageName() {
		return "cn.m2c.system.domain.SalesManCreated";
	}

	@Override
	@SuppressWarnings("all")
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		try {
			LOGGER.info("----------------------accept Staff add msg, {}", aTextMessage);
        	
            NotificationReader reader = new NotificationReader(aTextMessage);
            String roleName  = reader.eventStringValue("roleName");
            if(roleName!=null && roleName.equals("经销商业务员")){
            	Integer sex = reader.eventStringValue("sex").equals("MAN") ? 1 : 0;
                Integer age = reader.eventIntegerValue("age");
                String proCode  = reader.eventStringValue("proCode");
                String cityCode  = reader.eventStringValue("cityCode");
                String areaCode  = reader.eventStringValue("areaCode");
                
                String proName  = reader.eventStringValue("proName");
                String cityName  = reader.eventStringValue("cityName");
                String areaName  = reader.eventStringValue("areaName");
                
                String userId = reader.eventStringValue("salersId");
                String userName = reader.eventStringValue("name");
                String userNo = reader.eventStringValue("userNo");
                String telNo = reader.eventStringValue("telNo");
                String email = reader.eventStringValue("email");
                Date createDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                		.parse(reader.eventStringValue("createDate"));
                LOGGER.info("-sex"+sex+"-age-"+age+"-proCode-"+proCode);
                //do business
                String staffId = IDGenerator.get(IDGenerator.DEALER_SELLER_PREFIX_TITLE);
                StaffAddOrUpdateCmd cmd = new StaffAddOrUpdateCmd(userId, userNo, telNo, userName, email, sex, age, proCode,cityCode,areaCode,proName,cityName,areaName,createDate.getTime());
                staffApplication.addStaff(cmd, staffId);
            }else{
            	LOGGER.info("不是经销商业务员，不消费");
            }
            
		} catch (Exception e) {
			LOGGER.error("消费业务员添加失败",e);
		}
		LOGGER.info("ok===========");
	}
  
	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.system.domain.SalesManCreated"};
	}

}
