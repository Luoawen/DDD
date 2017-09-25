package cn.m2c.scm.port.adapter.job;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.m2c.scm.application.order.order.OrderApplication;
import cn.m2c.scm.application.order.order.command.ConfirmReceiptCommand;
import cn.m2c.scm.application.order.order.query.SpringJdbcOrderQuery;
import cn.m2c.scm.domain.NegativeException;

/**
 * 
 * @ClassName: ComfirmReceiptJob
 * @Description: 定时扫描未确认收货的订单,如果过指定时间则确认收货
 * @author moyj
 * @date 2017年8月5日 下午2:43:54
 *
 */
public class ComfirmReceiptJob {
	
	private static final Logger logger = LoggerFactory.getLogger(ComfirmReceiptJob.class);
	@Autowired
	private SpringJdbcOrderQuery springJdbcOrderQuery;
	@Autowired
	private OrderApplication orderApplication;

	
	@SuppressWarnings("unchecked")
	public void work() {
		
		int pageNumber = 1;
		int rows = 2000;	
		try{
			Integer count = springJdbcOrderQuery.waitReceiptCount();
			while((pageNumber-1)*rows <= count){				
				List<?> list = springJdbcOrderQuery.waitReceiptList(pageNumber, rows);
				if(list == null || list.size() == 0){
					break;
				}
				for(Map<String,Object> map: (List<Map<String,Object>>)list){
					String orderId = map.get("orderId")==null?"":(String)map.get("orderId");
					ConfirmReceiptCommand command = new ConfirmReceiptCommand(orderId);
					orderApplication.confirmReceipt(command);
				}
				pageNumber ++;
			}
		}catch(NegativeException e){
			logger.error("ComfirmReceiptJob.work >> error",e);
		}catch(Exception e){
			logger.error("ComfirmReceiptJob.work >> error",e);
		}
	}
}


