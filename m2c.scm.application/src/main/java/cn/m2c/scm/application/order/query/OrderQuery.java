package cn.m2c.scm.application.order.query;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.OrderGoodsBean;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.model.order.MainOrder;
import cn.m2c.scm.application.order.data.bean.AllOrderBean;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.MainOrderBean;

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
	 * @param mediaInfo 媒体信息
	 * @param dealerClassify 商家分类
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	public List<MainOrderBean> mainOrderListQuery(Integer orderStatus, Integer afterSaleStatus, String startTime,String orderId,
			String endTime, String condition, Integer payWay,Integer mediaInfo,String dealerClassify, Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT d.dealer_order_id,m.order_id,m.pay_no,m.pay_way,m.created_date,m.goods_amount,m.order_freight,m.plateform_discount,m.dealer_discount, ");
		sql.append(" d.goods_amount AS dealerAmount,d.order_freight AS orderFreight,d._status,dealer.dealer_name ");
		sql.append("  FROM t_scm_order_dealer d ");
		sql.append(" LEFT OUTER JOIN t_scm_order_main m ON d.order_id = m.order_id ");
		sql.append(" LEFT OUTER JOIN t_scm_dealer dealer ON d.dealer_id = dealer.dealer_id ");
		sql.append(" WHERE 1=1 ");
		if (orderStatus != null) {
			sql.append(" AND d._status =  ? ");
			params.add(orderStatus);
		}
		if (afterSaleStatus != null) {
			sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status = ?) ");
			params.add(afterSaleStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND (d.created_date BETWEEN ? AND ?)");
			params.add(startTime + " 00:00:00");
			params.add(endTime+ " 23:59:59");
		}
		if (StringUtils.isNotEmpty(condition) && condition != null && !"".equals(condition)) {
			sql.append(" AND (d.dealer_order_id LIKE ?	OR m.pay_no LIKE ? OR dealer.dealer_name LIKE ?) ");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND m.pay_way = ? ");
			params.add(payWay);
		}
		if (mediaInfo != null) {
			if (mediaInfo == 0) {
				sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = 0 AND dtl.media_res_id = '')");
			}
			if (mediaInfo ==1) {
				sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = 0 AND dtl.media_res_id != '')");
			}
		}
		if (!StringUtils.isEmpty(dealerClassify)) {
			sql.append(" AND dealer.dealer_classify = ? ");
			params.add(dealerClassify);
		}
		sql.append(" ORDER BY m.order_id DESC, m.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);
		List<AllOrderBean> allOrderList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				AllOrderBean.class, params.toArray());
		List<MainOrderBean> mainOrderList = new ArrayList<MainOrderBean>();
		
		MainOrderBean mainOrder = null;
		String tmpOrderId = null;
		for (AllOrderBean allOrder : allOrderList) {
			OrderDealerBean dealerBean = null;
			String id = allOrder.getOrderId();
			if (!id.equals(tmpOrderId)) {
				tmpOrderId = id;
				mainOrder = new MainOrderBean();
				mainOrderList.add(mainOrder);
				mainOrder.setOrderId(allOrder.getOrderId());
				mainOrder.setPayNo(allOrder.getPayNo());
				mainOrder.setCreateDate(allOrder.getCreatedDate());
				mainOrder.setGoodAmount(allOrder.getMainGoodsAmount());
				mainOrder.setOderFreight(allOrder.getMainOrderFreight());
				mainOrder.setDealerDiscount(allOrder.getDealerDiscount());
				mainOrder.setPlateFormDiscount(allOrder.getPlateformDiscount());
				List<OrderDealerBean> dealerOrderList = new ArrayList<OrderDealerBean>();
				mainOrder.setDealerOrderBeans(dealerOrderList);
			}
			dealerBean = new OrderDealerBean();
			dealerBean.setOrderId(allOrder.getOrderId());
			dealerBean.setDealerName(allOrder.getDealerName());
			dealerBean.setDealerOrderId(allOrder.getDealerOrderId());
			dealerBean.setGoodAmount(allOrder.getDealerGoodsAmount());
			dealerBean.setOderFreight(allOrder.getDealerOrderFreight());
			dealerBean.setPlateFormDiscount(allOrder.getPlateformDiscount());
			dealerBean.setDealerDiscount(allOrder.getDealerDiscount());
			dealerBean.setStatus(allOrder.getStatus());
			mainOrder.getDealerOrderBeans().add(dealerBean);
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
	public List<OrderDealerBean> dealerOrderListQuery(String orderId) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT odealer.order_id  orderId , odealer.dealer_id  dealerId  ,odealer.dealer_order_id  dealerOrderId  ");
		sql.append("  ,odealer.dealer_discount  dealerDiscount  ,dealer.dealer_name  dealerName  ,odealer._status  status");
		sql.append("  ,odealer.goods_amount  goodsAmount  ,odealer.order_freight  orderFreight  ,odealer.plateform_discount  plateformDiscount ");
		sql.append("  ,odealer.dealer_discount  dealerDiscount");
		sql.append(" FROM t_scm_order_dealer odealer");
		sql.append(" LEFT JOIN t_scm_order_after_sell oafter ");
		sql.append(" ON odealer.dealer_order_id = oafter.dealer_order_id ");
		sql.append(" INNER JOIN t_scm_dealer dealer ");
		sql.append(" WHERE 1=1 AND odealer.order_id = ? ");
		/*if (orderStatus != null) {
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
		}*/
		sql.append(" AND odealer.dealer_id = dealer.dealer_id ");
		
		sql.append(" ORDER BY odealer.created_date DESC ");
		return this.supportJdbcTemplate.queryForBeanList(sql.toString(), OrderDealerBean.class, orderId);
	}

	/**
	 * 查询订单总数
	 * 
	 * @param orderStatus
	 * @param afterSaleStatus
	 * @param startTime
	 * @param endTime
	 * @param condition
	 * @param mediaInfo 
	 * @param dealerClassify
	 * @param payWay
	 * @return
	 */
	public Integer mainOrderQueryTotal(Integer orderStatus, Integer afterSaleStatus, String startTime, String endTime,
			String condition, Integer payWay,Integer mediaInfo,String dealerClassify) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(1) ");
		sql.append("  FROM t_scm_order_dealer d");
		sql.append(" LEFT OUTER JOIN t_scm_order_main m ON d.order_id = m.order_id ");
		sql.append(" LEFT OUTER JOIN t_scm_dealer dealer ON d.dealer_id = dealer.dealer_id ");
		sql.append(" WHERE 1=1 ");
		if (orderStatus != null) {
			sql.append(" AND d._status =  ? ");
			params.add(orderStatus);
		}
		if (afterSaleStatus != null) {
			sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status = ?) ");
			params.add(afterSaleStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND (d.created_date BETWEEN ? AND ?)");
			params.add(startTime + " 00:00:00");
			params.add(endTime+ " 23:59:59");
		}
		if (StringUtils.isNotEmpty(condition) && condition != null && !"".equals(condition)) {
			sql.append(" AND (d.dealer_order_id LIKE ?	OR m.pay_no LIKE ? OR dealer.dealer_name LIKE ?) ");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND m.pay_way = ? ");
			params.add(payWay);
		}
		if (mediaInfo != null) {
			if (mediaInfo == 0) {
				sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = 0 AND dtl.media_res_id = '')");
			}
			if (mediaInfo ==1) {
				sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = 0 AND dtl.media_res_id != '')");
			}
		}
		if (!StringUtils.isEmpty(dealerClassify)) {
			sql.append(" AND dealer.dealer_classify = ? ");
			params.add(dealerClassify);
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
		sql.append(" t4.dealer_name dealerName,t4.dealer_classify dealerClassify,t1.dealer_id dealerId, ");
		sql.append(" t1.plateform_discount plateformDiscount,t1.dealer_discount dealerDiscount ");
		sql.append(" FROM t_scm_order_dealer t1 ");
		sql.append(" LEFT OUTER JOIN t_scm_order_main t3 ON t1.order_id = t3.order_id");
		sql.append(" LEFT OUTER JOIN t_scm_dealer t4 ON t1.dealer_id = t4.dealer_id");
		sql.append(" WHERE t1.dealer_order_id = ? ");
		DealerOrderDetailBean dealerOrderDetailBean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderDetailBean.class, dealerOrderId);
		long totalPrice = 0; // 商品总价格
		long totalFrieght = 0; // 运费总价格
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
		return dealerOrderDetailBean;
	}
	
	/**
	 * 查询商家订单详情
	 * fanjc
	 * @param dealerId
	 * @return
	 */
	public DealerOrderDetailBean dealerOrderDetailQuery1(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT t2.dealer_name dealerName, t1._status orderStatus,t1.order_id orderId,t1.created_date createdDate, t3.pay_way payWay,t3.pay_time payTime,t3.pay_no payNo, \r\n") 
		.append(" t1.rev_person revPerson,t1.rev_phone revPhone,t1.province province,t1.city city,t1.area_county areaCounty,t1.province_code provinceCode,t1.city_code cityCode,t1.area_code areaCode,t1.street_addr streetAddr, \r\n")
		.append(" t1.dealer_id dealerId, t1.noted, t1.invoice_header, t1.invoice_name, t1.invoice_code, t1.invoice_type, \r\n")
		.append(" t1.plateform_discount plateformDiscount,t1.dealer_discount dealerDiscount, t1.goods_amount, t1.order_freight, \r\n")
		.append(" t1.area_code ,t1.city_code, t1.province_code \r\n")
		.append(" FROM t_scm_order_dealer t1 \r\n")
		.append(" LEFT OUTER JOIN t_scm_order_main t3 ON t1.order_id = t3.order_id\r\n") 
		.append(" LEFT OUTER JOIN t_scm_dealer t2 ON t1.dealer_id = t2.dealer_id ")
		.append(" WHERE t1.dealer_order_id = ?"); 
		System.out.println("==================SQL===============>"+sql.toString());
		DealerOrderDetailBean dealerOrderDetailBean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderDetailBean.class, dealerOrderId);
		if (dealerOrderDetailBean != null) {
			dealerOrderDetailBean.setDealerOrderId(dealerOrderId);
		}
		List<GoodsInfoBean> goodsInfoList = getGoodsInfoList(dealerOrderId);
		dealerOrderDetailBean.setGoodsInfoBeans(goodsInfoList);
		/*for (GoodsInfoBean goodsInfo : dealerOrderDetailBean.getGoodsInfoBeans()) {
			totalPrice += goodsInfo.getTotalPrice();
			totalFrieght += goodsInfo.getFreight();
		}

		dealerOrderDetailBean.setTotalOrderPrice(totalPrice);
		dealerOrderDetailBean.setTotalFreight(totalFrieght);*/

		/**
		 * 获取商家订单总价格 (商品总额-运费总额-平台优惠信息-商家优惠信息)
		 */
		/*dealerOrderDetailBean
				.setOrderPrice(dealerOrderDetailBean.getTotalOrderPrice() - dealerOrderDetailBean.getTotalFreight()
						- dealerOrderDetailBean.getPlateformDiscount() - dealerOrderDetailBean.getDealerDiscount());*/
		
		return dealerOrderDetailBean;
	}

	/**
	 * 查询商品信息列表 
	 * @param dealerOrderId
	 * @return
	 */
	public List<GoodsInfoBean> getGoodsInfoList(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  dtl.goods_icon, dtl.goods_name,dtl.sku_name, dtl.sku_id, \r\n")
		.append(" dtl.media_res_id,dtl.sell_num,dtl.goods_unit, dtl.discount_price,dtl.freight \r\n") 
		.append(" FROM  t_scm_order_dealer dealer \r\n")
		.append(" ,t_scm_order_detail dtl \r\n")
		.append(" WHERE dealer.dealer_order_id = ? ")
		.append(" AND dealer.dealer_order_id = dtl.dealer_order_id ");
		//.append(" AND dtl.sku_id NOT IN (SELECT a.sku_id FROM t_scm_order_after_sell a WHERE a.dealer_order_id=dtl.dealer_order_id AND a._status >= 4) ");
		
		List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				GoodsInfoBean.class, dealerOrderId);
		for (GoodsInfoBean goodsInfo : goodsInfoList) {
			goodsInfo.setTotalPrice(goodsInfo.getPrice() * goodsInfo.getSellNum());
		}
		return goodsInfoList;
	}

	/***
	 * 获取订单数据
	 * @param orderNo
	 * @param userId
	 * @return
	 */
	public MainOrderBean getOrderByNo(String orderNo, String userId) throws NegativeException {
		
		if (StringUtils.isEmpty(orderNo)) {
			throw new NegativeException(MCode.V_1, "订单号参数为空！");
		}
		
		if (StringUtils.isEmpty(userId)) {
			throw new NegativeException(MCode.V_1, "用户ID参数为空！");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT order_id orderId, _status status, pay_no payNo, goods_amount goodAmount, order_freight oderFreight, ")
		.append("plateform_discount plateFormDiscount, dealer_discount dealerDiscount, user_id userId, pay_time payTime\r\n")
		.append(",created_date createDate FROM t_scm_order_main WHERE order_id= ?");
		
		MainOrderBean order = supportJdbcTemplate.queryForBean(sql.toString(), MainOrderBean.class, orderNo);
		
		sql.delete(0, sql.length());
		sql.append(" SELECT order_id  orderId , dealer_id  dealerId,dealer_order_id dealerOrderId \r\n");
		sql.append("  ,dealer_discount  dealerDiscount, _status  status\r\n");
		sql.append("  ,goods_amount goodsAmount, order_freight orderFreight,plateform_discount plateformDiscount\r\n");
		sql.append("  ,dealer_discount dealerDiscount, term_of_payment termOfPayment\r\n");
		sql.append(" FROM t_scm_order_dealer WHERE order_id=?");
		order.setDealerOrderBeans(supportJdbcTemplate.queryForBeanList(sql.toString(), OrderDealerBean.class, orderNo));
		
		//获取商品
		sql.delete(0, sql.length());
		sql.append("SELECT dealer_order_id, _status, freight, plateform_discount, goods_amount, rate,goods_id, sku_id, sku_name,supply_price, discount_price, \r\n")
		.append(" sell_num, bds_rate, media_id, media_res_id, saler_user_id, saler_user_rate, is_change, change_price, res_rate, marketing_id,market_level\r\n")
		.append(" FROM t_scm_order_detail WHERE order_id=? ORDER BY dealer_order_id");
		List<OrderGoodsBean> ls = supportJdbcTemplate.queryForBeanList(sql.toString(), OrderGoodsBean.class, orderNo);
		
		Map<String, List<OrderGoodsBean>> goodses = new HashMap<String, List<OrderGoodsBean>>();
		for (OrderGoodsBean a : ls) {
			String doId = a.getDealerOrderId();
			List<OrderGoodsBean> gls = goodses.get(doId);
			if (gls == null) {
				gls = new ArrayList<OrderGoodsBean>();	
				goodses.put(doId, gls);
			}
			gls.add(a);
		}
		order.setGoodses(goodses);
		
		
		sql.delete(0, sql.length());
		sql.append("SELECT marketing_id, market_level,market_type, threshold, threshold_type, discount, share_percent \r\n")
		.append(" FROM t_scm_order_marketing_used WHERE order_id=? ");
		order.setMarkets(supportJdbcTemplate.queryForBeanList(sql.toString(), SimpleMarket.class, orderNo));
		sql = null;
		return order;
	}
	
	/***
	 * 获取成效订单数
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public Integer getPayedOrders(String startTime, String endTime) throws NegativeException {
		
		if (StringUtils.isEmpty(startTime)) {
			throw new NegativeException(MCode.V_1, "开始时间参数为空！");
		}
		
		if (StringUtils.isEmpty(endTime)) {
			throw new NegativeException(MCode.V_1, "结束时间参数为空！");
		}
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT count(1)\r\n")
		.append(" FROM t_scm_order_main WHERE pay_time BETWEEN ? AND ?");
		
		Integer orders = supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, startTime, endTime);
		
		return orders;
	}
	
	/***
	 * 获取可结算订单
	 * @param ids
	 * @return
	 */
	public Map<String, Integer> getCanCheckOrder(List<String> ids) throws NegativeException {
		
		Map<String, Integer> result = null;
		
		if (ids == null || ids.size() < 1) {
			return result;
		}
		
		StringBuilder sql = new StringBuilder();
		sql.append(" select a.dealer_order_id from t_scm_order_dealer a \r\n")
		.append(",t_scm_order_main b \r\n")
		.append("where a.order_id = b.order_id\r\n")
		.append("and a._status IN (4, 5)\r\n")
		.append("and b._status IN (4, 5)\r\n");
		sql.append("and a.dealer_order_id IN(");
		int sz = ids.size();
		for(int i = 0; i<sz; i++) {
			if (i > 0)
				sql.append(",");
			sql.append("?");
		}
		sql.append(")");
		List<String> ls = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), String.class, ids.toArray());
		if (ls == null)
			return result;
		
		result = new HashMap<String, Integer>();
		for(String a : ls) {
			result.put(a, 1);
		}
		return result;
	}
}
