package cn.m2c.scm.port.adapter.messaging.rabbitmq.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.domain.model.unit.Unit;
import cn.m2c.scm.domain.model.unit.UnitRepository;

public class UnitDeleteListener extends ExchangeListener{

	@Autowired
    UnitRepository unitRepository;
	
	public UnitDeleteListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		NotificationReader reader = new NotificationReader(aTextMessage);
        String unitId = reader.eventStringValue("goodsUnitId");
        if (null != unitId) {
			Unit unit = unitRepository.getUnitByUnitId(unitId);
			if (unit.getUseNum() > 0) {
				unit.noUsed();
				unitRepository.saveUnit(unit);
			}
		}
		
	}

	@Override
	protected String[] listensTo() {
		 return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsDeleteEvent"};
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

}
