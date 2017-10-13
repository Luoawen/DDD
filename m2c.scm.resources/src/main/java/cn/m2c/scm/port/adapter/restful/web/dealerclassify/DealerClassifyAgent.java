package cn.m2c.scm.port.adapter.restful.web.dealerclassify;

import java.util.List;

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
import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;
import cn.m2c.scm.application.dealerclassify.query.DealerClassifyQuery;

@RestController
@RequestMapping("/dealerclassify/sys")
public class DealerClassifyAgent {
	private final static Logger log = LoggerFactory.getLogger(DealerClassifyAgent.class);
	@Autowired
	DealerClassifyQuery dealerClassifyQuery;
	
	
	 @RequestMapping(value = "/firstClassify", method = RequestMethod.GET)
	    public ResponseEntity<MResult> firstClassify() {
		 MResult result = new MResult(MCode.V_1);
	        try {
	        	List<DealerClassifyBean> firstClassifyBeanList = dealerClassifyQuery.getFirstClassifyList();
	        	result.setContent(firstClassifyBeanList);
	            result.setStatus(MCode.V_200);
	        } catch (Exception e) {
	        	log.error("一级分类列表出错", e);
	            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
	        }
	        return new ResponseEntity<MResult>(result, HttpStatus.OK);
	    }
	 
	 @RequestMapping(value = "/secondClassify", method = RequestMethod.GET)
	    public ResponseEntity<MResult> secondClassify(@RequestParam(value="dealerFirstClassifyId",required=true)String dealerFirstClassifyId) {
		 MResult result = new MResult(MCode.V_1);
	        try {
	        	List<DealerClassifyBean> secondClassifyBeanList = dealerClassifyQuery.getSecondClassifyList(dealerFirstClassifyId);
	        	result.setContent(secondClassifyBeanList);
	            result.setStatus(MCode.V_200);
	        } catch (Exception e) {
	        	log.error("二级分类列表出错", e);
	            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
	        }
	        return new ResponseEntity<MResult>(result, HttpStatus.OK);
	    }
}
