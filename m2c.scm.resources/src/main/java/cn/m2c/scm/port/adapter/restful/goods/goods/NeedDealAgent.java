package cn.m2c.scm.port.adapter.restful.goods.goods;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;


@RestController
@RequestMapping("/needDo")
public class NeedDealAgent {

	private final static Logger LOGGER = LoggerFactory.getLogger(NeedDealAgent.class);
	
	@RequestMapping(value="/list",method = RequestMethod.GET)
	public ResponseEntity<MResult> getNeedDoList(@RequestParam(value="token",required=true)String token ){
		MResult result = new MResult(MCode.V_1);
		
		
		return new ResponseEntity<MResult>(result, HttpStatus.OK);
	}
}
