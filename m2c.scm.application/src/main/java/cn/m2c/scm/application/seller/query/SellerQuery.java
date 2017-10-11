package cn.m2c.scm.application.seller.query;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.seller.data.bean.SellerBean;
import cn.m2c.scm.domain.NegativeException;

@Repository
public class SellerQuery {
	private final static Logger log = LoggerFactory.getLogger(SellerQuery.class);
	@Resource
	 SupportJdbcTemplate supportJdbcTemplate;
	
	
	public List<SellerBean> getSellerList(String sellerName,
			String sellerPhone, String startTime, String endTime,
			Integer pageNum, Integer rows) throws NegativeException {
		List<SellerBean> sellerList = null;
		try {
			 StringBuilder sql = new StringBuilder();
			 List<Object> params = new ArrayList<Object>();
	            sql.append(" SELECT ");
	            sql.append(" * ");
	            sql.append(" FROM ");
	            sql.append(" t_scm_dealer_seller sds ");
	            sql.append(" WHERE ");
	            sql.append(" sds.seller_status = 1 ");
	            if(sellerName!=null && !"".equals(sellerName)){
	            	sql.append(" AND sds.seller_name LIKE concat('%', ?,'%') ");
	            	params.add(sellerName);
	            }
	            if(sellerPhone!=null && !"".equals(sellerPhone)){
	            	sql.append(" AND sds.seller_phone LIKE concat('%', ?,'%') ");
	            	params.add(sellerName);
	            }
	        	if(startTime !=null&&!"".equals(startTime)){
					
					sql.append(" AND sds.created_date>=?");
					params.add(startTime);
				}
				if(endTime!=null&&!"".equals(endTime)){
					
					sql.append(" AND sds.created_date<=?");
					params.add(endTime+" 23:59:59");
				}
				sql.append(" ORDER BY sds.created_date DESC ");
				sql.append(" LIMIT ?,?");
				params.add(rows*(pageNum - 1));
				params.add(rows);
				System.out.println("----查询业务员列表："+sql.toString());
				sellerList =  this.supportJdbcTemplate.queryForBeanList(sql.toString(), SellerBean.class, params.toArray());
				
		} catch (Exception e) {
			log.error("查询经销商列表出错",e);
			throw new NegativeException(500, "经销商查询出错");
		}
		return sellerList;
	}


	public Integer getCount(String sellerName, String sellerPhone,
			String startTime, String endTime) throws NegativeException {
		Integer result = 0;
		try {
			 StringBuilder sql = new StringBuilder();
			 List<Object> params = new ArrayList<Object>();
	            sql.append(" SELECT ");
	            sql.append(" COUNT(*) ");
	            sql.append(" FROM ");
	            sql.append(" t_scm_dealer_seller sds ");
	            sql.append(" WHERE ");
	            sql.append(" sds.seller_status = 1 ");
	            if(sellerName!=null && !"".equals(sellerName)){
	            	sql.append(" AND sds.seller_name LIKE concat('%', ?,'%') ");
	            	params.add(sellerName);
	            }
	            if(sellerPhone!=null && !"".equals(sellerPhone)){
	            	sql.append(" AND sds.seller_phone LIKE concat('%', ?,'%') ");
	            	params.add(sellerName);
	            }
	        	if(startTime !=null&&!"".equals(startTime)){
					
					sql.append(" AND sds.created_date>=?");
					params.add(startTime);
				}
				if(endTime!=null&&!"".equals(endTime)){
					
					sql.append(" AND sds.created_date<=?");
					params.add(endTime+" 23:59:59");
				}
				System.out.println("----查询业务员总数："+sql.toString());
				result =  this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());
		} catch (Exception e) {
			log.error("查询业务员总数出错",e);
			throw new NegativeException(500, "业务员总数出错");
		}
		return result;
	}
	
	
}
