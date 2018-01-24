package cn.m2c.scm.port.adapter.restful.web.dealerclassify;

import java.util.ArrayList;
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
import cn.m2c.scm.application.dealer.data.representation.DealerClassifyTreeRepresentation;
import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyBean;
import cn.m2c.scm.application.dealerclassify.data.bean.DealerClassifyedBean;
import cn.m2c.scm.application.dealerclassify.data.representation.DealerFirstClassifyRepresentation;
import cn.m2c.scm.application.dealerclassify.data.representation.DealerSecondClassifyRepresentation;
import cn.m2c.scm.application.dealerclassify.query.DealerClassifyQuery;
import cn.m2c.scm.domain.NegativeException;

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
	        	List<DealerFirstClassifyRepresentation> firstList = new ArrayList<DealerFirstClassifyRepresentation>();
	        	List<DealerClassifyBean> firstClassifyBeanList = dealerClassifyQuery.getFirstClassifyList();
	        	for (DealerClassifyBean dealerClassifyBean : firstClassifyBeanList) {
	        		firstList.add(new DealerFirstClassifyRepresentation(dealerClassifyBean));
				}
	        	result.setContent(firstList);
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
	        	List<DealerSecondClassifyRepresentation> secondList = new ArrayList<DealerSecondClassifyRepresentation>();
	        	List<DealerClassifyBean> secondClassifyBeanList = dealerClassifyQuery.getSecondClassifyList(dealerFirstClassifyId);
	        	for (DealerClassifyBean dealerClassifyBean : secondClassifyBeanList) {
	        		secondList.add(new DealerSecondClassifyRepresentation(dealerClassifyBean));
				}
	        	result.setContent(secondList);
	            result.setStatus(MCode.V_200);
	        } catch (Exception e) {
	        	log.error("二级分类列表出错", e);
	            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
	        }
	        return new ResponseEntity<MResult>(result, HttpStatus.OK);
	    }
	 
	 /**
	  * 获取商家分类树
	  * @return
	  */
	 @RequestMapping(value = "/tree", method = RequestMethod.GET)
	    public ResponseEntity<MResult> classifyTree() {
		 MResult result = new MResult(MCode.V_1);
	        try {
	        	List<DealerClassifyTreeRepresentation> treeList = new ArrayList<DealerClassifyTreeRepresentation>();
	        	List<DealerClassifyBean> firstClassifyBeanList = dealerClassifyQuery.getFirstClassifyList();
	        	for (DealerClassifyBean dealerClassifyBean : firstClassifyBeanList) {
	        		//查询二级分类
	        		List<DealerClassifyBean> secondClassifyBeanList = dealerClassifyQuery.getSecondClassifyList(dealerClassifyBean.getDealerClassifyId());
	        		treeList.add(new DealerClassifyTreeRepresentation(dealerClassifyBean,secondClassifyBeanList));
				}
	        	result.setContent(treeList);
	            result.setStatus(MCode.V_200);
	        } catch (Exception e) {
	        	log.error("商家分类树", e);
	            result = new MPager(MCode.V_400, "服务器开小差了，请稍后再试");
	        }
	        return new ResponseEntity<MResult>(result, HttpStatus.OK);
	    }
	 
	 
	 /**
	  * 获取商家所有二级分类
	  * @return
	  */
	 @RequestMapping(value = "/dealer/all/second/classify",method = RequestMethod.GET)
	 public ResponseEntity<MResult> classifyLevelTwo(){
		 MResult result = new MResult(MCode.V_1);
		 try {
			List<DealerClassifyedBean> list = dealerClassifyQuery.getDealerClassify();
			 result.setContent(list);
			 result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			log.error("获取所有商家二级分类",e.getMessage());
			result = new MResult(MCode.V_400,"查询失败");
		}
		 
		 return new ResponseEntity<MResult>(result, HttpStatus.OK);
	 }
	 
	 
}
