package cn.m2c.scm.application.shop.query;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.shop.data.bean.ShopBean;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.shop.ShopService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopQuery {
	private final static Logger log = LoggerFactory.getLogger(ShopQuery.class);

	@Autowired
	RestTemplate restTemplate;

	
	@Autowired
	ShopService shopService;
	@Resource
	SupportJdbcTemplate supportJdbcTemplate;

	@Autowired
	GoodsQueryApplication goodsQuery;

	public ShopBean getShopInfoByDealerId(String dealerId) {
		ShopBean bean = null;
		try {
			String sql = "SELECT * FROM t_scm_dealer_shop WHERE dealer_id=?";
			System.out.println("------------" + sql);
			bean = this.supportJdbcTemplate.queryForBean(sql, ShopBean.class, dealerId);
		} catch (Exception e) {
			log.error("查询店铺信息出错", e);
		}
		return bean;
	}

	
	/**
	 * 查询店铺是否被关注
	 * @param dealerId
	 * @param userId
	 * @return
	 */
	public ShopBean getAppShopInfo(String dealerId, String userId) {
		ShopBean bean = null;
		
		try {
			String sql = "SELECT * FROM t_scm_dealer_shop WHERE dealer_id=?";
			Integer isFucos = shopService.shopIsOrNotFucos(dealerId, userId);
			bean = this.supportJdbcTemplate.queryForBean(sql, ShopBean.class, dealerId);
			if(bean!=null ){
				if (StringUtils.isEmpty(userId)) {
					bean.setIsFocus(0);
					bean.setUserId("");
				}else {
					bean.setUserId(userId);
					bean.setIsFocus(isFucos);
				}
			}
		} catch (Exception e) {
			log.error("查询店铺信息出错", e);
		}
		return bean;
	}


	/**
	 * 查询店铺列表
	 * 
	 * @param dealerName
	 * @param dealerClassify
	 * @param dealerId
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<ShopBean> getShopList(String dealerName, String dealerClassify, String dealerId, Integer pageNum,
			Integer rows) {
		List<Object> params = new ArrayList<Object>();
		//List<ShopBean> shopBeanList = new ArrayList<ShopBean>();
			StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer_shop ds , t_scm_dealer d  WHERE 1 = 1  AND  ds.dealer_id = d.dealer_id ");
			if (dealerClassify != null && !"".equals(dealerClassify)) {
				sql.append(" AND d.dealer_classify LIKE concat('%', ?,'%') ");
				params.add(dealerClassify);
			}
			if (dealerName != null && !"".equals(dealerName)) {
				sql.append(" AND (d.dealer_name LIKE concat('%', ?,'%') or ds.shop_name LIKE concat('%', ?,'%'))");
				params.add(dealerName);
				params.add(dealerName);
			}
			if (dealerId != null && !"".equals(dealerId)) {
				sql.append(" AND d.dealer_id LIKE concat('%', ?,'%') ");
				params.add(dealerId);
			}
			sql.append(" LIMIT ?,?");
			params.add(rows * (pageNum - 1));
			params.add(rows);
			List<ShopBean> shopList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), ShopBean.class,
					params.toArray());
			System.out.println(shopList.toString());
			//设置在售数量
			if(shopList!=null && shopList.size()>0){
				for (ShopBean shopBean : shopList) {
					shopBean.setOnSaleGoods(getOnSaleGoodsCount(shopBean.getDealerId()));
				}
			}
		return shopList;
	}


	/**
	 * 根据商家id或者店铺id获取店铺信息
	 * @param dealerId
	 * @return
	 */
	public ShopBean getByDealerIdorShopId(String dealerId) {
		ShopBean shop  = null;
		try {
			StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer_shop shop INNER JOIN t_scm_dealer dealer WHERE dealer.dealer_status = 1 AND dealer.dealer_id = shop.dealer_id ");
			List<Object> params = new ArrayList<Object>();
			if(dealerId!=null && !"".equals(dealerId.trim())){
				sql.append(" AND (shop.dealer_id = ? or shop.shop_id = ?) ");
				params.add(dealerId);
				params.add(dealerId);
			}
			shop = this.supportJdbcTemplate.queryForBean(sql.toString(), ShopBean.class, params.toArray());
			if(shop!=null){
				shop.setOnSaleGoods(getOnSaleGoodsCount(shop.getDealerId()));
			}
		} catch (Exception e) {
			log.error("根据商家id或者店铺id获取店铺信息出错",e);
		}
		return shop;
		
	}
	
	/**
	 * 根据商家Id查询店铺信息<放入缓存>
	 * @param dealerId
	 * @return
	 */
	public ShopBean getShop(String dealerId) {
		log.info("查询店铺信息前时间"+System.currentTimeMillis());
		ShopBean shop = null;
		try {
			String key = ("m2c.scm.shop." + dealerId).trim();
			String redisShop = RedisUtil.getString(key); //从缓存中取数据
			if(redisShop!=null && !"".equals(redisShop)){
				ShopBean redisBean = JsonUtils.toBean(redisShop, ShopBean.class);
				 return redisBean;
			 }else {
				 	StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer sd WHERE dealer_status=1 AND dealer_id=?");
				 	DealerBean dealer = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerBean.class, dealerId);
				 	if (dealer != null) {
				 		shop = getShopInfo(dealerId, dealer.getDealerName());
				 	}
				 	RedisUtil.setString(key, 24 * 3600, JsonUtils.toStr(shop));//放入redis
			 }
		} catch (Exception e) {
			log.error("查询店铺详情出错", e);
		}
		log.info("查询店铺信息后时间"+System.currentTimeMillis());
		return shop;
	}

	/**
	 * 查询商家店铺信息
	 * 
	 * @param dealerId
	 * @return
	 */
	private ShopBean getShopInfo(String dealerId, String dealerName) {
		
			String sql = "SELECT * FROM t_scm_dealer_shop WHERE dealer_id=?";
			System.out.println("------------" + sql);
			ShopBean	bean = this.supportJdbcTemplate.queryForBean(sql, ShopBean.class, dealerId);
			if (bean != null) {
				bean.setOnSaleGoods(getOnSaleGoodsCount(dealerId));
				bean.setDealerName(dealerName);
			}
		
		return bean;
	}

	/**
	 * 查询商家在售商品数量
	 * 
	 * @param dealerId
	 * @return
	 */
	private Integer getOnSaleGoodsCount(String dealerId) {
		return goodsQuery.queryGoodsSellTotal(dealerId);
	}

	/**
	 * 查询店铺总数
	 * 
	 * @param dealerName
	 * @param dealerClassify
	 * @param dealerId
	 * @return
	 */
	public Integer getShopCount(String dealerName, String dealerClassify, String dealerId) {
		List<Object> params = new ArrayList<Object>();
		Integer resultCount = 0;
		try {
			StringBuffer sql = new StringBuffer(
					"SELECT COUNT(1) FROM t_scm_dealer_shop ds, t_scm_dealer d  WHERE 1 = 1  AND  ds.dealer_id = d.dealer_id");
			if (!StringUtils.isEmpty(dealerClassify)) {
				sql.append(" AND d.dealer_classify LIKE concat('%', ?,'%') ");
				params.add(dealerClassify);
			}
			if (!StringUtils.isEmpty(dealerName)) {
				sql.append(" AND (d.dealer_name LIKE concat('%', ?,'%') or ds.shop_name LIKE concat('%', ?,'%'))");
				params.add(dealerName);
				params.add(dealerName);
			}
			if (!StringUtils.isEmpty(dealerId)) {
				sql.append(" AND d.dealer_id LIKE concat('%', ?,'%') ");
				params.add(dealerId);
			}
			resultCount = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class,
					params.toArray());
		} catch (Exception e) {
			log.error("查询店铺总数出错", e);
		}
		return resultCount;
	}

	/**
	 * 根据店铺id查询店铺详情
	 * @param shopId
	 * @return
	 */
	public ShopBean getShopInfoByShopId(String shopId) {
		ShopBean shop = null;
		try {
			StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer_shop shop WHERE 1=1 AND shop.shop_id=?");
			shop = this.supportJdbcTemplate.queryForBean(sql.toString(), ShopBean.class, shopId);
		} catch (Exception e) {
			log.error("查询店铺详情出错", e);
		}
		return shop;
	}
	
	
	/**
	 * 通过商家Id查询客服号码
	 * @param dealerId
	 * @return
	 */
	public String getDealerShopCustmerTel(String dealerId) {
		String custmerTel = "";
		try {
			StringBuffer sql = new StringBuffer(" SELECT customer_service_tel from t_scm_dealer_shop where dealer_id = ? ");
			custmerTel = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), String.class,dealerId);
		} catch (DataAccessException e) {
			log.error("查询出错", e);
		}
		return custmerTel;
		
	}
	
	
	/**
	 * 多个商家Id获取店铺List
	 * @param dealerIds
	 * @return
	 */
	public List<ShopBean> getShopInfosByIds(List<String> dealerIds) throws NegativeException{
		ShopBean shop = null;
		List<ShopBean> shops = new ArrayList<ShopBean>();
		try {
			StringBuffer sql = new StringBuffer();
			sql.append(" SELECT s.dealer_id, d.dealer_name, s.shop_id, s.shop_name, s.shop_icon").
			append(" FROM t_scm_dealer_shop s LEFT JOIN t_scm_dealer d ON s.dealer_id = d.dealer_id ").
			append(" WHERE s.dealer_id = ? ");
			for (String dealerId : dealerIds) {
				shop = this.supportJdbcTemplate.queryForBean(sql.toString(), ShopBean.class,dealerId);
				if(shop != null) {
					shops.add(shop);
				}
			}
			if (shops == null || shops.size() <0) {
				throw new NegativeException(MCode.V_400,"没有查到记录");
			}
		} catch (Exception e) {
			log.error("查询店铺信息出错",e);
		}
		return shops;
	}
}
