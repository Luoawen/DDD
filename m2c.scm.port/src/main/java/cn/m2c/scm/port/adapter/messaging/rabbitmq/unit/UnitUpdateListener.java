package cn.m2c.scm.port.adapter.messaging.rabbitmq.unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.domain.model.unit.Unit;
import cn.m2c.scm.domain.model.unit.UnitRepository;

public class UnitUpdateListener  extends ExchangeListener {
	

	@Autowired
    UnitRepository unitRepository;
	
	public UnitUpdateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		NotificationReader reader = new NotificationReader(aTextMessage);
        String oldUnitId = reader.eventStringValue("oldUnitId");
        String newGoodsUnitId = reader.eventStringValue("newGoodsUnitId");
        if (!oldUnitId.equals(newGoodsUnitId) && oldUnitId != newGoodsUnitId) {
        	if (null != oldUnitId && null != newGoodsUnitId) {
    			Unit oldUnit = unitRepository.getUnitByUnitId(oldUnitId);
    			Unit newUnit = unitRepository.getUnitByUnitId(newGoodsUnitId);
    			oldUnit.noUsed();
    			newUnit.used();
    			unitRepository.saveUnit(oldUnit);
    			unitRepository.saveUnit(newUnit);
    		}
		}
	}

	@Override
	protected String[] listensTo() {
		 return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsChangedEvent"};
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}
}
