package cn.m2c.scm.port.adapter.service.seller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.alibaba.fastjson.JSONObject;
import com.baidu.disconf.client.usertools.DisconfDataGetter;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.service.seller.SellerService;

@Service("SellerRestService")
public class SellerServiceImpl implements SellerService{
	private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();

	@Autowired
	private SupportJdbcTemplate supportJdbcTemplate;
	
	public SupportJdbcTemplate getSupportJdbcTemplate() {
		return this.supportJdbcTemplate;
	}
	
	@Autowired
	RestTemplate restTemplate;
	
	@Override
	public boolean isSellerPhoneExist(String sellerPhone) {
		boolean isExist = false;
		String url = M2C_HOST_URL + "/m2c.operate/sys/user/phoneCheck?name={0}";
		String result = restTemplate.getForObject(url, String.class,sellerPhone);
		JSONObject json = JSONObject.parseObject(result);
		if (json.getInteger("status") == 200) {
			isExist = true;
		}else if(json.getInteger("status") == 400){
			isExist = false;
		}
		return isExist;
	}

}
