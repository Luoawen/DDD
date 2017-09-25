package cn.m2c.scm.application.order.order.query;

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
public class SpringJdbcExpCompanyQuery {
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
			String keyword,
			Integer pageNumber,Integer rows) throws NegativeException{
		try{
			
			String field = "e.exp_company_code AS expCompanyCode,"
						+ "e.exp_company_Name AS expCompanyName,"
						+ "e.exp_sort AS expSort,"
						+ "e.exp_status AS expStatus";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_exp_company e WHERE 1=1 ");		
			List<Object> params = new ArrayList<Object>();	
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND e.exp_company_code like ?");
				sql.append(" OR e.exp_company_Name like ?");
				params.add("%" + keyword +"%");
				params.add("%" + keyword +"%");
			}else{
				return null;
			}
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
	 * @param keyword		//关键字
	 * @return
	 * @throws NegativeException
	 */
	public Integer listCount(String keyword) throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)")
					.append(" FROM t_order_exp_company e WHERE 1=1");		
			List<Object> params = new ArrayList<Object>();	
			if(keyword != null && keyword.length() > 0){
				sql.append(" AND e.exp_company_code like ?");
				sql.append(" OR e.exp_company_Name like ?");
				params.add("%" + keyword +"%");
				params.add("%" + keyword +"%");
			}else{
				return 0;
			}		
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
	
	/**
	 * 列表
	 * @param businessId		//业务编号
	 * @param businessType		//业务类型
	 * @param pageNumber
	 * @param rows
	 * @return
	 * @throws NegativeException
	 */
	public List<?> listAll() throws NegativeException{
		try{
			
			String field = "e.exp_company_code AS expCompanyCode,"
						+ "e.exp_company_Name AS expCompanyName,"
						+ "e.exp_sort AS expSort,"
						+ "e.exp_status AS expStatus";
			StringBuffer sql = new StringBuffer()
					.append("SELECT " + field )
					.append(" FROM t_order_exp_company e WHERE 1=1 ");		
			List<Object> params = new ArrayList<Object>();	
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
	 * @param keyword		//关键字
	 * @return
	 * @throws NegativeException
	 */
	public Integer listAllCount() throws NegativeException {
		try{
			StringBuffer sql = new StringBuffer()
					.append("SELECT count(1)")
					.append(" FROM t_order_exp_company e WHERE 1=1");		
			List<Object> params = new ArrayList<Object>();	
			
			Integer count = jdbcTemplate.queryForObject(sql.toString(), params.toArray(),Integer.class);
			return count;
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	
	}
}
