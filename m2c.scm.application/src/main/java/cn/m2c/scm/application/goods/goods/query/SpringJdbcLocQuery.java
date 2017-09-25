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
public class SpringJdbcLocQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcLocQuery.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	String field = "l.location_id AS locationId,"
			+ "l.title AS title,"
			+ "l.loc_select AS locSelect,"
			+ "l.loc_type AS locType,"
			+ "l.is_online AS isOnline,"
			+ "l.display_order AS displayOrder,"
			+ "l.img_url AS imgUrl,"
			+ "l.effective_time AS effectiveTime,"
			+ "l.redirect_url AS redirectUrl,"
			+ "l.goods_id AS goodsId,l.goods_name AS goodsName,"
			+ "l.up_date AS upLineDate,l.created_date AS createdDate";

	public  List<Map<String,Object>> getLocList(String title, Integer isOnLine, Integer locSelect,
			Integer locType, Integer rows, Integer pageNum) {
		LOGGER.info("开始查询位置列表--------------param ==title:"+title+"==isOnLine:"+isOnLine+"==locSelect:"+locSelect+"==locType:"+locType);
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT " + field+" FROM t_goods_show_location l WHERE l.loc_status=1 ");
			if(title!=null && !"".equals(title.trim())){
				sql.append(" AND l.title LIKE concat('%', ?,'%') ");
				params.add(title);
			}
			if(isOnLine!=null && isOnLine!=0){
				sql.append(" AND l.is_online=?");
				params.add(isOnLine);
			}
			if(locSelect!=null && locSelect!=0){
				sql.append(" AND l.loc_select=?");
				params.add(locSelect);
			}
			if(locType!=null){
				sql.append(" AND l.loc_type=?");
				params.add(locType);
			}  
			sql.append(" ORDER BY l.up_date DESC");  
			sql.append(" LIMIT ?,?");
			params.add(rows*(pageNum - 1));
			params.add(rows);
			System.out.println("-----------------"+sql);
			List<Map<String,Object>> locList = jdbcTemplate.queryForList(sql.toString(),params.toArray());
			return locList;
		}catch(Exception e){
			LOGGER.error("查询位置列表出错",e);
			return null;
		}
	}

	public Map<String, Object> getLocCount(String title, Integer isOnLine, Integer locSelect,
			Integer locType, Integer rows, Integer pageNum) {
		LOGGER.info("开始查询位置列表--------------param ==title:"+title+"==isOnLine:"+isOnLine+"==locSelect:"+locSelect+"==locType:"+locType);
		try {
			StringBuffer sql = new StringBuffer();
			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT COUNT(*) AS LocCount FROM t_goods_show_location l WHERE l.loc_status=1 ");
			if(title!=null && !"".equals(title.trim())){
				sql.append(" AND l.title LIKE concat('%', ?,'%') ");
				params.add(title);
			}
			if(isOnLine!=null && isOnLine!=0){
				sql.append(" AND l.is_online=?");
				params.add(isOnLine);
			}
			if(locSelect!=null && locSelect!=0){
				sql.append(" AND l.loc_select=?");
				params.add(locSelect);
			}
			if(locType!=null){
				sql.append(" AND l.loc_type=?");
				params.add(locType);
			}
			System.out.println("-----------------"+sql);
			Map<String, Object> loccount = jdbcTemplate.queryForMap(sql.toString(),params.toArray());
			return loccount;
		}catch(Exception e){
			LOGGER.error("查询位置列表出错",e);
			return null;
		}
	}
	/**
	 * 推荐列表
	 * @param searchpageNum 
	 * @param searchrows 
	 * @return
	 */
	public List<Map<String, Object>> getRecommendList(Integer searchrows, Integer searchpageNum) {
		LOGGER.info("获取推荐商品列表-------");
		List<Object> params = new ArrayList<Object>();
		String sql = "SELECT l.goods_id AS goodsId,l.goods_name AS goodsName,l.img_url AS imgUrl,l.title AS title,l.goods_price AS goodsPrice FROM t_goods_show_location l WHERE l.loc_status=1 AND l.loc_select=2 AND l.effective_time>NOW() ORDER BY l.display_order  LIMIT ?,?";
		params.add(searchrows*(searchpageNum - 1));
		params.add(searchrows);
		List<Map<String, Object>> recommendList = null;
		try {
			recommendList = jdbcTemplate.queryForList(sql,params.toArray());
		} catch (Exception e) {
			LOGGER.error("获取推荐商品列表出错",e);
		}
		return recommendList;
	}

	/**
	 * 推荐总数
	 * @param searchrows
	 * @param searchpageNum
	 * @return
	 */
	public Integer getRecommendCount(Integer searchrows, Integer searchpageNum) {
		try {
			StringBuffer sql = new StringBuffer();
//			List<Object> params = new ArrayList<Object>();
			sql.append("SELECT COUNT(*) AS LocCount FROM t_goods_show_location l WHERE l.loc_status=1 AND l.loc_select=2 AND l.is_online=1 AND l.effective_time>NOW()");
			System.out.println("-----------------"+sql);
			Map<String, Object> loccount = jdbcTemplate.queryForMap(sql.toString());
			Long count = (Long) loccount.get("LocCount");
			return  count.intValue();
		}catch(Exception e){
			LOGGER.error("查询位置列表出错",e);
			return null;
		}
	}

	public List<Map<String, Object>> getViewImgs() {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		try {
			String sql = "SELECT m.title AS title, m.img_url AS imgUrl,m.redirect_url AS redirectUrl,"
				+ "m.display_order AS displayOrder "
				+ "FROM t_goods_show_location m WHERE m.loc_status=1 AND m.loc_select=1  AND m.is_online=1 AND m.effective_time>NOW() ORDER BY m.display_order";
			result = jdbcTemplate.queryForList(sql);
			return result;
		} catch (Exception e) {
			LOGGER.error("轮播图列表",e);
			return null;
		}
	}

}
