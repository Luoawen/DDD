package cn.m2c.scm.application.order.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.AfterSellApplyReason;
import cn.m2c.scm.application.order.data.bean.AfterSellBean;
import cn.m2c.scm.application.order.data.bean.AfterSellFlowBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.AftreSellLogisticsBean;
import cn.m2c.scm.application.order.data.bean.DealerOrderMoneyBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.SimpleCoupon;
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
				" after.after_sell_order_id,after.order_id,after.order_type,after.back_money, after.return_freight,after._status,dealer.dealer_name,after.created_date, ");
		sql.append(" detail.is_special,detail.special_price, after.sort_no ");
		sql.append(" FROM t_scm_order_after_sell after ");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id");
		sql.append(" LEFT JOIN t_scm_order_main main ON after.order_id = main.order_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id AND after.sku_id=detail.sku_id AND after.sort_no = detail.sort_no \r\n");
		sql.append(" WHERE 1 = 1 ");
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (status != null && (status >= 20 && status < 28)) {
        	switch(status) {
	        	case 20: //待商家同意
	        		sql.append(" AND after._status IN(?,?,?)\r\n");
		            params.add(0);
		            params.add(1);
		            params.add(2);
	        		break;
	        	case 21://待顾客寄回商品
	        		sql.append(" AND after.order_type IN(0,1) AND after._status =?\r\n");
		            params.add(4);
	        		break;
	        	case 22://待商家确认退款
	        		sql.append(" AND ((after.order_type=0 AND after._status =?) OR (after.order_type=1 AND after._status =?) OR (after.order_type=2 AND after._status =?))\r\n");
		            params.add(8);
		            params.add(6);
		            params.add(4);
	        		break;
	        	case 23://待商家发货
	        		sql.append(" AND (after.order_type=0 AND after._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (after.order_type=0 AND after._status =?)\r\n");
		            params.add(7);
	        		break;
	        	case 25://售后已完成
	        		sql.append(" AND after._status >= ?\r\n");
		            params.add(9);
	        		break;
	        	case 26://售后已取消
	        		sql.append(" AND after._status = ?\r\n");
		            params.add(-1);
	        		break;
	        	case 27://商家已拒绝
	        		sql.append(" AND after._status = ?\r\n");
		            params.add(3);
	        		break;	        	
        	}
        }
		if (null != createDate && "".equals(createDate)) {
			sql.append(" AND after.created_date = ? ");
			params.add(createDate);
		}
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE concat('%', ?,'%') OR after.after_sell_order_id LIKE concat('%', ?,'%') OR detail.goods_name LIKE concat('%', ?,'%') ");
			sql.append(" OR after.dealer_id IN (SELECT dd.dealer_id FROM t_scm_dealer dd WHERE dd.dealer_name LIKE concat('%', ?,'%')))");
			params.add(condition);
			params.add(condition);
			params.add(condition);
			params.add(condition);
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
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id AND after.sku_id=detail.sku_id AND after.sort_no = detail.sort_no \r\n");
		sql.append(" WHERE 1 = 1 ");
		if (null != orderType) {
			sql.append(" AND after.order_type = ? ");
			params.add(orderType);
		}
		if (status != null && (status >= 20 && status < 28)) {
        	switch(status) {
	        	case 20: //待商家同意
	        		sql.append(" AND after._status IN(?,?,?)\r\n");
		            params.add(0);
		            params.add(1);
		            params.add(2);
	        		break;
	        	case 21://待顾客寄回商品
	        		sql.append(" AND after.order_type IN(0,1) AND after._status =?\r\n");
		            params.add(4);
	        		break;
	        	case 22://待商家确认退款
	        		sql.append(" AND ((after.order_type=0 AND after._status =?) OR (after.order_type=1 AND after._status =?) OR (after.order_type=2 AND after._status =?))\r\n");
		            params.add(8);
		            params.add(6);
		            params.add(4);
	        		break;
	        	case 23://待商家发货
	        		sql.append(" AND (after.order_type=0 AND after._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (after.order_type=0 AND after._status =?)\r\n");
		            params.add(7);
	        		break;
	        	case 25://售后已完成
	        		sql.append(" AND after._status >= ?\r\n");
		            params.add(9);
	        		break;
	        	case 26://售后已取消
	        		sql.append(" AND after._status = ?\r\n");
		            params.add(-1);
	        		break;
	        	case 27://商家已拒绝
	        		sql.append(" AND after._status = ?\r\n");
		            params.add(3);
	        		break;	        	
        	}
        }
		if (null != createDate && "".equals(createDate)) {
			sql.append(" AND after.created_date = ? ");
			params.add(createDate);
		}
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE concat('%', ?,'%') OR after.after_sell_order_id LIKE concat('%', ?,'%') OR detail.goods_name LIKE concat('%', ?,'%') ");
			sql.append(" OR after.dealer_id IN (SELECT dd.dealer_id FROM t_scm_dealer dd WHERE dd.dealer_name LIKE concat('%', ?,'%')))");
			params.add(condition);
			params.add(condition);
			params.add(condition);
			params.add(condition);
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
		sql.append(" * ");
		sql.append(" FROM t_scm_order_after_sell af ");
		sql.append(" WHERE af.after_sell_order_id = ? ");
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
		sql.append(" dtl.goods_icon,dtl.goods_name, after.sell_num, dtl.sku_name, dtl.is_special, dtl.special_price, dtl.sort_no ");
		sql.append(" FROM t_scm_order_after_sell after ");
		sql.append(" LEFT OUTER JOIN t_scm_order_detail dtl ON after.dealer_order_id = dtl.dealer_order_id AND after.sku_id = dtl.sku_id AND after.sort_no=dtl.sort_no \r\n");
		sql.append(" WHERE after.after_sell_order_id = ? ");
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
		sql.append(" SELECT after._status, after.after_sell_order_id, after.back_money, after.reason, after.created_date, dtl.freight ")
		.append(" ,after.order_id, main.goods_amount, main.order_freight, main.plateform_discount, main.dealer_discount ")
		.append(" , dealer.dealer_name, dealer.dealer_classify, seller.seller_name, seller.seller_phone ")
		.append(" ,after.order_type ");		
		sql.append("  FROM t_scm_order_after_sell after ")
		.append(" LEFT JOIN t_scm_order_main main ON after.order_id = main.order_id ");
		sql.append("  INNER JOIN t_scm_dealer dealer   ");
		sql.append("  INNER JOIN t_scm_order_detail dtl ");
		sql.append("  INNER JOIN t_scm_dealer_seller seller   ");
		sql.append(" WHERE after.after_sell_order_id = ? ");
		sql.append(" AND after.dealer_id = dealer.dealer_id ");
		sql.append(" AND after.dealer_order_id = dtl.dealer_order_id AND after.sku_id = dtl.sku_id AND after.sort_no=dtl.sort_no ");
		sql.append(" AND after.sort_no = detail.sort_no ");
		sql.append(" AND dtl.saler_user_id = seller.seller_id ");
		
		AfterSellOrderDetailBean bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				AfterSellOrderDetailBean.class, afterSellOrderId);
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
				" SELECT  after.sell_num, after.back_money, after.order_id, dtl.dealer_discount ");
		sql.append(" ,dtl.media_res_id, dtl.discount_price, dtl.freight, dtl._price, dtl.plateform_discount, dtl.goods_amount ");
		sql.append(" ,dtl.goods_icon, dtl.goods_name, dtl.goods_unit, dtl.sku_name, dtl.is_special, dtl.special_price, dtl.is_change, dtl.change_price, dtl.sort_no ");
		sql.append(" FROM t_scm_order_detail dtl ");
		sql.append(" INNER JOIN t_scm_order_after_sell after ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE after.after_sell_order_id = ? ");
		sql.append(" AND dtl.dealer_order_id = after.dealer_order_id AND after.sku_id = dtl.sku_id AND dtl.sort_no=after.sort_no ");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), GoodsInfoBean.class, afterSellOrderId);

	}
	/***
	 * 获取同一个活动的订单中的商品sku及数量
	 * @param marketId
	 * @param orderId
	 */
	public List<SkuNumBean> getOrderDtlByMarketId(String marketId, String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.sku_id, a.sell_num, a.is_change, a.goods_amount, a.marketing_id, a.change_price, a.sort_no\r\n")
		.append("FROM t_scm_order_detail a\r\n")
		.append("WHERE a.order_id = ?\r\n")
		.append("AND a.marketing_id = ? ")
		.append("AND ((a.sort_no=0 AND a.sku_id NOT IN(SELECT b.sku_id FROM t_scm_order_after_sell b WHERE b.order_id=a.order_id AND b.dealer_order_id= a.dealer_order_id AND b._status > 3)) ")
		.append(" OR (a.sort_no NOT IN(SELECT b.sort_no FROM t_scm_order_after_sell b WHERE b.order_id=a.order_id AND b.dealer_order_id= a.dealer_order_id AND b._status > 3)))")
		;
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
	public SimpleMarket getMarketBySkuIdAndOrderId(String skuId, String orderId, int sortNo) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT marketing_id, market_level, market_type, threshold, threshold_type, discount\r\n")
		.append("FROM	t_scm_order_marketing_used\r\n")
		.append("WHERE	order_id = ? AND marketing_id = (SELECT a.marketing_id FROM t_scm_order_detail a WHERE a.order_id=? AND a.sku_id=? AND a.sort_no=?)")
		.append(" AND _status=1");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), SimpleMarket.class, orderId, orderId, skuId, sortNo);
	}
	
	/***
	 * 获取商家订单
	 * @param marketId
	 * @param orderId
	 */
	public DealerOrderMoneyBean getDealerOrderById(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT _status status, order_freight orderFreight FROM	t_scm_order_dealer WHERE dealer_order_id = ?");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderMoneyBean.class, dealerOrderId);
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
			sql.append("SELECT a.created_date, a.back_express_name, a.back_express_no, a.after_sell_order_id, a.order_id, a.dealer_order_id, a.dealer_id, a.goods_id, a.sku_id, a.sell_num, a._status, a.back_money, a.order_type\r\n")
			.append(",c.dealer_name, b.goods_name, b.sku_name, b.goods_type, b.goods_type_id, b.discount_price, b.goods_icon\r\n") 
			.append(", a.last_updated_date, a.reject_reason, a.reason, a.back_express_no, a.back_express_name, a.express_no, a.express_name, a.sort_no, a.return_freight ")
			.append("FROM t_scm_order_after_sell a \r\n")
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id AND a.sort_no=b.sort_no\r\n")
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
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id AND a.sort_no=b.sort_no\r\n")
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
	/***
	 * 获取已经同意售后的商品sku
	 * 
	 * @param dealerOrderId
	 * @return
	 * @throws NegativeException
	 */
	public List<SkuNumBean> getSkuIdsByDealerOrderId(String dealerOrderId) throws NegativeException {
		List<SkuNumBean> result = null;
		try {
			List<Object> params = new ArrayList<>(1);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.sku_id, a.sort_no FROM t_scm_order_after_sell a \r\n")
			.append(" WHERE a.dealer_order_id=? AND a._status NOT IN(-1, 0, 1, 2, 3)");
			params.add(dealerOrderId);
			
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), SkuNumBean.class, params.toArray());
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询商家订单号的售后skuId出错！");
		}
		return result;
	}
	
	
	/**
	 * 查询售后理由
	 * @return
	 * @throws NegativeException 
	 */
	public List<AfterSellApplyReason> getApplyReason(Integer applyStatus) throws NegativeException{
		List<AfterSellApplyReason> reasonList = null;
		try {
			List<Object> params = new ArrayList<>(1);
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT reason_id, apply_order_status, apply_desc FROM t_scm_order_after_sell_reason WHERE apply_order_status = ? ");
			params.add(applyStatus);
			reasonList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),AfterSellApplyReason.class,params.toArray());
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500,"查询售后申请理由出错");
		}
		return reasonList;
		
	}

	/**
	 * 根据商家订单id获取商家售后订单数量
	 * @param userId
	 * @param status
	 * @param dealerOrderId
	 * @return
	 * @throws NegativeException 
	 */
	public Integer getAppDealerSaleAfterTotal(String userId, Integer status,
			String dealerOrderId) throws NegativeException {
		Integer result = 0;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(1) FROM t_scm_order_after_sell a \r\n")
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id AND a.sort_no=b.sort_no\r\n")
			.append("LEFT OUTER JOIN t_scm_dealer c ON a.dealer_id = c.dealer_id \r\n")
			.append(" WHERE a.user_id=? AND a._status <> -1 ");
			params.add(userId);
			
			if (status != null) {
				sql.append(" AND a._status=? ");
				params.add(status);
			}
			
			if (!StringUtils.isEmpty(dealerOrderId)) {
				sql.append(" AND a.dealer_order_id=? ");
				params.add(dealerOrderId);
			}
			result = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询APP商家售后单列表总数出错");
		}
		return result;
	}

	/**
	 * 根据商家订单id获取商家售后订单
	 * @param userId
	 * @param status
	 * @param dealerOrderId
	 * @param pageIndex
	 * @param pageNum
	 * @return
	 * @throws NegativeException 
	 */
	public List<AfterSellBean> getAppDealerSaleAfterList(String userId,
			Integer status, String dealerOrderId, int pageIndex, int pageSize) throws NegativeException {
		List<AfterSellBean> result = null;
		try {
			List<Object> params = new ArrayList<>(4);
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.created_date, a.after_sell_order_id, a.order_id, a.dealer_order_id, a.dealer_id, a.goods_id, a.sku_id, a.sell_num, a._status, a.back_money, a.order_type\r\n")
			.append(",c.dealer_name, b.goods_name, b.sku_name, b.goods_type, b.goods_type_id, b.discount_price, b.goods_icon\r\n") 
			.append(", a.last_updated_date, a.reject_reason, a.reason, a.back_express_no, a.back_express_name, a.express_no, a.express_name, a.sort_no, a.return_freight ")
			.append("FROM t_scm_order_after_sell a \r\n")
			.append("LEFT OUTER JOIN t_scm_order_detail b ON a.order_id=b.order_id AND a.dealer_order_id=b.dealer_order_id AND a.sku_id = b.sku_id AND a.sort_no=b.sort_no\r\n")
			.append("LEFT OUTER JOIN t_scm_dealer c ON a.dealer_id = c.dealer_id \r\n")
			.append(" WHERE a.user_id=? AND a._status <> -1 ");
			params.add(userId);
			
			if (status != null) {
				sql.append(" AND a._status=? ");
				params.add(status);
			}
			if (!StringUtils.isEmpty(dealerOrderId)) {
				sql.append(" AND a.dealer_order_id=?");
				params.add(dealerOrderId);
			}
			
			sql.append("ORDER BY a.created_date DESC LIMIT ?,?");
			params.add((pageIndex - 1) * pageSize);
			params.add(pageSize);
			
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AfterSellBean.class, params.toArray());
			
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500, "查询APP商家售后单列表出错");
		}
		return result;
	}
	
	
	/**
	 * 查询售后流程记录
	 * @param afterSellNo
	 * @return
	 * @throws NegativeException
	 */
	public List<AfterSellFlowBean> getAfterSellFlow(String afterSellNo) throws NegativeException{
		List<AfterSellFlowBean> result = null;
		
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT * FROM t_scm_after_sell_flow WHERE after_sell_order_id = ? ORDER BY created_date DESC");
			result = this.supportJdbcTemplate.queryForBeanList(sql.toString(), AfterSellFlowBean.class,afterSellNo);
		} catch (Exception e) {
			throw new NegativeException(MCode.V_500,"查询售后流程记录出错");
		}
		
		return result;
		
	}

	/**
	 * 获取使用的优惠券实体
	 * @param couponId
	 * @param orderId
	 * @return
	 */
	public SimpleCoupon getCouponById(String couponId, String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT * \r\n")
		.append("FROM	t_scm_order_coupon_used\r\n")
		.append("WHERE	order_id = ? AND coupon_id = ? AND _status=1");
		return this.supportJdbcTemplate.queryForBean(sql.toString(), SimpleCoupon.class, orderId, couponId);
	}

	/**
	 * 获取所有的sku信息
	 * @param orderId
	 * @return
	 */
	public List<SkuNumBean> getTotalSku(String orderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT a.coupon_id,a.coupon_discount,a.sku_id, a.sell_num, a.is_change, a.goods_amount, a.marketing_id, a.change_price, a.sort_no\r\n")
		.append("FROM t_scm_order_detail a\r\n")
		.append("WHERE a.order_id = ?\r\n")
		.append("AND ((a.sort_no=0 AND a.sku_id NOT IN(SELECT b.sku_id FROM t_scm_order_after_sell b WHERE b.order_id=a.order_id AND b.dealer_order_id= a.dealer_order_id AND b._status > 3)) ")
		.append(" OR (a.sort_no NOT IN(SELECT b.sort_no FROM t_scm_order_after_sell b WHERE b.order_id=a.order_id AND b.dealer_order_id= a.dealer_order_id AND b._status > 3)))")
		;
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), SkuNumBean.class, orderId);
	}
}
