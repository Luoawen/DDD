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
import cn.m2c.common.MResult;
import cn.m2c.common.StringUtil;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGoodsClassifyQuery;

@RestController
@RequestMapping("/classify")
public class ClassifyAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(ClassifyAgent.class);
	@Autowired
	private SpringJdbcGoodsClassifyQuery classifyQuery;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResponseEntity<MResult> getClassifyList(@RequestParam(value="token",required=true)String token ){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			List<Map<String,Object>> classifyList = classifyQuery.getClassifyList();
			result.setContent(classifyList);
			result.setStatus(MCode.V_200);
		}catch (IllegalArgumentException e) {
			LOGGER.error("classify list Exception e:", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("classify list Exception e:", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	     
	/**
	 * 查询所有的一级分类
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/firstClassifyList",method = RequestMethod.GET)
	public ResponseEntity<MResult> getFirstClassifyList(@RequestParam(value="token",required=true)String token ){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			List<Map<String,Object>> firstClassifyList = classifyQuery.getFirstClassifyList();
			result.setContent(firstClassifyList);
			result.setStatus(MCode.V_200);
		}catch (IllegalArgumentException e) {
			LOGGER.error("一级分类查询出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("一级分类查询出错", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	/**
	 * 根据一级分类查询二级分类
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/secondClassifyList",method = RequestMethod.GET)
	public ResponseEntity<MResult> getSecondClassifyList(
			@RequestParam(value="token",required=true)String token ,
			@RequestParam(value="goodsClassifyId",required=true)String firstClassifyId 
			){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			List<Map<String,Object>> secondClassifyList = classifyQuery.getSecondClassifyList(firstClassifyId);
			result.setContent(secondClassifyList);
			result.setStatus(MCode.V_200);
		}catch (IllegalArgumentException e) {
			LOGGER.error("一级分类查询出错", e);
			result = new MResult(MCode.V_1, e.getMessage());
		} catch (Exception e) {
			LOGGER.error("一级分类查询出错", e);
			result = new MResult(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
	
	
	
}
