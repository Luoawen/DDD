package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class RefundPayedListener extends ExchangeListener {
	Logger LOGGER = LoggerFactory.getLogger(RefundPayedListener.class);
	//@Autowired
	//private SupportJdbcTemplate jdbcTemplate;
	
	@Autowired
	private SaleAfterOrderApp saleAfterApp;
	
	public RefundPayedListener(RabbitmqConfiguration rabbitmqConfig,
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
		LOGGER.info("====fanjc==receive msg for OrderRefundedEvent");
		try {
			RefundEvtBean bean = JsonUtils.toBean(msgBody, RefundEvtBean.class);
			
			//OrderPayedCmd cmd = new OrderPayedCmd(orderNo, userId, payNo, payWay, payTime);
			saleAfterApp.refundSuccess(bean);
		}
		catch (Exception e) {
			e.printStackTrace();
			LOGGER.info("====fanjc==" +  e.getMessage());
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
	
	public static void main(String[] args) {
		String a = "{\"orderRefundId\":\"16DR20171124102602271000909418\",\"orderPayId\":\"20171124102141766000845612\",\"afterSellOrderId\":\"20171124102531AL\",\"dealerId\":\"JXSF44C306010EE434DABC8847CA47B8D69\",\"userId\":\"HY8155311C70F849DAA126997124D8CB98\",\"payWay\":2,\"refundAmount\":1,\"tradeNo\":\"4200000032201711246671310729\",\"refundTime\":1511490362271}";
		RefundEvtBean bean = JsonUtils.toBean(a, RefundEvtBean.class);
		System.out.println(bean);
	}
}
