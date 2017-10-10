package cn.m2c.scm.application.seller.query;


import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.seller.data.bean.SellerBean;

@Repository
public class SellerQuery {
	private final static Logger log = LoggerFactory.getLogger(SellerQuery.class);
	@Resource
	 SupportJdbcTemplate supportJdbcTemplate;
	
	
	public List<SellerBean> getSellerList(String sellerName,
			String sellerPhone, String startTime, String endTime,
			Integer pageNum, Integer rows) {
		// TODO Auto-generated method stub
		return null;
	}


	public Integer getCount(String sellerName, String sellerPhone,
			String startTime, String endTime) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
