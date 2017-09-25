package cn.m2c.scm.port.adapter.restful.goods.goods;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.StaleObjectStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate4.HibernateOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.common.StringUtil;
import cn.m2c.helps.interfaces.dubbo.HelpsService;
import cn.m2c.media.interfaces.dubbo.MediaService;
import cn.m2c.pay.interfaces.dubbo.SettleRuleService;
import cn.m2c.scm.application.goods.goods.GoodsApplication;
import cn.m2c.scm.application.goods.goods.command.GoodsAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.command.MDViewGoodsCommand;
import cn.m2c.scm.application.goods.goods.command.RecognizedPicCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGoodsQuery;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcHotWordQuery;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcLocQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.users.interfaces.dubbo.UserService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * 品牌模板管理
 * @author ps
 *
 */
@RestController
@RequestMapping("/goods")
public class GoodsAgent {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GoodsAgent.class);
	
	private final static String goodsDescUri = "m2c.goods/goods/appGoodsDesc";
	
	
	
	@Autowired
	GoodsApplication goodsApplication;
	@Autowired
	SpringJdbcGoodsQuery query;
	@Autowired
	MediaService mediaService;
	@Autowired
	UserService userService;
	@Autowired
	HelpsService helpsService;  
	
	@Autowired
	SettleRuleService settleRuleService;
	
	@Autowired
	SpringJdbcHotWordQuery hotWordQuery;
	
	@Autowired
	SpringJdbcLocQuery locQuery;

	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addGoods(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="firstClassify",required=true)String firstClassify,
			@RequestParam(value="secondClassify",required=true)String secondClassify,
			@RequestParam(value="goodsNo",required=false,defaultValue="")String goodsNo,
			@RequestParam(value="barNo",required=false ,defaultValue="")String barNo,
			@RequestParam(value="goodsName",required=true)String goodsName,
			@RequestParam(value="subtitle",required=false,defaultValue="")String subtitle,
			@RequestParam(value="salePrice",required=true)Long salePrice,
			@RequestParam(value="marketPrice",required=true)Long marketPrice,
			@RequestParam(value="brandId",required=true)String brandId,
			@RequestParam(value="propertyId",required=true)String propertyId,
			@RequestParam(value="transportFeeId",required=true)String transportFeeId,
			@RequestParam(value="guarantee",required=true)String guarantee,
			@RequestParam(value="advertisementPic")String advertisementPic,
			@RequestParam(value="gallery")String gallery,
			@RequestParam(value="goodsDesc",required=true)String goodsDesc){
		MResult result = new MResult(MCode.V_1);
		LOGGER.info("商品描述的内容是---------------"+goodsDesc);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			
			Map<String, Object> rule = settleRuleService.getRule();
			
			String divideScale = (Integer) rule.get("dealerPercent")+":"+(Integer) rule.get("mediaPercent")+":"+(Integer) rule.get("salerPercent")+":"+(Integer) rule.get("platformPercent");
			System.out.println("-----分成比例:----"+divideScale);
			String goodsId = IDGenerator.get(IDGenerator.GOODS_PREFIX_TITLE);
			GoodsAddOrUpdateCommand command = new GoodsAddOrUpdateCommand(goodsId, dealerId,firstClassify, secondClassify,
				goodsNo, barNo,goodsName, subtitle,
				salePrice, marketPrice, brandId,
				propertyId,transportFeeId, guarantee,
				advertisementPic, gallery, goodsDesc,divideScale);
				goodsApplication.addGood(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("Goods add Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Goods add Exception e:", e);
				result = new MResult(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Goods add Exception e:", e);
				result = new MResult(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateGoods(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="goodsId",required=true)String goodsId,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="firstClassify",required=true)String firstClassify,
			@RequestParam(value="secondClassify",required=true)String secondClassify,
			@RequestParam(value="goodsNo",required=true)String goodsNo,
			@RequestParam(value="barNo",required=true)String barNo,
			@RequestParam(value="goodsName",required=true)String goodsName,
			@RequestParam(value="subtitle")String subtitle,
			@RequestParam(value="salePrice",required=true)Long salePrice,
			@RequestParam(value="marketPrice",required=true)Long marketPrice,
			@RequestParam(value="brandId",required=true)String brandId,
			@RequestParam(value="propertyId",required=true)String propertyId,
			@RequestParam(value="transportFeeId")String transportFeeId,
			@RequestParam(value="guarantee",required=true)String guarantee,
			@RequestParam(value="advertisementPic")String advertisementPic,
			@RequestParam(value="gallery")String gallery,
			@RequestParam(value="goodsDesc",required=true)String goodsDesc){
		MResult result = new MResult(MCode.V_1);
		LOGGER.info("商品描述的内容是---------------"+goodsDesc);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			Map<String, Object> rule = settleRuleService.getRule();
			String divideScale = (Integer) rule.get("dealerPercent")+":"+(Integer) rule.get("mediaPercent")+":"+(Integer) rule.get("salerPercent")+":"+(Integer) rule.get("platformPercent");
			
			GoodsAddOrUpdateCommand command = new GoodsAddOrUpdateCommand(goodsId, dealerId,firstClassify, secondClassify,
				goodsNo, barNo,goodsName, subtitle,
				salePrice, marketPrice, brandId,
				propertyId,transportFeeId, guarantee,
				advertisementPic, gallery, goodsDesc,divideScale);
				goodsApplication.updateGood(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("Goods update Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("Goods update Exception e:", e);
				result = new MResult(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("Goods update Exception e:", e);
				result = new MResult(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public ResponseEntity<MResult> delGoods(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="goodsId",required=true)String goodsId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		
		try {
			goodsApplication.delGood(goodsId);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("Goods del Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods del Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods del Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/updateStatus",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateStatus(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="goodsId",required=true)String goodsId,
			@RequestParam(value="goodStatus",required=true)Integer goodStatus
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		
		if(StringUtil.isEmpty(goodsId)){
			result.setErrorMessage("goodsId为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		if(goodStatus==null){
			result.setErrorMessage("goodStatus为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		
		try {
			goodsApplication.updateStatus(goodsId,goodStatus);
			result.setStatus(MCode.V_200);
		}  catch (NegativeException e) {
			LOGGER.error("Goods updatestaus Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods updatestaus Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods updatestaus Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ResponseEntity<MPager> list(
			@RequestParam(value="token")String token,
			@RequestParam(value="goodsName",required=false)String goodsName,
			@RequestParam(value="dealerName",required=false)String dealerName,
			@RequestParam(value="firstClassify",required=false)String firstClassify,
			@RequestParam(value="secondClassify",required = false)String secondClassify,
			@RequestParam(value="goodsStatus",required = false)Integer goodsStatus,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="mediaId",required=false)String mediaId,
			@RequestParam(value="startTime",required = false)String startTime,
			@RequestParam(value="endTime",required = false)String endTime,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		
		MPager result = new MPager(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		
		try {
			List<Map<String, Object>> goodsList = query.getlist(goodsName,dealerName,firstClassify,secondClassify,goodsStatus,cooperationMode,mediaId,startTime,endTime,rows,pageNum);
			//查询用户总数量
			Integer totalCount = query.getGoodsCount(goodsName,dealerName,firstClassify,secondClassify,goodsStatus,cooperationMode,mediaId,startTime,endTime);
			result.setPager(totalCount, pageNum, rows);
//			String strIn = ObjectSerializer.instance().serialize(goodsList);
//			result.setContent(EncryptUtils.encrypt(strIn, token));
			result.setContent(goodsList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods list Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods list Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public ResponseEntity<MResult> detail(
			@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value="goodsId",required=true)String goodsId){
		MResult result = new MResult(MCode.V_1);
		try {
			Map<String,Object> detailMap = query.getGoodsDetail(goodsId);
			result.setContent(detailMap);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	/**
	 * app商品列表
	 * @param token
	 * @param rows
	 * @param pageNum
	 * @return
	 */
	@RequestMapping(value = "/applist",method = RequestMethod.GET)
	public ResponseEntity<MPager> applist(
			@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value = "goodsName" ,required = false) String goodsName,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		
		MPager result = new MPager(MCode.V_1);
		try {
			Map<String, Object> strIn = new HashMap<String, Object>();
			
			List<Map<String, Object>> goodsList = query.getappGoodslist(goodsName,rows,pageNum);
			strIn.put("goodsList", goodsList);
			//查询用户总数量
			Integer totalCount = query.getGoodsCount(goodsName);
			result.setPager(totalCount, pageNum, rows);
			
			if(goodsName==null || "".equals(goodsName)){
				try {
					
//					List<Map<String, Object>> viewImgs = helpsService.getViewImgs();//获取轮播图
					List<Map<String, Object>> viewImgs = locQuery.getViewImgs();//获取轮播图
					strIn.put("viewImgs", viewImgs);
				} catch (Exception e) {
					LOGGER.error("调用媒体中心的轮播图出错",e);
				}
			}
			result.setContent(strIn);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods list Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods list Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * app商品详情
	 * @param token
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/appDetail",method = RequestMethod.GET)
	public ResponseEntity<MResult> appDetail(
			@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value = "userId" ,required = false,defaultValue="") String userId,
			@RequestParam(value="goodsId",required=true)String goodsId
			){
		MResult result = new MResult(MCode.V_1);
		try {
			Map<String,Object> detailMap = query.getappGoodsDetail(goodsId);
			System.out.println("--------商品详情数据------"+detailMap);
			if(detailMap==null){
				result.setErrorMessage("根据id查询商品不存在");
				result.setStatus(-1);
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			System.out.println("--------商品状态------"+detailMap.get("goodsStatus"));
			Integer goodsStatus = (Integer) detailMap.get("goodsStatus");
			if(goodsStatus==1){
				result.setStatus(10);
				result.setErrorMessage("商品还未上架");
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			if(goodsStatus==2){
				result.setStatus(11);
				result.setErrorMessage("商品已被删除，不存在");
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			if(goodsStatus==4){
				result.setStatus(12);
				result.setErrorMessage("商品已下架");
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			if(goodsStatus==5){
				result.setStatus(13);
				result.setErrorMessage("商品缺货中");
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			
			detailMap.remove("goodsStatus");
			//调用用户中心判断商品
			if(StringUtil.isEmpty(userId)){
				detailMap.put("isFavorite", 0);
				detailMap.put("favoriteId", "");
			}else{
				Map<String, Object> checkIsFavorite = userService.checkIsFavorite(userId, goodsId);
				if(checkIsFavorite==null){
					detailMap.put("favoriteId", "");
					detailMap.put("isFavorite", 0);
				}else{
					detailMap.put("isFavorite", 1);
					detailMap.put("favoriteId", checkIsFavorite.get("favoriteId"));
				}
			}
			
			String appGoodsDesc = goodsDescUri+"?goodsId="+goodsId;
			System.out.println("------------"+appGoodsDesc);
			detailMap.put("appGoodsDesc", appGoodsDesc);
			result.setContent(detailMap);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("商品详情查询出错", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("商品详情查询出错", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 绑定识别图片
	 * @param token
	 * @param goodsId
	 * @param dealerId
	 * @param goodsName
	 * @param dealerName
	 * @param recognizedId
	 * @param recognizedUrl
	 * @return
	 */
	@RequestMapping(value="/recognizedPic",method = RequestMethod.POST)
	public ResponseEntity<MResult> recognizedPic(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="goodsId",required=true)String goodsId,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="goodsName",required=true)String goodsName,
			@RequestParam(value="dealerName",required=true)String dealerName,
			@RequestParam(value="recognizedId",required=true)String recognizedId,
			@RequestParam(value="recognizedUrl",required=true)String recognizedUrl
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
		RecognizedPicCommand command = new RecognizedPicCommand(goodsId,dealerId,goodsName,dealerName,recognizedId,recognizedUrl);

	
			goodsApplication.updateRecognizedPic(command);
			result.setStatus(MCode.V_200);
		}catch (HibernateOptimisticLockingFailureException e) {
			LOGGER.error("已操作成功请不要重复提交", e);
			result = new MResult(400, "已操作成功请不要重复提交");
		}catch (StaleObjectStateException e) {
			LOGGER.error("已操作成功请不要重复提交", e);
			result = new MResult(400, "已操作成功请不要重复提交");
		} 
		catch (NegativeException e) {
			LOGGER.error("保存失败", e);
			result = new MResult(MCode.V_1, "保存失败");
		} catch (IllegalArgumentException e) {
			LOGGER.error("保存失败", e);
			result = new MResult(MCode.V_1, "保存失败");
		} catch (Exception e) {
			LOGGER.error("保存失败", e);
			result = new MResult(MCode.V_1, "保存失败");
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/app/recognizedGoods",method = RequestMethod.GET)
	public ResponseEntity<MResult> recognizedPic(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="recognizedInfo",required=false)String recognizedInfo,
			@RequestParam(value="barNo",required=false)String barNo,
			@RequestParam(value="location",required=false)String location,
			@RequestParam(value = "sn" ,required = true) String sn,
			@RequestParam(value = "os" ,required = true) String os,
			@RequestParam(value = "appVersion" ,required = true) String appVersion,
			@RequestParam(value = "osVersion" ,required = true) String osVersion,
			@RequestParam(value = "triggerTime" ,required = true) long triggerTime,
			@RequestParam(value = "userId" ,required = false,defaultValue="") String userId,
			@RequestParam(value = "userName" ,required = false,defaultValue="") String userName
			){
		MResult result = new MResult(MCode.V_1);
		System.out.println("-----------------------------------------------------------start recognized---------------------------------");
		LOGGER.info("token---"+token);
		LOGGER.info("barNo---"+barNo);
		LOGGER.info("recognizedInfo---"+recognizedInfo);
		LOGGER.info("location---"+location);
		
		try {
			System.out.println("recognizedInfo");
			List<String> recognizedIds = new ArrayList<String>();
			List<Double> scoreList = new ArrayList<Double>();
			if(recognizedInfo!=null && !"".equals(recognizedInfo)){
				
			}
			//解析json
			List<Map<String,Object>> recognizedList = new Gson().fromJson(recognizedInfo, new TypeToken<List<Map<String,Object>>>(){}.getType());
			List<Map<String, Object>> getrecognizedGoods = null;
			//根据条码获取商品媒体资源信息
			Map<String, Object> mediaData = null;
			if(barNo!=null && !"".equals(barNo)){
				try {
					mediaData = mediaService.getMres(null,barNo);
				} catch (Exception e) {
					LOGGER.info("调用媒体中心获取媒体信息出错",e);
				}
			}
			LOGGER.info("-----获取媒体中心数据"+mediaData);
			
			//识别图片列表逻辑
			if(recognizedList!=null && recognizedList.size()>0){
				for (int i = 0; i < recognizedList.size(); i++) {
					 Double score = (Double) recognizedList.get(i).get("score");
					scoreList.add(score);
				}
				//判断识别率高低
				Collections.sort(scoreList);//升序排序
				Collections.reverse(scoreList);//倒序
				if(scoreList.get(0)>0.75){
					recognizedIds.add((String) recognizedList.get(0).get("recognizedId"));
					getrecognizedGoods = query.getrecognizedGoods(recognizedIds,mediaData==null?"":(String)mediaData.get("mresId"));
					//-----------------------------------------------埋点
					if(getrecognizedGoods!=null){//埋点事件
						MDViewGoodsCommand command = new MDViewGoodsCommand(sn, os, appVersion, osVersion, triggerTime, userId, userName, getrecognizedGoods.get(0)==null?"":(String)getrecognizedGoods.get(0).get("goodsId"), getrecognizedGoods.get(0)==null?"":(String)getrecognizedGoods.get(0).get("goodsName"), mediaData==null?"":(String)mediaData.get("mediaId"), mediaData==null?"":(String)mediaData.get("mediaName"), mediaData==null?"":(String)mediaData.get("mresId"), mediaData==null?"":(String)mediaData.get("mresName"));
						getRecognizedMD(command);
					}
					//-----------------------------------------------
					result.setContent(getrecognizedGoods);
					result.setStatus(MCode.V_200);
					return new ResponseEntity<MResult>(result, HttpStatus.OK);
				}else if(scoreList.get(0)>0.01){
					//取前10条
					for (int j = 0; j < scoreList.size()&&j<10; j++) {
						if(scoreList.get(j)>0.01)
							recognizedIds.add((String) recognizedList.get(j).get("recognizedId"));
					}
					getrecognizedGoods = query.getrecognizedGoods(recognizedIds,mediaData==null?"":(String)mediaData.get("mresId"));
					//-----------------------------------------------埋点
					if(getrecognizedGoods!=null){//埋点事件
						MDViewGoodsCommand command = new MDViewGoodsCommand(sn, os, appVersion, osVersion, triggerTime, userId, userName, getrecognizedGoods.get(0)==null?"":(String)getrecognizedGoods.get(0).get("goodsId"), getrecognizedGoods.get(0)==null?"":(String)getrecognizedGoods.get(0).get("goodsName"), mediaData==null?"":(String)mediaData.get("mediaId"), mediaData==null?"":(String)mediaData.get("mediaName"), mediaData==null?"":(String)mediaData.get("mresId"), mediaData==null?"":(String)mediaData.get("mresName"));
						getRecognizedMD(command);
					}
					//-----------------------------------------------埋点
					result.setContent(getrecognizedGoods);
					result.setStatus(MCode.V_200);
					return new ResponseEntity<MResult>(result, HttpStatus.OK);
				}
				
			}
		
			
			Map<String,Object> locationInfo = new Gson().fromJson(location, new TypeToken<Map<String,Object>>(){}.getType());
			//查询经纬度信息
			if(locationInfo!=null){
				String longitude = (String) locationInfo.get("longitude");
				String latitude = (String) locationInfo.get("latitude");
				List<Map<String, Object>> goodsIdList = mediaService.getGoodsList(Double.parseDouble(longitude),Double.parseDouble(latitude));
				List<Map<String,Object>> recognizedlocationGoodsList = new ArrayList<Map<String,Object>>();
				for (int i = 0; i < goodsIdList.size(); i++) {
					String goodsId = (String) goodsIdList.get(i).get("goodsId");
					Map<String, Object> recognizedlocationGoodsMap = query.getRecognizedlocationGoods(goodsId);
					recognizedlocationGoodsMap.put("mresId", mediaData==null?"":(String)mediaData.get("mresId"));
					recognizedlocationGoodsList.add(recognizedlocationGoodsMap);
				}
				//------------------------------埋点
				if(recognizedlocationGoodsList!=null){//埋点事件
					MDViewGoodsCommand command = new MDViewGoodsCommand(sn, os, appVersion, osVersion, triggerTime, userId, userName, getrecognizedGoods==null?"":(String)getrecognizedGoods.get(0).get("goodsId"), getrecognizedGoods==null?"":(String)getrecognizedGoods.get(0).get("goodsName"), mediaData==null?"":(String)mediaData.get("mediaId"), mediaData==null?"":(String)mediaData.get("mediaName"), mediaData==null?"":(String)mediaData.get("mresId"), mediaData==null?"":(String)mediaData.get("mresName"));
					getRecognizedMD(command);
				}
				
				//------------------------------
				result.setContent(recognizedlocationGoodsList);
				result.setStatus(MCode.V_200);
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
		} catch (IllegalArgumentException e) {
			LOGGER.error("recognizedGoods Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("recognizedGoods Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		result.setStatus(MCode.V_200);
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 识别埋点
	 * @param userName 
	 * @param userId 
	 * @param triggerTime 
	 * @param osVersion 
	 * @param appVersion 
	 * @param os 
	 * @param sn 
	 */
	private void getRecognizedMD(MDViewGoodsCommand command) {
		goodsApplication.recognizedMD(command);
	}


	/**
	 * 商品詳情接口
	 * @param token
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/appGoodsDesc",method = RequestMethod.GET)
	public void appGoodsDesc(Writer writer,
			@RequestParam(value="goodsId",required=true)String goodsId){
		try {
			Map<String,Object> descMap = query.getappGoodsDesc(goodsId);
			
			StringBuffer sb = new StringBuffer();
			sb.append("<!doctype html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" /><style>img{max-width:100%; margin : 0;padding :0 ;}</style>");
			sb.append(descMap.get("goodsDesc"));
			writer.write(sb.toString()); 
			writer.close();
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods Detail Exception e:", e);
		} catch (Exception e) {
			LOGGER.error("Goods Detail Exception e:", e);
		}
	}
	
	
	@RequestMapping(value = "/test" ,method = RequestMethod.POST)
	public ResponseEntity<MResult> test(
			@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value="goodsId",required=true)String goodsId,
			@RequestParam(value="goodsNum",required=true)Integer goodsNum){
		MResult result = new MResult(MCode.V_1);
		try {
			goodsApplication.consumeOrderInfo(goodsId, goodsNum);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 查询搜索热词
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/getHotWord" ,method = RequestMethod.GET)
	public ResponseEntity<MResult> getHotWord(@RequestParam(value = "token" ,required = true) String token){
		MResult result = new MResult(MCode.V_1);
		try {
			List<Map<String,Object>> hotWordList = hotWordQuery.getHotWordList();
			result.setContent(hotWordList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 分享商品
	 * @param token
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/shareGoods" ,method = RequestMethod.POST)
	public ResponseEntity<MResult> shareGoods(@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value = "goodsId" ,required = true) String goodsId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
		}
		try {
			goodsApplication.shareNum(goodsId);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods Detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	/**
	 * 商品访问埋点
	 * 
	 */
	@RequestMapping(value = "/mdViewGoods" ,method = RequestMethod.POST)
	public ResponseEntity<MResult> mdViewGoods(@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value = "sn" ,required = true) String sn,
			@RequestParam(value = "os" ,required = true) String os,
			@RequestParam(value = "appVersion" ,required = true) String appVersion,
			@RequestParam(value = "osVersion" ,required = true) String osVersion,
			@RequestParam(value = "triggerTime" ,required = true) long triggerTime,
			@RequestParam(value = "lastTime" ,required = true) long lastTime,
			@RequestParam(value = "userId" ,required = false,defaultValue="") String userId,
			@RequestParam(value = "userName" ,required = false,defaultValue="") String userName,
			@RequestParam(value = "goodsId" ,required = true) String goodsId,
			@RequestParam(value = "goodsName" ,required = true) String goodsName,
			@RequestParam(value = "mediaId" ,required = false,defaultValue="") String mediaId,
			@RequestParam(value = "mediaName" ,required = false,defaultValue="") String mediaName,
			@RequestParam(value = "mresId" ,required = false,defaultValue="") String mresId,
			@RequestParam(value = "mresName" ,required = false,defaultValue="") String mresName
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
		}
		try {
			String areaProvince="";
			String areaDistrict="";
			String provinceCode="";
			String districtCode="";
			if(userId!=null && !"".equals(userId)){
				Map<String, Object> userInfo = userService.getUserInfo(userId);
				areaProvince = (String) userInfo.get("areaProvince");
				areaDistrict = (String) userInfo.get("areaDistrict");
				provinceCode = (String) userInfo.get("provinceCode");
				districtCode = (String) userInfo.get("districtCode");
			}
			MDViewGoodsCommand command = new MDViewGoodsCommand(sn,os,appVersion,osVersion,triggerTime,(lastTime-triggerTime),userId,userName,areaProvince,areaDistrict,provinceCode,districtCode,goodsId,goodsName,mediaId,mediaName,mresId,mresName);
			LOGGER.info("商品访问埋点接口访问"+command);
			goodsApplication.MDViewGoods(command);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("Goods mdViewGoods Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Goods mdViewGoods Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 搜索商品
	 * @param token
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value = "/appSearchGoods",method = RequestMethod.GET)
	public ResponseEntity<MPager> appSearchGoods(
			@RequestParam(value = "token" ,required = true) String token,
			@RequestParam(value = "goodsName" ,required = true) String goodsName,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum,
			@RequestParam(value = "searchrows" ,required = false,defaultValue = "20") Integer searchrows,
			@RequestParam(value = "searchpageNum", required = false ,defaultValue = "1") Integer searchpageNum){
		
		MPager result = new MPager(MCode.V_1);
		try {
			Map<String, Object> strIn = new HashMap<String, Object>();
			List<Map<String, Object>> goodsList = query.getappGoodslist(goodsName,rows,pageNum);//搜索商品列表
			Integer totalCount = query.getGoodsCount(goodsName);//搜索商品的总数
			List<Map<String, Object>> recommendList = new ArrayList<Map<String,Object>>();//推荐商品列表
			strIn.put("goodsList", goodsList);//推荐列表
			if(totalCount>0){//找到商品
			LOGGER.info("找到商品了"+goodsList);
				strIn.put("recommendList", recommendList);
				result.setPager(totalCount, pageNum, rows);
				result.setContent(strIn);
				result.setStatus(MCode.V_200);
				return new ResponseEntity<MPager>(result, HttpStatus.OK);
			}
			LOGGER.info("未找到商品");
				recommendList = locQuery.getRecommendList(searchrows,searchpageNum);//获取推荐列表
				Integer searchtotalCount = locQuery.getRecommendCount(searchrows,searchpageNum);//搜索商品的总数
				strIn.put("recommendList", recommendList);
				result.setPager(searchtotalCount, searchpageNum, searchrows);
				result.setContent(strIn);
				result.setStatus(MCode.V_200);
				return new ResponseEntity<MPager>(result, HttpStatus.OK);
		} catch (IllegalArgumentException e) {
			LOGGER.error("商品搜索出异常", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("商品搜索出异常", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
}
