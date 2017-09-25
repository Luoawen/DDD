package cn.m2c.scm.application.goods.goods.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpringJdbcDealerQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcDealerQuery.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	String field = "d.dealer_id dealerId,d.user_id userId,d.user_name userName,d.mobile mobile,d.dealer_name dealerName,d.dealer_mobile dealerMobile,d.dealer_province dealerProvince,d.dealer_city dealerCity,"
			+ "d.dealer_area dealerArea,d.province_code provinceCode,d.city_code cityCode,d.area_code areaCode,d.detail_address detailAddress,"
			+ "d.second_classification secondClassification,d.cooperation_mode cooperationMode,"
			+ "d.seller_name sellerName,d.created_date createdDate";
	

	public List<Map<String, Object>> getlist(String mobile, String dealerName,
			String provinceCode, String cityCode, String areaCode,
			String secondClassification, Integer cooperationMode,
			String sellerFilter, String detailAddress, String startTime,
			String endTime, Integer rows, Integer pageNum) {
		
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT " + field+" FROM t_goods_dealer d WHERE d.dealer_status=1");
			if(mobile!=null&&!"".equals(mobile)){
				sql.append(" AND d.mobile=?");
				params.add(mobile);
			}
			if(dealerName!=null&&!"".equals(dealerName)){
				sql.append(" AND d.dealer_name LIKE concat('%', ?,'%') ");
				params.add(dealerName);
			}
			if(provinceCode!=null&&!"".equals(provinceCode)){
				
				sql.append(" AND d.province_code=?");
				params.add(provinceCode);
			}
			if(cityCode!=null&&!"".equals(cityCode)){
				
				sql.append(" AND d.city_code=?");
				params.add(cityCode);
			}
			if(areaCode!=null&&!"".equals(areaCode)){
	
				sql.append(" AND d.area_code=?");
				params.add(areaCode);
			}
			if(secondClassification!=null&&!"".equals(secondClassification)){
				
				sql.append(" AND d.second_classification=?");
				params.add(secondClassification);
			}
			if(cooperationMode!=null){
				
				sql.append(" AND d.cooperation_mode=?");
				params.add(cooperationMode);
			}
			if(sellerFilter!=null&&!"".equals(sellerFilter)){
				
				sql.append(" AND (d.seller_mobile LIKE concat('%', ?,'%') or d.seller_name LIKE concat('%', ?,'%'))");
				params.add(sellerFilter);
				params.add(sellerFilter);
			}
			if(detailAddress!=null&&!"".equals(detailAddress)){
				
				sql.append(" AND d.detail_address LIKE concat('%', ?,'%')");
				params.add(detailAddress);
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
	 * @param mobile
	 * @param dealerName
	 * @param dealerProvince
	 * @param dealerCity
	 * @param dealerarea
	 * @param secondClassification
	 * @param cooperationMode
	 * @param sellerFilter
	 * @param detailAddress
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getDealerCount(String mobile, String dealerName,
			String dealerProvince, String dealerCity, String dealerArea,
			String secondClassification, Integer cooperationMode,
			String sellerFilter, String detailAddress, String startTime,
			String endTime) {
		Long result = -1L;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT COUNT(*) totalCount FROM t_goods_dealer d WHERE d.dealer_status=1");
		List<Object> params = new ArrayList<Object>();
		
		if(mobile!=null&&!"".equals(mobile)){
			sql.append(" AND d.mobile=?");
			params.add(mobile);
		}
		if(dealerName!=null&&!"".equals(dealerName)){
			
			sql.append(" AND d.dealer_name=?");
			params.add(dealerName);
		}
		if(dealerProvince!=null&&!"".equals(dealerProvince)){
			
			sql.append(" AND d.dealer_province=?");
			params.add(dealerProvince);
		}
		if(dealerCity!=null&&!"".equals(dealerCity)){
			
			sql.append(" AND d.dealer_city=?");
			params.add(dealerCity);
		}
		if(dealerArea!=null&&!"".equals(dealerArea)){

			sql.append(" AND d.dealer_area=?");
			params.add(dealerArea);
		}
		if(secondClassification!=null&&!"".equals(secondClassification)){
			
			sql.append(" AND d.second_classification=?");
			params.add(secondClassification);
		}
		if(cooperationMode!=null){
			
			sql.append(" AND d.cooperation_mode=?");
			params.add(cooperationMode);
		}
		if(sellerFilter!=null&&!"".equals(sellerFilter)){
			
			sql.append(" AND (d.seller_mobile LIKE concat('%', ?,'%') or d.seller_name LIKE concat('%', ?,'%'))");
			params.add(sellerFilter);
			params.add(sellerFilter);
		}
		if(detailAddress!=null&&!"".equals(detailAddress)){
			
			sql.append(" AND d.detail_address LIKE concat('%', ?,'%')");
			params.add(detailAddress);
		}
		if(startTime !=null&&!"".equals(startTime)){
			
			sql.append(" AND d.created_date>=?");
			params.add(startTime);
		}
		if(endTime!=null&&!"".equals(endTime)){
			
			sql.append(" AND d.created_date<=?");
			params.add(endTime+" 23:59:59");
		}
		Map<String, Object> countMap = jdbcTemplate.queryForMap(sql.toString(),params.toArray());
		result = (Long) countMap.get("totalCount");
		return result.intValue();
	}



	/**
	 * 根据经销商id查询经销商信息
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getDealerInfo(String dealerId) {
		Map<String, Object> result = null;
		String sql = "SELECT "
				+ "d.dealer_id AS dealerId,"
				+ "d.dealer_name AS dealerName,"
				+ "d.first_classification AS firstClassification,"
				+ "d.second_classification AS secondClassification,"
				+ "d.cooperation_mode AS cooperationMode,"
				+ "d.dealer_province AS dealerProvince,"
				+ "d.dealer_city AS dealerCity,"
				+ "d.dealer_area AS dealerArea,"
				+ "d.province_code AS dealerPcode,"
				+ "d.city_code AS dealerCcode,"
				+ "d.area_code AS dealerAcode,"
				+ "d.detail_address AS detailAddress,"
				+ "d.seller_id AS sellerId,"
				+ "d.seller_name AS sellerName,"
				+ "d.user_id AS userId,"
				+ "d.dealer_mobile AS dealerMobile,"
				+ "d.created_date AS createdDate"
				+ " FROM t_goods_dealer d where dealer_status=1 AND d.dealer_id=?" ;
		try {
			result = jdbcTemplate.queryForMap(sql,dealerId);
		} catch (Exception e) {
			LOGGER.error("dealerId查询经销商信息失败");
		}
		return result;
		
	}



	/**
	 * 
	 * @param userId
	 * @return
	 */
	public Map<String, Object> getAppDealerInfo(String userId) {
		Map<String, Object> result = null;
		String sql = "SELECT "
				+ "d.dealer_id AS dealerId,"
				+ "d.dealer_name AS dealerName,"
				+ "d.dealer_province AS dealerProvince,"
				+ "d.dealer_city AS dealerCity,"
				+ "d.dealer_area AS dealerArea,"
				+ "d.province_code AS provinceCode,"
				+ "d.city_code AS cityCode,"
				+ "d.area_code AS areaCode,"
				+ "d.detail_address AS detailAddress,"
				+ "d.dealer_mobile AS dealerMobile,"
				+ "d.user_name AS userName,"
				+ "d.created_date AS createdDate"
				+ " FROM t_goods_dealer d where dealer_status=1 AND d.user_id=?" ;
		try {
			result = jdbcTemplate.queryForMap(sql,userId);
		} catch (Exception e) {
			LOGGER.error("userId查询经销商信息失败");
		}
		return result;
		
	}



	public Map<String, Object> getDealer(String dealerId) {
		Map<String, Object> result = null;
		String sql = "SELECT "
				+ "d.dealer_id AS dealerId,"
				+ "d.dealer_name AS dealerName,"
				+ "d.first_classification AS firstClassification,"
				+ "d.second_classification AS secondClassification,"
				+ "d.cooperation_mode AS cooperationMode,"
				+ "d.dealer_province AS dealerProvince,"
				+ "d.dealer_city AS dealerCity,"
				+ "d.dealer_area AS dealerArea,"
				+ "d.province_code AS provinceCode,"
				+ "d.city_code AS cityCode,"
				+ "d.area_code AS areaCode,"
				+ "d.detail_address AS detailAddress,"
				+ "d.dealer_mobile AS dealerMobile"
				+ " FROM t_goods_dealer d where dealer_status=1 AND d.dealer_id=?" ;
		try {
			result = jdbcTemplate.queryForMap(sql,dealerId);
		} catch (Exception e) {
			LOGGER.error("dealerId查询经销商信息失败");
		}
		return result;
		
	}
	
	
}
