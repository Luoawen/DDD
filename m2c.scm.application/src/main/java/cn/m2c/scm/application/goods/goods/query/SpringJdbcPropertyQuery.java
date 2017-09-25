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
public class SpringJdbcPropertyQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcPropertyQuery.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	String field = "p.property_id AS propertyId,p.model_name AS modelName,p.property_count AS propertyCount,p.property_value AS propertyValue,p.created_date AS createdDate";

	public List<Map<String, Object>> getlist(String dealerId,Integer rows, Integer pageNum) {
		
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT " + field+" FROM t_goods_property p WHERE p.property_status=1");
			if(dealerId!=null && !"".equals(dealerId)){
				sql.append(" AND p.dealer_id=?");
				sql.append(" ORDER BY created_date DESC ");
					params.add(dealerId);
			}else{
				sql.append(" ORDER BY p.created_date DESC ");
				sql.append(" LIMIT ?,?");
				params.add(rows*(pageNum - 1));
				params.add(rows);
			}
			System.out.println("-----------------"+sql);
			List<Map<String,Object>> dealerLsit = jdbcTemplate.queryForList(sql.toString(),params.toArray());
			return dealerLsit;
		}catch(Exception e){
			LOGGER.error("查询经销商列表出错");
			return null;
		}
	
	}

	public Integer getPropertyCount() {
		Long result = -1L;
		String sql="SELECT COUNT(*) totalCount FROM t_goods_property p WHERE p.property_status=1";
		Map<String, Object> countMap = jdbcTemplate.queryForMap(sql);
		result = (Long) countMap.get("totalCount");
		return result.intValue();
	}

	
}
