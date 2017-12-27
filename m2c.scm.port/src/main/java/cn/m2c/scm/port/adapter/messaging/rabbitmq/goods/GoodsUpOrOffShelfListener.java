package cn.m2c.scm.port.adapter.messaging.rabbitmq.goods;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.HibernateTransactionManager;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.application.configuration.RabbitmqConfiguration;
import cn.m2c.ddd.common.event.ConsumedEventStore;
import cn.m2c.ddd.common.port.adapter.messaging.rabbitmq.ExchangeListener;
import cn.m2c.scm.application.goods.GoodsApplication;
import cn.m2c.scm.domain.model.goods.GoodsRecognized;

/**
 * 商品批量上/下架，监听上/下架时修改识别图 
 *
 */
public class GoodsUpOrOffShelfListener extends ExchangeListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsUpOrOffShelfListener.class);
	
	@Autowired
	GoodsApplication goodsApplication;
	
	public GoodsUpOrOffShelfListener(RabbitmqConfiguration rabbitmqConfiguration,
			HibernateTransactionManager hibernateTransactionManager, ConsumedEventStore consumedEventStore) {
		super(rabbitmqConfiguration, hibernateTransactionManager, consumedEventStore);
	}

	@Override
	protected String packageName() {
		 return this.getClass().getPackage().getName();
	}

	@Override
	protected void filteredDispatch(String aType, String aTextMessage) throws Exception {
		LOGGER.info("GoodsUpShelfListener aTextMessage => " + aTextMessage);
		JSONObject jsonObject = JSONObject.parseObject(aTextMessage);
	    JSONObject object = jsonObject.getJSONObject("event");
	    Integer status = object.getInteger("status");
	    String goodsRecognizedsStr = object.getString("goodsRecognizeds");
	    List<GoodsRecognized> list = JsonUtils.toList(goodsRecognizedsStr, GoodsRecognized.class);
	    //JSONArray array = object.getJSONArray("goodsRecognizeds");
	    if(null != list && list.size() > 0) {
	    	//List<GoodsRecognized> list = array.toJavaList(GoodsRecognized.class);
	    	goodsApplication.updateRecognizedImgStatus(list, status);
	    }
	}

	@Override
	protected String[] listensTo() {
		return new String[]{"cn.m2c.scm.domain.model.goods.event.GoodsUpShelfEvent","cn.m2c.scm.domain.model.goods.event.GoodsOffShelfEvent"};
	}

}
