package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.data.bean.RefundEvtBean;
/***
 * 监听退款付款完成消息
 * @author fanjc
 * created date 2017年10月24日
 * copyrighted@m2c
 */
public class RefoundPayedListener extends ExchangeListener {

	//@Autowired
	//private SupportJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SaleAfterOrderApp saleAfterApp;
	
	public RefoundPayedListener(RabbitmqConfiguration rabbitmqConfig,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfig, hibernateTransactionManager, consumedEventStore);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void filteredDispatch(String aType, String msgBody) throws Exception {
		// TODO Auto-generated method stub
		/*NotificationReader reader = new NotificationReader(msgBody);
		
		String payNo = reader.eventStringValue("orderRefundId");
		String orderNo = reader.eventStringValue("afterSellOrderId");
		//订单支付
		String orderPayId = reader.eventStringValue("orderPayId");
		String dealerId = reader.eventStringValue("dealerId");
		
		String userId = reader.eventStringValue("userId");
		Integer payWay = reader.eventIntegerValue("payWay");
		Long time = reader.eventLongValue("refundTime");
		Date payTime = null;
		if (time != null) {
			new Date(time.longValue());
		}
		else {
			payTime = new Date();
		}*/
		try {
			RefundEvtBean bean = JsonUtils.toBean(msgBody, RefundEvtBean.class);
			
			//OrderPayedCmd cmd = new OrderPayedCmd(orderNo, userId, payNo, payWay, payTime);
			saleAfterApp.refundSuccess(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		//cmd = null;
	}

	@Override
	protected String[] listensTo() {
		// TODO Auto-generated method stub
		return new String[] {"cn.m2c.trading.domain.model.payment.OrderRefundedEvent"};
	}

	@Override
	protected String packageName() {
		// TODO Auto-generated method stub
		return this.getClass().getPackage().getName();
	}

}
