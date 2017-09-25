package cn.m2c.scm.port.adapter.restful.order.logger;

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

import cn.m2c.common.EncryptUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.application.order.logger.query.SpringJdbcOperLoggerQuery;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.port.adapter.restful.order.comment.CommentAgent;

/**
 * 
 * @ClassName: OperLoggerAgent
 * @Description: 日子
 * @author moyj
 * @date 2017年7月13日 下午7:58:57
 *
 */
@RestController
@RequestMapping(value = "/logger")
public class OperLoggerAgent {
	
	private static final Logger logger = LoggerFactory.getLogger(CommentAgent.class);
	
	@Autowired
	private SpringJdbcOperLoggerQuery springJdbcOperLoggerQuery;	
	
	/**
	 * 日志列表
	 * @param token
	 * @param orderId			//订单编号
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public ResponseEntity<MPager> list(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "businessId" ,required=false) String businessId,
			@RequestParam(value = "businessType" ,required=false) Integer businessType,
			@RequestParam(value = "pageNumber", defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
		String bussData = "[]";
 		try {			
 			List<?> list = new ArrayList<Object>();
 			list = springJdbcOperLoggerQuery.list(businessId,businessType, pageNumber, rows);
 			Integer count = springJdbcOperLoggerQuery.listCount(businessId,businessType);
 	 	
 			if(list != null && list.size() > 0){
 				bussData = new ObjectSerializer(true).serialize(list);
 	 			bussData = EncryptUtils.encrypt(bussData, token);
 			}	
 			
 			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			result.setContent(bussData);
 			
 			if(isEncry){
 				result.setContent(bussData);
 			}else{
 				result.setContent(list);
 			}
			result.setStatus(MCode.V_200);
 		} catch (NegativeException e) {
			logger.error("StatementBillAgent add Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(),e.getMessage());
		}catch (Exception e) {
			logger.error("CommentAgent list Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400,e.getMessage());
		}	
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}

}
