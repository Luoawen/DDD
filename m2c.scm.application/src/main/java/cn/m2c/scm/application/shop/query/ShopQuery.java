package cn.m2c.scm.application.shop.query;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.shop.data.bean.ShopBean;

public class ShopQuery {
	private final static Logger log = LoggerFactory.getLogger(ShopQuery.class);
	 @Resource
	  SupportJdbcTemplate supportJdbcTemplate;
	   
	 
	 
	public ShopBean getShopInfoByDealerId(String dealerId) {
		String sql = "SELECT * FROM t_scm_dealer_shop WHERE dealer_id=?";
		System.out.println("------------"+sql);
		ShopBean bean = this.supportJdbcTemplate.queryForBean(sql, ShopBean.class,dealerId);
		return bean;
	}
}
