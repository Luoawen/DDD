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
import cn.m2c.scm.application.goods.goods.query.SpringJdbcGuaranteeQuery;


@RestController
@RequestMapping("/guarantee")
public class GuaranteeAgent {
	private final static Logger LOGGER = LoggerFactory.getLogger(GuaranteeAgent.class);
	@Autowired
	SpringJdbcGuaranteeQuery query;
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResponseEntity<MResult> getGuaranteeList(@RequestParam(value="token",required=true)String token ){
		MResult result = new MResult(MCode.V_1);
		if(StringUtil.isEmpty(token)){
			result.setErrorMessage("token不能为空");
			return new ResponseEntity<MResult>(result, HttpStatus.OK);
		}
		try {
			List<Map<String, Object>> guaranteeList = query.getGuaranteeList();
			result.setContent(guaranteeList);
			result.setStatus(MCode.V_200);
		} catch (Exception e) {
			LOGGER.error("查询保障列表出错");
			result.setStatus(MCode.V_400);
		}
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
