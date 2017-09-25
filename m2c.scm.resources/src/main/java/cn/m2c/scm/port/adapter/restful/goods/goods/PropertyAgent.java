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
import cn.m2c.scm.application.goods.goods.PropertyApplication;
import cn.m2c.scm.application.goods.goods.command.PropertyAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcPropertyQuery;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * 品牌模板管理
 * @author ps
 *
 */
@RestController
@RequestMapping("/property")
public class PropertyAgent {
	@Autowired
	PropertyApplication propertyApplication;
	@Autowired
	SpringJdbcPropertyQuery query;
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PropertyAgent.class);
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addProperty(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="propertyValue",required=true)String propertyValue,
			@RequestParam(value="dealerId",required=true)String dealerId 
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		String propertyId = IDGenerator.get(IDGenerator.GOODS_Property_PREFIX_TITLE);
			try {
				PropertyAddOrUpdateCommand command = new PropertyAddOrUpdateCommand(propertyId,modelName,propertyValue,dealerId);
				propertyApplication.addProperty(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("property delete Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("property delete Exception e:", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("property delete Exception e:", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateProperty(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="propertyId",required=true)String propertyId,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="propertyValue",required=true)String propertyValue){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			PropertyAddOrUpdateCommand command = new PropertyAddOrUpdateCommand(propertyId,modelName,propertyValue,dealerId);
				propertyApplication.updateProperty(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("property update Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("property update Exception e:", e);
				result = new MPager(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("property update Exception e:", e);
				result = new MPager(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public ResponseEntity<MResult> delProperty(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="propertyId",required=true)String propertyId
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			propertyApplication.delProperty(propertyId);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("property del Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("property del Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("property del Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ResponseEntity<MPager> listProperty(@RequestParam(value="token",required=true)String token,
			@RequestParam(value = "dealerId" ,required = false) String dealerId,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		try {
			List<Map<String, Object>> propertyList = query.getlist(dealerId,rows,pageNum);
			Integer totalCount = query.getPropertyCount();
			result.setPager(totalCount, pageNum, rows);
			result.setContent(propertyList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("property list Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("property list Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
}
