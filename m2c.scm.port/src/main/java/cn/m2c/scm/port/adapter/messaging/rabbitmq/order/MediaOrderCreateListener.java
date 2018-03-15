package cn.m2c.scm.port.adapter.messaging.rabbitmq.order;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.notification.NotificationReader;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.order.OrderMediaApplication;
import cn.m2c.scm.application.order.command.OrderMediaCommand;
import cn.m2c.scm.domain.model.order.event.MediaGoods;
import cn.m2c.scm.port.adapter.service.order.OrderServiceImpl;

public class MediaOrderCreateListener extends ExchangeListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MediaOrderCreateListener.class);
	
	@Autowired
	OrderMediaApplication orderMediaApplication;
	@Autowired
	OrderServiceImpl orderServiceImpl;
	
	public MediaOrderCreateListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("MediaOrderCreateListener aTextMessage ==> " + aTextMessage);
		/*NotificationReader reader = new NotificationReader(aTextMessage);
		String orderId = reader.eventStringValue("orderId");
		String dealerOrderId = reader.eventStringValue("dealerOrderId");
		String mediaId = reader.eventStringValue("mediaId");
		String mediaResId = reader.eventStringValue("mediaResId");
		Integer sortNo = reader.eventIntegerValue("sortNo");*/
		
		//原事件修改--> key平台订单号,val 为订单中商品与媒体数据 Map<String, List<MediaGoods>> data
		String eventStringValue = JSONObject.parseObject(aTextMessage).getJSONObject("event").getString("data");
		
		if(StringUtils.isNotEmpty(eventStringValue)) {
			Map data = JSONObject.parseObject(eventStringValue);
			if(null != data && data.size() > 0 ) {
			    Iterator it = data.entrySet().iterator();
			    while(it.hasNext()){
			        Entry entry = (Entry) it.next();
			        String orderId = (String) entry.getKey();
			        JSONArray array = (JSONArray) data.get(orderId);
			        if(null !=array && array.size() > 0){
			            for(int i = 0; i < array.size(); i++){
			                JSONObject job = array.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
			                String dealerOrderId = job.getString("dealerOrderId");
			                String mediaId = job.getString("mediaId");
			                String mediaResId = job.getString("mediaResId");
			                Integer sortNo = job.getInteger("sortNo");
			            
			                //调用媒体接口查询广告位信息
			                Map map = orderServiceImpl.getMediaMessageInfo(mediaId, mediaResId);
			                if(null != map && map.size() > 0) {
			                	String mediaCate = map.get("mediaCate") == null ? null : (String) map.get("mediaCate");
			                	Integer mediaNo = map.get("mediaNo") == null ? null : Integer.parseInt((String) map.get("mediaNo")); 
			                	String mediaName = map.get("mediaName") == null ? null : (String) map.get("mediaName");
			                	Integer mresCate = map.get("mresCate") == null ? null : Integer.parseInt((String) map.get("mresCate"));
			                	Integer formId = map.get("formId") == null ? null : Integer.parseInt((String) map.get("formId"));
			                	Long mresNo = map.get("mresNo") == null ? null : Long.parseLong((String) map.get("mresNo"));
			                	Integer level = map.get("level") == null ? null : Integer.parseInt((String) map.get("level"));
			    			    //封装，保存
			                	OrderMediaCommand command = new OrderMediaCommand(orderId, dealerOrderId, mediaCate, mediaNo, mediaName, mresCate, formId, mresNo, level, sortNo);
			                	orderMediaApplication.addOrderMedia(command);
				            }
			            }
			        }
				}
			}
		}
	}
	
	@Override
	protected String[] listensTo() {
		return new String[] {"cn.m2c.scm.domain.model.order.event.MediaOrderCreateEvent"};
	}

}
