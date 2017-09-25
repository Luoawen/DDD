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
import cn.m2c.goods.domain.IDGenerator;
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.BrandApplication;
import cn.m2c.scm.application.goods.goods.command.BrandAddOrUpdateCommand;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcBrandQuery;

/**
 * 
 * 品牌模板管理
 * @author ps
 *
 */
@RestController
@RequestMapping("/brand")
public class BrandAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(BrandAgent.class);
	@Autowired
	BrandApplication brandApplication;
	@Autowired
	SpringJdbcBrandQuery query;
	
	
	@RequestMapping(value="/add",method = RequestMethod.POST)
	public ResponseEntity<MResult> addBrand(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="brandName",required=true)String brandName,
			@RequestParam(value="brandPic",required=true)String brandPic,
			@RequestParam(value="brandDesc",required=true)String brandDesc){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		String brandId = IDGenerator.get(IDGenerator.GOODS_BRAND_PREFIX_TITLE);
			try {
				BrandAddOrUpdateCommand command = new BrandAddOrUpdateCommand(brandId,modelName,brandName,brandPic,brandDesc,dealerId);
				brandApplication.addBrand(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("brand add Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("brand add Exception e:", e);
				result = new MResult(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("brand add Exception e:", e);
				result = new MResult(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/update",method = RequestMethod.POST)
	public ResponseEntity<MResult> updateBrand(@RequestParam(value="token",required=true)String token,
			@RequestParam(value="brandId",required=true)String brandId,
			@RequestParam(value="dealerId",required=true)String dealerId,
			@RequestParam(value="modelName",required=true)String modelName,
			@RequestParam(value="brandName",required=true)String brandName,
			@RequestParam(value="brandPic",required=true)String brandPic,
			@RequestParam(value="brandDesc",required=true)String brandDesc){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
			try {
				BrandAddOrUpdateCommand command = new BrandAddOrUpdateCommand(brandId,modelName,brandName,brandPic,brandDesc,dealerId);
				brandApplication.updateBrand(command);
				result.setStatus(MCode.V_200);
			} catch (NegativeException e) {
				LOGGER.error("brand update Exception e:", e);
				result = new MResult(e.getStatus(), e.getMessage());
			} catch (IllegalArgumentException e) {
				LOGGER.error("brand update Exception e:", e);
				result = new MResult(MCode.V_1, e.getMessage());
			} catch (Exception e) {
				LOGGER.error("brand update Exception e:", e);
				result = new MResult(MCode.V_400, e.getMessage());
			}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/del",method=RequestMethod.POST)
	public ResponseEntity<MResult> delBrand(
			@RequestParam(value="token",required=true)String token,
			@RequestParam(value="brandId",required=true)String brandId
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			brandApplication.delBrand(brandId);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			LOGGER.error("brand delete Exception e:", e);
			result = new MResult(e.getStatus(), e.getMessage());
		} catch (IllegalArgumentException e) {
			LOGGER.error("brand delete Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("brand delete Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	public ResponseEntity<MPager> listBrand(@RequestParam(value="token",required=true)String token,
			@RequestParam(value = "dealerId" ,required = false) String dealerId,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token为空");
			return new ResponseEntity<MPager>(result, HttpStatus.OK);
		}
		
		try {
			List<Map<String, Object>> brandList = query.getlist(dealerId,rows,pageNum);
			Integer totalCount = query.getBrandCount();
			result.setPager(totalCount, pageNum, rows);
			result.setContent(brandList);
			result.setStatus(MCode.V_200);
		} catch (IllegalArgumentException e) {
			LOGGER.error("brand delete Exception e:", e);
			result = new MPager(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("brand delete Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
}
