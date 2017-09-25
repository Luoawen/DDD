package cn.m2c.scm.application.order.logger.query;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedCaseInsensitiveMap;

import cn.m2c.scm.domain.NegativeException;

@Repository
public class SpringJdbcOperLoggerQuery {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 列表
	 * @param businessId		//业务编号
	 * @param businessType		//业务类型
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> list(
			String businessId,
			Integer businessType,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			
			String field = "l.logger_id AS loggerId,"
						+ "l.business_id AS businessId,"
						+ "l.business_type AS businessType,"
						+ "l.oper_name AS operName,"
						+ "l.oper_des AS operDes,"
						+ "l.oper_result AS operResult,"
						+ "l.oper_time AS operTime,"
						+ "l.oper_user_id AS operUserId,"
						+ "l.oper_user_name AS operUserName";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_oper_logger l ")
					.append(" where 1=1 AND l.business_id = ? AND l.business_type = ?  ");
			
			List<Object> params = new ArrayList<Object>();	
			params.add(businessId);
			params.add(businessType);
			sql.append(" limit ?,?");	
			Integer startNumber = (pageNumber - 1) * rows;
			params.add(startNumber);
			params.add(rows);
			
			List<Map<String,Object>> resultList = jdbcTemplate.query(sql.toString(), params.toArray(),new RowMapper<Map<String,Object>>(){
				@Override
				public Map<String,Object> mapRow(ResultSet rs, int rowNum)
						throws SQLException {
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();	
	
					Map<String, Object> mapOfColValues = new LinkedCaseInsensitiveMap<Object>(columnCount);					
					for (int i = 1; i <= columnCount; i++) {
						String key = JdbcUtils.lookupColumnName(rsmd, i);
						Object obj = JdbcUtils.getResultSetValue(rs, i);
						mapOfColValues.put(key, obj);
					}
					
					return mapOfColValues;
				}
				
			});		
			return resultList;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 列表数
	 * @param orderId		//订单号
	 * @param startTime		//申请开始时间
	 * @param endTime		//申请结束时间
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCount(String businessId,Integer businessType) throws NegativeException {
		try{

			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)")
					.append(" FROM t_order_oper_logger l ")
					.append(" where 1=1 AND l.business_id = ? AND l.business_type = ?  ");
			
			List<Object> params = new ArrayList<Object>();	
			params.add(businessId);
			params.add(businessId);
			
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
}
