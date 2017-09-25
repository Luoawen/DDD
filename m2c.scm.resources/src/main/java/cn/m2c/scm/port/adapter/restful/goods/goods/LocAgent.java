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
import cn.m2c.scm.application.goods.goods.LocApplication;
import cn.m2c.scm.application.goods.goods.command.LocationAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcLocQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;


@RestController
@RequestMapping("/location")
public class LocAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(LocAgent.class);
	
	@Autowired
	LocApplication locApplication;
	@Autowired
	SpringJdbcLocQuery locQuery;
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addLocation(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="locSelect",required=true)Integer locSelect,
			@RequestParam(value="title",required=false)String title,
			@RequestParam(value="isOnLine",required=false)Integer isOnline,
			@RequestParam(value="effectiveTime",required=false)String effectiveTime, 
			@RequestParam(value="displayOrder",required=true)Integer displayOrder, 
			@RequestParam(value="imgUrl",required=false)String imgUrl, 
			@RequestParam(value="locType",required=false)Integer locType, 
			@RequestParam(value="redirectUrl",required=false)String redirectUrl, 
			@RequestParam(value="goodsId",required=false)String goodsId, 
			@RequestParam(value="goodsName",required=false)String goodsName,
			@RequestParam(value="goodsPrice",required=false)Long goodsPrice
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		String locationId = IDGenerator.get(IDGenerator.LOCATION_ID);
			try {
				LocationAddOrUpdateCommand command = new LocationAddOrUpdateCommand(locationId,locSelect,title,isOnline,effectiveTime,displayOrder,imgUrl,locType,redirectUrl,goodsId,goodsName,goodsPrice);
				locApplication.add(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("添加位置参数出错", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("添加位置出错", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("添加位置出错", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateLocation(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="locationId",required=true)String locationId,
			@RequestParam(value="locSelect",required=true)Integer locSelect,
			@RequestParam(value="title",required=false)String title,
			@RequestParam(value="isOnLine",required=false)Integer isOnline,
			@RequestParam(value="effectiveTime",required=false)String effectiveTime, 
			@RequestParam(value="displayOrder",required=true)Integer displayOrder, 
			@RequestParam(value="imgUrl",required=false)String imgUrl, 
			@RequestParam(value="locType",required=false)Integer locType, 
			@RequestParam(value="redirectUrl",required=false)String redirectUrl, 
			@RequestParam(value="goodsId",required=false)String goodsId, 
			@RequestParam(value="goodsName",required=false)String goodsName,
			@RequestParam(value="goodsPrice",required=false)Long goodsPrice
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
			try {
				LocationAddOrUpdateCommand command = new LocationAddOrUpdateCommand(locationId,locSelect,title,isOnline,effectiveTime,displayOrder,imgUrl,locType,redirectUrl,goodsId,goodsName,goodsPrice);
				locApplication.update(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("修改位置参数出错", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("修改位置出错", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("修改位置出错", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/del",method = RequestMethod.POST)
	public ResponseEntity<MResult> delLocation(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="locationId",required=true)String locationId){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			locApplication.del(locationId);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("删除位置出错", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("删除位置出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("删除位置出错", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	 
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResponseEntity<MPager> listLocation(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="title",required=false)String title,
			@RequestParam(value="isOnLine",required=false)Integer isOnLine,
			@RequestParam(value="locSelect",required=false)Integer locSelect,
			@RequestParam(value="locType",required=false)Integer locType,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		try {
			List<Map<String, Object>> locList = locQuery.getLocList(title,isOnLine,locSelect,locType,rows,pageNum);
			Map<String, Object> locCount = locQuery.getLocCount(title,isOnLine,locSelect,locType,rows,pageNum);
			Long count = (Long) locCount.get("LocCount");
			result.setPager(count.intValue(), pageNum, rows);
			result.setContent(locList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("查询位置列表出错", e);
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
		
	}
	
	
	
}
