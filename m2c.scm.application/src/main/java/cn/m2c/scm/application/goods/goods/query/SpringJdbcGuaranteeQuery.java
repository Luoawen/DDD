package cn.m2c.scm.application.goods.goods.query;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpringJdbcGuaranteeQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcGuaranteeQuery.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getGuaranteeList(){
		List<Map<String, Object>> guaranteeList = null;
		LOGGER.info("jdbc get GuaranteeList  >>{}");
		String sql = "SELECT "
				+ "g.guarantee_id AS guaranteeId,"
				+ "g.guarantee_name AS guaranteeName,"
				+ "g.guarantee_desc AS guaranteeDesc,"
				+ "g.guarantee_pic AS guaranteePic,"
				+ "g.guarantee_order AS guaranteeOrder"
				+ " FROM t_goods_guarantee g order by guarantee_order asc";
		LOGGER.info("jdbc get GuaranteeList  >>{}",sql);
		try {
			guaranteeList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("查询保障列表出错");
			return null;
		}
		return guaranteeList;
	}

}
