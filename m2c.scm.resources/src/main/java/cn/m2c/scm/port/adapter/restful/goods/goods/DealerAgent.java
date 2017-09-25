package cn.m2c.scm.port.adapter.restful.goods.goods;

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

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.common.MResult;
import cn.m2c.common.StringUtil;
import cn.m2c.scm.application.goods.goods.DealerApplication;
import cn.m2c.scm.application.goods.goods.command.DealerAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcDealerQuery;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGoodsQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.users.interfaces.dubbo.UserService;
/**
 * 经销商管理
 * @author ps
 *
 */
@RestController
@RequestMapping("/dealer")
public class DealerAgent {
	private Logger logger = LoggerFactory.getLogger(DealerAgent.class);
	@Autowired
	UserService userService;
	@Autowired
	DealerApplication dealerApplication;
	@Autowired
	SpringJdbcDealerQuery query;
	@Autowired
	SpringJdbcGoodsQuery goodsQuery;

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addDealer(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="groupType",required = true)Integer groupType,
			@RequestParam(value="mobile",required = true)String mobile,
			@RequestParam(value="password",required = true)String password,
			@RequestParam(value="username",required = true)String username,
			@RequestParam(value="sex",required = true)Integer sex,
			@RequestParam(value="age",required = true)Integer age,
			@RequestParam(value="areaProvince",required = false)String areaProvince,
			@RequestParam(value="areaDistrict",required = false)String areaDistrict,
			@RequestParam(value="provinceCode",required = false)String provinceCode,
			@RequestParam(value="districtCode",required = false)String districtCode,
			@RequestParam(value="icon",required = false)String icon,
			@RequestParam(value="dealerName",required = true)String dealerName,
			@RequestParam(value="firstClassification",required = false)String firstClassification,
			@RequestParam(value="secondClassification",required = false)String secondClassification,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="dealerProvince")String dealerProvince,
			@RequestParam(value="dealerCity")String dealerCity,
			@RequestParam(value="dealerarea")String dealerarea,
			@RequestParam(value="dealerPcode")String dealerPcode,
			@RequestParam(value="dealerCcode")String dealerCcode,
			@RequestParam(value="dealerAcode")String dealerAcode,
			@RequestParam(value="detailAddress",required = false)String detailAddress,
			@RequestParam(value="dealerMobile",required = true)String dealerMobile,
			@RequestParam(value="sellerId",required = false)String sellerId){
		MResult result = new MResult(MCode.V_1);
		String dealerId = IDGenerator.get(IDGenerator.DEALER_PREFIX_TITLE);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		
		
		try {
			Map<String, Object> userServiceResult = userService.addOrUpdateUser(groupType, mobile, password, username, sex, age, areaProvince, areaDistrict, provinceCode,districtCode,icon,"","",dealerId,dealerName,"","","");
			String userId = "";
			if(userServiceResult!=null){
				boolean isSucc = (boolean) userServiceResult.get("status");
				userId = (String) userServiceResult.get("userId");
				logger.info("-----------------新增经销商用户"+userId);
				if(!isSucc){
					result.setErrorMessage("添加经销商用户失败");
					result.setStatus(MCode.V_400);
					return new ResponseEntity<MResult>(result, HttpStatus.OK);
				}
			}
			DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,mobile,dealerName,firstClassification,secondClassification,cooperationMode,dealerProvince,dealerCity,dealerarea,dealerPcode,dealerCcode,dealerAcode,detailAddress,dealerMobile,sellerId,userId,username);
			dealerApplication.add(command);
			result.setStatus(MCode.V_200);
		}  catch (NegativeException e) {
			logger.error("dealer add Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("dealer add Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer add Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateDealer(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required = true)String dealerId,
			@RequestParam(value="groupType",required = true)Integer groupType,
			@RequestParam(value="mobile",required = true)String mobile,
			@RequestParam(value="password",required = false)String password,
			@RequestParam(value="username",required = true)String username,
			@RequestParam(value="sex",required = true)Integer sex,
			@RequestParam(value="age",required = true)Integer age,
			@RequestParam(value="areaProvince",required = false)String areaProvince,
			@RequestParam(value="areaDistrict",required = false)String areaDistrict,
			@RequestParam(value="provinceCode",required = false)String provinceCode,
			@RequestParam(value="districtCode",required = false)String districtCode,
			@RequestParam(value="icon",required = false)String icon,
			@RequestParam(value="dealerName",required = true)String dealerName,
			@RequestParam(value="firstClassification",required = false)String firstClassification,
			@RequestParam(value="secondClassification",required = false)String secondClassification,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="dealerProvince",required = true)String dealerProvince,
			@RequestParam(value="dealerCity",required = true)String dealerCity,
			@RequestParam(value="dealerarea",required = true)String dealerarea,
			@RequestParam(value="dealerPcode")String dealerPcode,
			@RequestParam(value="dealerCcode")String dealerCcode,
			@RequestParam(value="dealerAcode")String dealerAcode,
			@RequestParam(value="detailAddress",required = false)String detailAddress,
			@RequestParam(value="dealerMobile",required = true)String dealerMobile,
			@RequestParam(value="sellerId",required = false)String sellerId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			Map<String, Object> userServiceResult = userService.addOrUpdateUser(groupType, mobile, password, username, sex, age, areaProvince, areaDistrict,provinceCode, districtCode,icon,"","",dealerId,dealerName,"","","");
			String userId = "";
			if(userServiceResult!=null){
				
				boolean isSucc = (boolean) userServiceResult.get("status");
				 userId = (String) userServiceResult.get("userId");
				if(!isSucc){
					result.setErrorMessage("添加经销商用户失败");
					result.setStatus(MCode.V_400);
				}
			}
			DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,mobile,dealerName,firstClassification,secondClassification,cooperationMode,dealerProvince,dealerCity,dealerarea,dealerPcode,dealerCcode,dealerAcode,detailAddress,dealerMobile,sellerId,userId,username);
			dealerApplication.update(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("dealer update Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("dealer update Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer update Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	@RequestMapping(value = "/del",method = RequestMethod.POST)
	public ResponseEntity<MResult> delDealer(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required = true)String dealerId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		if(dealerId==null){
			result.setErrorMessage("供应商id不能为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			Integer goodsCount = goodsQuery.checkDealerGoods(dealerId);
			if(goodsCount>0){
				result.setErrorMessage("此供应商还有商品未下架");
				result.setStatus(MCode.V_400);
				return new ResponseEntity<MResult>(result, HttpStatus.OK);
			}
			dealerApplication.delDealer(dealerId);
			result.setStatus(MCode.V_200);
		}catch (NegativeException e) {
			logger.error("dealer del Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("dealer del Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer del Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	public ResponseEntity<MPager> list(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="mobile",required=false)String mobile,
			@RequestParam(value="dealerName",required=false)String dealerName,
			@RequestParam(value="dealerPcode",required=false)String dealerPcode,
			@RequestParam(value="dealerCcode",required = false)String dealerCcode,
			@RequestParam(value="dealerAcode",required = false)String dealerAcode,
			@RequestParam(value="secondClassification",required = false)String secondClassification,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="sellerFilter",required=false)String sellerFilter,
			@RequestParam(value="detailAddress",required = false)String detailAddress,
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
			List<Map<String, Object>> dealerList = query.getlist(mobile,dealerName,dealerPcode,dealerCcode,dealerAcode,secondClassification,cooperationMode,sellerFilter,detailAddress,startTime,endTime,rows,pageNum);
			//查询用户总数量
			Integer totalCount = query.getDealerCount(mobile,dealerName,dealerPcode,dealerCcode,dealerAcode,secondClassification,cooperationMode,sellerFilter,detailAddress,startTime,endTime);
			result.setPager(totalCount, pageNum, rows);
			result.setContent(dealerList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			logger.error("dealer list Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer list Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/detail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getDealer(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(dealerId)){
			result.setErrorMessage("dealerId为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			Map<String, Object> dealerInfo = query.getDealerInfo(dealerId);
			result.setStatus(MCode.V_200);
			result.setContent(dealerInfo);
		} catch (IllegalArgumentException e) {
			logger.error("dealer detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/app/detail",method = RequestMethod.GET)
	public ResponseEntity<MResult> getappDealer(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="userId",required=true)String userId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		if(StringUtil.isEmpty(userId)){
			result.setErrorMessage("userId为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			Map<String, Object> dealerInfo = query.getAppDealerInfo(userId);
			result.setStatus(MCode.V_200);
			result.setContent(dealerInfo);
		} catch (IllegalArgumentException e) {
			logger.error("dealer detail Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("dealer detail Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	/**
	 * 新增接口添加经销商
	 * @param token
	 * @param mobile
	 * @param userId
	 * @param username
	 * @param dealerName
	 * @param firstClassification
	 * @param secondClassification
	 * @param cooperationMode
	 * @param dealerProvince
	 * @param dealerCity
	 * @param dealerarea
	 * @param dealerPcode
	 * @param dealerCcode
	 * @param dealerAcode
	 * @param detailAddress
	 * @param dealerMobile
	 * @param sellerId
	 * @return
	 */
	@RequestMapping(value = "/addDealer",method = RequestMethod.POST)
	public ResponseEntity<MResult> addDealer(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="userId",required = false)String userId,
			@RequestParam(value="mobile",required = false)String mobile,
			@RequestParam(value="username",required = false)String username,
			@RequestParam(value="dealerName",required = true)String dealerName,
			@RequestParam(value="firstClassification",required = false)String firstClassification,
			@RequestParam(value="secondClassification",required = false)String secondClassification,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="dealerProvince")String dealerProvince,
			@RequestParam(value="dealerCity")String dealerCity,
			@RequestParam(value="dealerarea")String dealerarea,
			@RequestParam(value="dealerPcode")String dealerPcode,
			@RequestParam(value="dealerCcode")String dealerCcode,
			@RequestParam(value="dealerAcode")String dealerAcode,
			@RequestParam(value="detailAddress",required = false)String detailAddress,
			@RequestParam(value="dealerMobile",required = true)String dealerMobile,
			@RequestParam(value="sellerId",required = false)String sellerId){
		MResult result = new MResult(MCode.V_1);
		String dealerId = IDGenerator.get(IDGenerator.DEALER_PREFIX_TITLE);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			if(mobile!=null && !"".equals(mobile)){
				if(userId==null || "".equals(userId)){
					Map<String,Object> userIdMap = userService.getByMobile(mobile);
					userId = (String) userIdMap.get("userId");
					if(userId==null || "".equals(userId)){
						result.setErrorMessage("用户id无法拿到");
						return new ResponseEntity<MResult>(result, HttpStatus.OK);
					}
				}
				System.out.println("拿到用户id====="+userId);
				Boolean isAddDealer = userService.addOrUpdateUser(userId, 4, mobile, username, "", "", dealerId, dealerName, "", "", "");
				if(!isAddDealer){
					result.setErrorMessage("更新用户中心信息失败");
					return new ResponseEntity<MResult>(result, HttpStatus.OK);
				}
			}
			DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,mobile,dealerName,firstClassification,secondClassification,cooperationMode,dealerProvince,dealerCity,dealerarea,dealerPcode,dealerCcode,dealerAcode,detailAddress,dealerMobile,sellerId,userId,username);
			dealerApplication.add(command);
			result.setStatus(MCode.V_200);
		}  catch (NegativeException e) {
			logger.error("添加经销商出错", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("添加经销商出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("添加经销商出错", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/updateDealer",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateDealer(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required = true)String dealerId,
			@RequestParam(value="userId",required = false)String userId,
			@RequestParam(value="mobile",required = false)String mobile,
			@RequestParam(value="username",required = false)String username,
			@RequestParam(value="dealerName",required = true)String dealerName,
			@RequestParam(value="firstClassification",required = false)String firstClassification,
			@RequestParam(value="secondClassification",required = false)String secondClassification,
			@RequestParam(value="cooperationMode",required = false)Integer cooperationMode,
			@RequestParam(value="dealerProvince",required = true)String dealerProvince,
			@RequestParam(value="dealerCity",required = true)String dealerCity,
			@RequestParam(value="dealerarea",required = true)String dealerarea,
			@RequestParam(value="dealerPcode")String dealerPcode,
			@RequestParam(value="dealerCcode")String dealerCcode,
			@RequestParam(value="dealerAcode")String dealerAcode,
			@RequestParam(value="detailAddress",required = false)String detailAddress,
			@RequestParam(value="dealerMobile",required = true)String dealerMobile,
			@RequestParam(value="sellerId",required = false)String sellerId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			if(mobile!=null && !"".equals(mobile)){
				if(userId==null || "".equals(userId)){
					Map<String,Object> userIdMap = userService.getByMobile(mobile);
					userId = (String) userIdMap.get("userId");
					if(userId==null || "".equals(userId)){
						result.setErrorMessage("用户id无法拿到");
						return new ResponseEntity<MResult>(result, HttpStatus.OK);
					}
				}
				System.out.println("--------------"+userId);
				Boolean isAddDealer = userService.addOrUpdateUser(userId, 4, mobile, username, "", "", dealerId, dealerName, "", "", "");
				if(!isAddDealer){
					result.setErrorMessage("更新用户中心信息失败");
					return new ResponseEntity<MResult>(result, HttpStatus.OK);
				}
			}
			DealerAddOrUpdateCommand command = new DealerAddOrUpdateCommand(dealerId,mobile,dealerName,firstClassification,secondClassification,cooperationMode,dealerProvince,dealerCity,dealerarea,dealerPcode,dealerCcode,dealerAcode,detailAddress,dealerMobile,sellerId,userId,username);
			dealerApplication.update(command);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("更新经销商出错", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("更新经销商出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			logger.error("更新经销商出错", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
}
