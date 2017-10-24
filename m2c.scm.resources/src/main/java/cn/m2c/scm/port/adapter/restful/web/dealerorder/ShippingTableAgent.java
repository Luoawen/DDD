package cn.m2c.scm.port.adapter.restful.web.dealerorder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MResult;

/**
 * 生成发货单
 * @author lqwen
 *
 */
@RestController
@RequestMapping("/dealerorder")
public class ShippingTableAgent {

	/**
	 * 
	 * @param dealerOrderId
	 * @return
	 */
	@RequestMapping(value = "/shippingtable",method = RequestMethod.GET)
	public ResponseEntity<MResult> shippingTable(
			@RequestParam(value = "dealerId",required = false) String dealerOrderId) {
		
		return null;
		
	}
}
