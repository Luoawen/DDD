package cn.m2c.scm.port.adapter.restful.domain.order;

import java.util.List;

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
import cn.m2c.scm.application.order.data.representation.OrderNums;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.domain.NegativeException;

/***
 * 订单中的对外服务
 * @author 89776
 * created date 2017年12月27日
 * copyrighted@m2c
 */
@RestController
@RequestMapping("/domain/order")
public class OrderDomainAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderDomainAgent.class);
	
	@Autowired
	private OrderQuery orderQuery;
	
    /**
     * 获取在某个商家下单成功过的用户id列表
     */
    @RequestMapping(value="/get/users/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> getOrderByNo(@PathVariable("dealerId") String dealerId
    		,@RequestParam(value="userId", required=false) String userId){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		List<String> users = orderQuery.getDealerUsersHasPurchase(dealerId);
    		result.setContent(users);
    		result.setStatus(MCode.V_200);
		} 
    	catch (NegativeException e) {
    		result.setStatus(e.getStatus());
			result.setContent(e.getMessage());
    	}
    	catch (Exception e) {
			LOGGER.info("获取商家用户数据失败,e:" + e.getMessage());
			result.setStatus(MCode.V_400);
			result.setContent("获取商家用户数据失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 获取在某个用户下过的订单数
     */
    @RequestMapping(value="/get/num/{userId}", method = RequestMethod.GET)
    public ResponseEntity<MResult> getOrdersByUserId(@PathVariable("userId") String userId
    		,@RequestParam(value="hasPayed", required=false, defaultValue="0") int hasPayed){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		OrderNums nums = orderQuery.getUserOrders(userId, hasPayed);
    		result.setContent(nums);
    		result.setStatus(MCode.V_200);
		} 
    	catch (Exception e) {
			LOGGER.info("获取用户下单数失败,e:" + e.getMessage());
			result.setStatus(MCode.V_400);
			result.setContent("获取用户下单数失败");
		}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    public static void main(String args[]) {
    }
}
