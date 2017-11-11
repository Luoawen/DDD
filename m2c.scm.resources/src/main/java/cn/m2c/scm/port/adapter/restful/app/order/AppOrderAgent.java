package cn.m2c.scm.port.adapter.restful.app.order;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.CommonApplication;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.command.AddSaleAfterCmd;
import cn.m2c.scm.application.order.command.CancelOrderCmd;
import cn.m2c.scm.application.order.command.ConfirmSkuCmd;
import cn.m2c.scm.application.order.command.GetOrderCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.application.order.command.PayOrderCmd;
import cn.m2c.scm.application.order.command.SaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterShipCmd;
import cn.m2c.scm.application.order.data.bean.AfterSellBean;
import cn.m2c.scm.application.order.data.bean.AppOrderBean;
import cn.m2c.scm.application.order.data.bean.AppOrderDtl;
import cn.m2c.scm.application.order.data.representation.OrderNo;
import cn.m2c.scm.application.order.query.AfterSellOrderQuery;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.domain.NegativeException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/***
 * 订单
 * @author fanjc
 */
@RestController
@RequestMapping("/order")
public class AppOrderAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(AppOrderAgent.class);

    @Autowired
    OrderApplication orderApp;
    
    @Autowired
    CommonApplication commonApp;
    
    @Autowired
    SaleAfterOrderApp saleAfterApp;
    
    @Autowired
    AfterSellOrderQuery saleAfterQuery;
    
    @Autowired
    OrderQueryApplication orderQueryApp;
    /**
     * 获取订单号
     * @return
     */
    @RequestMapping(value = "/app/id", method = RequestMethod.GET)
    public ResponseEntity<MResult> getOrderNo() {
    	MResult result = new MResult(MCode.V_1);
        try {
        	String orderId = commonApp.generateOrderNo();
        	
        	if (!StringUtils.isEmpty(orderId)) {
        		OrderNo orderNo = new OrderNo();
            	orderNo.setOrderId(orderId);
        		result.setContent(orderNo);
        		result.setStatus(MCode.V_200);
        	}
        	else {
        		result.setStatus(1); // 需要重新获取
        	}
        } catch (Exception e) {
            LOGGER.error("get order no error, e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 提交订单     *
     * @param goodsId 商品ID
     * @return
     */
    @RequestMapping(value = "/app/add", method = RequestMethod.POST)
    public ResponseEntity<MResult> submitOrder(
            @RequestParam(value = "goodses", required = false) String goodses
            ,@RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "invoice", required = false) String invoice
            ,@RequestParam(value = "addr", required = false) String addr
            ,@RequestParam(value = "noted", required = false) String noted
            ,@RequestParam(value = "coupons", required = false) String coupons
            ,@RequestParam(value = "latitude", required = false) Double latitude
            ,@RequestParam(value = "longitude", required = false) Double longitude
            ,@RequestParam(value = "from", required = false, defaultValue="0") Integer from) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	OrderAddCommand cmd = new OrderAddCommand(orderId, userId, noted, goodses, invoice, addr, coupons,
        			latitude, longitude, from);
            result.setContent(orderApp.submitOrder(cmd));
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	int st = e.getStatus();
        	if (st == MCode.V_100) {
        		result.setStatus(e.getStatus());
        		result.setContent(e.getMessage());
        	}
        	else {
        		result.setStatus(e.getStatus());
        		result.setErrorMessage(e.getMessage());
        	}
        }
        catch (Exception e) {
            LOGGER.error("order add Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 获取订单列表
     * @param userId 当前登录用户ID,app用户id
     * @return
     */
    @RequestMapping(value = "/app/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getOrderListByUser(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "pageIndex", required = false, defaultValue="1") Integer pageIndex
            ,@RequestParam(value = "pageNum", required = false, defaultValue="5") Integer pageNum
            ,@RequestParam(value = "status", required = false) Integer status
            ,@RequestParam(value = "commentStatus", required = false) Integer commentStatus
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	Integer total = orderQueryApp.getAppOrderListTotal(userId, status, commentStatus);
        	if (pageNum == 0)
        		pageNum = 1;
        	List<AppOrderBean> cntList = orderQueryApp.getAppOrderList(userId, status, commentStatus, pageIndex, pageNum);
            result.setPager(total, pageIndex, pageNum);
            result.setContent(cntList);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("app get order list error, e:", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 取消订单
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/app/cancel", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<MResult> cancelOrder(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	CancelOrderCmd cmd = new CancelOrderCmd(orderId, userId);
        	orderApp.cancelOrder(cmd);
        	OrderNo orderNo = new OrderNo();
        	orderNo.setOrderId(orderId);
    		result.setContent(orderNo);
    		result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("order cancel Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 订单支付
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/app/pay__", method = RequestMethod.POST)
    public ResponseEntity<MResult> payOrder(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	PayOrderCmd cmd = new PayOrderCmd(orderId, userId);
        	result.setContent(orderApp.payOrder(cmd));
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("pay order Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 确认收货
     * @param userId
     * @param orderId
     * @param dealerOrderId
     * @param skuId
     * @return
     */
    @RequestMapping(value = "/app/confirm", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<MResult> confirmReceive(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	ConfirmSkuCmd cmd = new ConfirmSkuCmd(orderId, userId, skuId, dealerOrderId);
        	//result.setContent(orderApp.confirmSku(cmd));
        	orderApp.confirmSku(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("pay order Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 申请售后
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/app/aftersale", method = RequestMethod.POST)
    public ResponseEntity<MResult> afterSale(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ,@RequestParam(value = "type", required = false, defaultValue = "-1") int type
            ,@RequestParam(value = "goodsId", required = false) String goodsId
            ,@RequestParam(value = "dealerId", required = false) String dealerId
            ,@RequestParam(value = "backNum", required = false) int backNum
            ,@RequestParam(value = "reason", required = false) String reason
            ,@RequestParam(value = "reasonCode", required = false) int rCode
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	AddSaleAfterCmd cmd = new AddSaleAfterCmd(userId, orderId, dealerOrderId,
        			skuId, saleAfterNo, type, dealerId, goodsId, backNum, reason, rCode);
        	saleAfterApp.createSaleAfterOrder(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result.setStatus(e.getStatus());
        	result.setErrorMessage(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Aplly after sale Exception e:", e);
            result.setStatus(MCode.V_400);
        	result.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 用户退货发货
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/app/aftersale/ship", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<MResult> afterSaleShip(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "expressNo", required = false) String expressNo
            ,@RequestParam(value = "expressCode", required = false) String expressCode
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ,@RequestParam(value = "expressName", required = false) String expressName
            ,@RequestParam(value = "noted", required = false) String noted
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterShipCmd cmd = new SaleAfterShipCmd(userId, saleAfterNo, skuId,
        			expressNo, expressCode, expressName);
        	saleAfterApp.userShipGoods(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Aplly after sale Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 用户确认收货
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/app/aftersale/user-rev", method = {RequestMethod.PUT, RequestMethod.POST})
    public ResponseEntity<MResult> userConfirmRev(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterCmd cmd = new SaleAfterCmd(userId, saleAfterNo, skuId);
        	saleAfterApp.userConfirmRev(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Aplly after sale Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 订单详情
     * @param userId
     * @param orderId
     * @param dealerOrderId
     * @return
     */
    @RequestMapping(value = "/app/orderdtl", method = RequestMethod.GET)
    public ResponseEntity<MResult> getOrderDtl(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	GetOrderCmd cmd = new GetOrderCmd(userId, orderId, dealerOrderId);
        	AppOrderDtl orderBean = orderQueryApp.getOrderDtl(cmd);
        	result.setContent(orderBean);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result.setStatus(e.getStatus());
        	result.setErrorMessage(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Aplly after sale Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/app/del", method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<MResult> delOrder(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	CancelOrderCmd cmd = new CancelOrderCmd(orderId, userId, dealerOrderId);
        	orderApp.delOrder(cmd);
        	//result.setContent(orderBean);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result.setStatus(e.getStatus());
        	result.setErrorMessage(e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("Aplly after sale Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 获取订单列表
     * @param userId 当前登录用户ID,app用户id
     * @param status 0申请退货,1申请换货,2申请退款,3拒绝,4同意(退换货),5客户寄出,6商家收到,7商家寄出,8客户收到,9同意退款, 10确认退款,11交易完成，12交易关闭
     * @return
     */
    @RequestMapping(value = "/app/saleAfter/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getSaleAfterOrderListByUser(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "pageIndex", required = false, defaultValue="1") Integer pageIndex
            ,@RequestParam(value = "pageNum", required = false, defaultValue="5") Integer pageNum
            ,@RequestParam(value = "status", required = false) Integer status
            ) {
    	MPager result = new MPager(MCode.V_1);
    	
        try {
        	
        	if (StringUtils.isEmpty(userId)) {
        		throw new NegativeException(MCode.V_1, "用户id为空！");
        	}
        	
        	Integer total = saleAfterQuery.getAppSaleAfterTotal(userId, status);
        	List<AfterSellBean> cntList = saleAfterQuery.getAppSaleAfterList(userId, status, pageIndex, pageNum);
            result.setPager(total, pageIndex, pageNum);
            result.setContent(cntList);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("app get order list error, e:", e);
            result.setStatus(MCode.V_400);
            result.setErrorMessage(e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
