package cn.m2c.scm.application.order.query;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.DealerOrderBean;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.mainOrderBean;

@Repository
public class OrderQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderQuery.class);

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 查询mainOrder订单
	 * 
	 * @param orderStatus  订单状态
	 * @param afterSaleStatus 售后状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param condition 搜索条件(goodsName,orderId,payNo,revPhone,dealerName,dealerId)
	 * @param payWay 支付方式
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	public List<mainOrderBean> mainOrderListQuery(Integer orderStatus, Integer afterSaleStatus, String startTime,
			String endTime, String condition, Integer payWay, Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT omain.order_id AS orderId");
		sql.append("  ,omain.pay_no AS payNo");
		sql.append("  ,omain.created_date AS createDate");
		sql.append("  ,omain.goods_amount AS goodsAmount");
		sql.append("  ,omain.order_freight AS orderFreight");
		sql.append("  ,omain.plateform_discount AS plateformDiscount");
		sql.append("  ,omain.dealer_discount AS dealerDiscount");

		sql.append(" FROM ");
		sql.append("  t_scm_order_main omain ");
		sql.append(" WHERE 1=1 ");
		if (orderStatus != null) {
			sql.append(" AND odealer._status = ? ");
			params.add(orderStatus);
		}
		if (afterSaleStatus != null) {
			sql.append(" AND oafter._status = ? ");
			params.add(afterSaleStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND omain.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (condition != null && !"".equals(condition)) {
			sql.append(
					" AND omain.order_id = ? OR omain.pay_no = ? OR odetail.rev_phone = ? OR odealer.dealer_id = ? OR odetail.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND omain.pay_way = ? ");
			params.add(payWay);
		}
		sql.append(" ORDER BY omain.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);

		/**
		 * 将商家订单塞进对应的平台订单
		 */
		List<mainOrderBean> mainOrderList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				mainOrderBean.class, params.toArray());
		List<DealerOrderBean> dealerOrderList = dealerOrderListQuery(orderStatus, afterSaleStatus, startTime, endTime,
				condition, payWay, pageNum, rows);
		for (mainOrderBean mainOrder : mainOrderList) {
			for (DealerOrderBean dealerOrder : dealerOrderList) {
				List<DealerOrderBean> list = new ArrayList<DealerOrderBean>();
				if (mainOrder.getOrderId() == dealerOrder.getOrderId()) {
					list.add(dealerOrder);
				}
				mainOrder.setDealerOrderBeans(list);
			}
		}
		return mainOrderList;
	}

	/**
	 * 查询商家订单
	 * 
	 * @param orderStatus
	 * @param afterSaleStatus
	 * @param startTime
	 * @param endTime
	 * @param condition
	 * @param payWay
	 * @param pageNum
	 * @param rows
	 * @return
	 */
	public List<DealerOrderBean> dealerOrderListQuery(Integer orderStatus, Integer afterSaleStatus, String startTime,
			String endTime, String condition, Integer payWay, Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT odealer.order_id  orderId , odealer.dealer_id  dealerId  ,odealer.dealer_order_id  dealerOrderId  ");
		sql.append("  ,odealer.dealer_discount  dealerDiscount  ,dealer.dealer_name  dealerName  ,odealer._status  status");
		sql.append("  ,odealer.goods_amount  goodsAmount  ,odealer.order_freight  orderFreight  ,odealer.plateform_discount  plateformDiscount ");
		sql.append("  ,odealer.dealer_discount  dealerDiscount");
		sql.append(" FROM t_scm_order_dealer odealer");
		sql.append(" RIGHT JOIN t_scm_order_after_sell oafter ");
		sql.append(" ON odealer.dealer_order_id = oafter.dealer_order_id ");
		sql.append(" INNER JOIN t_scm_dealer dealer ");
		sql.append(" WHERE 1=1 ");
		if (orderStatus != null) {
			sql.append(" AND odealer._status = ? ");
			params.add(orderStatus);
		}
		if (afterSaleStatus != null) {
			sql.append(" AND oafter._status = ? ");
			params.add(afterSaleStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND omain.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (condition != null && !"".equals(condition)) {
			sql.append(
					" AND omain.order_id = ? OR omain.pay_no = ? OR odetail.rev_phone = ? OR odealer.dealer_id = ? OR odetail.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND omain.pay_way = ? ");
			params.add(payWay);
		}
		sql.append(" AND odealer.dealer_id = dealer.dealer_id ");
		
		sql.append(" ORDER BY odealer.created_date DESC ");
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerOrderBean.class, params.toArray());
	}

	/**
	 * 查询订单总数
	 * 
	 * @param orderStatus
	 * @param afterSaleStatus
	 * @param startTime
	 * @param endTime
	 * @param condition
	 * @param payWay
	 * @return
	 */
	public Integer mainOrderQueryTotal(Integer orderStatus, Integer afterSaleStatus, String startTime, String endTime,
			String condition, Integer payWay) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) ");
		sql.append(" FROM ");
		sql.append("  t_scm_order_main omain LEFT JOIN t_scm_order_dealer odealer ON  omain.order_id = odealer.order_id ");
		sql.append(" LEFT JOIN t_scm_order_detail odetail ON omain.order_id = odetail.order_id ");
		sql.append(" LEFT JOIN t_scm_order_after_sell oafter  ");
		sql.append(" ON omain.order_id = oafter.order_id ");
		sql.append(" WHERE 1=1 ");
		if (orderStatus != null) {
			sql.append(" AND omain._status = ? ");
			params.add(orderStatus);
		}
		if (afterSaleStatus != null) {
			sql.append(" AND oafter._status = ? ");
			params.add(afterSaleStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND omain.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (StringUtils.isNotEmpty(condition) && condition != null && !"".equals(condition)) {
			sql.append(" AND omain.order_id = ? OR omain.pay_no = ? OR odetail.rev_phone = ? OR odealer.dealer_id = ? OR odetail.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND omain.pay_way = ? ");
			params.add(payWay);
		}
		Integer total = this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), params.toArray(), Integer.class);
		return total;
	}

	/**
	 * 查询商家订单详情
	 * 
	 * @param dealerId
	 * @return
	 */
	public DealerOrderDetailBean dealerOrderDetailQuery(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT t1._status orderStatus,t1.order_id orderId,t1.created_date createdDate, t3.pay_way payWay,t3.pay_time payTime,t3.pay_no payNo, ");
		sql.append(" t1.rev_person revPerson,t1.rev_phone revPhone,t1.province province,t1.city city,t1.area_county areaCounty,t1.street_addr streetAddr, ");
		sql.append(" t4.dealer_name dealerName,t4.dealer_classify dealerClassify,t1.dealer_id dealerId,  t6.seller_name sellerName,t6.seller_phone sellerPhone, ");
		sql.append(" t2.plateform_discount plateformDiscount,t2.dealer_discount dealerDiscount ");
		sql.append(" FROM t_scm_order_dealer t1 ");
		sql.append(" INNER JOIN t_scm_order_detail t2 ");
		sql.append(" INNER JOIN t_scm_order_main t3 ");
		sql.append(" INNER JOIN t_scm_dealer t4");
		sql.append(" INNER JOIN t_scm_goods t5 ");
		sql.append(" INNER JOIN t_scm_dealer_seller t6");
		sql.append(" WHERE 1 = 1 AND t1.dealer_order_id = ? ");
		sql.append(" AND t1.dealer_order_id = t2.dealer_order_id ");
		sql.append(" AND t1.order_id = t3.order_id AND t1.dealer_id = t4.dealer_id ");
		sql.append(" AND t2.goods_id = t5.goods_id ");
		sql.append(" AND t2.saler_user_id  = t6.seller_id ");
		DealerOrderDetailBean dealerOrderDetailBean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderDetailBean.class, dealerOrderId);
		Integer totalPrice = 0; // 商品总价格
		Integer totalFrieght = 0; // 运费总价格
		List<GoodsInfoBean> goodsInfoList = getGoodsInfoList(dealerOrderId);
		dealerOrderDetailBean.setGoodsInfoBeans(goodsInfoList);
		for (GoodsInfoBean goodsInfo : dealerOrderDetailBean.getGoodsInfoBeans()) {
			totalPrice += goodsInfo.getTotalPrice();
			totalFrieght += goodsInfo.getFreight();
		}

		dealerOrderDetailBean.setTotalOrderPrice(totalPrice);
		dealerOrderDetailBean.setTotalFreight(totalFrieght);

		/**
		 * 获取商家订单总价格 (商品总额-运费总额-平台优惠信息-商家优惠信息)
		 */
		dealerOrderDetailBean
				.setOrderPrice(dealerOrderDetailBean.getTotalOrderPrice() - dealerOrderDetailBean.getTotalFreight()
						- dealerOrderDetailBean.getPlateformDiscount() - dealerOrderDetailBean.getDealerDiscount());
		
		return dealerOrderDetailBean;
	}

	/**
	 * 查询商品信息列表
	 * 
	 * @param dealerOrderId
	 * @return
	 */
	public List<GoodsInfoBean> getGoodsInfoList(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  ");
		sql.append(" goods.goods_main_images,odetail.goods_name,odetail.stantard_name, ");
		sql.append(" odetail.media_res_id,odetail.sell_num,odetail.goods_unit,odetail._price,odetail.freight ");
		sql.append(" FROM ");
		sql.append(" t_scm_order_dealer odealer INNER JOIN t_scm_goods goods INNER JOIN t_scm_order_detail odetail ");
		sql.append(" WHERE 1 = 1 AND odealer.dealer_order_id = ? ");
		sql.append(" AND odealer.dealer_id = goods.dealer_id ");
		sql.append(" AND odealer.dealer_order_id = odetail.dealer_order_id ");
		
		List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				GoodsInfoBean.class, dealerOrderId);
		for (GoodsInfoBean goodsInfo : goodsInfoList) {
			goodsInfo.setTotalPrice(goodsInfo.getPrice() * goodsInfo.getSellNum());
		}
		return goodsInfoList;
	}

}
