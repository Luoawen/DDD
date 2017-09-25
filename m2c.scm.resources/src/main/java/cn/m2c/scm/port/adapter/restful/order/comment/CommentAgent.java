package cn.m2c.scm.port.adapter.restful.order.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
import cn.m2c.scm.application.order.comment.CommentApplication;
import cn.m2c.scm.application.order.comment.command.AdminCreateCommand;
import cn.m2c.scm.application.order.comment.command.AppCreateCommand;
import cn.m2c.scm.application.order.comment.command.AppModCommentCommand;
import cn.m2c.scm.application.order.comment.command.DeleteByAdminCommand;
import cn.m2c.scm.application.order.comment.command.DeteteByUserCommand;
import cn.m2c.scm.application.order.comment.command.HideCommand;
import cn.m2c.scm.application.order.comment.command.ReplyCommand;
import cn.m2c.scm.application.order.comment.command.TopCommand;
import cn.m2c.scm.application.order.comment.query.SpringJdbcCommentQuery;
import cn.m2c.scm.domain.IDPrefix;
import cn.m2c.scm.domain.NegativeException;

import com.baidu.disconf.client.usertools.DisconfDataGetter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
/**
 * 
 * @ClassName: CommentAgent
 * @Description: 评论
 * @author moyj
 * @date 2017年6月30日 下午2:51:33
 *
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentAgent {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentAgent.class);
	
	@Autowired
	private SpringJdbcCommentQuery springJdbcCommentQuery;	
	
	@Autowired
	private CommentApplication commentApplication;	
	
	/**
	 * 评论详情
	 * @param commentId	//评论编号
	 * @return
	 */
	@RequestMapping(value="/detail",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MResult> detail(
				@RequestParam(value = "token") String token,
				@RequestParam(value = "commentId") String commentId,
				@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1); 
		try {
			String bussData = "{}";	
			Map<String,Object> map = springJdbcCommentQuery.findT(commentId);
			bussData = new ObjectSerializer(true).serialize(map);
			if(isEncry){
				bussData = EncryptUtils.encrypt(bussData, token);
				result.setContent(bussData);
 			}else{
 				result.setContent(map);
 			}	
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("CommentAgent detail Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400,e.getMessage());
		} catch (Exception e) {
			logger.error("CommentAgent detail Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400,e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 评论列表
	 * @param token
	 * @param commentId			//评论编号
	 * @param orderId			//订单编号
	 * @param goodsKeyword		//货品关键字
	 * @param dealerKeyword		//商家关键字
	 * @param buyerKeyword		//买家关键字
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别 1好 2 中 3差
	 * @param replyStatus		//回复状态  1未回复 2已回复
	 * @param startTime			//开始时间
	 * @param endTime			//结束时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MPager> list(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "commentId" ,required=false) String commentId,
			@RequestParam(value = "orderId" ,required=false) String orderId,
			@RequestParam(value = "goodsKeyword" ,required=false) String goodsKeyword,
			@RequestParam(value = "dealerKeyword" ,required=false) String dealerKeyword,
			@RequestParam(value = "buyerKeyword" ,required=false) String buyerKeyword,
			@RequestParam(value = "commentLevel" ,required=false) Integer commentLevel,	
			@RequestParam(value = "replyStatus" ,required=false) Integer replyStatus,	
			@RequestParam(value = "startTime" ,required=false) String startTime,	
			@RequestParam(value = "endTime" ,required=false) String endTime,

			@RequestParam(value = "pageNumber", defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
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
 			List<?> list = springJdbcCommentQuery.list(commentId, orderId, goodsKeyword, dealerKeyword, buyerKeyword,commentLevel, replyStatus, lstartTime, lendTime, pageNumber, rows);
 			Integer count = springJdbcCommentQuery.listCount(commentId, orderId, goodsKeyword, dealerKeyword, buyerKeyword,commentLevel, replyStatus, lstartTime, lendTime);
 	 	
  			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			
  			if(isEncry){
 				if(list != null && list.size() > 0){
 					String bussData = new ObjectSerializer(true).serialize(list);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 			}else{
 				result.setContent(list);
 			}
			result.setStatus(MCode.V_200);
 		} catch (NegativeException e) {
			logger.error("StatementBillAgent add Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(),e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent list Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400,e.getMessage());
		}	
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

	/**
	 * 评论详情(供应商)
	 * @param commentId	//评论编号
	 * @return
	 */
	@RequestMapping(value="/detail/for/dealer",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MResult> detailForDealer(
				@RequestParam(value = "token") String token,
				@RequestParam(value = "commentId") String commentId,
				@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MResult result = new MResult(MCode.V_1); 
		try {
			String bussData = "{}";	
			Map<String,Object> map = springJdbcCommentQuery.detailForDealer(commentId);
			bussData = new ObjectSerializer(true).serialize(map);
			if(isEncry){
				bussData = EncryptUtils.encrypt(bussData, token);
				result.setContent(bussData);
 			}else{
 				result.setContent(map);
 			}	
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("CommentAgent detail Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400,e.getMessage());
		} catch (Exception e) {
			logger.error("CommentAgent detail Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_400,e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 评论列表(供应商)
	 * @param token
	 * @param keyword			//关键字
	 * @param commentLevel		//评论级别 1好 2 中 3差
	 * @param replyStatus		//回复状态  1未回复 2已回复
	 * @param startTime			//开始时间
	 * @param endTime			//结束时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value="/list/for/dealer",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MPager> listForDealer(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "dealerId" ,required=false) String dealerId,
			@RequestParam(value = "keyword" ,required=false) String keyword,
			@RequestParam(value = "commentLevel" ,required=false) Integer commentLevel,	
			@RequestParam(value = "replyStatus" ,required=false) Integer replyStatus,	
			@RequestParam(value = "startTime" ,required=false) String startTime,	
			@RequestParam(value = "endTime" ,required=false) String endTime,
			@RequestParam(value = "pageNumber", defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
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
 			List<?> list  = springJdbcCommentQuery.listForDealer(dealerId,keyword, commentLevel, replyStatus, lstartTime, lendTime, pageNumber, rows);
 			Integer count = springJdbcCommentQuery.listCountForDealer(dealerId,keyword, commentLevel, replyStatus, lstartTime, lendTime);

 			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			if(isEncry){
 				if(list != null && list.size() > 0){
 					String bussData = new ObjectSerializer(true).serialize(list);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 			}else{
 				result.setContent(list);
 			}
			result.setStatus(MCode.V_200);
 		} catch (NegativeException e) {
			logger.error("StatementBillAgent add Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(),e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent list Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400,e.getMessage());
		}	
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

	/**
	 * 评论列表
	 * @param token
	 * @param goodsId			//货品编号
	 * @param commentLevel		//评论级别 1好 2 中 3差

	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/list/for/app",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MPager> listForApp(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "goodsId") String goodsId,
			@RequestParam(value = "commentLevel" ,required=false) Integer commentLevel,
			@RequestParam(value = "pageNumber", defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			Map<String,Object> resultMap = new HashMap<String,Object>();
 			Integer totalCount = springJdbcCommentQuery.totalCount(goodsId);
 			List<?> resultlist = new ArrayList<Map<String,Object>>();
 			
 			List<?> list = springJdbcCommentQuery.listForApp(goodsId, commentLevel, pageNumber, rows);
 			Integer count = springJdbcCommentQuery.listCountForApp(goodsId, commentLevel);
 			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			
 			if(list != null){
 				resultlist = list;
 				for(Map<String,Object> map : (List<Map<String,Object>>)resultlist){
 					String buyerName = map.get("buyerName")==null?null:(String)map.get("buyerName");
 					if(buyerName != null){
 						String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
 						if(Pattern.matches(REGEX_MOBILE, buyerName)){
 							buyerName = buyerName.substring(0,3) + "***" + buyerName.substring(buyerName.length()-3,buyerName.length());
 							map.put("buyerName", buyerName);
 						}
 					}
 				}
 			}
 			resultMap.put("totalCount", totalCount);
 			resultMap.put("dataList", resultlist);
 			
 			if(isEncry){
 	 				String bussData = new ObjectSerializer(true).serialize(resultMap);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 
 			}else{
 				result.setContent(resultMap);
 			}
			result.setStatus(MCode.V_200);
 		} catch (NegativeException e) {
			logger.error("StatementBillAgent add Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(),e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent list Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400,e.getMessage());
		}	
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 我的评论列表
	 * @param token
	 * @param userId
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/my/list",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MPager> myList(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "pageNumber", defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			List<?> resultlist = new ArrayList<Map<String,Object>>();
 			
 			List<?> list = springJdbcCommentQuery.myList(userId, pageNumber, rows);
 			Integer count = springJdbcCommentQuery.myListCount(userId);
 			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			
 			if(list != null){
 				resultlist = list;
 				for(Map<String,Object> map : (List<Map<String,Object>>)resultlist){
 					//用户名
 					String buyerName = map.get("buyerName")==null?null:(String)map.get("buyerName");
 					if(buyerName != null){
 						String REGEX_MOBILE = "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
 						if(Pattern.matches(REGEX_MOBILE, buyerName)){
 							buyerName = buyerName.substring(0,3) + "***" + buyerName.substring(buyerName.length()-3,buyerName.length());
 							map.put("buyerName", buyerName);
 						}
 					}
 					//评论时间
 					Long modCommentTime = map.get("modCommentTime")==null?0L:(Long)map.get("modCommentTime");
 					Long deadline=(Long) DisconfDataGetter.getByFileItem("constants.properties", "comment.modify.deadline");
 					//应许
 					if(modCommentTime + deadline > System.currentTimeMillis()){
 						map.put("allowMod", 1);
 					}
 					//不应许
 					else{
 						map.put("allowMod", 2);
 					}
 				
 				}
 			}
 			
 			if(isEncry){
 				String bussData = new ObjectSerializer(true).serialize(resultlist);
 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 			result.setContent(bussData);
 			}else{
 				result.setContent(resultlist);
 			}
			result.setStatus(MCode.V_200);
 		} catch (NegativeException e) {
			logger.error("StatementBillAgent add Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(),e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent list Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400,e.getMessage());
		}	
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 新增评论（APP）
	 * 
	 * @param commentId			//评论ID
	 * @param orderId			//订单Id
	 * @param buyerId			//买家ID
	 * @param commentLevel		//评论级别  1好  2中  3差
	 * @param commentContent	//评论内容
	 * @return
	 */
	@RequestMapping(value = "/app/create", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> appCreate(
			@RequestParam(value = "token") String token,	
			//@RequestParam(value = "commentId") String commentId,		
			@RequestParam(value = "orderId") String orderId,	
			@RequestParam(value = "userId") String userId,	
			@RequestParam(value = "starLevel") Integer starLevel,
			@RequestParam(value = "commentContent") String commentContent
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			String commentId = IDGenerator.get(IDPrefix.ORDER_COMMENT_PREFIX);
 			AppCreateCommand command = new AppCreateCommand(commentId, orderId, userId, starLevel, commentContent);
 			commentApplication.appCreate(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent appCreate Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		} 
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 修改评论（APP）
	 * 
	 * @param commentId			//评论ID
	 * @param orderId			//订单Id
	 * @param buyerId			//买家ID
	 * @param commentLevel		//评论级别  1好  2中  3差
	 * @param commentContent	//评论内容
	 * @return
	 */
	@RequestMapping(value = "/app/modify", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> appModify(
			@RequestParam(value = "token") String token,		
			@RequestParam(value = "commentId") String commentId,	
			@RequestParam(value = "starLevel") Integer starLevel,
			@RequestParam(value = "commentContent") String commentContent
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			Long deadline=(Long) DisconfDataGetter.getByFileItem("constants.properties", "comment.modify.deadline");
 			AppModCommentCommand command = new AppModCommentCommand(commentId, starLevel, commentContent,deadline);
 			commentApplication.appModComment(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent appCreate Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		} 
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 新增评论（后台）
	 * 
	 * @param commentId			//订单编号
	 * @param goodsId			//货品编号
	 * @param goodsName			//货品名称
	 * @param buyerName			//买家名称
	 * @param buyerIcon			//买家头像
	 * @param commentContent	//评论
	 * @param commentTime		//评论时间		
	 * @param buyingTime		//买家时间				
	 * @return
	 */
	@RequestMapping(value = "/admin/create", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> adminCreate(
			@RequestParam(value = "token") String token,	
			//@RequestParam(value = "commentId") String commentId,	
			@RequestParam(value = "goodsId") String goodsId,	
			@RequestParam(value = "goodsName") String goodsName,	
			@RequestParam(value = "buyerName") String buyerName,	
			@RequestParam(value = "buyerIcon") String buyerIcon,	
			@RequestParam(value = "commentContent") String commentContent,	
			@RequestParam(value = "starLevel") Integer starLevel,
			@RequestParam(value = "commentTime") Long commentTime,
			@RequestParam(value = "buyingTime") Long buyingTime
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			String commentId = IDGenerator.get(IDPrefix.ORDER_COMMENT_PREFIX);			
 			AdminCreateCommand command = new AdminCreateCommand(commentId, goodsId, goodsName, buyerName, buyerIcon, commentContent,starLevel, commentTime, buyingTime);
 			commentApplication.adminCreate(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("CommentAgent adminCreate Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,e.getMessage());
		} 
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 回复
	 * @param token
	 * @param commentId		//评论编号
	 * @param replyContent	//回复内容
	 * @return
	 */
	@RequestMapping(value = "/reply", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> reply(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "commentId") String commentId,	
			@RequestParam(value = "replyContent") String replyContent
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			ReplyCommand command = new ReplyCommand(commentId,replyContent);
 			commentApplication.reply(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent reply Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 置顶
	 * @param token
	 * @param commentIdList		//评论ID列表
	 * @param topFlag			//置顶标志 1置顶 2取消置顶
	 * @return
	 */
	@RequestMapping(value = "/top", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> top(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "commentIdList") String commentIdList,
			@RequestParam(value = "topFlag") Integer topFlag
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			List<String> idList = null;
			try{
				if(commentIdList != null && commentIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(commentIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
 			TopCommand command = new TopCommand(idList,topFlag);
 			commentApplication.top(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent top Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 隐藏
	 * @param token
	 * @param commentIdList  	//评论ID列表
	 * @param hideFlag   		//隐藏标志 1隐藏  2取消隐藏
	 * @return
	 */
	@RequestMapping(value = "/hide", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> hide(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "commentIdList") String commentIdList,
			@RequestParam(value = "hideFlag") Integer hideFlag
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			List<String> idList = null;
			try{
				if(commentIdList != null && commentIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(commentIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
			HideCommand command = new HideCommand(idList,hideFlag);
 			commentApplication.hide(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent hide Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 通过管理员删除
	 * @param token
	 * @param commentIdList
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> deleteByAdmin(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "commentIdList") String commentIdList
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			List<String> idList = null;
			try{
				if(commentIdList != null && commentIdList.length() > 2000){
					throw new Exception();
				}
				idList = new Gson().fromJson(commentIdList, new TypeToken<List<String>>(){}.getType());
			}catch(Exception e){
				throw new IllegalArgumentException("propertyList is format error");
			}
			DeleteByAdminCommand command = new DeleteByAdminCommand(idList);
 			commentApplication.deleteByAdmin(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("CommentAgent delete Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	 
	/**
	 * 通过用户删除
	 * @param token
	 * @param commentIdList
	 * @return
	 */
	@RequestMapping(value = "/delete/by/user", method = { RequestMethod.POST,RequestMethod.GET })
	public ResponseEntity<MResult> deleteByUser(
			@RequestParam(value = "token") String token,	
			@RequestParam(value = "commentId") String commentId
			){
		MResult result = new MResult(MCode.V_1);	
 		try {	
 			DeteteByUserCommand command = new DeteteByUserCommand(commentId);
 			commentApplication.deleteByUser(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("Order cancel Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("CommentAgent delete Exception e:" + e.getMessage(), e);
			result = new MResult(MCode.V_500,"");
		}  
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	public static void main(String[] args){

	}

}
