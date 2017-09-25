package cn.m2c.scm.port.adapter.restful.order.expco;
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
import cn.m2c.scm.application.order.order.query.SpringJdbcExpCompanyQuery;
import cn.m2c.scm.domain.NegativeException;

@RestController
@RequestMapping(value = "/exp/company")
public class ExpCompanyAgent {

	private static final Logger logger = LoggerFactory.getLogger(ExpCompanyAgent.class);
	@Autowired
	private SpringJdbcExpCompanyQuery springJdbcExpCompanyQuery;
	
	/**
	 * 列表
	 * @param keyword		//关键字
	 * @param endTime		//下单开始时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> list(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "keyword" ,required=false) String keyword,
			@RequestParam(value = "pageNumber",defaultValue="1") Integer pageNumber,
			@RequestParam(value = "rows",defaultValue="10") Integer rows,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			List<?> list = springJdbcExpCompanyQuery.list(keyword, pageNumber, rows);			
 			Integer count = springJdbcExpCompanyQuery.listCount(keyword);
 				 			
 			result.setPager(count.intValue(), pageNumber.intValue(), rows.intValue());
 			result.setContent(list);
 			if(isEncry){
 				if(list != null && list.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(list);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
	
	/**
	 * 列表
	 * @param keyword		//关键字
	 * @param endTime		//下单开始时间
	 * @param pageNumber
	 * @param rows
	 * @param isEncry
	 * @return
	 */
	@RequestMapping(value = "/list/all", method = { RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<MPager> listAll(
			@RequestParam(value = "token") String token,
			@RequestParam(value = "isEncry",defaultValue="true") boolean isEncry
			){
		MPager result = new MPager(MCode.V_1);
 		try {		
 			List<?> list = springJdbcExpCompanyQuery.listAll();			
 			Integer count = springJdbcExpCompanyQuery.listAllCount();
 				 			
 			result.setPager(count.intValue(), 1, count);
 			result.setContent(list);
 			if(isEncry){
 				if(list != null && list.size() > 0){
 	 				String bussData = new ObjectSerializer(true).serialize(list);
 	 	 			bussData = EncryptUtils.encrypt(bussData, token);
 	 	 			result.setContent(bussData);
 	 			}else{
 	 				result.setContent("[]");
 	 			}
 			}
			result.setStatus(MCode.V_200);
		} catch (NegativeException e) {
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(e.getStatus(), e.getMessage());
		}catch(Exception e){
			e.printStackTrace();
			logger.error("Order commit Exception e:" + e.getMessage(), e);
			result = new MPager(MCode.V_400, e.getMessage());
		}
		return new ResponseEntity<MPager>(result, HttpStatus.OK);
	}
}
