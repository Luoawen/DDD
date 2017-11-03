package cn.m2c.scm.port.adapter.service.shop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.service.shop.ShopService;

@Service
public class ShopServiceImpl implements ShopService {

	@Autowired
	private SupportJdbcTemplate supportJdbcTemplate;

	public SupportJdbcTemplate getSupportJdbcTemplate() {
		return this.supportJdbcTemplate;
	}

	@Autowired
	RestTemplate restTemplate;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Integer shopIsOrNotFucos(String dealerId, String userId) {
		List<Map> resultList = new ArrayList<>();
		String url = "http://api.m2c2017local.com/m2c.users/favoriteshop/app/detail?token=12345678&userId={0}&dealerId={1}";
		String result = restTemplate.getForObject(url, String.class,userId,dealerId);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getInteger("status") == 200) {
			JSONArray contents = json.getJSONArray("content");
			resultList = (List<Map>) JSONArray.toJavaObject(contents, Object.class);
		}
		if (resultList.size() == 0) {
			return 0;
		}else {
			return 1;
		}
	}

}
