package cn.m2c.scm.application.order.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.AfterSellBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.AftreSellLogisticsBean;
import cn.m2c.scm.application.order.data.bean.AppOrderBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.OrderDetailBean;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.application.order.data.bean.SkuNumBean;
import cn.m2c.scm.domain.NegativeException;

/**
 * 售后订单查询
 * 
 * @author lqwen
 *
 */
@Repository
public class AfterSellOrderQuery {

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 查询售后订单列表
	 * 
	 * @param orderType
	 *            售后期望
	 * @param status
	 *            售后状态
	 * @param createDate
	 *            申请时间
	 * @param condition
	 *            搜索条件(商品名称、订单号、售后号)
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param dealerClassify
	 *            商家类型
	 * @param mediaInfo
	 *            媒体信息(判断media_id是否为空)
	 * @return
	 */
	public List<AfterSellOrderBean> afterSelleOrderQuery(Integer orderType, Integer status, String createDate,
			String condition, String startTime, String endTime, String dealerClassify, String mediaInfo,
			Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(
				" after.after_sell_order_id,after.order_id,after.order_type,after.back_money,after._status,dealer.dealer_name,after.created_date ");
		sql.append(" FROM t_scm_order_after_sell after ");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id");
		sql.append(" LEFT JOIN t_scm_order_main main ON after.order_id = main.order_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE 1 = 1 ");
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND after._status = ? ");
			params.add(status);
		}
		if (null != createDate && "".equals(createDate)) {
			sql.append(" AND after.created_date = ? ");
			params.add(createDate);
		}
		if (null != condition && !"".equals(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE ? OR after.after_sell_order_id LIKE ? OR detail.goods_name LIKE ?) ");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (null != dealerClassify && "".equals(dealerClassify)) {
			sql.append(" AND dealer.dealer_classify = ? ");
			params.add(dealerClassify);
		}
		if ("有媒体信息".equals(mediaInfo)) {
			sql.append(" AND detail.media_id != '' ");
		}
		if ("无媒体信息".equals(mediaInfo)) {
			sql.append(" AND detail.meidia_id = '' ");
		}
		sql.append(" ORDER BY after.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);
		
		System.out.println("    SHOW    LIST  SQL---------------------------------"+sql);
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), AfterSellOrderBean.class, params.toArray());
	}

	/**
	 * 获取售后单总数
	 * 
	 * @param orderType
	 * @param status
	 * @param createDate
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param dealerClassify
	 * @param mediaInfo
	 * @return
	 */
	public Integer totalAfterSelleOrderQuery(Integer orderType, Integer status, String createDate, String condition,
			String startTime, String endTime, String dealerClassify, String mediaInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*)  ");
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_goods goods ON after.goods_id = goods.goods_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE 1 = 1 ");
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND after._status = ? ");
			params.add(status);
		}
		if (null != createDate && "".equals(createDate)) {
			sql.append(" AND after.created_date = ? ");
			params.add(createDate);
		}
		if (null != condition && !"".equals(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE ? OR after.after_sell_order_id LIKE ? OR goods.goods_name LIKE ?) ");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (null != dealerClassify && "".equals(dealerClassify)) {
			sql.append(" AND dealer.dealer_classify = ? ");
			params.add(dealerClassify);
		}
		if ("1".equals(mediaInfo)) {
			sql.append(" AND detail.media_id != '' ");
		}
		if ("0".equals(mediaInfo)) {
			sql.append(" AND detail.meidia_id = '' ");
		}

		System.out.println("SHOW   TOTAL SQL ----------------------------------------"+sql);
		return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());

	}

	/**
	 * 获取退货/换货信息
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public AftreSellLogisticsBean afterSellOrderLogisticsQuery(String afterSellOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" aftersell._status, aftersell.after_sell_order_id, aftersell.order_type ");
		sql.append(" ,aftersell.back_express_no, aftersell.back_express_name ");
		sql.append(" ,aftersell.express_no, aftersell.express_name ");
		sql.append(" FROM t_scm_order_after_sell aftersell ");
		sql.append(" WHERE 1 = 1 AND aftersell.after_sell_order_id = ? ");
		AftreSellLogisticsBean bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				AftreSellLogisticsBean.class, afterSellOrderId);
		GoodsInfoBean goodsInfo = afterSellGoodsInfoQuery(afterSellOrderId);
		if (null != bean) {
			bean.setGoodsInfo(goodsInfo);
		}
		return bean;

	}

	/**
	 * 获取售后类型（退货/换货）
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public Integer afterSellOrderTypeQuery(String afterSellOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT order_type FROM t_scm_order_after_sell WHERE after_sell_order_id = ? ");
		Integer orderType = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class,
				afterSellOrderId);
		return orderType;

	}

	/**
	 * 获取售后订单商品列表
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public GoodsInfoBean afterSellGoodsInfoQuery(String afterSellOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  ");
		sql.append(" detail.goods_icon,detail.goods_name,after.sell_num,detail.sku_name ");
		sql.append(" FROM ");
		sql.append(" t_scm_order_after_sell after ");
		sql.append(" INNER JOIN t_scm_order_detail detail ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		sql.append(" AND after.dealer_order_id = detail.dealer_order_id AND after.sku_id = detail.sku_id ");
		GoodsInfoBean goodsInfo = this.supportJdbcTemplate.queryForBean(sql.toString(),
				GoodsInfoBean.class, afterSellOrderId);
		return goodsInfo;
	}

	/**
	 * 售后单详情
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public AfterSellOrderDetailBean afterSellOrderDetailQeury(String afterSellOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT after._status,after.after_sell_order_id,after.back_money,after.reason,after.created_date,detail.freight ")
		.append(" ,after.order_id,main.goods_amount,main.order_freight,main.plateform_discount,main.dealer_discount ");
		sql.append(" ,dealer.dealer_name,dealer.dealer_classify  ,seller.seller_name,seller.seller_phone ");
		if (afterSellOrderTypeQuery(afterSellOrderId) == 0) {
			sql.append(" ,after.order_type ");
		}
		if (afterSellOrderTypeQuery(afterSellOrderId) == 1) {
			sql.append(" ,after.order_type ");
		}
		sql.append("  FROM t_scm_order_after_sell after ")
		.append(" LEFT JOIN t_scm_order_main main ON after.order_id = main.order_id ");
		sql.append("  INNER JOIN t_scm_dealer dealer   ");
		sql.append("  INNER JOIN t_scm_order_detail detail ");
		sql.append("  INNER JOIN t_scm_dealer_seller seller   ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		sql.append(" AND after.dealer_id = dealer.dealer_id ");
		sql.append(" AND after.dealer_order_id = detail.dealer_order_id AND after.sku_id = detail.sku_id  ");
		sql.append("  AND detail.saler_user_id = seller.seller_id ");
		AfterSellOrderDetailBean bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				AfterSellOrderDetailBean.class, afterSellOrderId);
		GoodsInfoBean goodsInfoBeans = aftetSellOrderDetailGoodsInfoQuery(afterSellOrderId);
		long totalPrice = 0;
		long orderTotalPrice = 0;
		totalPrice += (goodsInfoBeans.getPrice() * goodsInfoBeans.getSellNum() + goodsInfoBeans.getFreight());
		goodsInfoBeans.setTotalPrice(totalPrice);
		orderTotalPrice += totalPrice;
		bean.setOrderTotalMoney(orderTotalPrice);
		bean.setGoodsInfo(aftetSellOrderDetailGoodsInfoQuery(afterSellOrderId));
		return bean;
	}

	/**
	 * 获取退货单商品信息
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public GoodsInfoBean aftetSellOrderDetailGoodsInfoQuery(String afterSellOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT detail.discount_price,after.sell_num,detail.freight,detail.plateform_discount,detail.dealer_discount ");
		sql.append(" ,detail.media_res_id,after.back_money,after.order_id,detail._price ");
		sql.append(" ,detail.goods_icon,detail.goods_name,detail.sku_name ");
		sql.append(" FROM t_scm_order_detail detail ");
		sql.append(" INNER JOIN t_scm_order_after_sell after ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		sql.append(" AND detail.dealer_order_id = after.dealer_order_id AND after.sku_id = detail.sku_id ");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), GoodsInfoBean.class, afterSellOrderId);

	}
	/***
	 * 获取同一个活动的订单中的商品列表
	 * @param marketId
	 * @param orderId
	 */
	public List<SkuNumBean> getOrderDtlByMarketId(String marketId, String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.sku_id, a.sell_num, a.is_change, a.goods_amount, a.marketing_id, a.change_price\r\n")
		.append("FROM	t_scm_order_detail a\r\n")
		.append("WHERE	a.order_id = ?\r\n")
		.append("AND a.marketing_id = ? ")
		.append("AND a.sku_id NOT IN(SELECT b.sku_id FROM t_scm_order_after_sell b WHERE b.order_id=a.order_id AND b.dealer_order_id= a.dealer_order_id AND b._status > 3) ");
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), SkuNumBean.class, orderId, marketId);
	}
	
	/***
	 * 获取订单中一个活动
	 * @param marketId
	 * @param orderId
	 */
	public SimpleMarket getMarketById(String marketId, String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT marketing_id, market_level, market_type, threshold, threshold_type, discount\r\n")
		.append("FROM	t_scm_order_marketing_used\r\n")
		.append("WHERE	order_id = ? AND marketing_id = ? AND _status=1");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), SimpleMarket.class, orderId, marketId);
	}
	
	/***
	 * 获取订单中一个活动
	 * @param marketId
	 * @param orderId
	 */
	public SimpleMarket getMarketBySkuIdAndOrderId(String skuId, String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT marketing_id, market_level, market_type, threshold, threshold_type, discount\r\n")
		.append("FROM	t_scm_order_marketing_used\r\n")
		.append("WHERE	order_id = ? AND marketing_id = (SELECT a.marketing_id FROM t_scm_order_detail a WHERE a.order_id=? AND a.sku_id=?)")
		.append(" AND _status=1");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), SimpleMarket.class, orderId, orderId, skuId);
	}
	
	/***
	 * 获取商家订单
	 * @param marketId
	 * @param orderId
	 */
	public OrderDealerBean getDealerOrderById(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT _status status, order_freight oderFreight FROM	t_scm_order_dealer WHERE dealer_order_id = ?");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), OrderDealerBean.class, dealerOrderId);
	}
	
	/***
	 * 获取APP售后单列表页面
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws NegativeException
	 */
	public List<AfterSellBean> getAppSaleAfterList(String userId, Integer status,
			int pageIndex, int pageSize) throws NegativeException {
		List<AfterSellBean> result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.created_date, a.after_sell_order_id, a.order_id, a.dealer_order_id, a.dealer_id, a.goods_id, a.sku_id, a.sell_num, a._status, a.back_money, a.order_type\r\n")
			.append(",c.dealer_name, b.goods_name, b.sku_name, b.goods_type, b.goods_type_id, b.discount_price, b.goods_icon\r\n") 
			.append(", a.last_updated_date, a.reject_reason, a.reason, a.back_express_no, a.back_express_name, a.express_no, a.express_name ")
			.append("FROM t_scm_order_after_sell a \r\n")
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id\r\n")
			.append("LEFT OUTER JOIN t_scm_dealer c ON a.dealer_id = c.dealer_id \r\n")
			.append(" WHERE a.user_id=?");
			params.add(userId);
			
			if (status != null) {
				sql.append(" AND a._status=?");
				params.add(status);
			}
			
			sql.append("ORDER BY a.created_date DESC LIMIT ?,?");
			params.add((pageIndex - 1) * pageSize);
			params.add(pageSize);
			
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AfterSellBean.class, params.toArray());
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询APP售后单列表出错");
		}
		return result;
	}
	
	/***
	 * 获取APP售后单列表页面
	 * @param userId
	 * @param status
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws NegativeException
	 */
	public Integer getAppSaleAfterTotal(String userId, Integer status) throws NegativeException {
		Integer result = 0;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(1) FROM t_scm_order_after_sell a \r\n")
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id\r\n")
			.append("LEFT OUTER JOIN t_scm_dealer c ON a.dealer_id = c.dealer_id \r\n")
			.append(" WHERE a.user_id=?");
			params.add(userId);
			
			if (status != null) {
				sql.append(" AND a._status=?");
				params.add(status);
			}
			
			result = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询APP售后单列表总数出错");
		}
		return result;
	}
	
	public String getMainOrderPayNo(String orderNo) throws NegativeException {
		String result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT pay_no FROM t_scm_order_main a \r\n")
			.append(" WHERE a.order_id=?");
			
			params.add(orderNo);
			
			result = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), String.class);
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询Order 支付单号出错！");
		}
		return result;
	}
}
