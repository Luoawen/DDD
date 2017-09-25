package cn.m2c.scm.port.adapter.restful.order.order;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.DateUtils;
import cn.m2c.common.EncryptUtils;
import cn.m2c.common.IDGenerator;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.application.order.comment.query.SpringJdbcCommentQuery;
import cn.m2c.scm.application.order.fsales.query.SpringJdbcAfterSalesQuery;
import cn.m2c.scm.application.order.order.OrderApplication;
import cn.m2c.scm.application.order.order.command.CancelOrderCommand;
import cn.m2c.scm.application.order.order.command.CommitOrderCommand;
import cn.m2c.scm.application.order.order.command.CommitWaybillCommand;
import cn.m2c.scm.application.order.order.command.ConfirmReceiptCommand;
import cn.m2c.scm.application.order.order.command.DelForAppCommand;
import cn.m2c.scm.application.order.order.command.ModReceiverAddrCommand;
import cn.m2c.scm.application.order.order.command.ModStatusCommand;
import cn.m2c.scm.application.order.order.query.SpringJdbcOrderQuery;
import cn.m2c.scm.application.order.token.TokenApplication;
import cn.m2c.scm.domain.IDPrefix;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.AfterSalesStatus;
import cn.m2c.scm.port.adapter.restful.order.util.ExpressUtil;
import cn.m2c.scm.port.adapter.restful.order.util.LogisticsRet;

import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 
 * @ClassName: OrderAgent
 * @Description: 订单
 * @author moyj
 * @date 2017年4月18日 下午3:57:39
 *
 */
