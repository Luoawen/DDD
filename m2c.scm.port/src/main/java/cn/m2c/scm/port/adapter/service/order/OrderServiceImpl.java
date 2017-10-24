package cn.m2c.scm.port.adapter.service.order;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;

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
	public void lockStock(Map<String, Integer> skus) {
		// TODO Auto-generated method stub
		// 抛出异常表示锁定不成功.
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
	 * 获取营销规则列表通过营销ID
	 * @param marketingIds
	 * @return
	 */
	@Override
	public Map<String, Object> getMarketingsByIds(List<String> marketingIds) {
		String url = M2C_HOST_URL + "/m2c.media/mres/ratios/client?mresIds={0}&orderTime={1}";
		
		
		
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

	@Override
	public void unlockStock(Map<String, Float> skus) {
		// TODO Auto-generated method stub
		
	}
}
