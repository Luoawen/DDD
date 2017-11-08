package cn.m2c.scm.port.adapter.messaging.rabbitmq.stantard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.alibaba.fastjson.JSONObject;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.domain.model.stantard.Stantard;
import cn.m2c.scm.domain.model.stantard.StantardRepository;

public class StantardDeleteListener extends ExchangeListener{
	
	@Autowired
	StantardRepository stantardRepository;

	public StantardDeleteListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		JSONObject jsonObject = JSONObject.parseObject(aTextMessage);
        List<String> stantardIds = JsonUtils.toList(jsonObject.getJSONArray("standardIds").toJSONString(), String.class);
        
        if (stantardIds != null && stantardIds.size() > 0) {
        	for (String stantardId : stantardIds) {
            	if (null != stantardId) {
            		Stantard stantard = stantardRepository.getStantardByStantardId(stantardId);
            		if (stantard.getUseNum() > 0) {
            			stantard.noUsed();
            			stantardRepository.saveStantard(stantard);
					}
            	}
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