@RestController
@RequestMapping(value = "/order")
public class OrderAgent {
	private static final Logger logger = LoggerFactory.getLogger(OrderAgent.class);
	@Autowired
	private SpringJdbcAfterSalesQuery springJdbcAfterSalesQuery;
	@Autowired
	private OrderApplication orderApplication;
	@Autowired
	private SpringJdbcOrderQuery springJdbcOrderQuery;
	@Autowired
	private SpringJdbcCommentQuery springJdbcCommentQuery;
	@Autowired
	private TokenApplication tokenApplication;
	/**
	 * 提交订单
	 * 
	 * @param orderId			//订单编号
	 * @param buyerId			//买家编号
	 * @param buyerName			//买家名称
	 * @param buyerMessage		//买家留言
	 * @param specId			//货品规格编号
	 * @param goodsNum			//货品数量
	 * @param mresId			//媒体资源编号
	 * @param receiverId		//收货地址编号
	 * @return
	 */
	@RequestMapping(value = "/commit", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> commit(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "dispatchWay") String dispatchWay,
			@RequestParam(value = "invoiceType") Integer invoiceType,
			@RequestParam(value = "invoiceTitle",required=false) String invoiceTitle,
			@RequestParam(value = "taxIdenNum",required=false) String taxIdenNum,
			@RequestParam(value = "buyerId") String buyerId,						
			@RequestParam(value = "buyerName",required=false) String buyerName,
			@RequestParam(value = "buyerMessage",required=false) String buyerMessage,	
			@RequestParam(value = "goodsId") String goodsId,	
			@RequestParam(value = "propertyId") String propertyId,	
			@RequestParam(value = "propertyList") String propertyList,	
			@RequestParam(value = "goodsNum") Integer goodsNum,						
			@RequestParam(value = "mresId",required=false) String mresId,	
			@RequestParam(value = "receiverId") String receiverId,				
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			) {
		MResult result = new MResult(MCode.V_1);
		try {
			//
			List<Map<String,Object>> proList = null;
			try{
				if(propertyList != null && propertyList.length() > 500){
					throw new Exception();
				}
				proList = new Gson().fromJson(propertyList, new TypeToken<List<Map<String,Object>>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
			//提交订单
			CommitOrderCommand command = new CommitOrderCommand(orderId,dispatchWay,invoiceType,invoiceTitle,taxIdenNum,buyerId,
					buyerName,buyerMessage,goodsId,propertyId,proList,goodsNum,mresId,receiverId);			
			String retOrderId = orderApplication.commitOrder(command);
			Map<String, String> retMap = new HashMap<String, String>();
			retMap.put("orderId", retOrderId);	
			String bussData = new ObjectSerializer(true).serialize(retMap);
			if(isEncry){
				bussData = EncryptUtils.encrypt(bussData, token);
 				result.setContent(bussData);
 			}else{
 				result.setContent(retMap);
 			}	
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 确认收货
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/confirm/receipt", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> confirmReceipt(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId								
			){
		MResult result = new MResult(MCode.V_1);
		
 		try {	
 			ConfirmReceiptCommand command = new ConfirmReceiptCommand(orderId);
			orderApplication.confirmReceipt(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order confirm receipt Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order confirm receipt Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 取消订单
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/cancel", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> cancelOrder(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId								
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			CancelOrderCommand command = new CancelOrderCommand(orderId);
			orderApplication.cancelOrder(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 删除订单
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/app/delete", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> delOrderForApp(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId								
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			DelForAppCommand command = new DelForAppCommand(orderId);
			orderApplication.delForApp(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 提交运单
	 * 
	 * @param orderId			//订单编号
	 * @param waybillNo			//运单号
	 * @return
	 */
	@RequestMapping(value = "/commit/waybill", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> commitWaybill(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "waybillNo") String waybillNo,
			@RequestParam(value = "expCompanyCode") String expCompanyCode,
			@RequestParam(value = "expCompanyName") String expCompanyName,
			@RequestParam(value = "loginUserId",required=false) String loginUserId,
			@RequestParam(value = "loginUserName",required=false) String loginUserName
			){
		MResult result = new MResult(MCode.V_1);			
 		try {	
 			if(loginUserId == null || loginUserId.length() == 0){
 				loginUserName = tokenApplication.getUserId(token);
 			}
 			if(loginUserName == null || loginUserName.length() == 0){
 				loginUserName = tokenApplication.getUserName(token);
 			}
 			CommitWaybillCommand command = new CommitWaybillCommand(orderId,waybillNo,expCompanyCode,expCompanyName,loginUserId,loginUserName);	
			orderApplication.commitWaybill(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit waybill Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order commit waybill  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 修改收货地址
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/mod/receiver/addr", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> modReceiverAddr(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "provinceCode",required=false) String provinceCode,
			@RequestParam(value = "provinceName",required=false) String provinceName,
			@RequestParam(value = "cityCode",required=false) String cityCode,
			@RequestParam(value = "cityName",required=false) String cityName,
			@RequestParam(value = "areaCode",required=false) String areaCode,
			@RequestParam(value = "areaName",required=false) String areaName,
			@RequestParam(value = "receiverAddr",required=false) String receiverAddr
			){
		MResult result = new MResult(MCode.V_1);
 		try {	
 			ModReceiverAddrCommand command = new ModReceiverAddrCommand(orderId,provinceCode,provinceName,
 					cityCode,cityName,areaCode,areaName,receiverAddr);
			orderApplication.modReceiverAddr(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled bartered Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled bartered  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 修改状态
	 * 
	 * @param orderId			//订单编号
	 * @param orderStatus		//订单状态 1正常 2取消
	 * @param payStatus			//支付状态 1待支付 2已支付
	 * @param logisticsStatus	//物流状态 1待发货 2待收货 3 已收货
	 * @param saledStatus		//售后状态 1正常 2申请售后 3 审核通过 4审核不通过 5已退货 6换货中 7换货完成
	 * @return
	 */
	@RequestMapping(value = "/mod/status", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> modStatus(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "orderStatus",required=false) Integer orderStatus,
			@RequestParam(value = "payStatus",required=false) Integer payStatus,
			@RequestParam(value = "logisticsStatus",required=false) Integer logisticsStatus,
			@RequestParam(value = "afterSalesStatus",required=false) Integer afterSalesStatus
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			ModStatusCommand command = new ModStatusCommand(orderId,orderStatus,payStatus,logisticsStatus,afterSalesStatus);
			orderApplication.modStatus(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order mod status Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order mod status  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 获取我的订单详情
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/my/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> myOrderDetail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.myOrderDetail(orderId);
 			String bussData = "{}";
 			if(orderVo != null){		
 				bussData = new ObjectSerializer(true).serialize(orderVo);
 				bussData = EncryptUtils.encrypt(bussData, token);			
 			}
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 我的订单列表
	 * 
	 * @param userId		//用户编号
	 * @param status		//订单状态
	 * @return
	 */
	@RequestMapping(value = "/my/list", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> myOrderList(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "status",required=false) Integer status,
			@RequestParam(value = "pageNumber",required=false) Integer pageNumber,
			@RequestParam(value = "rows",required=false) Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
		String bussData = "[]";
 		try {		
 			if(status == null){
 				status = 1;
 			}
 			if(pageNumber == null){
 				pageNumber = 1;
 			}
 			if(rows == null){
 				rows = 10;
 			}
 			List<?> orderList = new ArrayList<Object>();
 			Integer totalCount = 0;
 			orderList = springJdbcOrderQuery.myOrderList(userId,status,pageNumber,rows);
 			totalCount = springJdbcOrderQuery.myOrderListCount(userId,status);
 	 	
 			if(orderList != null && orderList.size() > 0){
 				bussData = new ObjectSerializer(true).serialize(orderList);
 	 			bussData = EncryptUtils.encrypt(bussData, token);
 			}			 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			result.setContent(bussData);
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 我的订单统计
	 * 
	 * @param userId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/my/statistics", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MResult> myOrderStatistics(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
		String bussData = "[]";
 		try {			
 			List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
 			
 			Integer pendingPayCount =  springJdbcOrderQuery.myOrderListCount(userId,2);
 			Integer pendingReceiptGoodsCount =  springJdbcOrderQuery.myOrderListCount(userId,3);
 			Integer pendingCommentCount =  springJdbcOrderQuery.myOrderListCount(userId,4);
 			Integer saledCount =  springJdbcOrderQuery.myOrderListCount(userId,5);
 			Integer orderCount = springJdbcOrderQuery.myOrderListCount(userId,1);
 			
 			Map<String,Object> pendingDeliverGoodsMap = new HashMap<String,Object>();
 			Map<String,Object> pendingReceiptGoodsMap = new HashMap<String,Object>();
 			Map<String,Object> pendingPayMap = new HashMap<String,Object>();
 			Map<String,Object> orderCountMap = new HashMap<String,Object>();
 			//所有
 			orderCountMap.put("orderStatus", 1);
 			orderCountMap.put("count", orderCount);
 			retList.add(orderCountMap);
 			//待付款 
 			pendingPayMap.put("orderStatus", 2);
 			pendingPayMap.put("count", pendingPayCount);
 			retList.add(pendingPayMap);
 			//待收货
 			pendingDeliverGoodsMap.put("orderStatus", 3);
 			pendingDeliverGoodsMap.put("count", pendingReceiptGoodsCount);
 			retList.add(pendingDeliverGoodsMap);
 			//待评论
 			pendingReceiptGoodsMap.put("orderStatus", 4);	
 			pendingReceiptGoodsMap.put("count", pendingCommentCount);	
 			retList.add(pendingReceiptGoodsMap);		
 			//退款售后
 			pendingReceiptGoodsMap.put("orderStatus", 5);	
 			pendingReceiptGoodsMap.put("count", saledCount);	
 			retList.add(pendingReceiptGoodsMap);	
 			
 			if(retList != null && retList.size() > 0){
 				bussData = new ObjectSerializer(true).serialize(retList);
 				bussData = EncryptUtils.encrypt(bussData, token);
 			}	
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(retList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 查看物流轨迹
	 * @param token		//
	 * @param orderId	//订单编号
	 * @return
	 */
	@RequestMapping(value = "/logistics", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MResult> logistics(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {	
 			Long startTime = null;
 			int count = 0;
 			Map<String,Object> resultMap = new HashMap<String,Object>();
 			List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
 			Map<String,Object> orderMap = springJdbcOrderQuery.findT(orderId);
 			String expCompanyCode = orderMap.get("expCompanyCode")==null?"":(String)orderMap.get("expCompanyCode");
 			String expCompanyName = orderMap.get("expCompanyName")==null?"":(String)orderMap.get("expCompanyName");
 			String waybillNo = orderMap.get("waybillNo")==null?"":(String)orderMap.get("waybillNo");
 			
 			resultMap.put("expCompanyCode", expCompanyCode.trim());
 			resultMap.put("expCompanyName", expCompanyName);
 			resultMap.put("waybillNo", waybillNo);
 			
 			LogisticsRet logisticsRet =  new ExpressUtil().logistics(waybillNo, expCompanyCode);
 			if(logisticsRet != null){
 				resultList = logisticsRet.data;
 			}
 			
 			int index = 0;
 			if(resultList != null && resultList.size() > 0){
 				for(Map<String,Object> map: resultList){
 					//String context = map.get("context") == null?"":(String)map.get("context");
 					//String location = map.get("location") == null?"":(String)map.get("location");
 					String time = map.get("time") == null?"":(String)map.get("time");
 					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 					Long ltime = null;
 					try{
 						ltime = format.parse(time).getTime();
 					}catch(Exception e){
 						
 					}
 			       map.put("time", ltime==null?"":ltime);
 			       if(index == 0){
 			    	   startTime = ltime;
 			       }
 			       index ++;
 				}
 				count = resultList.size();
 			}
 			
 			resultMap.put("startTime", startTime==null?"":startTime);
 			resultMap.put("count", count);
 			resultMap.put("logisticsList", resultList);
 			resultMap.put("expStatus", logisticsRet==null?"":logisticsRet.state);
 			
 			if(isEncry){
 				String bussData = new ObjectSerializer(true).serialize(resultMap);
 				bussData = EncryptUtils.encrypt(bussData, token);
 				result.setContent(bussData);
 			}else{
 				result.setContent(resultMap);
 			}
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 *  获取订单编号
	 * 
	 * @param userId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/id", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MResult> orderId(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
	
		MResult result = new MResult(MCode.V_1);	
	
		String orderId = IDGenerator.get(IDPrefix.ORDER_ORDER_PREFIX);
		Map<String,Object> retMap = new HashMap<String,Object>();
		retMap.put("orderId", orderId);
		String bussData = new ObjectSerializer(true).serialize(retMap);
		bussData = EncryptUtils.encrypt(bussData, token);
		if(isEncry){
			result.setContent(bussData);
		}else{
			result.setContent(retMap);
		}
		
		result.setStatus(MCode.V_200);
		
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单详情(订单管理)
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> orderDetail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.detail(orderId);
 			String bussData = "{}";
 			if(orderVo != null){		
 				bussData = new ObjectSerializer(true).serialize(orderVo);
 				bussData = EncryptUtils.encrypt(bussData, token);			
 			}
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 订单列表(订单管理)
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param goodsKeyword		//货品关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param mresKeyword		//媒体资源关键字
	 * @param salerKeyword		//促销员关键字
	 * @param goodsType			//销售货品类型 1实物货品 2 服务型 3虚拟货品
	 * @param orderStatus		//订单状态 1 正常 2 取消
	 * @param payStatus			//支付状态 1待支付 2已支付
	 * @param logisticsStatus	//物流状态 1待发货 2待收货 3确认收货
	 * @param afterSalesStatus		//售后状态 1待申请 2待审核 3 退换中 4.已退款
	 * @param commentStatus		//评论状态 1待评论 2已评论
	 * @param startTime			//下单开始时间
	 * @param endTime			//下单最后时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> orderList(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "orderNo" ,required=false) String orderNo,
			@RequestParam(value = "buyerKeyword" ,required=false) String buyerKeyword,
			@RequestParam(value = "goodsKeyword" ,required=false) String goodsKeyword,
			@RequestParam(value = "dealerKeyword" ,required=false) String dealerKeyword,
			@RequestParam(value = "mediaKeyword" ,required=false) String mediaKeyword,
			@RequestParam(value = "mresKeyword" ,required=false) String mresKeyword,
			@RequestParam(value = "salerKeyword" ,required=false) String salerKeyword,	
			@RequestParam(value = "goodsType",required=false) Integer goodsType,
			@RequestParam(value = "orderStatus",required=false) Integer orderStatus,
			@RequestParam(value = "payStatus",required=false) Integer payStatus,
			@RequestParam(value = "logisticsStatus",required=false) Integer logisticsStatus,
			@RequestParam(value = "afterSalesStatus",required=false) Integer afterSalesStatus,
			@RequestParam(value = "commentStatus",required=false) Integer commentStatus,
			@RequestParam(value = "startTime",required=false) String startTime,
			@RequestParam(value = "endTime",required=false) String endTime,
			@RequestParam(value = "pageNumber",required=false,defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",required=false,defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			Long lstartTime = null;
 			Long lendTime = null;
 			if(startTime != null){
 				Date date = DateUtils.stringtoDate(startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
 				lstartTime = date==null?null:date.getTime();
 			}
 			if(endTime != null){
 				Date date = DateUtils.stringtoDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
 				lendTime = date==null?null:date.getTime();
 			}
 			List<?> orderList  = springJdbcOrderQuery.list(orderId,orderNo, buyerKeyword, goodsKeyword, dealerKeyword, mediaKeyword, mresKeyword, salerKeyword, goodsType, orderStatus, payStatus, logisticsStatus, afterSalesStatus, commentStatus, lstartTime, lendTime, pageNumber, rows);
 			Integer totalCount = springJdbcOrderQuery.listCount(orderId,orderNo, buyerKeyword, goodsKeyword, dealerKeyword, mediaKeyword, mresKeyword, salerKeyword, goodsType, orderStatus, payStatus, logisticsStatus, afterSalesStatus, commentStatus, lstartTime, lendTime);
		 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			if(isEncry){
 				if(orderList != null && orderList.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(orderList);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 
	 * 订单列表(结算管理)
	 * 
	 * @param orderId			//订单编号
	 * @param buyerKeyword		//买家关键字
	 * @param dealerKeyword		//商家关键字
	 * @param mediaKeyword		//媒体关键字
	 * @param salerKeyword		//促销员关键字
	 * @param settleStatus		//订单状态 1 待结算 2 已结算
	 * @param commitsTime		//下单开始时间
	 * @param commiteTime		//下单最后时间
	 * @param receiversTime		//收货开始时间
	 * @param receivereTime		//收货最后时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list/for/settle", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> listForSettle(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "orderNo" ,required=false) String orderNo,
			@RequestParam(value = "buyerKeyword" ,required=false) String buyerKeyword,
			@RequestParam(value = "dealerKeyword" ,required=false) String dealerKeyword,
			@RequestParam(value = "mediaKeyword" ,required=false) String mediaKeyword,
			@RequestParam(value = "salerKeyword" ,required=false) String salerKeyword,
			@RequestParam(value = "settleStatus",required=false) Integer settleStatus,
			@RequestParam(value = "commitsTime",required=false) String commitsTime,
			@RequestParam(value = "commiteTime",required=false) String commiteTime,
			@RequestParam(value = "receiversTime",required=false) String receiversTime,
			@RequestParam(value = "receivereTime",required=false) String receivereTime,
			@RequestParam(value = "pageNumber",required=false ,defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",required=false ,defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			Long lcommitsTime = null;
 			Long lcommiteTime = null;
 			if(commitsTime != null){
 				Date date = DateUtils.stringtoDate(commitsTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
 				lcommitsTime = date==null?null:date.getTime();
 			}
 			if(commiteTime != null){
 				Date date = DateUtils.stringtoDate(commiteTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
 				lcommiteTime = date==null?null:date.getTime();
 			}
 			Long lreceiversTime = null;
 			Long lreceivereTime = null;
 			if(receiversTime != null){
 				Date date = DateUtils.stringtoDate(receiversTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
 				lreceiversTime = date==null?null:date.getTime();
 			}
 			if(receivereTime != null){
 				Date date = DateUtils.stringtoDate(receivereTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
 				lreceivereTime = date==null?null:date.getTime();
 			}

 			List<?> orderList = springJdbcOrderQuery.listForSettle(orderId,orderNo,buyerKeyword, dealerKeyword, mediaKeyword, salerKeyword, settleStatus, lcommitsTime, lcommiteTime, lreceiversTime, lreceivereTime, pageNumber, rows);
 			Integer totalCount = springJdbcOrderQuery.listCountForSettle(orderId,orderNo, buyerKeyword, dealerKeyword, mediaKeyword, salerKeyword, settleStatus, lcommitsTime, lcommiteTime, lreceiversTime, lreceivereTime);
 					 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			
 			if(isEncry){
 				if(orderList != null && orderList.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(orderList);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}	
 				result.setContent("[]");
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单详情(供应商)
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/detail/for/dealer", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> detailForDealer(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.detailForDealer(orderId);	
 			if(orderVo != null){
 				Long commitTime = orderVo.get("commitTime") == null?null:(Long)orderVo.get("commitTime");
 				Long payEndTime = orderVo.get("payEndTime") == null?null:(Long)orderVo.get("payEndTime");
 				Long commitWaybTime = orderVo.get("commitWaybTime") == null?null:(Long)orderVo.get("commitWaybTime");
 				Long receiverTime = orderVo.get("receiverTime") == null?null:(Long)orderVo.get("receiverTime");
 				Integer trailStatus = orderVo.get("trailStatus") == null?null:(Integer)orderVo.get("trailStatus");
 				Integer afterSalesStatus = orderVo.get("afterSalesStatus") == null?null:(Integer)orderVo.get("afterSalesStatus");
 				//1、已下单  2、待发货 3、已发货  4、已收货  5、完成
 				if(trailStatus != null && trailStatus.intValue() == 1){
 					orderVo.put("trailTime", commitTime);
 				}else if(trailStatus != null && trailStatus.intValue() == 2){
 					orderVo.put("trailTime", payEndTime);
 				}else if(trailStatus != null && trailStatus.intValue() == 3){
 					orderVo.put("trailTime", commitWaybTime);
 				}else if(trailStatus != null && trailStatus.intValue() == 4){
 					orderVo.put("trailTime", receiverTime);
 				}else if(trailStatus != null && trailStatus.intValue() == 5){
 					Long fsaleDeadline = (Long) DisconfDataGetter.getByFileItem("constants.properties", "after.sales.deadline");
 					if(receiverTime == null || fsaleDeadline == null){
 						orderVo.put("trailTime", null);
 					}else{
 						orderVo.put("trailTime", receiverTime + fsaleDeadline);
 					}
 					if(afterSalesStatus != null && afterSalesStatus == AfterSalesStatus.FINISHED.getId()){
 						orderVo.put("trailTime", orderVo.get("refundTime") == null?null:(Long)orderVo.get("refundTime"));
 					}
 					
 				}else{
 					orderVo.put("trailTime", null);
 				}
 			}
 			if(isEncry){
 				if(orderVo != null){		
 					String bussData = new ObjectSerializer(true).serialize(orderVo);
 	 				bussData = EncryptUtils.encrypt(bussData, token);	
 	 				result.setContent(bussData);
 	 			}else{
 	 				result.setContent("{}");
 	 			}
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单列表(供应商订单管理)
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list/for/dealer", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> listForDealer(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "dealerId" ,required=false) String dealerId,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "orderNo" ,required=false) String orderNo,
			@RequestParam(value = "keywordType",required=false) Integer keywordType,
			@RequestParam(value = "keyword" ,required=false) String keyword,
			@RequestParam(value = "statusFlag",required=false) Integer statusFlag,
			@RequestParam(value = "payWay",required=false) Integer payWay,
			@RequestParam(value = "startTime",required=false) String startTime,
			@RequestParam(value = "endTime",required=false) String endTime,
			@RequestParam(value = "pageNumber",required=false,defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",required=false,defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			Long lstartTime = null;
 			Long lendTime = null;
 			if(startTime != null){
 				Date date = DateUtils.stringtoDate(startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
 				lstartTime = date==null?null:date.getTime();
 			}
 			if(endTime != null){
 				Date date = DateUtils.stringtoDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
 				lendTime = date==null?null:date.getTime();
 			}
 			List<?> orderList = springJdbcOrderQuery.listForDealer(dealerId,orderId,orderNo,keywordType, keyword, statusFlag, payWay, lstartTime, lendTime, pageNumber, rows);		
 			Integer totalCount = springJdbcOrderQuery.listCountForDealer(dealerId,orderId,orderNo,keywordType, keyword, statusFlag, payWay, lstartTime, lendTime);
 				 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			if(isEncry){
 				if(orderList != null && orderList.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(orderList);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 				
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

	/**
	 * 订单收货地址详情
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/recaddr/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> recaddrDetail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.recaddrDetail(orderId);
 			String bussData = "{}";
 			if(orderVo != null){		
 				bussData = new ObjectSerializer(true).serialize(orderVo);
 				bussData = EncryptUtils.encrypt(bussData, token);			
 			}
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单状态详情
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/status/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> statusDetail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.statusDetail(orderId);
 			String bussData = "{}";
 			if(orderVo != null){		
 				bussData = new ObjectSerializer(true).serialize(orderVo);
 				bussData = EncryptUtils.encrypt(bussData, token);			
 			}
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单状态详情
	 * 
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/express/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> expressDetail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcOrderQuery.expressDetail(orderId);
 			String bussData = "{}";
 			if(orderVo != null){		
 				bussData = new ObjectSerializer(true).serialize(orderVo);
 				bussData = EncryptUtils.encrypt(bussData, token);			
 			}
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 订单列表(供应商订单管理)
	 * @param orderIdList  //订单号
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/print/delivery", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> listForPrintDelivery(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderIdList" ,required=false) String orderIdList,
			@RequestParam(value = "pageNumber",required=false) Integer pageNumber,
			@RequestParam(value = "rows",required=false) Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
		String bussData = "[]";
 		try {		
 			if(pageNumber == null){
 				pageNumber = 1;
 			}
 			if(rows == null){
 				rows = 200;
 			}
 			List<String> idList = null;
			try{
				//if(orderIdList != null && orderIdList.length() > 2000){
				//	throw new Exception();
				//}
				idList = new Gson().fromJson(orderIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
 			List<?> orderList = new ArrayList<Object>();
 			Integer totalCount = 0;
 			orderList = springJdbcOrderQuery.listForPrintDelivery(idList, pageNumber, rows);		
 			totalCount = springJdbcOrderQuery.listCountForPrintDelivery(idList);
 			if(orderList != null && orderList.size() > 0){
 				bussData = new ObjectSerializer(true).serialize(orderList);
 	 			bussData = EncryptUtils.encrypt(bussData, token);
 			}			 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			result.setContent(bussData);
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 订单列表(供应商订单管理)
	 * @param orderId		//订单号
	 * @param keywordType	//关键字类型 1收货人 2收货人电话 3运单号
	 * @param keyword		//关键字
	 * @param statusFlag	//1待付款 2待发货 3待收货 4取消 5退换中6已退 7已换
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单结束时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/export", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> listForExport(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderIdList" ,required=false) String orderIdList,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "keywordType",required=false) Integer keywordType,
			@RequestParam(value = "keyword" ,required=false) String keyword,
			@RequestParam(value = "statusFlag",required=false) Integer statusFlag,
			@RequestParam(value = "payWay",required=false) Integer payWay,
			@RequestParam(value = "startTime",required=false) String startTime,
			@RequestParam(value = "endTime",required=false) String endTime,
			@RequestParam(value = "pageNumber",required=false,defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",required=false,defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			Long lstartTime = null;
 			Long lendTime = null;
 			if(startTime != null){
 				Date date = DateUtils.stringtoDate(startTime + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
 				lstartTime = date==null?null:date.getTime();
 			}
 			if(endTime != null){
 				Date date = DateUtils.stringtoDate(endTime + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
 				lendTime = date==null?null:date.getTime();
 			}
 			List<String> idList = null;
			try{
				if(orderIdList != null && orderIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(orderIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
 			List<?> orderList = springJdbcOrderQuery.listForExport(idList, orderId, keywordType, keyword, statusFlag, payWay, lstartTime, lendTime, pageNumber, rows);	
 			Integer totalCount = springJdbcOrderQuery.listCountForExport(idList, orderId, keywordType, keyword, statusFlag, payWay, lstartTime, lendTime);
 				 			
 			result.setPager(totalCount.intValue(), pageNumber.intValue(), rows.intValue());
 			if(isEncry){
 				if(orderList != null && orderList.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(orderList);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 				
 			}else{
 				result.setContent(orderList);
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 *  解密
	 * 
	 * @param token			//
	 * @param content		//加密内容
	 * @return
	 */
	@RequestMapping(value = "/decrypt", method = { RequestMethod.POST,RequestMethod.GET})
	public String decrypt(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "content") String content
			){
	
		try {
			return EncryptUtils.decrypt(content, token);
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 商家订单统计
	 * 
	 * @param userId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/statistics/for/dealer", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MResult> dealerOrderStatistics(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "dealerId") String dealerId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
		String bussData = "{}";
 		try {			
 			List<Map<String,Object>> retList = new ArrayList<Map<String,Object>>();
 			Integer curDayCommitCount =  springJdbcOrderQuery.curDayCommitCount(dealerId);
 			Integer curDaySendedGoodsCount =  springJdbcOrderQuery.curDaySendedGoodsCount(dealerId);
 			Integer waitpayCount =  springJdbcOrderQuery.waitpayCount(dealerId);
 			Integer waitSendGoodsCount =  springJdbcOrderQuery.waitSendGoodsCount(dealerId);
 			Integer waitReceiverGoodsCount = springJdbcAfterSalesQuery.listCount(dealerId,null, null, null, 1, null, null, null);
 			
 			Integer commentCount =  springJdbcCommentQuery.commentCountForDealer(dealerId);
 			Integer replyCount = springJdbcCommentQuery.replyCountForDealer(dealerId);
 			
 			Map<String,Object> curDayCommitMap = new HashMap<String,Object>();
 			Map<String,Object> curDaySendedGoodsMap = new HashMap<String,Object>();
 			Map<String,Object> waitpayMap = new HashMap<String,Object>();
 			Map<String,Object> waitSendGoodsMap = new HashMap<String,Object>();
 			Map<String,Object> waitReceiverGoodsMap  = new HashMap<String,Object>();
 			Map<String,Object> commentMap  = new HashMap<String,Object>();
 			Map<String,Object> replyMap  = new HashMap<String,Object>();
 			
 			//今日下单
 			curDayCommitMap.put("flag", 1);
 			curDayCommitMap.put("count", curDayCommitCount);
 			retList.add(curDayCommitMap);
 			//今日发货
 			curDaySendedGoodsMap.put("flag", 2);
 			curDaySendedGoodsMap.put("count", curDaySendedGoodsCount);
 			retList.add(curDaySendedGoodsMap);
 			//待付款
 			waitpayMap.put("flag", 3);
 			waitpayMap.put("count", waitpayCount);
 			retList.add(waitpayMap);
 			//待发货
 			waitSendGoodsMap.put("flag", 4);	
 			waitSendGoodsMap.put("count", waitSendGoodsCount);	
 			retList.add(waitSendGoodsMap);		
 			//售后处理
 			waitReceiverGoodsMap.put("flag", 5);	
 			waitReceiverGoodsMap.put("count", waitReceiverGoodsCount);	
 			retList.add(waitReceiverGoodsMap);	
 			
 			//订单评论
 			commentMap.put("flag", 6);	
 			commentMap.put("count", commentCount);	
 			retList.add(commentMap);
 			
 			//回复评论
 			replyMap.put("flag", 7);	
 			replyMap.put("count", replyCount);	
 			retList.add(replyMap);
 			
 			if(retList != null && retList.size() > 0){
 				bussData = new ObjectSerializer(true).serialize(retList);
 				bussData = EncryptUtils.encrypt(bussData, token);
 			}	
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(retList);
 			}
			result.setStatus(MCode.V_200);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	
	/**
	 * 
	 * 我的订单支付状态
	 * @param orderId		//订单编号
	 * @return
	 */
	@RequestMapping(value = "/my/order/pay/status", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> myOrderPayStatus(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {	
 			Map<String,Object> orderVo = springJdbcOrderQuery.myOrderPayStatus(orderId);
 			if(isEncry){
 				if(orderVo != null){		
 	 				String bussData = new ObjectSerializer(true).serialize(orderVo);
 	 				bussData = EncryptUtils.encrypt(bussData, token);	
 	 				result.setContent(bussData);
 	 			}
 			}else{
 				result.setContent(orderVo);
 			}			
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
