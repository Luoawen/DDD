package cn.m2c.scm.port.adapter.restful.order.fsales;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.application.order.fsales.AfterSalesApplication;
import cn.m2c.scm.application.order.fsales.command.ApplyAfterSalesCommand;
import cn.m2c.scm.application.order.fsales.command.AuditAfterSalesCommand;
import cn.m2c.scm.application.order.fsales.command.BackedGoodsCommand;
import cn.m2c.scm.application.order.fsales.command.BarteredCommand;
import cn.m2c.scm.application.order.fsales.command.BarteringCommand;
import cn.m2c.scm.application.order.fsales.command.BatchBarteredCommand;
import cn.m2c.scm.application.order.fsales.query.SpringJdbcAfterSalesQuery;
import cn.m2c.scm.application.order.token.TokenApplication;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.fsales.FsalesStatus;
import cn.m2c.scm.port.adapter.rpc.dubbo.order.OrderServiceImpl;

import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * @ClassName: AfterSalesAgent
 * @Description: 订单售后服务
 * @author moyj
 * @date 2017年7月7日 下午5:36:48
 *
 */
@RestController
@RequestMapping(value = "/after/sales")
public class AfterSalesAgent {
	
	private static final Logger logger = LoggerFactory.getLogger(AfterSalesAgent.class);
	@Autowired
	private AfterSalesApplication afterSalesApplication;
	@Autowired
	private SpringJdbcAfterSalesQuery springJdbcAfterSalesQuery;
	@Autowired
	private TokenApplication tokenApplication;
	@Autowired
	private OrderServiceImpl orderServiceImpl;
	
	
	/**
	 * 申请售后
	 * 
	 * @param orderId			//订单编号
	 * @param applyReason		//申请售后原因
	 * @param applyFlag			//退换标识 1退货  2换货 
	 * @return
	 */
	@RequestMapping(value = "/apply", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> applyAfterSales(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "applyReason") String applyReason
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			Long fsaleDeadline = (Long) DisconfDataGetter.getByFileItem("constants.properties", "after.sales.deadline");
 			ApplyAfterSalesCommand command = new ApplyAfterSalesCommand(orderId,applyReason,fsaleDeadline);
 			afterSalesApplication.applyAfterSales(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled apply Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled apply  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 售后申请审核
	 * 
	 * @param orderId			//订单编号
	 * @param auditFlag			//1审核通过	2审核不通过
	 * @param unauditReason;	//审核不通过理由
	 * @return
	 */
	@RequestMapping(value = "/audit", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> auditAfterSales(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderIdList") String orderIdList,
			@RequestParam(value = "auditFlag") Integer auditFlag,
			@RequestParam(value = "unauditReason",required=false) String unauditReason,
			@RequestParam(value = "userId",required=false) String userId,
			@RequestParam(value = "userName",required=false) String userName
			){
		MResult result = new MResult(MCode.V_1);
	
 		try {	
			List<String> idList = null;
			try{
				if(orderIdList != null && orderIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(orderIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
			if(userId == null || userId.length() == 0){
 				userId = tokenApplication.getUserId(token);
 			}
 			if(userName == null || userName.length() == 0){
 				userName = tokenApplication.getUserName(token);
 			}
			AuditAfterSalesCommand command = new AuditAfterSalesCommand(idList,auditFlag,unauditReason,userId,userName);
			afterSalesApplication.auditAfterSales(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled audit Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled audit  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 退货完成
	 * 
	 * @param orderId						//订单编号
	 * @param String refbaredReason;		//退/换理由
	 * @return
	 */
	//@RequestMapping(value = "/backed", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> backedGoods(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "refundAmount") Long refundAmount,
			@RequestParam(value = "orderId") String orderId,		
			@RequestParam(value = "refbaredReason") String refbaredReason,
			@RequestParam(value = "loginUserId",required=false) String loginUserId,
			@RequestParam(value = "loginUserName",required=false) String loginUserName
			){
		MResult result = new MResult(MCode.V_1);	
 		try {		
 			if(loginUserId == null || loginUserId.length() == 0){
 				loginUserId = tokenApplication.getUserId(token);
 			}
 			if(loginUserName == null || loginUserName.length() == 0){
 				loginUserName = tokenApplication.getUserName(token);
 			}
 			BackedGoodsCommand command = new BackedGoodsCommand(orderId,refundAmount,refbaredReason,loginUserId,loginUserName);
 			afterSalesApplication.backedGoods(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order backed goods Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order backed goods  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}   
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 换货中
	 * 
	 * @param orderId					//订单编号
	 * @param String refbaredReason;	//退/换理由
	 * @return
	 */
	@RequestMapping(value = "/bartering", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> saledBartering(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "refbaredReason") String refbaredReason,
			@RequestParam(value = "loginUserId",required=false) String loginUserId,
			@RequestParam(value = "loginUserName",required=false) String loginUserName
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			if(loginUserId == null || loginUserId.length() == 0){
 				loginUserId = tokenApplication.getUserId(token);
 			}
 			if(loginUserName == null || loginUserName.length() == 0){
 				loginUserName = tokenApplication.getUserName(token);
 			}
 			BarteringCommand command = new BarteringCommand(orderId,refbaredReason,loginUserId,loginUserName);
 			afterSalesApplication.bartering(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled bartering Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled bartering  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 换货完成
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/bartered", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> saledBartered(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "loginUserId",required=false) String loginUserId,
			@RequestParam(value = "loginUserName",required=false) String loginUserName
			){
		MResult result = new MResult(MCode.V_1);	
 		try {
 			if(loginUserId == null || loginUserId.length() == 0){
 				loginUserId = tokenApplication.getUserId(token);
 			}
 			if(loginUserName == null || loginUserName.length() == 0){
 				loginUserName = tokenApplication.getUserName(token);
 			}
 			BarteredCommand command = new BarteredCommand(orderId,loginUserId,loginUserName);
 			afterSalesApplication.bartered(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled bartered Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled bartered  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}

	/**
	 * 批量换货完成
	 * 
	 * @param orderId			//订单编号
	 * @return
	 */
	@RequestMapping(value = "/batch/bartered", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> batchBartered(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "orderIdList") String orderIdList,
			@RequestParam(value = "loginUserId",required=false) String loginUserId,
			@RequestParam(value = "loginUserName",required=false) String loginUserName
			){
		MResult result = new MResult(MCode.V_1);	
 		try {
 			List<String> idList = null;
			try{
				if(orderIdList != null && orderIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(orderIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
			if(loginUserId == null || loginUserId.length() == 0){
				loginUserId = tokenApplication.getUserId(token);
 			}
 			if(loginUserName == null || loginUserName.length() == 0){
 				loginUserName = tokenApplication.getUserName(token);
 			}
			BatchBarteredCommand command = new BatchBarteredCommand(idList,loginUserId,loginUserName);
 			afterSalesApplication.batchBartered(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order saled bartered Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order saled bartered  Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500, "");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 售后详细
	 * 
	 * @param fsalesId		//售后ID
	 * @return
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> detail(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "fsalesId") String fsalesId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {			
 			Map<String,Object> orderVo = springJdbcAfterSalesQuery.detail(fsalesId);
 			if(orderVo != null){	
 				Long applyTime = orderVo.get("applyTime") == null?null:(Long)orderVo.get("applyTime");
 				Long auditTime = orderVo.get("auditTime") == null?null:(Long)orderVo.get("auditTime");
 				Long finishedTime = orderVo.get("finishedTime") == null?null:(Long)orderVo.get("finishedTime");
 				Integer trailStatus = orderVo.get("trailStatus") == null?null:(Integer)orderVo.get("trailStatus");
 				Integer fsalesStatus = orderVo.get("fsalesStatus") == null?null:(Integer)orderVo.get("fsalesStatus");
 				//1待审核  2退换中  3退换完成
				if(trailStatus != null &&  trailStatus == 1){
					orderVo.put("trailTime", applyTime);
				}else if(trailStatus != null &&  trailStatus == 2){
					orderVo.put("trailTime", auditTime);
				}else if(trailStatus != null &&  trailStatus == 3){
					if(fsalesStatus != null && fsalesStatus == FsalesStatus.APPLY_UNPASS.getId()){
						orderVo.put("trailTime", auditTime);
					}else{
						orderVo.put("trailTime", finishedTime);
					}	
				}else{
					orderVo.put("trailTime", null);
				}
				//orderVo.remove("auditTime");
				orderVo.remove("finishedTime");
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
	 * 订单列表(退换货管理)
	 * @param orderId		//订单号
	 * @param keyword		//关键字
	 * @param trailStatus	//1待审核  2退换中  3退换完成
	 * @param payWay		//付款方式 1支付宝 2微信
	 * @param startTime		//下单开始时间
	 * @param endTime		//下单开始时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> list(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "dealerId" ,required=false) String dealerId,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "orderNo" ,required=false) String orderNo,
			@RequestParam(value = "keyword" ,required=false) String keyword,
			@RequestParam(value = "statusFlag",required=false) Integer statusFlag,
			@RequestParam(value = "payWay",required=false) Integer payWay,
			@RequestParam(value = "startTime",required=false) String startTime,
			@RequestParam(value = "endTime",required=false) String endTime,
			@RequestParam(value = "pageNumber",defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="true") Integer rows,
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
 			List<?> orderList = springJdbcAfterSalesQuery.list(dealerId,orderId,orderNo, keyword, statusFlag, payWay, lstartTime, lendTime, pageNumber, rows);	
 			Integer totalCount = springJdbcAfterSalesQuery.listCount(dealerId,orderId,orderNo,keyword, statusFlag, payWay, lstartTime, lendTime);
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
	 * 退换货进度跟踪
	 * @param token
	 * @param orderId		//订单ID
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/progress/tracking", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> progressTracking(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "orderId") String orderId,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1);
 		try {		
 			Map<String,Object> resultMap = new HashMap<String,Object>();
 			Map<String,Object> orderVo = springJdbcAfterSalesQuery.progressTracking(orderId);
 			String bussData = "{}";		
 			if(orderVo != null){
 				resultMap.put("orderId", orderVo.get("orderId")== null?"":(String)orderVo.get("orderId"));
 				resultMap.put("orderNo", orderVo.get("orderNo")== null?"":(String)orderVo.get("orderNo"));
 				resultMap.put("unauditReason", orderVo.get("unauditReason") == null?"":(String)orderVo.get("unauditReason") );
 				resultMap.put("refbaredReason", orderVo.get("refbaredReason") == null?"":(String)orderVo.get("refbaredReason") );
 				resultMap.put("applyTime", orderVo.get("applyTime"));
 				resultMap.put("auditTime", orderVo.get("auditTime")== null?"":(Long)orderVo.get("auditTime"));
 				Integer fsalesStatus = (Integer)orderVo.get("fsalesStatus");
 				if(fsalesStatus == FsalesStatus.APPLY.getId()){
 					resultMap.put("fsalesStatus", 1);
 					resultMap.put("changTime",orderVo.get("applyTime")== null?"":(Long)orderVo.get("applyTime"));
 					resultMap.put("finishedTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 				}else if(fsalesStatus == FsalesStatus.APPLY_UNPASS.getId()){
 					resultMap.put("fsalesStatus", 2);
 					resultMap.put("changTime",orderVo.get("auditTime")== null?"":(Long)orderVo.get("auditTime"));
 					resultMap.put("finishedTime",orderVo.get("auditTime")== null?"":(Long)orderVo.get("auditTime"));
 				}else if(fsalesStatus == FsalesStatus.BACKED_GOODS.getId()){
 					resultMap.put("fsalesStatus", 5);
 					resultMap.put("changTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 					resultMap.put("finishedTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 				}else if(fsalesStatus == FsalesStatus.BARTERED.getId()){
 					resultMap.put("fsalesStatus", 4);
 					resultMap.put("changTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 					resultMap.put("finishedTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 				}
 				else if(fsalesStatus == FsalesStatus.APPLY_PASS.getId()|| fsalesStatus == FsalesStatus.BARTERING.getId()){
 					resultMap.put("fsalesStatus", 3);
 					resultMap.put("changTime",orderVo.get("auditTime")== null?"":(Long)orderVo.get("auditTime"));
 					resultMap.put("finishedTime",orderVo.get("finishedTime")== null?"":(Long)orderVo.get("finishedTime"));
 				}
 			}else{
 				throw new NegativeException(MCode.V_400,"退换状态已发生改变");
 			}
 		
 			if(isEncry){
 				
 	 			if(resultMap != null){		
 	 				bussData = new ObjectSerializer(true).serialize(resultMap);
 	 				bussData = EncryptUtils.encrypt(bussData, token);		
 	 			}
 				
 				result.setContent(bussData);
 			}else{
 				result.setContent(resultMap);
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
	
	@RequestMapping(value="/test",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MResult> test1(){
		MResult result = new MResult(MCode.V_1); 
		try {		
 			result.setContent(orderServiceImpl.mediaOrderStatistics("18MD3D6966445CF14A3BACDF474B423E8839"));
 			
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			logger.error("PayAccountAgent add Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	public static void main(String[] args) throws ParseException{
		String s= "2017-06-11 20:21:21";
		Date d;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		d = sdf.parse(s);
		System.out.println(d.getTime());
		long t2 = new Date().getTime();
		System.out.println(System.currentTimeMillis());
		System.out.println(t2);
		System.out.println(15*24*60*60*1000L);
	}
}
