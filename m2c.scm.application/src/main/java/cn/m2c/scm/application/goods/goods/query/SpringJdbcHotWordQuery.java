package cn.m2c.scm.application.goods.goods.query;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
public class SpringJdbcHotWordQuery {
	
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcHotWordQuery.class);
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getHotWordList() {
		List<Map<String, Object>> result = null;
		try {
			String sql = "SELECT h.hot_word_id AS hotWordId, h.hot_word AS hotWord FROM t_goods_hot_word h WHERE h.hot_word_status = 1  ";
			System.out.println("-----------------"+sql);
			result = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("查询热搜词出问题",e);
		}
		return result;
	}

}
