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
		sql.append(" SELECT after.sku_id,after.order_id,");
		sql.append(" after.after_sell_order_id,after.order_type,after.back_money,after._status,dealer.dealer_name,after.created_date, after.return_freight ");
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id \r\n");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id AND after.sku_id = detail.sku_id");
		sql.append(" WHERE dealer.dealer_id = ? \r\n");
		params.add(dealerId);
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
	        		sql.append(" AND (after.order_type=2 AND after._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (after.order_type=2 AND after._status =?)\r\n");
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
		
		if (!StringUtils.isEmpty(condition)) {
			sql.append(
					" AND (after.dealer_order_id LIKE concat('%',?,'%') OR after.after_sell_order_id LIKE concat('%',?,'%') OR detail.goods_name LIKE concat('%',?,'%')) ");
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
			sql.append("  AND  after.created_date > ?");
			params.add(startTime+" 00:00:00");
		}
		if(!StringUtils.isEmpty(endTime)){
			sql.append("  AND  after.created_date < ?");
			params.add(endTime+" 23:59:59");
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
	 * @param afterSellOrderId
	 * @return
	 */
	public GoodsInfoBean afterSellGoodsInfoQuery(String skuId,String afterSellOrderId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT b.goods_icon, b.goods_name, b.goods_title as goods_sub_title, b.sku_name, ")
				.append(" b.discount_price, a.sell_num, b.is_change, b.change_price ").append(" FROM t_scm_order_after_sell a")
				.append(" LEFT OUTER JOIN t_scm_order_detail b ON a.dealer_order_id = b.dealer_order_id AND a.order_id = b.order_id AND a.sku_id= b.sku_id")
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
		sql.append(" FROM t_scm_order_after_sell after");
		sql.append(" LEFT JOIN t_scm_dealer dealer ON after.dealer_id = dealer.dealer_id ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON after.dealer_order_id = detail.dealer_order_id AND after.sku_id = detail.sku_id");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ?");
		params.add(dealerId);
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
	        		sql.append(" AND (after.order_type=2 AND after._status =?)\r\n");
		            params.add(6);
	        		break;
	        	case 24://待顾客收货
	        		sql.append(" AND (after.order_type=2 AND after._status =?)\r\n");
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
		if (!StringUtils.isEmpty(condition)) {
			sql.append(" AND (after.dealer_order_id LIKE concat('%',?,'%') OR after.after_sell_order_id LIKE concat('%',?,'%') OR detail.goods_name LIKE concat('%',?,'%') ) ");
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
			sql.append("  AND  after.created_date > ?");
			params.add(startTime+" 00:00:00");
		}
		if(!StringUtils.isEmpty(endTime)){
			sql.append("  AND  after.created_date < ?");
			params.add(endTime+" 23:59:59");
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
		.append("LEFT OUTER JOIN t_scm_order_after_sell b ON a.dealer_order_id = b.dealer_order_id AND a.sku_id = b.sku_id AND b._status NOT IN(-1, 3)\r\n") 
		.append("where a.dealer_order_id=? and a.sku_id != ? ");
		param.add(dealerOrderId);
		param.add(skuId);
		
		bean = this.supportJdbcTemplate.queryForBean(sql.toString(),
				SaleFreightBean.class, param.toArray());
		return bean;
	}
}
