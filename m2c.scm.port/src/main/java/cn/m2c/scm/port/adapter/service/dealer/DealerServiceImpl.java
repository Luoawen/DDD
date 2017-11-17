package cn.m2c.scm.port.adapter.service.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;

import cn.m2c.scm.domain.service.dealer.DealerService;

public class DealerServiceImpl implements DealerService{
	
	private final static Logger log = LoggerFactory.getLogger(DealerServiceImpl.class);
	
	private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();
	private static final String DEFAULT_SHOP_ICON = DisconfDataGetter.getByFileItem("constants.properties", "default.shopIcon").toString().trim();
	
	@Autowired
	RestTemplate restTemplate;

	public void addShop(String dealerId, String dealerName) {
		try {
			String url = M2C_HOST_URL + "/m2c.scm/shop/sys/shopInfo?dealerId={0}&shopName={1}&shopIcon={2}";
			String result = restTemplate.getForObject(url, String.class,dealerId,dealerName, DEFAULT_SHOP_ICON);
			JSONObject json = JSONObject.parseObject(result);
			if (json.getInteger("status") == 200) {
				log.info("添加店铺成功!",this.getClass().getName());
			}else {
				String errorMessage = json.getString("errorMessage");
				log.info("添加店铺失败!",errorMessage);
			}
		} catch (Exception e) {
			log.info("添加店铺出现异常",e.getMessage());
			throw e;
		}
	}

}
