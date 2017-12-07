package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.domain.util.GetDisconfDataGetter;
/***
 * 监听退款付款失败消息
 * @author fanjc
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class RefundFailListener extends ExchangeListener {
	Logger LOGGER = LoggerFactory.getLogger(RefundFailListener.class);
	//@Autowired
	//private SupportJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SaleAfterOrderApp saleAfterApp;
	
	public RefundFailListener(RabbitmqConfiguration rabbitmqConfig,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfig, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void filteredDispatch(String aType, String msgBody) throws Exception {
		// TODO Auto-generated method stub
		NotificationReader reader = new NotificationReader(msgBody);
		
		String orderNo = reader.eventStringValue("afterSellOrderId");
		//String userId = reader.eventStringValue("userId");
		String val = GetDisconfDataGetter.getDisconfProperty("scm.job.user");
		saleAfterApp.refundFailed(orderNo, val);
		//cmd = null;
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.trading.domain.model.payment.OrderRefundFailEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}
	
	public static void main(String[] args) {
	}
}
