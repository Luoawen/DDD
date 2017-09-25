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
public class SpringJdbcDealerRepQuery {
	private Logger LOGGER = LoggerFactory.getLogger(SpringJdbcDealerRepQuery.class);
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	/**
	 * 获取此经销商对应的上线商品列表
	 * @param dealerId 
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> getDealerOnLineGoods(String dealerId, Integer pageNum,
			Integer rows) {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT g.up_goods_date AS upGoodsDate ,g.goods_id AS goodsId,g.goods_name AS goodsName,g.sale_price AS salePrice,g.market_price AS marketPrice,g.pay_goods_num AS payGoodsNum FROM t_goods_goods g WHERE g.goods_status=3 and g.dealer_id=?");
		params.add(dealerId);
		sql.append(" ORDER BY g.up_goods_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows*(pageNum - 1));
		params.add(rows);
		System.out.println("-----------------"+sql);
		
		try {
			result = jdbcTemplate.queryForList(sql.toString(), params.toArray());
			return result;
		} catch (Exception e) {
			LOGGER.error("经销商在线商品列表查询出错",e);
			return null;
		}
	}


	/**
	 * 获取此经销商对应的下线商品
	 * @param dealerId
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<Map<String, Object>> getDealerDownLineGoods(String dealerId,
			Integer pageNum, Integer rows) {
		try {
		List<Map<String, Object>> result = new ArrayList<Map<String,Object>>();
		List<Object> params = new ArrayList<Object>();
		StringBuffer sql = new StringBuffer("SELECT g.up_goods_date AS upGoodsDate ,g.down_goods_date AS downDoodsDate,g.goods_id AS goodsId,g.goods_name AS goodsName,g.sale_price AS salePrice,g.market_price AS marketPrice,g.pay_goods_num AS payGoodsNum,g.change_goods_num AS changeGoodsNum,g.return_goods_num AS returnGoodsNum FROM t_goods_goods g WHERE g.goods_status <>2 and g.goods_status<>3   and g.dealer_id=?");
		params.add(dealerId);
		sql.append(" LIMIT ?,?");
		params.add(rows*(pageNum - 1));
		params.add(rows);
		System.out.println("-----------------"+sql);	
		result = jdbcTemplate.queryForList(sql.toString(), params.toArray());
		return result;
		} catch (Exception e) {
			LOGGER.error("经销商下线商品列表查询出错",e);
			return null;
		}
	}


	/**
	 * 获取上线商品的总数
	 * @param dealerId
	 * @return
	 */
	public Integer getDealerOnLineGoodsCount(String dealerId) {
		String sql = "SELECT COUNT(*) FROM t_goods_goods g WHERE g.goods_status=3 and g.dealer_id=?";
		try {
			Integer result = jdbcTemplate.queryForObject(sql, Integer.class,dealerId);
			return result;
		} catch (Exception e) {
			LOGGER.error("经销商上线商品总数出错",e);
			return 0;
		}
	}


	/**
	 * 获取下线商品总数
	 * @param dealerId
	 * @return
	 */
	public Integer getDealerDownLineGoodsCount(String dealerId) {
		String sql = "SELECT COUNT(*) FROM t_goods_goods g  WHERE g.goods_status <>2 and g.goods_status<>3   and g.dealer_id=?";
		try {
			Integer result = jdbcTemplate.queryForObject(sql, Integer.class,dealerId);
			return result;
		} catch (Exception e) {
			LOGGER.error("经销商下线商品总数出错",e);
			return 0;
		}
	}
	
	
}
