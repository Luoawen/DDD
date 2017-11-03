package cn.m2c.scm.port.adapter.restful.app.dealer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.shop.data.bean.ShopBean;
import cn.m2c.scm.application.shop.query.ShopQuery;

/**
 * 店铺首页信息
 *
 * @author ps
 */
@RestController
@RequestMapping("/shop/app")
public class AppShopAgent {
	 private final static Logger LOGGER = LoggerFactory.getLogger(AppShopAgent.class);

	 @Autowired
	 ShopQuery shopQuery;
	 
	@RequestMapping(value = "", method = RequestMethod.GET)
	 public ResponseEntity<MResult> getShopInfo(@RequestParam(value="dealerId",required = true) String dealerId,
			 @RequestParam(value = "userId",required = false) String userId) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	ShopBean appShopInfo = shopQuery.getAppShopInfo(dealerId,userId);
        	result.setContent(appShopInfo);
        	result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("app查询店铺出错:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
