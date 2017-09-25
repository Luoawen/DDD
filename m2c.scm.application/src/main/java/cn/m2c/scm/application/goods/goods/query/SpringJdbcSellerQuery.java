package cn.m2c.scm.application.goods.goods.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.goods.exception.NegativeException;

@Repository
public class SpringJdbcSellerQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcSellerQuery.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	String field = "d.seller_id sellerId,d.seller_name sellerName,d.acc_no accNo,d.province province,d.city city,d.area area,d.sex sex,d.age age,"
			+ "d.remarks remarks,d.created_date createdDate";
	

	public List<Map<String, Object>> getlist(String sellerFilter,
			String province, String city, String area, String startTime,
			String endTime, Integer rows, Integer pageNum) {
		
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT " + field+" FROM t_goods_dealer_seller d WHERE d.seller_status=1");
			if(sellerFilter!=null&&!"".equals(sellerFilter)){
				sql.append(" AND (d.seller_phone=? or d.seller_name=?)");
				params.add(sellerFilter);
			}
			if(province!=null&&!"".equals(province)){
				
				sql.append(" AND d.province=?");
				params.add(province);
			}
			if(city!=null&&!"".equals(city)){
				
				sql.append(" AND d.city=?");
				params.add(city);
			}
			if(area!=null&&!"".equals(area)){
				
				sql.append(" AND d.area=?");
				params.add(area);
			}
			if(startTime !=null&&!"".equals(startTime)){
				
				sql.append(" AND d.created_date>=?");
				params.add(startTime);
			}
			if(endTime!=null&&!"".equals(endTime)){
				
				sql.append(" AND d.created_date<=?");
				params.add(endTime+" 23:59:59");
			}
			sql.append(" ORDER BY d.created_date DESC ");
			sql.append(" LIMIT ?,?");
			params.add(rows*(pageNum - 1));
			params.add(rows);
			System.out.println("-----------------"+sql);
			List<Map<String,Object>> dealerLsit = jdbcTemplate.queryForList(sql.toString(),params.toArray());
			return dealerLsit;
		}catch(Exception e){
			LOGGER.error("查询经销商列表出错");
			return null;
		}
	
	}


	/**
	 * 查询总数
	 * @return
	 */
	public Integer getSellerCount() {
		Long result = -1L;
		String sql="SELECT COUNT(*) totalCount FROM t_goods_dealer_seller d WHERE d.seller_status=1";
		Map<String, Object> countMap = jdbcTemplate.queryForMap(sql);
		result = (Long) countMap.get("totalCount");
		return result.intValue();
	}


	/**
	 * 查询报表
	 * @param sellerPhone
	 * @param sellerName
	 * @param province
	 * @param city
	 * @param area
	 * @param startTime
	 * @param endTime
	 * @param rows
	 * @param pageNum
	 * @return
	 * @throws NegativeException 
	 */
	public Integer getTotalCount( String sellerName,
			String province, String city, String area, String startTime,
			String endTime, Integer rows, Integer pageNum) throws NegativeException {
		Integer num = null;
		try {
			
			List<Object> paramList = new ArrayList<>();
			StringBuilder sqlBuilder = new StringBuilder( "SELECT COUNT(1) "+
					" FROM t_goods_dealer_seller mf LEFT JOIN ( SELECT t.staff_id, sum(t.dealer_num) AS dealer_num, "+
					" sum(t.goods_num) AS goods_num, sum(t.order_num) AS order_num, t.report_date "+
					" FROM ( SELECT mc.staff_id,  IF ( mc.obj_type = 1, mc.count_num, 0 ) AS dealer_num,"+  
					" IF ( mc.obj_type = 2, mc.count_num, 0 ) AS goods_num,  IF ( mc.obj_type = 3,"+
					" mc.count_num, 0 ) AS order_num, DATE_FORMAT(mc.report_date, '%Y-%m') AS report_date "+
					" FROM t_goods_report_count mc where 1=1");
				if(startTime!=null && !"".equals(startTime)){
					sqlBuilder.append( " AND mc.report_date >=?");
					paramList.add(startTime);
				}
				if(endTime!=null && !"".equals(endTime)){
					sqlBuilder.append( " AND mc.report_date <=?");
					paramList.add(endTime+" 23:59:59");
				}
				sqlBuilder.append( ") t GROUP BY t.staff_id, t.report_date ) tt ON mf.user_id = tt.staff_id AND mf.is_valid = 1 WHERE 1=1  ");
				
					if(startTime!=null && !"".equals(startTime)){
						sqlBuilder.append(" and tt.report_date is not null");
					}
					if(sellerName!=null && !"".equals(sellerName)){
						sqlBuilder.append(" AND (mf.staff_name LIKE concat('%', ?,'%') or  mf.phone LIKE concat('%', ?,'%'))");
						paramList.add(sellerName);
						paramList.add(sellerName);
					}
					if(province!=null && !"".equals(province)){
						sqlBuilder.append(" AND mf.pro_code = ? ");
						paramList.add(province);
					}
					if(city!=null && !"".equals(city)){
						sqlBuilder.append(" AND mf.city_code = ? ");
						paramList.add(city);
					}
					if(area!=null && !"".equals(area)){
						sqlBuilder.append(" AND mf.area_code = ? ");
						paramList.add(area);
					}
			System.out.println("----------------count--"+sqlBuilder.toString());
			num = jdbcTemplate.queryForObject(sqlBuilder.toString(), Integer.class, paramList.toArray());
		} catch (Exception e) {
			LOGGER.error("查询总数失败", e);
		}
		return num;
	}


	public List<Map<String, Object>> getSllerReport(
			String sellerName, String province, String city, String area,
			String startTime, String endTime, Integer rows, Integer pageNum) throws NegativeException {
		List<Map<String, Object>> staffList;
		try {
			List<Object> paramList = new ArrayList<>();
			StringBuilder sqlBuilder = new StringBuilder( "SELECT mf.staff_id AS staffId, mf.staff_name AS staffName, mf.phone AS phone, "+
				" mf.pro_code AS proCode,mf.city_code AS cityCode,mf.area_code AS areaCode, mf.pro_name AS proName,mf.city_name AS cityName,mf.area_name AS areaName,tt.dealer_num as dealerNum, tt.goods_num as goodsNum, "+
				" tt.order_num as orderNum, tt.report_date as reportDate "+
				" FROM t_goods_dealer_seller mf LEFT JOIN ( SELECT t.staff_id, sum(t.dealer_num) AS dealer_num, "+
				" sum(t.goods_num) AS goods_num, sum(t.order_num) AS order_num, t.report_date "+
				" FROM ( SELECT mc.staff_id,  IF ( mc.obj_type = 1, mc.count_num, 0 ) AS dealer_num,"+  
				" IF ( mc.obj_type = 2, mc.count_num, 0 ) AS goods_num,  IF ( mc.obj_type = 3,"+
				" mc.count_num, 0 ) AS order_num, DATE_FORMAT(mc.report_date, '%Y-%m') AS report_date "+
				" FROM t_goods_report_count mc where 1=1");
			if(startTime!=null && !"".equals(startTime)){
				sqlBuilder.append( " AND mc.report_date >=?");
				paramList.add(startTime);
			}
			if(endTime!=null && !"".equals(endTime)){
				sqlBuilder.append( " AND mc.report_date <=?");  
				paramList.add(endTime+" 23:59:59");
			}
			sqlBuilder.append( ") t GROUP BY t.staff_id, t.report_date ) tt ON mf.user_id = tt.staff_id AND mf.is_valid = 1 WHERE 1=1  ");
			
				if(startTime!=null && !"".equals(startTime)){
					sqlBuilder.append(" and tt.report_date is not null");
				}
				if(sellerName!=null && !"".equals(sellerName)){
					sqlBuilder.append(" AND (mf.staff_name LIKE concat('%', ?,'%') or  mf.phone LIKE concat('%', ?,'%'))");
					paramList.add(sellerName);
					paramList.add(sellerName);
				}
				if(province!=null && !"".equals(province)){
					sqlBuilder.append(" AND mf.pro_code = ? ");       
					paramList.add(province);
				}
				if(city!=null && !"".equals(city)){
					sqlBuilder.append(" AND mf.city_code = ? ");
					paramList.add(city);
				}
				if(area!=null && !"".equals(area)){
					sqlBuilder.append(" AND mf.area_code = ? ");
					paramList.add(area);
				}
				sqlBuilder.append(" LIMIT ?,?");
				paramList.add(rows*(pageNum - 1));
				paramList.add(rows);
				System.out.println("-----------------data :"+sqlBuilder.toString());
				
				staffList = jdbcTemplate.queryForList(sqlBuilder.toString(), paramList.toArray());
			} catch (Exception e) {
				LOGGER.error("getSalerList's sqlCondition bad for excute.", e.getMessage());
				throw new NegativeException(MCode.V_400, "查询执行失败.");
			}
				return staffList;
			}
	
}