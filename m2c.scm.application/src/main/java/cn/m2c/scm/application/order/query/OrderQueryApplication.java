package cn.m2c.scm.application.order.query;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.representation.DealerOrderBean;
import cn.m2c.scm.application.order.data.representation.OptLogBean;
import cn.m2c.scm.application.order.data.representation.OrderBean;

/**
 * 订单查询
 * @author fanjc
 * created date 2017年10月17日
 * copyrighted@m2c
 */
@Service
public class OrderQueryApplication {
	/**调试打日志用*/
	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQueryApplication.class);
	
	@Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return supportJdbcTemplate;
    }
    /***
     * 商家管理平台，获取订单列表
     * @return
     */
    public List<OrderBean> getOrderList() {
    	return null;
    }
    
    /***
     * 商家平台，获取订单列表
     * @return
     */
    public List<DealerOrderBean> getDealerOrderList(String dealerId) {
    	return null;
    }
    
    /***
     * 获取商家订单操作日志列表
     * @return
     */
    public List<OptLogBean> getDealerOrderOptLog(String dealerId) {
    	return null;
    }
}
