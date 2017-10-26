package cn.m2c.scm.application.dealerorder.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderAfterSellDetailBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

@Repository
public class DealerOrderAfterSellQuery {
	
	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 查询售后订单列表
	 * 
	 * @param orderType 售后期望
	 * @param status 售后状态
	 * @param condition 搜索条件(商品名称、订单号、售后号)
	 * @param startTime 开始时间
	 * @param endTime  结束时间
	 * @param dealerClassify 商家类型
	 * @param mediaInfo 媒体信息(判断media_id是否为空)
	 * @return
	 */
	public List<AfterSellOrderBean> dealerOrderAfterSellQuery(Integer orderType, String dealerId,String dealerOrderId,Integer status,
			String condition, String startTime, String endTime, String mediaInfo,
			Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" after.after_sell_order_id,after.order_type,after.back_money,after._status,dealer.dealer_name,after.created_date ");
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_goods goods ON after.goods_id = goods.goods_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ? ");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (null != status) {
			sql.append(" AND after._status = ? ");
			params.add(status);
		}
		if (null != condition && "".equals(condition)) {
			sql.append(" AND after.dealer_order_id OR after.after_sell_order_id OR goods.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (null != mediaInfo && !"".equals(mediaInfo)) {
			if ("有媒体信息".equals(mediaInfo)) {
				sql.append(" AND detail.media_id != '' ");
			}
			if ("无媒体信息".equals(mediaInfo)) {
				sql.append(" AND detail.meidia_id = '' ");
			}
		}
		
		sql.append(" ORDER BY after.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);
		List<AfterSellOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AfterSellOrderBean.class, params.toArray());
		for (AfterSellOrderBean bean : beanList) {
			bean.setGoodsInfo(afterSellDealerOrderGoodsInfoQuery(dealerId, dealerOrderId));
		}
		return beanList;
	}
	
	/**
	 * 获取售后订单列表商品信息
	 * @param dealerId
	 * @param dealerOrderId
	 * @return
	 */
	public List<GoodsInfoBean> afterSellDealerOrderGoodsInfoQuery(String dealerId,String dealerOrderId) {
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
		
		 List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), GoodsInfoBean.class,params.toArray());
		 return goodsInfoList;
		
	}
	
	/**
	 * 获取售后单总数
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
	public Integer totalDealerOrderAfterSellQuery(Integer orderType, String dealerId,Integer status, String condition,
			String startTime, String endTime, String mediaInfo) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*)  ");
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
		if (null != condition && "".equals(condition)) {
			sql.append(" AND after.dealer_order_id OR after.after_sell_order_id OR goods.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if ("有媒体信息".equals(mediaInfo)) {
			sql.append(" AND detail.media_id != '' ");
		}
		if ("无媒体信息".equals(mediaInfo)) {
			sql.append(" AND detail.meidia_id = '' ");
		}
		return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class,params.toArray());
	}
	
	/**
	 * 商家售后单详情
	 * @param afterSellOrderId
	 * @return
	 */
	public DealerOrderAfterSellDetailBean afterSellDealerOrderDetailQeury(String afterSellOrderId,String dealerId) {
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<Object>();
		sql.append(" SELECT after._status,after.order_type,after.after_sell_order_id,after.back_money,after.reason,after.created_date ");
		sql.append(" ,dealer.dealer_order_id");
		sql.append("  FROM t_scm_order_after_sell after ");
		sql.append("  INNER JOIN t_scm_order_dealer dealer   ");
		sql.append("  INNER JOIN t_scm_order_detail detail ");
		sql.append("  INNER JOIN t_scm_dealer_seller seller   ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		sql.append(" AND after.dealer_id = ? ");
		param.add(dealerId);
		sql.append(" AND after.dealer_order_id = dealer.dealer_order_id ");
		sql.append(" AND after.dealer_order_id = detail.dealer_order_id  ");
		sql.append("  AND detail.saler_user_id = seller.seller_id ");
		DealerOrderAfterSellDetailBean bean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderAfterSellDetailBean.class, param.toArray());
		List<GoodsInfoBean> goodsInfoBeans = aftetSellDealerOrderDetailGoodsInfoQuery(afterSellOrderId,dealerId);
		long totalPrice = 0;
		long orderTotalPrice = 0;
		
		for (GoodsInfoBean goodsInfo : goodsInfoBeans) {
			totalPrice += (goodsInfo.getPrice() * goodsInfo.getSellNum() +  goodsInfo.getFreight());
			goodsInfo.setTotalPrice(totalPrice);
			orderTotalPrice += totalPrice;
		}
		bean.setOrderTotalMoney(orderTotalPrice);
		bean.setGoodsInfoList(aftetSellDealerOrderDetailGoodsInfoQuery(afterSellOrderId,dealerId));
		return bean;
	}
	
	/**
	 * 获取商家退货单商品信息
	 * @param afterSellOrderId
	 * @return
	 */
	public List<GoodsInfoBean> aftetSellDealerOrderDetailGoodsInfoQuery(String afterSellOrderId,String dealerId) {
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<Object>();
		sql.append(" SELECT detail.discount_price,detail.sell_num,detail.freight,detail.plateform_discount,detail.dealer_discount ");
		sql.append(" ,detail.media_res_id,after.back_money");
		sql.append(" ,goods.goods_main_images,goods.goods_name,goods.goods_specifications ");
		sql.append(" FROM t_scm_order_detail detail ");
		sql.append(" INNER JOIN t_scm_order_after_sell after ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE 1 = 1 AND after.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		sql.append(" AND after.dealer_id = ? ");
		param.add(dealerId);
		sql.append(" AND detail.dealer_order_id = after.dealer_order_id ");
		sql.append(" AND detail.goods_id = goods.goods_id ");
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), GoodsInfoBean.class,param.toArray());
	}
	

}
