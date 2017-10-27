package cn.m2c.scm.application.dealerorder.query;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderGoodsInfoBean;
import cn.m2c.scm.application.dealerorder.data.bean.ShippingOrderBean;

/**
 * 生成发货单
 * 
 * @author lqwen
 */
@Repository
public class ShippingTableQuery {

	@Resource
	private SupportJdbcTemplate supportJdbcTemplate;

	public ShippingOrderBean shippingOrderQuery(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT detail.rev_person,detail.rev_phone,detail.dealer_order_id,detail.created_date, ");
		sql.append(" detail.province,detail.city,detail.area_county,detail.street_addr,detail.noted, ");
		sql.append(" dealer.dealer_name,dealerorder.order_freight,dealerorder.plateform_discount,dealerorder.dealer_discount ");
		sql.append(" FROM t_scm_order_dealer dealerorder ");
		sql.append(" INNER JOIN t_scm_order_detail detail ");
		sql.append(" INNER JOIN t_scm_dealer dealer ");
		sql.append(" WHERE 1 = 1 AND dealerorder.dealer_order_id = ? ");
		sql.append(" AND dealerorder.dealer_order_id = detail.dealer_order_id ");
		sql.append(" AND dealerorder.dealer_id = dealer.dealer_id ");
		List<ShippingOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				ShippingOrderBean.class, dealerOrderId);
		List<DealerOrderGoodsInfoBean> goodsInfoList = shippingOrderGoodsInfoQuery(dealerOrderId);
		long totalOrderMoney = 0;
		for (ShippingOrderBean shippingOrderBean : beanList) {
			shippingOrderBean.setGoodsInfos(goodsInfoList);
			shippingOrderBean.setDiscountMoney(shippingOrderBean.getDealerDiscount() + shippingOrderBean.getPlateformDiscount());
			for (DealerOrderGoodsInfoBean goodsInfo : goodsInfoList) {
				totalOrderMoney += goodsInfo.getGoodsMoney();
			}
			shippingOrderBean.setTotalOrderMoney(totalOrderMoney);
		}
		return beanList.get(0);
	}

	public List<DealerOrderGoodsInfoBean> shippingOrderGoodsInfoQuery(String dealerOrderId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT ");
		sql.append(" detail.goods_name,goods.goods_specifications, ");
		sql.append(" detail.discount_price,detail.sell_num ");
		sql.append(" FROM t_scm_order_detail detail ");
		sql.append(" INNER JOIN t_scm_goods goods ");
		sql.append(" WHERE 1 = 1 AND detail.dealer_order_id = ? AND detail.goods_id = goods.goods_id ");
		List<DealerOrderGoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
				DealerOrderGoodsInfoBean.class, dealerOrderId);
		for (DealerOrderGoodsInfoBean dealerOrderGoodsInfo : goodsInfoList) {
			dealerOrderGoodsInfo.setGoodsMoney(dealerOrderGoodsInfo.getSellNum() * dealerOrderGoodsInfo.getPrice());
		}
		return goodsInfoList;

	}
}
