package cn.m2c.scm.port.adapter.messaging.rabbitmq.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.shop.ShopApplication;
import cn.m2c.scm.application.shop.command.ShopInfoUpdateCommand;
import cn.m2c.scm.domain.IDGenerator;

/**
 * 新增商家时候新增默认的店铺信息
 * @author ps
 *command.getDealerId(), command.getDealerName(), command.getUserPhone()
 */
public class DefaultShopListener  extends ExchangeListener{
	private static final Logger log = LoggerFactory.getLogger(DefaultShopListener.class);
	private static final String DEFAULT_SHOP_ICON = DisconfDataGetter.getByFileItem("constants.properties", "default.shopIcon").toString().trim();
	
	@Autowired
	ShopApplication shopApplication;
	
	public DefaultShopListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager,
			ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage)
			throws Exception {
				try {
					log.info("消费商家管新增事件======>"+aTextMessage);
					NotificationReader reader = new NotificationReader(aTextMessage);
					String dealerId = reader.eventStringValue("dealerId");
					String dealerName = reader.eventStringValue("dealerName");
					String userPhone = reader.eventStringValue("userPhone");
					String shopId = IDGenerator.get(IDGenerator.SHOP_PREFIX_TITLE);
					ShopInfoUpdateCommand command = new ShopInfoUpdateCommand(dealerId, shopId, dealerName, DEFAULT_SHOP_ICON, "", "", userPhone);
					shopApplication.addShopInfo(command);
					log.info("新增默认店铺成功！！！");
				} catch (Exception e) {
					log.info("新增默认店铺",e);
					throw new Exception();
				}
		
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.scm.domain.model.dealer.event.DealerAddEvent"};
	}

}
