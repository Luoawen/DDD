package cn.m2c.scm.port.adapter.service.order;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cn.m2c.scm.application.order.data.bean.MediaResBean;
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
	
	@Autowired
    RestTemplate restTemplate;
	@Override
	public Map<String, Object> judgeStock(Map<String, Integer> skus) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lockMarketIds(List<String> marketIds, String orderNo, String userId) {
		// TODO Auto-generated method stub
		if (null == marketIds || marketIds.size() < 1) {
			return true;
		}
		String url = M2C_HOST_URL + "/m2c.market/fullcut/use?full_cut_ids={0}&order_id={1}&user_id={2}";
		String rtResult = restTemplate.postForObject(url, null, String.class, JSONObject.toJSONString(marketIds), orderNo, userId, String.class);
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
	public Map<String, Object> getMediaBdByResIds(List<String> resIds, long time) {
		// TODO Auto-generated method stub
		if (null == resIds || resIds.size() < 1) {
			return null;
		}
		
		String url = M2C_HOST_URL + "/m2c.media/mres/ratios/client?mresIds={0}&orderTime={1}";
		
		String rtResult = restTemplate.getForObject(url, String.class, JSONObject.toJSONString(resIds),
				time);
		JSONObject json = JSONObject.parseObject(rtResult);
		
		Map<String, Object> result = null;
        if (json.getInteger("status") == 200) {
        	result = new HashMap<String, Object>();
        	JSONArray contentArr = json.getJSONArray("content");
            int sz = contentArr.size();
            Gson gson = new Gson();
            for (int i=0; i< sz; i++) {
            	JSONObject obj = contentArr.getJSONObject(i);
            	MediaResBean m = gson.fromJson(obj.toJSONString(), MediaResBean.class);
            	result.put(m.getMresId(), m);
            }
        }
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
}
