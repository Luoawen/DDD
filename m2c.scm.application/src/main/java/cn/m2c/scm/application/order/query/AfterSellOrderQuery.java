package cn.m2c.scm.application.order.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.AftreSellLogisticsBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

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
			sql.append(" AND (after.dealer_order_id LIKE ? OR after.after_sell_order_id LIKE ? OR goods.goods_name LIKE ?) ");
			params.add(condition);
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
			params.add(condition);
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
		sql.append(" aftersell._status,aftersell.after_sell_order_id ");
		if (afterSellOrderTypeQuery(afterSellOrderId) == 0) {
			sql.append(" ,aftersell.back_express_no,aftersell.back_express_name ");
		}
		if (afterSellOrderTypeQuery(afterSellOrderId) == 1) {
			sql.append(" ,aftersell.express_no,aftersell.express_name ");
		}
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

}
