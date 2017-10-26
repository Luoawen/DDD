package cn.m2c.scm.port.adapter.restful.web.dealerorder;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.dealerorder.data.bean.ShippingOrderBean;
import cn.m2c.scm.application.dealerorder.query.ShippingTableQuery;

/**
 * 生成发货单
 * 
 * @author lqwen
 *
 */
@RestController
@RequestMapping("/dealerorder")
public class ShippingTableAgent {

	private final static Logger LOGGER = LoggerFactory.getLogger(DealerOrderAfterSellAgent.class);

	@Autowired
	ShippingTableQuery shippingTableQuery;

	/**
	 * 
	 * @param dealerOrderId
	 * @return
	 */
	@RequestMapping(value = "/shippingtable", method = RequestMethod.GET)
	public ResponseEntity<MResult> shippingTable(
			@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId) {

		MResult result = new MResult(MCode.V_1);
		try {
			ShippingOrderBean shippingOrder = shippingTableQuery.shippingOrderQuery(dealerOrderId);
			result.setContent(shippingOrder);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("生成发货单出错" + e.getMessage(), e);
			result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result,HttpStatus.OK);

	}
}
