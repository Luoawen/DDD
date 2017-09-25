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
import cn.m2c.goods.exception.NegativeException;
import cn.m2c.scm.application.goods.goods.SellerApplication;
import cn.m2c.scm.application.goods.goods.query.SpringJdbcSellerQuery;
/**
 * 经销商业务员
 * @author ps
 *
 */
@RestController
@RequestMapping("/dseller")
public class SellerAgent {
	private Logger logger = LoggerFactory.getLogger(SellerAgent.class);
	@Autowired
	SellerApplication sellerApplication;
	@Autowired
	SpringJdbcSellerQuery query;

	
	@RequestMapping(value="/rep",method={RequestMethod.GET})
	public ResponseEntity<MPager> getStaffReport(@RequestParam(value="token")String token,
			@RequestParam(value="sellerName",required=false)String sellerName,
			@RequestParam(value="province",required=false)String province,
			@RequestParam(value="city",required=false)String city,
			@RequestParam(value="area",required=false)String area,
			@RequestParam(value="startTime",required = false)String startTime,
			@RequestParam(value="endTime",required = false)String endTime,
			@RequestParam(value = "rows" ,required = false,defaultValue = "20") Integer rows,
			@RequestParam(value = "pageNum", required = false ,defaultValue = "1") Integer pageNum){
		MPager result = new MPager(MCode.V_1);
		try {
			Integer totalCount = query.getTotalCount(sellerName,province,city,area,startTime,endTime,rows,pageNum);
			result.setPager(totalCount, pageNum, rows);
			List<Map<String,Object>> sellerReport = query.getSllerReport(sellerName,province,city,area,startTime,endTime,rows,pageNum);
				result.setContent(sellerReport);
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("get staff report Exception e:", e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
}