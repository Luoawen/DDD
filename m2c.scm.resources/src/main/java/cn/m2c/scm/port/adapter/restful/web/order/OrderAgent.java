package cn.m2c.scm.port.adapter.restful.web.order;

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
import cn.m2c.scm.application.order.DealerOrderApplication;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.command.AproveSaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterCmd;
import cn.m2c.scm.application.order.command.SaleAfterShipCmd;
import cn.m2c.scm.application.order.command.SendOrderCommand;
import cn.m2c.scm.application.order.data.bean.DealerOrderBean;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.MainOrderBean;
import cn.m2c.scm.application.order.data.bean.OrderExpressBean;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.domain.NegativeException;


@RestController
@RequestMapping("/order")
public class OrderAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OrderAgent.class);
	
	@Autowired
	OrderApplication orderapplication;
	
	@Autowired
	OrderQuery orderQuery;
	
	@Autowired
	SaleAfterOrderApp saleAfterApp;
	
	@Autowired
    DealerOrderApplication dealerOrderApplication;
    
    
    @Autowired
    OrderQueryApplication orderAppQuery;
	/**
	 * 查询订单列表
	 * @param orderStatus 订单状态
	 * @param afterSellStatus 售后状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param condition 搜索条件(goodsName,orderId,payNo,revPhone,dealerName,dealerId)
	 * @param payWay 支付方式
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	@RequestMapping(value = "/manager/orderList",method = RequestMethod.GET)
	public ResponseEntity<MPager> getOrderList(
			@RequestParam(value = "orderStatus",required = false)Integer orderStatus,
			@RequestParam(value = "afterSellStatus",required = false)Integer afterSellStatus,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "payWay",required = false)Integer payWay,
			@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows
			) {
		MPager result = new MPager(MCode.V_1);
		try {
			Integer total = orderQuery.mainOrderQueryTotal(orderStatus, afterSellStatus, startTime, endTime, condition, payWay);
			List<MainOrderBean> mainOrderList = orderQuery.mainOrderListQuery(orderStatus, afterSellStatus, startTime, endTime, condition, payWay, pageNum, rows);
			
			result.setPager(total, pageNum, rows);
			result.setContent(mainOrderList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("获取订单列表出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MPager>(result,HttpStatus.OK);
	}

	/**
	 * 订货单详情
	 * @param dealerOrderId
	 * @return
	 */
	@RequestMapping(value = "/manager/orderdetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getOrderDetail(@RequestParam(value = "dealerOrderId",required = false)String dealerOrderId){
		MResult result = new MResult(MCode.V_1);
		try {
			DealerOrderDetailBean dealerDetail = orderQuery.dealerOrderDetailQuery(dealerOrderId);
			if (null != dealerDetail) {
				result.setContent(dealerDetail);
				result.setStatus(MCode.V_200);
			}
		} catch (Exception e) {
			LOGGER.error("获取商家订单出错" + e.getMessage(), e);
            result = new MPager(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result,HttpStatus.OK);
	}
	/***
	 * 商家同意售后申请
	 * @param userId
	 * @param saleAfterNo
	 * @param dealerId
	 * @return
	 */
	@RequestMapping(value="/dealer/agree-apply-sale", method=RequestMethod.PUT)
	public ResponseEntity<MResult> agreeApplySaleAfter(@RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ,@RequestParam(value = "dealerId", required = false) String dealerId) {
		MResult result = new MResult(MCode.V_1);
		try {
			AproveSaleAfterCmd cmd = new AproveSaleAfterCmd(userId, saleAfterNo, dealerId);
			saleAfterApp.agreeApply(cmd);
			result.setStatus(MCode.V_200);
		}
		catch (NegativeException e) {
			result.setStatus(e.getStatus());
			result.setErrorMessage(e.getMessage());
		}
		catch (Exception e) {
			LOGGER.error("同意售后申请出错，err:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, "服务器开小差了");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
     * 商家换货发货
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/aftersale/dealer/ship", method = RequestMethod.PUT)
    public ResponseEntity<MResult> afterSaleShip(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "expressNo", required = false) String expressNo
            ,@RequestParam(value = "expressCode", required = false) String expressCode
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ,@RequestParam(value = "expressName", required = false) String expressName
            ,@RequestParam(value = "expressPerson", required = false) String expressPerson
            ,@RequestParam(value = "phone", required = false) String expressPhone
            ,@RequestParam(value = "expressWay", required = false) int expressWay
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterShipCmd cmd = new SaleAfterShipCmd(userId, saleAfterNo, skuId,
        			expressNo, expressCode, expressName, expressPerson, expressPhone, expressWay);
        	saleAfterApp.userShipGoods(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("dealer ship Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 商家确认收货
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/aftersale/dealer-rev", method = RequestMethod.PUT)
    public ResponseEntity<MResult> userConfirmRev(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterCmd cmd = new SaleAfterCmd(userId, saleAfterNo, skuId);
        	saleAfterApp.dealerConfirmRev(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("dealer confirm receiver Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 商家同意退款
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/aftersale/dealer/agree-rt-money", method = RequestMethod.PUT)
    public ResponseEntity<MResult> agreeBackMoney(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterCmd cmd = new SaleAfterCmd(userId, saleAfterNo, skuId);
        	saleAfterApp.agreeBackMoney(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("dealer confirm receiver Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 商家确认退款
     * @param userId
     * @param orderId
     * @return
     */
    @RequestMapping(value = "/aftersale/dealer/confirm-rt-money", method = RequestMethod.PUT)
    public ResponseEntity<MResult> confirmRtMoney(
            @RequestParam(value = "userId", required = false) String userId
            ,@RequestParam(value = "skuId", required = false) String skuId
            ,@RequestParam(value = "saleAfterNo", required = false) String saleAfterNo
            ) {
    	MResult result = new MResult(MCode.V_1);
        try {
        	SaleAfterCmd cmd = new SaleAfterCmd(userId, saleAfterNo, skuId);
        	saleAfterApp.approveReturnMoney(cmd);
            result.setStatus(MCode.V_200);
        } 
        catch (NegativeException e) {
        	result = new MResult(e.getStatus(), e.getMessage());
        }
        catch (Exception e) {
            LOGGER.error("dealer confirm receiver Exception e:", e);
            result = new MResult(MCode.V_400, e.getMessage());
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
    
    /**
     * 订单发货详情
     */
    @RequestMapping(value="/dealer/sendOrderDetail", method = RequestMethod.GET)
    public ResponseEntity<MResult> sendOrderDetail(
    		@RequestParam(value = "dealerOrderId", required = false) String dealerOrderId){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		//1查询商家订单
    		DealerOrderBean dealerOrder = orderAppQuery.getDealerOrder(dealerOrderId);
    		result.setContent(dealerOrder);
    		result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.info("查询发货详情失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 订单发货
     * 1根据商家订单id更新物流信息
      *2更新状态为已发货
      *3发出已发货事件（先不做，后期需要再做）
     */
    @RequestMapping(value="/dealer/sendOrder", method = RequestMethod.POST)
    public ResponseEntity<MResult> sendOrder(
    		@RequestParam(value = "dealerOrderId", required = true) String dealerOrderId,
    		@RequestParam(value = "expressNo", required = false) String expressNo,
    		@RequestParam(value = "expressName", required = false) String expressName,
    		@RequestParam(value = "expressNote", required = false) String expressNote,
    		@RequestParam(value = "expressPerson", required = false) String expressPerson,
    		@RequestParam(value = "expressPhone", required = false) String expressPhone,
    		@RequestParam(value = "expressWay", required = true) Integer expressWay
    		,@RequestParam(value = "expressCode", required = true) String expressCode
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		SendOrderCommand command = new SendOrderCommand(dealerOrderId, expressNo, expressName, expressPerson, expressPhone, 
    				expressWay, expressNote, expressCode);
    		dealerOrderApplication.updateExpress(command);
    		result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			result.setStatus(MCode.V_400);
			LOGGER.info("发货失败");
		}
    	
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 商家管理平台订单发货物流详情
     * 
     */
    @RequestMapping(value="/manage/sendOrderDetail", method = RequestMethod.GET)
    public ResponseEntity<MResult> manageSendOrderDetail(
    		@RequestParam(value = "dealerOrderId", required = true) String dealerOrderId
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		DealerOrderBean dealerOrder = orderAppQuery.getDealerOrder(dealerOrderId);
    		result.setContent(dealerOrder);
    		result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.info("查询发货详情失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 商家平台订单发货物流详情
     * 
     */
    @RequestMapping(value="/dealer/sendDealerOrderDetail", method = RequestMethod.GET)
    public ResponseEntity<MResult> sendDealerOrderDetail(
    		@RequestParam(value = "dealerOrderId", required = true) String dealerOrderId
    		){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		DealerOrderBean dealerOrder = orderAppQuery.getDealerOrder(dealerOrderId);
    		result.setContent(dealerOrder);
    		result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.info("查询商家平台订单详情失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
    
    /**
     * 查询所有物流公司信息
     */
    @RequestMapping(value="/dealer/express", method = RequestMethod.GET)
    public ResponseEntity<MResult> getAllExpress(){
    	MResult result = new MResult(MCode.V_1);
    	try {
    		 List<OrderExpressBean> allExpress = orderAppQuery.getAllExpress();
    		result.setContent(allExpress);
    		result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.info("查询物流公司失败");
		}
    	return new ResponseEntity<MResult>(result,HttpStatus.OK);
    }
}
