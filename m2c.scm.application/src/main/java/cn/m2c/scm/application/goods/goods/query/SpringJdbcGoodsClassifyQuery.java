package cn.m2c.scm.application.goods.goods.query;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class SpringJdbcGoodsClassifyQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcGoodsClassifyQuery.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public List<Map<String, Object>> getClassifyList(){
		List<Map<String, Object>> classifyList = null;
		LOGGER.info("jdbc get category list >>{}");
		String sql = "SELECT c.goods_classify_id AS goodsClassifyId,"
				+ "c.goods_classify_name AS goodsClassifyName,"
				+ "c.goods_count AS goodsCount,"
				+ "c.order_by AS orderBy,"
				+ "c.is_parent AS isParent,"
				+ "c.parent_id AS parentId,"
				+ "c.created_date AS createdDate"
				+ " FROM t_goods_classify c";
		try {
			
			classifyList = jdbcTemplate.queryForList(sql);
		} catch (Exception e) {
			LOGGER.error("查询商品列表出错");
		}
		return classifyList;
	}

	/**
	 * 查询所有二级列表
	 * @return
	 */
	public List<Map<String, Object>>  getGoodSecondClassify() {
		List<Map<String, Object>> secondClassifyList = null;
		LOGGER.info("jdbc get SecondClassify list >>{}");
		String sql = "SELECT c.goods_classify_id AS goodsClassifyId,"
				+ "c.goods_classify_name AS goodsClassifyName,"
				+ "c.goods_count AS goodsCount"
				+ " FROM t_goods_classify c where c.is_parent=0";
		try {
			secondClassifyList = jdbcTemplate.queryForList(sql);
			} catch (Exception e) {
				LOGGER.error("查询商品二级分类列表出错");
				return null;
			}
			return secondClassifyList;
		}

	/**
	 * 一级分类列表
	 * @return
	 */
	public List<Map<String, Object>> getFirstClassifyList() {
		List<Map<String, Object>> firstClassifyList = null;
		LOGGER.info(" 一级分类列表>>{}");
		String sql = "SELECT c.goods_classify_id AS goodsClassifyId,"
				+ "c.goods_classify_name AS goodsClassifyName,"
				+ "c.goods_count AS goodsCount"
				+ " FROM t_goods_classify c where c.is_parent=1";
		try {
			firstClassifyList = jdbcTemplate.queryForList(sql);
			} catch (Exception e) {
				LOGGER.error("查询商品一级分类列表出错");
				return null;
			}
			return firstClassifyList;
	}

	/**
	 * 根据一级分类查询二级分类
	 * @param firstClassifyId
	 * @return
	 */
	public List<Map<String, Object>> getSecondClassifyList(
			String firstClassifyId) {
		LOGGER.info(" 二级分类列表>>{}",firstClassifyId);
		List<Map<String, Object>> secondClassifyList = null;
		String sql = "SELECT c.goods_classify_id AS goodsClassifyId,"
				+ "c.goods_classify_name AS goodsClassifyName,"
				+ "c.goods_count AS goodsCount"
				+ " FROM t_goods_classify c where c.is_parent=0 and c.parent_id=?";
		try {
			secondClassifyList = jdbcTemplate.queryForList(sql,firstClassifyId);
		} catch (Exception e) {
			LOGGER.error("查询商品二级分类列表出错");
			return null;
		}
		return secondClassifyList;
	}
	
}
