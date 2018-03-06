package cn.m2c.scm.port.adapter.restful.domain.order;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.data.bean.DealerOrderMoneyInfoBean;
import cn.m2c.scm.application.order.data.bean.RefundEvtBean;
import cn.m2c.scm.application.order.data.representation.DealerOrderMoneyInfoRepresentation;
import cn.m2c.scm.application.order.data.representation.OrderNums;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Autowired
	private SaleAfterOrderApp saleApp;
	
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
			result.setErrorMessage("获取商家用户数据失败");
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
			result.setErrorMessage("获取用户下单数失败");
		}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

	/**
	 * 获取在某个用户下过的订单数
	 */
	@RequestMapping(value="/get/user/before/num", method = RequestMethod.GET)
	public ResponseEntity<MResult> getOrdersByUserId(@RequestParam(value="mainOrderId", required=false) String mainOrderId){
		MResult result = new MResult(MCode.V_1);
		try {
			Integer num = orderQuery.getUserBeforeOrders(mainOrderId);
			result.setContent(num);
			result.setStatus(MCode.V_200);
		}
		catch (Exception e) {
			LOGGER.info("获取用户以前下单数失败,e:" + e.getMessage());
			result.setStatus(MCode.V_400);
			result.setErrorMessage("获取用户以前下单数失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
    
    /**
     * 获取多个商家订单的金额列表
     * @param dealerOrderIds
     * @param userId
     * @return
     */
    @RequestMapping(value="/get/dealer/orders",method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerOrders(
    		@RequestParam(value="dealerOrderIds", required=false)List dealerOrderIds,
    		@RequestParam(value="userId", required=false)String userId){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		List<DealerOrderMoneyInfoBean> orderMoneyInfo = orderQuery.getDealerOrders(dealerOrderIds);
    		if(null != orderMoneyInfo && orderMoneyInfo.size()>0) {
        		Map<String, Object> map = new HashMap<String, Object>();
    			for(DealerOrderMoneyInfoBean bean : orderMoneyInfo) {
    				DealerOrderMoneyInfoRepresentation representation = new DealerOrderMoneyInfoRepresentation(bean);
    				map.put(bean.getDealerOrderId(), representation);
    			}
    			result.setContent(map);	
    		}
    		result.setStatus(MCode.V_200);
    	}catch (NegativeException ne) {
			LOGGER.error("getDealerOrders NegativeException e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
    	}catch (Exception e) {
    		LOGGER.error("获取多个商家订单的金额列表失败,e:" + e.getMessage());
			result.setStatus(MCode.V_400);
			result.setContent("获取多个商家订单金额列表失败");
    	}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 获取多个商家订单的金额列表
     * @param afterSellId
     * @param orderRefundId 退款单号
     * @param refundTime 退款时间 long型
     * @param userId
     * @return
     */
    @RequestMapping(value="/after/return/ok",method = RequestMethod.PUT)
    public ResponseEntity<MResult> afterReturnMoneyOk(
    		@RequestParam(value="afterSellId", required=false)String afterSellId,
    		@RequestParam(value="orderRefundId", required=false)String orderRefundId,
    		@RequestParam(value="refundTime", required=false)Long refundTime,
    		@RequestParam(value="userId", required=false)String userId){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		if (StringUtils.isEmpty(afterSellId) || StringUtils.isEmpty(orderRefundId)
    				|| refundTime == null) {
    			throw new NegativeException(MCode.V_400, "参数不正确");
    		}
    		RefundEvtBean bean = new RefundEvtBean();
    		bean.setAfterSellOrderId(afterSellId);
    		bean.setOrderRefundId(orderRefundId);
    		bean.setRefundTime(refundTime);
    		saleApp.refundSuccess(bean);
    		result.setStatus(MCode.V_200);
    		bean = null;
    	} catch (NegativeException ne) {
			LOGGER.error("售后退款成功调用失败， e:", ne);
            result = new MResult(ne.getStatus(), ne.getMessage());
    	} catch (Exception e) {
    		LOGGER.error("售后退款成功调用失败,e:" + e.getMessage());
			result.setStatus(MCode.V_400);
			result.setContent("售后退款成功调用失败");
    	}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    public static void main(String args[]) {
    }
}
