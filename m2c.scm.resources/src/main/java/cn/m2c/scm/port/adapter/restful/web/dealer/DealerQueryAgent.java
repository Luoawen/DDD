package cn.m2c.scm.port.adapter.restful.web.dealer;

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
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.data.representation.DealerDetailRepresentation;
import cn.m2c.scm.application.dealer.query.DealerQuery;

/**
 * 商家查询（提供出去的接口）
 */
@RestController
@RequestMapping("/dealer-out")
public class DealerQueryAgent {
	private static final Logger LOGGER = LoggerFactory.getLogger(DealerQueryAgent.class);
	
	@Autowired
    DealerQuery dealerQuery;
	
	/**
     * 根据商家Id/商家名(模糊查询)查询商家信息
     * 
     * @param dealerMessage   商家信息(商家Id/商家名)
     * @param pageOrNot       是否分页(0:不分页, 1:分页)
     * @param pageNum		     第几页
     * @param rows            每页多少行
     * @return
     */
    @RequestMapping(value = "/information",method = RequestMethod.GET)
    public ResponseEntity<MPager> getDealerByDealerIdOrName(
    		@RequestParam(value = "dealerMessage",required = false) String dealerMessage,
            @RequestParam(value = "pageOrNot", required = false, defaultValue = "1") Integer pageOrNot,
    		@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer rows){
    	MPager result = new MPager(MCode.V_1);
    	try {
    		Integer total = dealerQuery.queryDealerByDealerIdOrNameTotal(dealerMessage);
    		if(total > 0) {
    			List<DealerBean> dealerBeans = dealerQuery.queryDealerByDealerIdOrName(dealerMessage,pageOrNot,pageNum,rows);
                if (null != dealerBeans && dealerBeans.size() > 0) {
                    List<DealerDetailRepresentation> resultList = new ArrayList<DealerDetailRepresentation>();
                    for (DealerBean dealerBean : dealerBeans) {
                        resultList.add(new DealerDetailRepresentation(dealerBean));
                    }
                    result.setContent(resultList);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
		} catch (Exception e) {
			 LOGGER.error("getDealerByDealerIdOrName Exception e:", e);
	         result = new MPager(MCode.V_400, "查询商家信息失败");
		}
    	return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
