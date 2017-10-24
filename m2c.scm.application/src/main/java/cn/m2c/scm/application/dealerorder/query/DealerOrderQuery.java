package cn.m2c.scm.application.dealerorder.query;



import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderGoodsInfo;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

@Repository
public class DealerOrderQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(DealerOrderQuery.class);

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 获取订单列表
	 * @param dealerOrderId 商家订单号
	 * @param orderStatus 订单状态
	 * @param afterSellStatus 售后状态
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param condition 搜索条件(goodsName,dealerOrderId,payNo,revPhone)
	 * @param payWay 支付方式
	 * @param commentStatus 评论状态
	 * @param mediaInfo 广告位
	 * @param invoice 开发票
	 * @param orderClassify 订单类型
	 * @param pageNum 第几页
	 * @param rows 每页多少行
	 * @return
	 */
	public List<DealerOrderBean> dealerOrderQuery(String dealerId,String dealerOrderId, Integer orderStatus, Integer afterSellStatus,
			String startTime, String endTime, String condition, Integer payWay, Integer commentStatus,
			Integer orderClassify, String mediaInfo,
			Integer invoice, Integer pageNum, Integer rows) {
		
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT dealer.created_date,detail.rev_person,detail.rev_phone,dealer._status ");
		sql.append(" FROM t_scm_order_dealer dealer ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ");
		sql.append(" ON dealer.dealer_order_id = detail.dealer_order_id ");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ? ");
		params.add(dealerId);
		if (orderStatus != null) {
			sql.append(" AND dealer._status = ? ");
			params.add(orderStatus);
		}
		if (afterSellStatus != null) {
			sql.append(" AND after._status = ? ");
			params.add(afterSellStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND omain.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (condition != null && !"".equals(condition)) {
			sql.append(" AND dealer.dealer_order_id OR main.pay_no OR detail.rev_phone = ? OR OR detail.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND main.pay_way = ? ");
			params.add(payWay);
		}
		if (commentStatus != null) {
			sql.append(" AND detail.comment_status = ? ");
			params.add(commentStatus);
		}
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			if ("有广告位".equals(mediaInfo)) {
				sql.append(" AND detail.media_id != '' ");
			}
			if ("无广告位".equals(mediaInfo)) {
				sql.append(" AND detail.meidia_id = '' ");
			}
		}
		if(invoice != null) {
			sql.append(" AND dealer.invoice_type = ? ");
			params.add(invoice);
		}
		sql.append(" ORDER BY dealer.created_date DESC ");
		sql.append(" LIMIT ?,?");
		params.add(rows * (pageNum - 1));
		params.add(rows);
		List<DealerOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerOrderBean.class,params.toArray());
		List<DealerOrderGoodsInfo> goodsInfoList = dealerOrderGoodsInfoQuery(dealerOrderId);
		long orderTotalMoney = 0;
		
		/**
		 * 计算出订单总额
		 */
		for (DealerOrderGoodsInfo goodsInfoBean : goodsInfoList) {
			goodsInfoBean.setTotalPrice(goodsInfoBean.getPrice() * goodsInfoBean.getSellNum());
			orderTotalMoney += goodsInfoBean.getTotalPrice();
		}
		
		/**
		 * 将商品信息塞到商家订单中
		 */
		if (beanList != null) {
			for (DealerOrderBean dealerOrderBean : beanList) {
				dealerOrderBean.setGoodsInfoList(goodsInfoList);
				dealerOrderBean.setTotalMoney(orderTotalMoney);
			}
		}
		
		return beanList;
	}
	
	/**
	 * 获取商家订单总数
	 * @param dealerOrderId
	 * @param orderStatus
	 * @param afterSellStatus
	 * @param startTime
	 * @param endTime
	 * @param condition
	 * @param payWay
	 * @param commentStatus
	 * @param orderClassify
	 * @param mediaInfo
	 * @param invoice
	 * @return
	 */
	public Integer dealerOrderTotalQuery(String dealerId,String dealerOrderId, Integer orderStatus, Integer afterSellStatus,
			String startTime, String endTime, String condition, Integer payWay, Integer commentStatus,
			Integer orderClassify, String mediaInfo,
			Integer invoice) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT COUNT(*) ");
		sql.append(" FROM t_scm_order_dealer dealer ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ");
		sql.append(" ON dealer.dealer_order_id = detail.dealer_order_id ");
		sql.append(" LEFT JOIN t_scm_order_after_sell after ON dealer.dealer_order_id = after.dealer_order_id ");
		sql.append(" INNER JOIN t_scm_order_main main ");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ? ");
		params.add(dealerId);
		if (orderStatus != null) {
			sql.append(" AND dealer._status = ? ");
			params.add(orderStatus);
		}
		if (afterSellStatus != null) {
			sql.append(" AND after._status = ? ");
			params.add(afterSellStatus);
		}
		if (StringUtils.isNotEmpty(endTime) && StringUtils.isNotEmpty(endTime)) {
			sql.append(" AND dealer.created_date BETWEEN ? AND ? ");
			params.add(startTime);
			params.add(endTime);
		}
		if (condition != null && !"".equals(condition)) {
			sql.append(" AND dealer.dealer_order_id OR main.pay_no OR detail.rev_phone = ? OR OR detail.goods_name LIKE ? ");
			params.add(condition);
			params.add("%" + condition + "%");
		}
		if (payWay != null) {
			sql.append(" AND main.pay_way = ? ");
			params.add(payWay);
		}
		if (commentStatus != null) {
			sql.append(" AND detail.comment_status = ? ");
			params.add(commentStatus);
		}
		if (mediaInfo != null && !"".equals(mediaInfo)) {
			if ("有广告位".equals(mediaInfo)) {
				sql.append(" AND detail.media_id != '' ");
			}
			if ("无广告位".equals(mediaInfo)) {
				sql.append(" AND detail.meidia_id = '' ");
			}
		}
		if(invoice != null) {
			sql.append(" AND dealer.invoice_type = ? ");
			params.add(invoice);
		}
		sql.append(" AND dealer.order_id = main.order_id ");
		return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class,params.toArray());
		
	}
	
	/**
	 * 获取商家订单商品信息
	 * @param dealerOrderId
	 * @return
	 */
	public List<DealerOrderGoodsInfo> dealerOrderGoodsInfoQuery(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  ");
		sql.append(" goods.goods_main_images,detail.goods_name,detail.stantard_name, ");
		sql.append(" detail.discount_price,detail.sell_num, after._status");
		sql.append(" FROM t_scm_order_dealer dealer ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ON dealer.dealer_order_id = detail.dealer_order_id ");
		sql.append(" LEFT JOIN t_scm_goods goods ON detail.goods_id = goods.goods_id ");
		sql.append(" LEFT JOIN t_scm_order_after_sell after ON dealer.dealer_order_id = after.dealer_order_id ");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_order_id = ? ");
		 List<DealerOrderGoodsInfo> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerOrderGoodsInfo.class,dealerOrderId);
		 return goodsInfoList;
	}
	
	/**
	 * 查询商家订单详情
	 * @param dealerId
	 * @return
	 */
	public DealerOrderDetailBean dealerOrderDetailQuery(String dealerOrderId,String dealerId) {
		StringBuilder sql = new StringBuilder();
		List<Object> params = new ArrayList<Object>();
		sql.append(" SELECT t1._status orderStatus,t1.order_id orderId,t1.created_date createdDate, t3.pay_way payWay,t3.pay_time payTime,t3.pay_no payNo, ");
		sql.append(" t1.rev_person revPerson,t1.rev_phone revPhone,t1.province province,t1.city city,t1.area_county areaCounty,t1.street_addr streetAddr, ");
		sql.append(" t4.dealer_name dealerName,t4.dealer_classify dealerClassify,t1.dealer_id dealerId,  t6.seller_name sellerName,t6.seller_phone sellerPhone, ");
		sql.append(" t2.plateform_discount plateformDiscount,t2.dealer_discount dealerDiscount, ");
		sql.append(" t2.invoice_type,t2.invoice_name,t2.invoice_code ");
		sql.append(" FROM t_scm_order_dealer t1 ");
		sql.append(" INNER JOIN t_scm_order_detail t2 ");
		sql.append(" INNER JOIN t_scm_order_main t3 ");
		sql.append(" INNER JOIN t_scm_dealer t4");
		sql.append(" INNER JOIN t_scm_goods t5 ");
		sql.append(" INNER JOIN t_scm_dealer_seller t6");
		sql.append(" WHERE 1 = 1 AND t1.dealer_order_id = ? ");
		params.add(dealerOrderId);
		sql.append(" AND t1.dealer_id = ? ");
		params.add(dealerId);
		sql.append(" AND t1.dealer_order_id = t2.dealer_order_id ");
		sql.append(" AND t1.order_id = t3.order_id AND t1.dealer_id = t4.dealer_id ");
		sql.append(" AND t2.goods_id = t5.goods_id ");
		sql.append(" AND t2.saler_user_id  = t6.seller_id ");
		System.out.println("商家订单  SHOW SQL-------------------------"+sql);
		DealerOrderDetailBean dealerOrderDetailBean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderDetailBean.class, params.toArray());
		System.out.println("取出来的数据" + dealerOrderDetailBean);
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
