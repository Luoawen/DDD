package cn.m2c.scm.application.dealerorder.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderAfterSellDetailBean;
import cn.m2c.scm.application.dealerorder.data.bean.SaleFreightBean;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.SaleAfterExpQB;
import cn.m2c.scm.application.order.data.export.SaleAfterExpModel;
import cn.m2c.scm.domain.NegativeException;

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
		sql.append(" SELECT af.sku_id,af.order_id, dtl.coupon_discount,");
		sql.append(" af.after_sell_order_id,af.order_type,af.back_money,af._status,dealer.dealer_name,af.created_date, af.return_freight,dtl.is_special,dtl.special_price ");
		sql.append(" FROM t_scm_order_after_sell af");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON af.dealer_id = dealer.dealer_id \r\n");
		sql.append(" LEFT JOIN t_scm_order_detail dtl ON af.dealer_order_id = dtl.dealer_order_id AND af.sku_id = dtl.sku_id AND af.sort_no=dtl.sort_no");
		sql.append(" WHERE dealer.dealer_id = ? \r\n");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND af.order_type = ? ");
			params.add(orderType);
		}
		
		if (status != null && (status >= 20 && status < 28)) {
        	switch(status) {
	        	case 20: //待商家同意
	        		sql.append(" AND af._status IN(?,?,?)\r\n");
		            params.add(0);
		            params.add(1);
		            params.add(2);
	        		break;
	        	case 21://待顾客寄回商品
	        		sql.append(" AND af.order_type IN(0,1) AND af._status =?\r\n");
		            params.add(4);
	        		break;
	        	case 22://待商家确认退款
	        		sql.append(" AND ((af.order_type=0 AND af._status =?) OR (af.order_type=1 AND af._status =?) OR (af.order_type=2 AND af._status =?))\r\n");
		            params.add(8);
		            params.add(6);
		            params.add(4);
	        		break;
	        	case 23://待商家发货
	        		sql.append(" AND (af.order_type=0 AND af._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (af.order_type=0 AND af._status =?)\r\n");
		            params.add(7);
	        		break;
	        	case 25://售后已完成
	        		sql.append(" AND af._status >= ?\r\n");
		            params.add(9);
	        		break;
	        	case 26://售后已取消
	        		sql.append(" AND af._status = ?\r\n");
		            params.add(-1);
	        		break;
	        	case 27://商家已拒绝
	        		sql.append(" AND af._status = ?\r\n");
		            params.add(3);
	        		break;	        	
        	}
        }
		
		if (!StringUtils.isEmpty(condition)) {
			sql.append(
					" AND (af.dealer_order_id LIKE concat('%',?,'%') OR af.after_sell_order_id LIKE concat('%',?,'%') OR dtl.goods_name LIKE concat('%',?,'%')) ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
//		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
//			params.add(startTime);
//			params.add(endTime);
//		}
		if(!StringUtils.isEmpty(startTime)){
			sql.append("  AND  af.created_date > ?");
			params.add(startTime+" 00:00:00");
		}
		if(!StringUtils.isEmpty(endTime)){
			sql.append("  AND  af.created_date < ?");
			params.add(endTime+" 23:59:59");
		}
		if (!StringUtils.isEmpty(mediaInfo)) {
			if ("1".equals(mediaInfo)) {
				sql.append(" AND dtl.media_id IS NOT NULL ");
			}
			if ("0".equals(mediaInfo)) {
				sql.append(" AND dtl.media_id IS NULL ");
			}
		}

		sql.append(" ORDER BY af.created_date DESC ");
		if (rows != null && pageNum != null) {
			sql.append(" LIMIT ?,?");
			params.add(rows * (pageNum - 1));
			params.add(rows);
		}
		System.out.println("---"+sql.toString());
		List<AfterSellOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				AfterSellOrderBean.class, params.toArray());
		for (AfterSellOrderBean bean : beanList) {
			bean.setGoodsInfo(afterSellGoodsInfoQuery(bean.getSkuId(),bean.getAfterSellOrderId()));
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
		sql.append(" FROM t_scm_order_after_sell af ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE 1 = 1 AND af.dealer_id = ? ");
		params.add(dealerId);
		sql.append(" AND af.dealer_order_id = ? ");
		params.add(dealerOrderId);
		sql.append(" AND af.goods_id = goods.goods_id ");

		List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				GoodsInfoBean.class, params.toArray());
		return goodsInfoList;

	}

	/**
	 * 获取售后订单列表商品信息
	 * 
	 * @param skuId
	 * @param afterSellOrderId
	 * @return
	 */
	public GoodsInfoBean afterSellGoodsInfoQuery(String skuId,String afterSellOrderId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.goods_icon, b.goods_name, b.goods_title as goods_sub_title, b.sku_name, ")
				.append(" b.discount_price, a.sell_num, b.is_change, b.change_price, a.sort_no ").append(" FROM t_scm_order_after_sell a")
				.append(" LEFT OUTER JOIN t_scm_order_detail b ON a.dealer_order_id = b.dealer_order_id AND a.order_id = b.order_id AND a.sku_id= b.sku_id AND a.sort_no=b.sort_no")
				.append(" where a.sku_id=? and a.after_sell_order_id =?");
		params.add(skuId);
		params.add(afterSellOrderId);
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
		sql.append(" SELECT COUNT(1)  ");
		sql.append(" FROM t_scm_order_after_sell af");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON af.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_order_detail dtl ON af.dealer_order_id = dtl.dealer_order_id AND af.sku_id = dtl.sku_id AND af.sort_no=dtl.sort_no");
		sql.append(" WHERE dealer.dealer_id = ?");
		params.add(dealerId);
		if (null != orderType) {
			sql.append(" AND af.order_type = ? ");
			params.add(orderType);
		}
		if (status != null && (status >= 20 && status < 28)) {
        	switch(status) {
	        	case 20: //待商家同意
	        		sql.append(" AND af._status IN(?,?,?)\r\n");
		            params.add(0);
		            params.add(1);
		            params.add(2);
	        		break;
	        	case 21://待顾客寄回商品
	        		sql.append(" AND af.order_type IN(0,1) AND af._status =?\r\n");
		            params.add(4);
	        		break;
	        	case 22://待商家确认退款
	        		sql.append(" AND ((af.order_type=0 AND af._status =?) OR (af.order_type=1 AND af._status =?) OR (af.order_type=2 AND af._status =?))\r\n");
		            params.add(8);
		            params.add(6);
		            params.add(4);
	        		break;
	        	case 23://待商家发货
	        		sql.append(" AND (af.order_type=0 AND af._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (af.order_type=0 AND af._status =?)\r\n");
		            params.add(7);
	        		break;
	        	case 25://售后已完成
	        		sql.append(" AND af._status >= ?\r\n");
		            params.add(9);
	        		break;
	        	case 26://售后已取消
	        		sql.append(" AND af._status = ?\r\n");
		            params.add(-1);
	        		break;
	        	case 27://商家已拒绝
	        		sql.append(" AND af._status = ?\r\n");
		            params.add(3);
	        		break;	        	
        	}
        }
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" AND (af.dealer_order_id LIKE concat('%',?,'%') OR af.after_sell_order_id LIKE concat('%',?,'%') OR dtl.goods_name LIKE concat('%',?,'%') ) ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
//		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
//			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
//			params.add(startTime);
//			params.add(endTime);
//		}
		if(!StringUtils.isEmpty(startTime)){
			sql.append("  AND  af.created_date > ?");
			params.add(startTime+" 00:00:00");
		}
		if(!StringUtils.isEmpty(endTime)){
			sql.append("  AND  af.created_date < ?");
			params.add(endTime+" 23:59:59");
		}
		if ("1".equals(mediaInfo)) {
			sql.append(" AND dtl.media_id IS NOT NULL ");
		}
		if ("0".equals(mediaInfo)) {
			sql.append(" AND dtl.media_id IS NULL ");
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
		sql.append(" SELECT af._status, dtl.coupon_discount, dealer.coupon_discount ddCouponDiscount, af.sku_id, af.order_type,af.after_sell_order_id,af.back_money,af.reason, af.order_id, ")
		.append(" af.created_date,af.return_freight,af.reject_reason, dealer.dealer_order_id, dealer._status doStatus");
		sql.append(" ,af.order_id,dealer.goods_amount,dealer.order_freight,dealer.plateform_discount,dealer.dealer_discount ");
		sql.append("  FROM t_scm_order_after_sell af ");
		sql.append("  LEFT OUTER JOIN t_scm_order_dealer dealer   ON af.dealer_order_id = dealer.dealer_order_id   ");
		sql.append("  LEFT OUTER JOIN t_scm_order_detail dtl  ON af.dealer_order_id = dtl.dealer_order_id AND af.sku_id = dtl.sku_id AND af.sort_no=dtl.sort_no ");
		sql.append("  LEFT OUTER JOIN t_scm_dealer_seller seller  ON dtl.saler_user_id = seller.seller_id   ")
		.append(" LEFT OUTER JOIN t_scm_order_main main ON af.order_id = main.order_id ");
		sql.append(" WHERE af.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		if (!StringUtils.isEmpty(dealerId)) {
			sql.append(" AND af.dealer_id = ? ");
			param.add(dealerId);
		}
		bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				DealerOrderAfterSellDetailBean.class, param.toArray());
		if (bean.getStatus() >=5 && bean.getStatus() < 7) {
			bean.setIsShowShip(1);
		}
		if (!StringUtils.isEmpty(dealerId)) {
			bean.setDealerId(dealerId);
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
		sql.append(" SELECT dtl.discount_price, af.sell_num, dtl.freight, dtl.plateform_discount, dtl.dealer_discount ");
		sql.append(" ,dtl.media_res_id, af.back_money, af.sku_id, dtl.is_special, dtl.special_price, dtl.is_change, dtl.change_price ");
		sql.append(" ,dtl.goods_icon, dtl.goods_name, dtl.goods_unit, dtl.sku_name, dtl.goods_amount ");
		sql.append(" FROM t_scm_order_detail dtl ");
		sql.append(" LEFT OUTER JOIN t_scm_order_after_sell af ON af.dealer_order_id = dtl.dealer_order_id AND af.sku_id = dtl.sku_id AND af.sort_no=dtl.sort_no ");
		sql.append(" WHERE af.after_sell_order_id = ? ");
		param.add(afterSellOrderId);
		if(!StringUtils.isEmpty(dealerId)) {
			sql.append(" AND af.dealer_id = ? ");
			param.add(dealerId);
		}
		sql.append(" AND dtl.sku_id = af.sku_id AND dtl.dealer_order_id = af.dealer_order_id ");
		System.out.println("SQL------------------>"+sql);
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
				" SELECT dtl.is_special isSpecial, dtl.special_price specialPrice,dtl.coupon_discount, dtl.discount_price saleAfterGoodsPrice, a.dealer_order_id dealerOrderId, a.sell_num sellNum, a.return_freight returnFreight, a.sku_id skuId, dtl.sku_name skuName, dtl.goods_name goodsName, dtl.goods_title goodsTitle,")
				.append(" a.after_sell_order_id saleAfterNo, a.order_type orderType, a.back_money backMoney, a._status status, dealer.dealer_name dealerName, a.created_date createTime")
				.append(" FROM t_scm_order_after_sell a")
				.append(" LEFT OUTER JOIN t_scm_dealer dealer ON a.dealer_id = dealer.dealer_id ")
				.append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.sku_id = a.sku_id AND dtl.dealer_order_id=a.dealer_order_id AND a.sort_no=dtl.sort_no ");
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
					" AND a.dealer_order_id LIKE concat('%',?,'%') OR a.after_sell_order_id LIKE concat('%',?,'%') OR dtl.goods_name LIKE concat('%',?,'%') ");
			params.add(condition);
			params.add(condition);
			params.add(condition);
		}
		if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
			sql.append(" AND a.created_date BETWEEN ? AND ? ");
			params.add(startTime + " 00:00:00");
			params.add(endTime + " 23:59:59");
		}
		if (!StringUtils.isEmpty(mediaInfo)) {
			if ("1".equals(mediaInfo)) {
				sql.append(" AND dtl.media_id != '' ");
			}
			if ("0".equals(mediaInfo)) {
				sql.append(" AND dtl.meidia_id = '' ");
			}
		}

		sql.append(" ORDER BY a.created_date DESC ");
		sql.append(" LIMIT 2000");
		List<SaleAfterExpQB> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				SaleAfterExpQB.class, params.toArray());
		List<SaleAfterExpModel> list = null;
		if(null != beanList && beanList.size() > 0) {
			list = new ArrayList<>();
			for(SaleAfterExpQB saleAfterExpQB : beanList) {
				list.add(new SaleAfterExpModel(saleAfterExpQB));
			}
		}
		return list;
	}
	
	/**
	 * 获取已经退了的运费
	 * 
	 * @param dealerOrderId
	 * @return
	 * @throws NegativeException 
	 */
	public SaleFreightBean getHasRtFreight(String dealerOrderId, String skuId) throws NegativeException {
		
		if (StringUtils.isEmpty(dealerOrderId)) {
			throw new NegativeException(MCode.V_1, "dealerOrderId 参数为空！");
		}
		
		if (StringUtils.isEmpty(skuId)) {
			throw new NegativeException(MCode.V_1, "skuId 参数为空！");
		}
		
		StringBuilder sql = new StringBuilder();
		List<Object> param = new ArrayList<Object>();
		SaleFreightBean bean = null;
		sql.append(" select sum(b.return_freight) costFt from t_scm_order_detail a \r\n")
		.append("LEFT OUTER JOIN t_scm_order_after_sell b ON a.dealer_order_id = b.dealer_order_id AND a.sku_id = b.sku_id AND b._status NOT IN(-1, 3) AND a.sort_no=b.sort_no \r\n") 
		.append("where a.dealer_order_id=? and a.sku_id != ? ");
		param.add(dealerOrderId);
		param.add(skuId);
		
		bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				SaleFreightBean.class, param.toArray());
		return bean;
	}
}
