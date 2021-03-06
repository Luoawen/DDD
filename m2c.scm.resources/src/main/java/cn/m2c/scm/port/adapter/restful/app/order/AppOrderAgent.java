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
import cn.m2c.scm.application.order.command.ExpressInfoBean;
import cn.m2c.scm.application.order.command.GetOrderCmd;
import cn.m2c.scm.application.order.command.OrderAddCommand;
import cn.m2c.scm.application.order.command.PayOrderCmd;
import cn.m2c.scm.application.order.command.SaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterShipCmd;
import cn.m2c.scm.application.order.data.bean.AfterSellApplyReason;
import cn.m2c.scm.application.order.data.bean.AfterSellBean;
import cn.m2c.scm.application.order.data.bean.AfterSellFlowBean;
import cn.m2c.scm.application.order.data.bean.AppOrderBean;
import cn.m2c.scm.application.order.data.bean.AppOrderDtl;
import cn.m2c.scm.application.order.data.bean.OrderExpressBean;
import cn.m2c.scm.application.order.data.bean.UserOrderStatic;
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
     * 提交订单
     * @param goodses 商品sku及数量等json串
     * @param userId 提交用户
     * @param couponUserId 
     * @param orderId 订单号
     * @param invoice 发票信息json串
     * @param addr 地址信息json串
     * @param noted 备注
     * @param coupons 优惠券 暂不用
     * @param latitude 纬度
     * @param longitude 经度
     * @param from 来源 0 购物车，1直接购买
     * @param appVersion app版本号
     * @param os 系统 android or ios
     * @param osVersion os版本号
     * @param sn 机器sn号
     * @return
     */
    @RequestMapping(value = "/app/add", method = RequestMethod.POST)
    public ResponseEntity<MResult> submitOrder(
            @RequestParam(value = "goodses", required = false) String goodses
            ,@RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "couponUserId", required = false) String couponUserId
            ,@RequestParam(value = "orderId", required = false) String orderId
            ,@RequestParam(value = "invoice", required = false) String invoice
            ,@RequestParam(value = "addr", required = false) String addr
            ,@RequestParam(value = "noted", required = false) String noted
            ,@RequestParam(value = "coupons", required = false) String coupons
            ,@RequestParam(value = "latitude", required = false) Double latitude
            ,@RequestParam(value = "longitude", required = false) Double longitude
            ,@RequestParam(value = "from", required = false, defaultValue="0") Integer from
            ,@RequestParam(value = "appVersion", required = false) String appVersion
            ,@RequestParam(value = "os", required = false) String os
            ,@RequestParam(value = "osVersion", required = false) String osVersion
            ,@RequestParam(value = "sn", required = false) String sn
    		) {
    	LOGGER.info("goodses:"+goodses);
    	LOGGER.info("couponUserId:"+couponUserId);
    	MResult result = new MResult(MCode.V_1);
        try {
        	OrderAddCommand cmd = new OrderAddCommand(orderId, userId,couponUserId, noted, goodses, invoice, addr, coupons,
        			latitude, longitude, from, appVersion, os, osVersion, sn);
            result.setContent(orderApp.submitOrder(cmd));
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	int st = e.getStatus();
        	if (st == MCode.V_100 || st == MCode.V_105) {
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
     * @param userId
     * @param pageIndex 页码
     * @param pageNum 页大小
     * @param status 订单状态
     * @param commentStatus 评论状态
     * @param keyword 查询关键字
     * @return
     */
    @RequestMapping(value = "/app/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getOrderListByUser(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "pageIndex", required = false, defaultValue="1") Integer pageIndex
            ,@RequestParam(value = "pageNum", required = false, defaultValue="5") Integer pageNum
            ,@RequestParam(value = "status", required = false) Integer status
            ,@RequestParam(value = "commentStatus", required = false) Integer commentStatus
            ,@RequestParam(value = "keyword", required = false) String keyword
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	Integer total = orderQueryApp.getAppOrderListTotal(userId, status, commentStatus, keyword);
        	if (pageNum == 0)
        		pageNum = 1;
        	List<AppOrderBean> cntList = orderQueryApp.getAppOrderList(userId, status, commentStatus, pageIndex, pageNum, keyword);
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
            ,@RequestParam(value = "sortNo", required = false, defaultValue="0") int sortNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	ConfirmSkuCmd cmd = new ConfirmSkuCmd(orderId, userId, skuId, dealerOrderId, sortNo);
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
     * @param dealerOrderId
     * @param skuId
     * @param saleAfterNo 售后单号
     * @param type 售后类型
     * @param goodsId 商品id
     * @param dealerId 商家id
     * @param backNum 售后数量
     * @param reason 售后原因
     * @param reasonCode 原因编码 
     * @param sortNo 新数据这个值必须大于0（2017-12-09之后）
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
            ,@RequestParam(value = "sortNo", required = false, defaultValue = "0") int sortNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	AddSaleAfterCmd cmd = new AddSaleAfterCmd(userId, orderId, dealerOrderId,
        			skuId, saleAfterNo, type, dealerId, goodsId, backNum, reason, rCode
        			,sortNo);
        	saleAfterApp.createSaleAfterOrder(cmd);
        	//saleAfterApp.afterSaleOrder(cmd);
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
     * @param expressNo 快递单号
     * @param expressCode 快递公司编码
     * @param skuId 发货商品
     * @param saleAfterNo 售后单号
     * @param expressName 快递公司名称
     * @param noted 备注
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
        			expressNo, expressName);
        	cmd.setExpressCode(expressCode);
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
     * @param saleAfterNo
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
            //检查本单的完成状态
            saleAfterApp.scanOrderDtlUpdated(userId, saleAfterNo);			
        } 
        catch (NegativeException e) {
        	LOGGER.error("After sale rev Exception e:", e);
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
     * @param dealerOrderId 商家订单号
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
            if (orderBean == null)
            result.setErrorMessage("您查的订单不存在或已被删除！");
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
     * 获取售后记录列表
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
    
    /**
     * 获取可售后的订单列表
     * @param userId 当前登录用户ID,app用户id
     * @return
     */
    @RequestMapping(value = "/app/may/saleAfter/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getMaySaleAfterListByUser(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "pageIndex", required = false, defaultValue="1") Integer pageIndex
            ,@RequestParam(value = "pageNum", required = false, defaultValue="5") Integer pageNum
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	Integer total = orderQueryApp.getMaySaleAfterListTotal(userId);
        	if (pageNum == 0)
        		pageNum = 1;
        	List<AppOrderBean> cntList = orderQueryApp.getMaySaleAfterList(userId, pageIndex, pageNum);
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
     * 获取物流信息
     * @param 
     * @return
     */
    @RequestMapping(value = "/app/getExpressInfo", method = RequestMethod.GET)
    public ResponseEntity<MPager> getExpressInfo(
            @RequestParam(value = "com",defaultValue="") String com
            ,@RequestParam(value = "nu", defaultValue="") String nu
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	if(com==null||"".equals(com)){
        		result.setContent("物流公司编码不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	if(nu==null||"".equals(nu)){
        		result.setContent("物流号不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	String rtResult = orderQueryApp.getExpressJson(com,nu);
        	result.setContent(rtResult);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询物流列表出错", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/app/dealer/statics", method = RequestMethod.GET)
    public ResponseEntity<MResult> getUserOrderStatics(
            @RequestParam(value = "userId",defaultValue="") String userId
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	if(StringUtils.isEmpty(userId)){
        		result.setContent("用户id为空!");
        		return new ResponseEntity<MResult>(result, HttpStatus.OK);
        	}
        	
        	UserOrderStatic data = orderQueryApp.getUserOrderStatics(userId);
        	result.setContent(data);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询物流列表出错", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 售后申请理由
     * @param applyStatus
     * @return
     */
    @RequestMapping(value = "/app/applyReason",method = RequestMethod.GET)
    public ResponseEntity<MResult> getAfterSellApplyReason(@RequestParam(value = "applyStatus",required = true) Integer applyStatus){
    	MResult result = new MResult(MCode.V_1);
    	if(null == applyStatus) {
    		LOGGER.error("售后申请状态为空");
			result = new MResult(MCode.V_400);
    	}
    	try {
			List<AfterSellApplyReason> reasonList = saleAfterQuery.getApplyReason(applyStatus);
			result.setContent(reasonList);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("查询售后申请理由出错",e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
    	return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    
    /**
     * 注册订阅物流单号
     * @param 
     * @return
     */
    @RequestMapping(value = "/app/registExpress", method = RequestMethod.POST)
    public ResponseEntity<MPager> registExpress(
            @RequestParam(value = "com",defaultValue="") String com
            ,@RequestParam(value = "nu", defaultValue="") String nu
            ,@RequestParam(value = "shipType",required = false) Integer shipType
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	if(com==null||"".equals(com)){
        		result.setContent("物流公司编码不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	if(nu==null||"".equals(nu)){
        		result.setContent("物流号不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	orderApp.registExpress(com,nu,shipType);
        	result.setContent("注册快递100物流单号成功");
            result.setStatus(MCode.V_200);
        }catch (NegativeException ne) {
            LOGGER.error("注册快递100物流单号出错", ne);
            result = new MPager(ne.getStatus(),ne.getMessage());
        }catch (Exception e) {
            LOGGER.error("注册快递100物流单号出错", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    
    /**
     * 查询物流，订阅模式new 接口
     * @param 
     * @return
     */
    @RequestMapping(value = "/app/queryExpress", method = RequestMethod.GET)
    public ResponseEntity<MPager> queryExpress(
            @RequestParam(value = "com",defaultValue="") String com
            ,@RequestParam(value = "nu", defaultValue="") String nu
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	if(com==null||"".equals(com)){
        		result.setContent("物流公司编码不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	if(nu==null||"".equals(nu)){
        		result.setContent("物流号不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	 ExpressInfoBean queryExpress = orderQueryApp.queryExpress(com,nu);
        	result.setContent(queryExpress);
            result.setStatus(MCode.V_200);
        }catch (NegativeException ne) {
            LOGGER.error("查询物流单号出错", ne);
            result = new MPager(ne.getStatus(),ne.getMessage());
        }catch (Exception e) {
            LOGGER.error("查询物流单号出错", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
    /**
     * 
     * @param userId 当前登录用户ID,app用户id
     * @param pageIndex 
     * @param pageNum
     * @param status 0申请退货,1申请换货,2申请退款,3拒绝,4同意(退换货),5客户寄出,6商家收到,7商家寄出,8客户收到,9同意退款, 10确认退款,11交易完成，12交易关闭
     * @return
     */
    @RequestMapping(value = "/app/dealerSaleAfter/list", method = RequestMethod.GET)
    public ResponseEntity<MPager> getSaleAfterDealerOrderListByDealerOrderId(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "pageIndex", required = false, defaultValue="1") Integer pageIndex
            ,@RequestParam(value = "pageNum", required = false, defaultValue="5") Integer pageNum
            ,@RequestParam(value = "status", required = false) Integer status
            ,@RequestParam(value = "dealerOrderId", required = false, defaultValue="") String dealerOrderId
            ) {
    	MPager result = new MPager(MCode.V_1);
    	
        try {
        	
        	if (StringUtils.isEmpty(userId)) {
        		throw new NegativeException(MCode.V_1, "用户id为空！");
        	}
        	
        	Integer total = saleAfterQuery.getAppDealerSaleAfterTotal(userId, status,dealerOrderId);
        	List<AfterSellBean> cntList = saleAfterQuery.getAppDealerSaleAfterList(userId, status, dealerOrderId ,pageIndex, pageNum);
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
    
    /**
     * 查询所有物流公司信息
     */
    @RequestMapping(value= {"/app/dealer/express"}, method = RequestMethod.GET)
    public ResponseEntity<MResult> getAllExpress(){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		 List<OrderExpressBean> allExpress = orderQueryApp.getAllExpress();
    		result.setContent(allExpress);
    		result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.info("查询物流公司失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    
    /**
     * 售后单号查询售后流程记录
     * @param saleAfterNo
     * @return
     */
    @RequestMapping(value = "app/after/sell/flow",method = RequestMethod.GET)
    public ResponseEntity<MResult> afterSellFlow(@RequestParam(value = "saleAfterNo",required = false)String saleAfterNo){
    	MResult result = new MResult(MCode.V_1);
    	
    	try {
			List<AfterSellFlowBean> afterSellFlow = saleAfterQuery.getAfterSellFlow(saleAfterNo);
			result.setContent(afterSellFlow);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("查询售后流程记录出错",e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
    	
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 获取物流信息
     * @param 
     * @return
     */
    @RequestMapping(value = "app/ship/expressInfo", method = RequestMethod.GET)
    public ResponseEntity<MPager> getExpressInfos(
            @RequestParam(value = "com",defaultValue="") String com
            ,@RequestParam(value = "nu", defaultValue="") String nu
            ) {
    	MPager result = new MPager(MCode.V_1);
        try {
        	if(StringUtils.isEmpty(com)){
        		result.setContent("物流公司编码不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	if(StringUtils.isEmpty(nu)){
        		result.setContent("物流号不能为空");
        		 return new ResponseEntity<MPager>(result, HttpStatus.OK);
        	}
        	 ExpressInfoBean queryExpress = orderQueryApp.queryExpress(com, nu);
        	result.setContent(queryExpress);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("查询物流列表出错", e);
            result = new MPager(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
