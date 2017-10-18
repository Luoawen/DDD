package cn.m2c.scm.port.adapter.restful.web.order;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.order.DealerOrderApplication;
import cn.m2c.scm.application.order.data.representation.DealerOrderBean;
import cn.m2c.scm.application.order.query.OrderQueryApplication;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 订单
 * @author fanjc
 */
@RestController
@RequestMapping("/order")
public class WebOrderAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(WebOrderAgent.class);

    @Autowired
    OrderQueryApplication orderApp;
    
    
    @Autowired
    DealerOrderApplication dealerOrderApplication;
    
    
    @Autowired
    OrderQueryApplication orderQuery;
    
    /**
     * 获取订单列表(管理平台)
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping(value = "/order/plateform/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> submitOrder(
            @RequestParam(value = "goodses", required = false) String goodses
            ,@RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "invoice", required = false) String invoice
            ,@RequestParam(value = "pageIndex", required = false) int pageIndex
            ,@RequestParam(value = "pageNum", required = false) int pageNum
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
            Map map = new HashMap<>();
            map.put("goodsName", "跑步机");
            map.put("goodsImageUrl", "http://dl.m2c2017.com/3pics/20170822/W8bq135021.jpg");
            map.put("goodsPrice", 249000);
            result.setContent(map);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("goods Detail Exception e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 获取订单列表
     * 通过商家获取订单列表
     * @return
     */
    @RequestMapping(value = "/list/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<MPager> getOrderListByDealer(
            @PathVariable(value = "dealerId") String userId
            ,@RequestParam(value = "pageIndex", required = false) int pageIndex
            ,@RequestParam(value = "pageNum", required = false) int pageNum
            ,@RequestParam(value = "status", required = false) int status
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
            Map<String, Object> map = new HashMap<>();
            
            List<Map<String, Object>> orderList = new ArrayList<>();
            orderList.add(map);
            
            result.setContent(orderList);
            result.setPager(1, 1, 2);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("get order list error, e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 获取订单列表
     * 通过商家获取订单操作日志列表
     * @return
     */
    @RequestMapping(value = "/logs/{dealerId}", method = RequestMethod.GET)
    public ResponseEntity<MPager> getOptLogsByDealer(
            @PathVariable(value = "dealerId") String userId
            ,@RequestParam(value = "pageIndex", required = false) int pageIndex
            ,@RequestParam(value = "pageNum", required = false) int pageNum
            ,@RequestParam(value = "status", required = false) int status
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
            Map<String, Object> map = new HashMap<>();
            
            List<Map<String, Object>> orderList = new ArrayList<>();
            orderList.add(map);
            
            result.setContent(orderList);
            result.setPager(1, 1, 2);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("get order list error, e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 订单发货详情
     */
    @RequestMapping(value="/dealer/sendOrderDetail", method = RequestMethod.GET)
    public ResponseEntity<MResult> sendOrder(
    		@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId){
    	MResult result = new MResult(MCode.V_1);
    	//1查询商家订单
    	DealerOrderBean dealerOrder = orderQuery.getDealerOrder(dealerOrderId);
    	//2根据商家订单查询订单详情
    	
    	//3封装成返回对象返回
    	
    	
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
}
