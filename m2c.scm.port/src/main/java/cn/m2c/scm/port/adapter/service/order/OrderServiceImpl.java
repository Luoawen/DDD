package cn.m2c.scm.port.adapter.service.order;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.http.protocol.HttpRequestExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.m2c.scm.application.order.data.bean.MediaResBean;
import cn.m2c.scm.application.utils.EXPRESSMD5;
import cn.m2c.scm.application.utils.HttpRequest;
import cn.m2c.scm.domain.service.order.OrderService;
/***
 * 订单领域服务实现类
 * @author fanjc
 * created date 2017年10月16日
 * copyrighted@m2c
 */
@Service
public class OrderServiceImpl implements OrderService {
	/***/
	private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();
	private static final String KUAIDI_100_URL = DisconfDataGetter.getByFileItem("constants.properties", "express.url").toString().trim();
	private static final String KUAIDI_100_KEY = DisconfDataGetter.getByFileItem("constants.properties", "express.key").toString().trim();
	private static final String KUAIDI_100_CUSTOMER = DisconfDataGetter.getByFileItem("constants.properties", "express.customer").toString().trim();
	
	@Autowired
    RestTemplate restTemplate;
	@Override
	public Map<String, Object> judgeStock(Map<String, Integer> skus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> boolean lockMarketIds(List<T> marketIds, String orderNo, String userId) {
		// TODO Auto-generated method stub
		if (null == marketIds || marketIds.size() < 1) {
			return true;
		}
		String url = M2C_HOST_URL + "/m2c.market/fullcut/use?goods_list={0}&order_id={1}&user_id={2}";
		String rtResult = restTemplate.postForObject(url, null, String.class, JSONObject.toJSONString(marketIds), orderNo, userId);
		JSONObject json = JSONObject.parseObject(rtResult);
        if (json.getInteger("status") != 200) {
        	return false;
        }
		return true;
	}

	@Override
	public Map<String, Object> getMarketings(Map<String, Object> goodses) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, Object>> getShopCarGoods(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	/***
	 * 获取营销规则列表
	 * @param goodsId, typeId, dealerId
	 * @return
	 */
	@Override
	public Map<String, Object> getMarketingsByGoods(Map<String, Object> skus) {
		return null;
	}
	
	/***
	 * 获取商品的供货价
	 * @param goods 商品ID, 营销Id
	 * @return
	 */
	@Override
	public Map<String, Object> getSupplyPriceByIds(List<Map<String, String>> goods) {
		return null;
	}
	
	@Override
	public void lockCoupons(List<String> couponsIds) {
		
	}

	@Override
	public <T> Map<String, Object> getMediaBdByResIds(List<T> resIds, long time) {
		// TODO Auto-generated method stub
		if (null == resIds || resIds.size() < 1) {
			return null;
		}
		
		String url = M2C_HOST_URL + "/m2c.media/order/ad?skuListStr={0}&orderDateTime={1}";
		//String url = "http://10.0.40.25:8080/m2c.media/order/ad?skuListStr={0}&orderDateTime={1}";
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String rtResult = restTemplate.getForObject(url, String.class, JSONObject.toJSONString(resIds),
				formatter.format(new Date(time)));
		formatter = null;
		JSONObject json = JSONObject.parseObject(rtResult);
		
		Map<String, Object> result = null;
        if (json.getInteger("status") == 200) {
        	result = new HashMap<String, Object>();
        	JSONObject contentArr = json.getJSONObject("content");
            //int sz = contentArr.size();
            Gson gson = new Gson();
            //for (int i=0; i< sz; i++) {
            //	JSONObject obj = contentArr.getJSONObject(i);
            	//MediaResBean m = gson.fromJson(obj.toJSONString(), MediaResBean.class);
            	//result.put(m.getMresId(), m);
            	Type type = new TypeToken<HashMap<String, MediaResBean>>(){}.getType();
        		
            	//HashMap<String, MediaResBean> mp = JSON.parseObject(obj.toJSONString(), type, Feature.IgnoreNotMatch, Feature.DisableCircularReferenceDetect);
            	HashMap<String, MediaResBean> mp = gson.fromJson(contentArr.toJSONString(), type);
            	result.putAll(mp);
            //}
        }
        
        /*if (json.getInteger("status") == 200) {
        	result = new HashMap<String, Object>();
        	Map<String, Object> contentArr = JsonUtils.toMap4Obj(json.getJSONObject("content").toJSONString());
        	Iterator<String> it = contentArr.keySet().iterator();
            while (it.hasNext()) {
            	String key = it.next();
            	JSONObject beanStr = (JSONObject)contentArr.get(key);
            	result.put(key, JsonUtils.toBean(beanStr.toJSONString(), MediaResBean.class));
            }
        }*/
		return result;
	}

	@Override
	public void unlockCoupons(List<String> couponsIds, String userId) {
		// TODO Auto-generated method stub
		
	}

	/***
	 * 获取营销活动
	 */
	@Override
	public <T> List<T> getMarketingsByIds(List<String> marketingIds, String userId, Class<T[]> clss) {
		// TODO Auto-generated method stub
		
		if (null == marketingIds || marketingIds.size() < 1) {
			return null;
		}
		
		String url = M2C_HOST_URL + "/m2c.market/fullcut/user/{0}/multi?full_cut_ids={1}";
		StringBuilder mks = new StringBuilder(200);
		for(int i=0; i<marketingIds.size(); i++) {
			if (i>0)
				mks.append(",");
			mks.append(marketingIds.get(i));
		}
		
		String rtResult = restTemplate.getForObject(url, String.class, userId, mks.toString());
		JSONObject json = JSONObject.parseObject(rtResult);
		
		List<T> result = null;
        if (json.getInteger("status") == 200) {
        	String content = json.getString("content");
        	Gson gson = new Gson();
        	//result = gson.fromJson(content, new TypeToken<List<T>>() {}.getType());
        	result = Arrays.asList(gson.fromJson(content, clss));
        }
		return result;
	}

	/**
	 * 获取第三方物流信息
	 * @throws Exception 
	 */
	@Override
	public String getExpressInfo(String com, String nu) throws Exception {
		String param ="{\"com\":\""+com+"\",\"num\":\""+nu+"\"}";
		String key = KUAIDI_100_KEY;
		String customer = KUAIDI_100_CUSTOMER;
		String sign = EXPRESSMD5.encode(param+key+customer);
		HashMap<String, String> params = dealData(param,sign,customer);
		String resp = "";
		try {
			resp = new HttpRequest().postData(KUAIDI_100_URL, params, "utf-8").toString();
			System.out.println("----------"+resp);
		} catch (Exception e) {
			throw e;
		}		
		return resp;
	}

	/**
	 * 封装参数map
	 * @param param
	 * @param sign
	 * @param customer
	 * @return
	 */
	private HashMap<String, String> dealData(String param, String sign,
			String customer) {
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("param",param);
		map.put("sign",sign);
		map.put("customer",customer);
		return map;
	}
	
	
}
