package cn.m2c.scm.application.dealerorder;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.order.data.bean.DealerOrderBean;

@Repository
public class DealerOrderQuery {

	private static final Logger LOGGER = LoggerFactory.getLogger(DealerOrderQuery.class);

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	/**
	 * 获取订单列表
	 * 
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
	public List<DealerOrderBean> dealerOrderQuery(String dealerOrderId, Integer orderStatus, Integer afterSellStatus,
			String startTime, String endTime, String condition, Integer payWay, Integer commentStatus,
			Integer orderClassify, String mediaInfo,
			Integer invoice, Integer pageNum, Integer rows) {
		List<Object> params = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT  ");
		sql.append(" FROM t_scm_order_dealer dealer ");
		sql.append(" LEFT JOIN t_scm_order_detail detail ");
		sql.append(" ON dealer.dealer_order_id = detail.dealer_order_id ");
		sql.append("");
		sql.append("");
		sql.append(" WHERE 1 = 1 AND dealer.dealer_id = ? ");
		params.add(dealerOrderId);
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
		if ("有广告位".equals(mediaInfo)) {
			sql.append(" AND detail.media_id != '' ");
		}
		if ("无广告位".equals(mediaInfo)) {
			sql.append(" AND detail.meidia_id = '' ");
		}
		if(invoice != null) {
			sql.append(" AND dealer.invoice_type = ? ");
			params.add(invoice);
		}
		sql.append(" AND  ");
		return null;

	}
}
