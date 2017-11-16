package cn.m2c.scm.application.dealerorder.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderAfterSellDetailBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.export.SaleAfterExpModel;

@Repository
public class DealerOrderAfterSellQuery {

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 查询售后订单列表
	 * 
	 * @param orderType
	 *            售后期望
	 * @param status
	 *            售后状态
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
	public List<AfterSellOrderBean> dealerOrderAfterSellQuery(Integer orderType, String dealerId, String dealerOrderId,
			Integer status, String condition, String startTime, String endTime, String mediaInfo, Integer pageNum,
			Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT DISTINCT after.sku_id,after.order_id,");
		sql.append(" after.after_sell_order_id,after.order_type,after.back_money,after._status,dealer.dealer_name,after.created_date ");
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_goods goods ON after.goods_id = goods.goods_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE dealer.dealer_id = ? ");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND after._status = ? ");
			params.add(status);
		}
		if (!StringUtils.isEmpty(condition)) {
			sql.append(
					" AND (after.dealer_order_id LIKE concat('%',?,'%') OR after.after_sell_order_id LIKE concat('%',?,'%') OR goods.goods_name LIKE concat('%',?,'%')) ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (!StringUtils.isEmpty(mediaInfo)) {
			if ("1".equals(mediaInfo)) {
				sql.append(" AND detail.media_id IS NOT NULL ");
			}
			if ("0".equals(mediaInfo)) {
				sql.append(" AND detail.media_id IS NULL ");
			}
		}

		sql.append(" ORDER BY after.created_date DESC ");
		if (rows != null && pageNum != null) {
			sql.append(" LIMIT ?,?");
			params.add(rows * (pageNum - 1));
			params.add(rows);
		}

		List<AfterSellOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				AfterSellOrderBean.class, params.toArray());
		for (AfterSellOrderBean bean : beanList) {
			bean.setGoodsInfo(afterSellGoodsInfoQuery(bean.getSkuId(), dealerOrderId));
		}
		return beanList;
	}

	/**
	 * 获取售后订单列表商品信息
	 * 
	 * @param dealerId
	 * @param dealerOrderId
	 * @return
	 */
	public List<GoodsInfoBean> afterSellDealerOrderGoodsInfoQuery(String dealerId, String dealerOrderId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT goods.goods_main_images,goods.goods_name ");
		sql.append(" FROM t_scm_order_after_sell after ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE 1 = 1 AND after.dealer_id = ? ");
		params.add(dealerId);
		sql.append(" AND after.dealer_order_id = ? ");
		params.add(dealerOrderId);
		sql.append(" AND after.goods_id = goods.goods_id ");

		List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				GoodsInfoBean.class, params.toArray());
		return goodsInfoList;

	}

	/**
	 * 获取售后订单列表商品信息
	 * 
	 * @param skuId
	 * @param dealerOrderId
	 * @return
	 */
	public GoodsInfoBean afterSellGoodsInfoQuery(String skuId, String dealerOrderId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT detail.goods_icon, g.goods_name, g.goods_sub_title, sku.sku_name, ")
				.append(" detail.discount_price,after.sell_num ").append(" FROM t_scm_goods_sku sku")
				.append(" LEFT OUTER JOIN t_scm_goods g ON sku.goods_id=g.id")
				.append(" LEFT OUTER JOIN t_scm_order_after_sell after ON sku.sku_id = after.sku_id ")
				.append(" LEFT OUTER JOIN t_scm_order_detail detail ON g.goods_id = detail.goods_id ")
				.append(" where sku.sku_id=?");
		params.add(skuId);
		// params.add(dealerOrderId);
		GoodsInfoBean goodsInfoList = this.supportJdbcTemplate.queryForBean(sql.toString(), GoodsInfoBean.class,
				params.toArray());
		return goodsInfoList;

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
	public Integer totalDealerOrderAfterSellQuery(Integer orderType, String dealerId, Integer status, String condition,
			String startTime, String endTime, String mediaInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT( DISTINCT after.id)  ");
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_goods goods ON after.goods_id = goods.goods_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ?");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND after._status = ? ");
			params.add(status);
		}
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE concat('%',?,'%') OR after.after_sell_order_id LIKE concat('%',?,'%') OR goods.goods_name LIKE concat('%',?,'%') ) ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if ("1".equals(mediaInfo)) {
			sql.append(" AND detail.media_id IS NOT NULL ");
		}
		if ("0".equals(mediaInfo)) {
			sql.append(" AND detail.media_id IS NULL ");
		}
		return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());
	}

	/**
	 * 商家售后单详情
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public DealerOrderAfterSellDetailBean afterSellDealerOrderDetailQeury(String afterSellOrderId, String dealerId) {
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<Object>();
		DealerOrderAfterSellDetailBean bean = null;
		sql.append(" SELECT after._status,after.order_type,after.after_sell_order_id,after.back_money,after.reason, ")
		.append(" after.created_date,after.return_freight,after.reject_reason, dealer.dealer_order_id, dealer._status doStatus");
		sql.append(" ,after.order_id,dealer.goods_amount,dealer.order_freight,dealer.plateform_discount,dealer.dealer_discount ");
		sql.append("  FROM t_scm_order_after_sell after ");
		sql.append("  LEFT OUTER JOIN t_scm_order_dealer dealer   ON after.dealer_order_id = dealer.dealer_order_id   ");
		sql.append("  LEFT OUTER JOIN t_scm_order_detail detail  ON after.dealer_order_id = detail.dealer_order_id AND after.sku_id = detail.sku_id  ");
		sql.append("  LEFT OUTER JOIN t_scm_dealer_seller seller  ON detail.saler_user_id = seller.seller_id   ")
		.append(" LEFT OUTER JOIN t_scm_order_main main ON after.order_id = main.order_id ");
		sql.append(" WHERE after.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		if (!StringUtils.isEmpty(dealerId)) {
			sql.append(" AND after.dealer_id = ? ");
			param.add(dealerId);
		}
		bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				DealerOrderAfterSellDetailBean.class, param.toArray());
		if (!StringUtils.isEmpty(dealerId)) {
			bean.setDealerId(dealerId);
		}
		GoodsInfoBean goodsInfo = aftetSellDealerOrderDetailGoodsInfoQuery(afterSellOrderId, dealerId);
		System.out.println(goodsInfo);
		long totalPrice = 0; // 商品总价格
		//long orderTotalPrice = 0; // 订单总价格
		if (goodsInfo != null) {
			totalPrice += (goodsInfo.getPrice() * goodsInfo.getSellNum());// + goodsInfo.getFreight()
			goodsInfo.setTotalPrice(totalPrice);
		}

		if (bean != null) {
			bean.setGoodsInfo(aftetSellDealerOrderDetailGoodsInfoQuery(afterSellOrderId, dealerId));
		}
		return bean;
	}

	/**
	 * 获取商家退货单商品信息
	 * 
	 * @param afterSellOrderId
	 * @return
	 */
	public GoodsInfoBean aftetSellDealerOrderDetailGoodsInfoQuery(String afterSellOrderId, String dealerId) {
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<Object>();
		sql.append(" SELECT detail.discount_price,after.sell_num,detail.freight,detail.plateform_discount,detail.dealer_discount ");
		sql.append(" ,detail.media_res_id,after.back_money,after.sku_id ");
		sql.append(" ,detail.goods_icon,detail.goods_name,detail.sku_name ");
		sql.append(" FROM t_scm_order_detail detail ");
		sql.append(" INNER JOIN t_scm_order_after_sell after ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		if(!StringUtils.isEmpty(dealerId)) {
			sql.append(" AND after.dealer_id = ? ");
			param.add(dealerId);
		}
		sql.append(" AND detail.sku_id = after.sku_id AND detail.dealer_order_id = after.dealer_order_id ");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), GoodsInfoBean.class, param.toArray());
	}

	/***
	 * 商家平台售后单导出
	 * 
	 * @param orderType
	 * @param dealerId
	 * @param dealerOrderId
	 * @param status
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param mediaInfo
	 * @return
	 */
	public List<SaleAfterExpModel> orderSaleAfterExportQuery(Integer orderType, String dealerId, Integer status,
			String condition, String startTime, String endTime, String mediaInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(
				" SELECT a.sku_id skuId, dtl.sku_name skuName, dtl.goods_name goodsName, dtl.goods_title goodsTitle,")
				.append(" a.after_sell_order_id saleAfterNo, a.order_type orderType, a.back_money backMoney, a._status status, dealer.dealer_name dealerName, a.created_date createTime")
				.append(" FROM t_scm_order_after_sell a")
				.append(" LEFT JOIN t_scm_dealer dealer ON a.dealer_id = dealer.dealer_id ")
				.append(" LEFT JOIN t_scm_order_detail dtl ON dtl.sku_id = a.sku_id AND dtl.dealer_order_id=a.dealer_order_id");
		// .append(" LEFT JOIN t_scm_goods_sku sku ON a.sku_id = sku.sku_id");
		sql.append(" WHERE dealer.dealer_id = ? ");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND a.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND a._status = ? ");
			params.add(status);
		}
		if (!StringUtils.isEmpty(condition)) {
			sql.append(
					" AND a.dealer_order_id LIKE concat('%',?,'%') OR a.after_sell_order_id LIKE concat('%',?,'%') OR goods.goods_name LIKE concat('%',?,'%') ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (!StringUtils.isEmpty(mediaInfo)) {
			if ("1".equals(mediaInfo)) {
				sql.append(" AND detail.media_id != '' ");
			}
			if ("0".equals(mediaInfo)) {
				sql.append(" AND detail.meidia_id = '' ");
			}
		}

		sql.append(" ORDER BY a.created_date DESC ");
		sql.append(" LIMIT 2000");
		List<SaleAfterExpModel> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				SaleAfterExpModel.class, params.toArray());
		return beanList;
	}
}
