package cn.m2c.scm.application.dealerorder.query;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import cn.m2c.scm.application.dealerorder.data.bean.ShippingOrderBean;

/**
 * 生成发货单
 * @author lqwen
 */
@Repository
public class ShippingTableQuery {

	public List<ShippingOrderBean> shippingOrderQuery() {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT  ");
		
		return null;
		
	}
}
