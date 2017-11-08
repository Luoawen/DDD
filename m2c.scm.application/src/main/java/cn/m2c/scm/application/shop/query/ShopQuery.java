package cn.m2c.scm.application.shop.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.shop.data.bean.ShopBean;
import cn.m2c.scm.domain.service.shop.ShopService;

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

	public ShopBean getAppShopInfo(String dealerId, String userId) {
		ShopBean bean = null;
		try {
			String sql = "SELECT * FROM t_scm_dealer_shop WHERE dealer_id=?";
			Integer isFucos = shopService.shopIsOrNotFucos(dealerId, userId);
			bean = this.supportJdbcTemplate.queryForBean(sql, ShopBean.class, dealerId);
			if (StringUtils.isEmpty(userId)) {
				bean.setIsFocus(0);
			}else {
				bean.setIsFocus(isFucos);
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
			StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer_shop ds , t_scm_dealer d WHERE 1 = 1 AND ds.dealer_id = d.dealer_id   ");
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
			System.out.println(shopList);
			/*if (dealerList != null && dealerList.size() > 0) {
				for (DealerBean dealerBean : dealerList) {
					ShopBean shopInfo = getShopInfo(dealerBean.getDealerId(), dealerBean.getDealerName());
					if (shopInfo != null)
						shopBeanList.add(shopInfo);
				}
			}*/
		return shopList;
	}

	public ShopBean getShop(String dealerId) {
		ShopBean shop = null;
		try {
			StringBuffer sql = new StringBuffer("SELECT * FROM t_scm_dealer sd WHERE dealer_status=1 AND dealer_id=?");
			DealerBean dealer = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerBean.class, dealerId);
			if (dealer != null) {
				shop = getShopInfo(dealerId, dealer.getDealerName());
			}
		} catch (Exception e) {
			log.error("查询店铺详情出错", e);
		}
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
					"SELECT COUNT(1) FROM t_scm_dealer_shop ds LEFT OUTER JOIN t_scm_dealer d ON ds.dealer_id = d.dealer_id WHERE 1 = 1  ");
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
}
