package cn.m2c.scm.port.adapter.service.dealer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;

import cn.m2c.scm.application.utils.HttpRequest;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.dealer.DealerService;

@Service("dealerRestService")
public class DealerServiceImpl implements DealerService{
	
	private final static Logger log = LoggerFactory.getLogger(DealerServiceImpl.class);
	
	private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();
	private static final String DEFAULT_SHOP_ICON = DisconfDataGetter.getByFileItem("constants.properties", "default.shopIcon").toString().trim();
	

	public void addShop(String dealerId, String dealerName,String userPhone) throws NegativeException {
		List<Map> resultList = new ArrayList<>();
		try {
			String url = M2C_HOST_URL + "/m2c.scm/shop/sys/shopInfo";
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("dealerId",dealerId);
			params.put("customerServiceTel",userPhone);
			params.put("shopName",dealerName);
			params.put("shopIcon",DEFAULT_SHOP_ICON);
			String resp = new HttpRequest().postData(url, params, "utf-8").toString();
				System.out.println("----------"+resp);
			JSONObject json = JSONObject.parseObject(resp);
			if (json.getInteger("status") == 200) {
				log.info("添加店铺成功!",this.getClass().getName());
			}else {
				String errorMessage = json.getString("errorMessage");
				log.info("添加店铺失败!",errorMessage);
			}
		} catch (Exception e) {
			log.info("添加店铺出现异常",e.getMessage());
			throw new NegativeException();
		}
	}

}
