package cn.m2c.scm.application.order.query;


import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.order.data.bean.AllOrderBean;
import cn.m2c.scm.application.order.data.bean.AppInfo;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;
import cn.m2c.scm.application.order.data.bean.MainOrderBean;
import cn.m2c.scm.application.order.data.bean.OrderDealerBean;
import cn.m2c.scm.application.order.data.bean.OrderGoodsBean;
import cn.m2c.scm.application.order.data.bean.SimpleCoupon;
import cn.m2c.scm.application.order.data.bean.SimpleMarket;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.service.order.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderQuery.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    @Autowired
    DealerQuery dealerQuery;
    @Autowired
    OrderService orderService;

    /**
     * 查询mainOrder订单
     *
     * @param orderStatus     订单状态
     * @param afterSaleStatus 售后状态
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param condition       搜索条件(goodsName,orderId,payNo,revPhone,dealerName,dealerId)
     * @param payWay          支付方式
     * @param mediaInfo       媒体信息
     * @param dealerClassify  商家分类
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    public List<MainOrderBean> mainOrderListQuery(Integer orderStatus, Integer afterSaleStatus, String startTime, String orderId,
                                                  String endTime, String condition, Integer payWay, Integer commentStatus, Integer mediaInfo, String dealerClassify, Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT d.dealer_order_id,m.order_id,m.pay_no,m.pay_way,m.created_date,m.goods_amount,m.order_freight,m.plateform_discount pDiscount,m.dealer_discount pDealerDiscount, ");
        sql.append(" d.goods_amount AS dealerAmount, d.plateform_discount, d.dealer_discount, d.order_freight AS orderFreight,d._status,dealer.dealer_name ");
        sql.append("  FROM t_scm_order_dealer d ");
        sql.append(" LEFT OUTER JOIN t_scm_order_main m ON d.order_id = m.order_id ");
        sql.append(" LEFT OUTER JOIN t_scm_dealer dealer ON d.dealer_id = dealer.dealer_id ");
        //sql.append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.dealer_order_id = d.dealer_order_id ");
        sql.append(" WHERE 1=1 ");
        if (orderStatus != null) {
            sql.append(" AND d._status =  ? ");
            params.add(orderStatus);
        }

        // 售后状态处理
        afterSaleStatusDeal(afterSaleStatus, sql, params);

        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND (d.created_date BETWEEN ? AND ?)");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(condition) && condition != null && !"".equals(condition)) {
            sql.append(" AND (d.dealer_order_id LIKE ?	OR m.pay_no LIKE ? OR dealer.dealer_name LIKE ? OR (d.dealer_order_id IN (SELECT od.dealer_order_id FROM t_scm_order_detail od WHERE od.goods_name LIKE ? )) OR d.rev_phone = ?) ");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add(condition);
        }
        if (payWay != null) {
            sql.append(" AND m.pay_way = ? ");
            params.add(payWay);
        }
        /*if (commentStatus != null && commentStatus >= 0) {
            sql.append(" AND dtl.comment_status = ?\r\n");
            params.add(commentStatus);
        }*/
        if (commentStatus != null && commentStatus >= 0) {
            sql.append(" AND d.dealer_order_id IN (SELECT DISTINCT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = ?)\r\n");
            params.add(commentStatus);
        }
        if (mediaInfo != null) {
            if (mediaInfo == 0) {
                sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.media_res_id IS NULL)");
            }
            if (mediaInfo == 1) {
                sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.media_res_id IS NOT NULL)");
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
                mainOrder.setGoodAmount(allOrder.getGoodsAmount());
                mainOrder.setOderFreight(allOrder.getStrMainOrderFreight());
                mainOrder.setDealerDiscount(allOrder.getStrPpDealerDiscount());
                mainOrder.setPlateFormDiscount(allOrder.getStrPpDiscount());
                List<OrderDealerBean> dealerOrderList = new ArrayList<OrderDealerBean>();
                mainOrder.setDealerOrderBeans(dealerOrderList);
            }
            dealerBean = new OrderDealerBean();
            dealerBean.setOrderId(allOrder.getOrderId());
            dealerBean.setDealerName(allOrder.getDealerName());
            dealerBean.setDealerOrderId(allOrder.getDealerOrderId());
            dealerBean.setGoodAmount(allOrder.getStrDealerGoodsAmount());
            dealerBean.setOderFreight(allOrder.getStrDealerOrderFreight());
            dealerBean.setPlateFormDiscount(allOrder.getStrPlateformDiscount());
            dealerBean.setDealerDiscount(allOrder.getStrDealerDiscount());
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
                                       String condition, Integer payWay, Integer commentStatus, Integer mediaInfo, String dealerClassify) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT COUNT(1) ");
        sql.append("  FROM t_scm_order_dealer d");
        sql.append(" LEFT OUTER JOIN t_scm_order_main m ON d.order_id = m.order_id ");
        sql.append(" LEFT OUTER JOIN t_scm_dealer dealer ON d.dealer_id = dealer.dealer_id ");
        //sql.append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.dealer_order_id = d.dealer_order_id ");
        sql.append(" WHERE 1=1 ");
        if (orderStatus != null) {
            sql.append(" AND d._status =  ? ");
            params.add(orderStatus);
        }

        // 售后状态处理
        afterSaleStatusDeal(afterSaleStatus, sql, params);

        if (StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)) {
            sql.append(" AND (d.created_date BETWEEN ? AND ?)");
            params.add(startTime + " 00:00:00");
            params.add(endTime + " 23:59:59");
        }
        if (StringUtils.isNotEmpty(condition) && condition != null && !"".equals(condition)) {
            sql.append(" AND (d.dealer_order_id LIKE ?	OR m.pay_no LIKE ? OR dealer.dealer_name LIKE ? OR (d.dealer_order_id IN (SELECT od.dealer_order_id FROM t_scm_order_detail od WHERE od.goods_name LIKE ? )) OR d.rev_phone = ?) ");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add("%" + condition + "%");
            params.add(condition);
        }
        /*if (commentStatus != null && commentStatus >= 0) {
        sql.append(" AND dtl.comment_status = ?\r\n");
        params.add(commentStatus);
	    }*/
        if (commentStatus != null && commentStatus >= 0) {
            sql.append(" AND d.dealer_order_id IN (SELECT DISTINCT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.comment_status = ?)\r\n");
            params.add(commentStatus);
        }
        if (payWay != null) {
            sql.append(" AND m.pay_way = ? ");
            params.add(payWay);
        }
        if (mediaInfo != null) {
            if (mediaInfo == 0) {
                sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.media_res_id IS NULL)");
            }
            if (mediaInfo == 1) {
                sql.append("AND d.dealer_order_id IN (SELECT dtl.dealer_order_id FROM t_scm_order_detail dtl WHERE dtl.media_res_id IS NOT NULL)");
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
        sql.append(" t4.dealer_name dealerName, t4.dealer_classify dealerClassify, t1.dealer_id dealerId, ");
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
     *
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
        DealerOrderDetailBean dealerOrderDetailBean = this.supportJdbcTemplate.queryForBean(sql.toString(), DealerOrderDetailBean.class, dealerOrderId);
        if (dealerOrderDetailBean != null) {
            dealerOrderDetailBean.setDealerOrderId(dealerOrderId);
        }
        List<GoodsInfoBean> goodsInfoList = getGoodsInfoList(dealerOrderId);
        //判断所有订单是否有售后满足发货条件
        Integer temp = 0;
		for (GoodsInfoBean goodsInfoBean : goodsInfoList) {
			if (StringUtils.isNotEmpty(goodsInfoBean.getAfterSellOrderId())) {
				if (goodsInfoBean.getAfterSellStatus() >= -1 && goodsInfoBean.getAfterSellStatus() <= 3)  { // 售后状态 大于等于-1小于等于3，表示可以发货
					temp++;
				}
			}
		}
		if (temp == 0) {
			dealerOrderDetailBean.setIsShowShip(0);
		}
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
     *
     * @param dealerOrderId
     * @return
     */
    public List<GoodsInfoBean> getGoodsInfoList(String dealerOrderId) {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        sql.append(" SELECT  dtl.goods_icon, dtl.goods_name,dtl.sku_name, dtl.sku_id,a.after_sell_order_id, a._status AS afterSellStatus, a.order_type AS afterOrderType, a.sell_num afNum, dtl.sort_no, dtl.goods_amount, \r\n")
                .append(" dtl.media_res_id,dtl.sell_num,dtl.goods_unit, dtl.discount_price,dtl.freight, dtl.is_change, dtl.change_price,dtl.is_special,dtl.special_price \r\n")
                .append(" FROM  t_scm_order_dealer dealer \r\n")
                .append(" ,t_scm_order_detail dtl \r\n")
                .append(" LEFT OUTER JOIN (SELECT * FROM t_scm_order_after_sell WHERE dealer_order_id = ?  ORDER BY created_date DESC LIMIT 1 ) a ON a.dealer_order_id=dtl.dealer_order_id AND a.sku_id = dtl.sku_id AND a.sort_no=dtl.sort_no \r\n")
                .append(" WHERE dealer.dealer_order_id = ? ")
                .append(" AND dealer.dealer_order_id = dtl.dealer_order_id ");
        //.append(" AND dtl.sku_id NOT IN (SELECT a.sku_id FROM t_scm_order_after_sell a WHERE a.dealer_order_id=dtl.dealer_order_id AND a._status >= 4) ");
        params.add(dealerOrderId);
        params.add(dealerOrderId);
        List<GoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(),
                GoodsInfoBean.class, params.toArray());
        /*for (GoodsInfoBean goodsInfo : goodsInfoList) {
            goodsInfo.setTotalPrice(goodsInfo.getPrice() * goodsInfo.getSellNum());
		}*/
        return goodsInfoList;
    }

    /***
     * 获取订单数据
     *
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
                .append(" sell_num, bds_rate, media_id, media_res_id, saler_user_id, saler_user_rate, is_change, change_price, res_rate, marketing_id, market_level\r\n")
                .append(" , is_special, special_price, sort_no, goods_name")
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
        sql.append("SELECT marketing_id, market_level, market_type, threshold, threshold_type, discount, share_percent, market_name \r\n")
                .append(" FROM t_scm_order_marketing_used WHERE order_id=? AND _status=1 ");
        order.setMarkets(supportJdbcTemplate.queryForBeanList(sql.toString(), SimpleMarket.class, orderNo));

        sql.delete(0, sql.length());
        sql.append("SELECT coupon_id, coupon_form, coupon_type, threshold, threshold_type, discount, share_percent, coupon_name \r\n")
                .append(" FROM t_scm_order_coupon_used WHERE order_id=? AND _status=1");
        order.setCoupons(supportJdbcTemplate.queryForBeanList(sql.toString(), SimpleCoupon.class, orderNo));

        sql.delete(0, sql.length());
        sql.append("SELECT order_id, os, os_version, app_version, sn \r\n")
                .append(" FROM t_scm_order_app WHERE order_id=? ");
        order.setAppInfo(supportJdbcTemplate.queryForBean(sql.toString(), AppInfo.class, orderNo));
        sql = null;
        return order;
    }

    /***
     * 获取成效订单数
     *
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
     *
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
        for (int i = 0; i < sz; i++) {
            if (i > 0)
                sql.append(",");
            sql.append("?");
        }
        sql.append(")");
        List<String> ls = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), String.class, ids.toArray());
        if (ls == null)
            return result;

        result = new HashMap<String, Integer>();
        for (String a : ls) {
            result.put(a, 1);
        }
        return result;
    }

    public long getOrderFreigh(long startTime, long endTime) throws NegativeException {
        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();

        if (startTime == 0) {
            throw new NegativeException(MCode.V_1, "开始时间参数为空！");
        }

        if (endTime == 0) {
            throw new NegativeException(MCode.V_1, "结束时间参数为空！");
        }

        if (startTime >= endTime) {
            throw new NegativeException(MCode.V_1, "开始时间不能大于结束时间");
        }
        sql.append(" SELECT SUM(order_freight) FROM t_scm_order_main ")
                .append(" WHERE _status >= 1 AND pay_time BETWEEN ? AND ? ");
        params.add(new Date(startTime));
        params.add(new Date(endTime));
        Object object = supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Object.class, params.toArray());
        long sum = 0;
        if (object != null && object instanceof BigDecimal)
            sum = ((BigDecimal) object).longValue();
        return sum;
    }

    /**
     * 根据主订单id获取用户id
     *
     * @param orderId
     * @return
     * @throws NegativeException
     */
    public String getOrderUserId(String orderId) throws NegativeException {
        String userId = "";
        try {
            String sql = "SELECT user_id FROM t_scm_order_main WHERE order_id = ?";
            userId = supportJdbcTemplate.jdbcTemplate().queryForObject(sql, String.class, orderId);
        } catch (Exception e) {
            LOGGER.info(" 根据主订单id获取用户id", e);
            throw new NegativeException(500, "根据订单id查询用户id失败");
        }
        return userId;
    }

    /***
     * 获取某个商家的下单成功用户ID列表
     *
     * @param dealerId
     * @return
     */
    public List<String> getDealerUsersHasPurchase(String dealerId) throws NegativeException {

        List<String> result = null;

        if (StringUtils.isEmpty(dealerId)) {
            return result;
        }

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT DISTINCT a.user_id FROM t_scm_order_main a, t_scm_order_dealer b WHERE a.order_id=b.order_id\r\n")
                .append("AND b._status >=1\r\n")
                .append("AND b.dealer_id=?");
        result = supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), String.class, dealerId);
        return result;
    }

    public Integer getAdminDealerOrderTotal(String orderId, String dealerOrderId, Integer orderStatus, Integer afterSellStatus, Integer commentStatus,
                                            Integer payStatus, Integer payWay, String goodsNameOrId, String shopName, String orderStartTime, String orderEndTime,
                                            String userName, String mediaOrResId) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  count(*) from (");
        sql.append(" SELECT d.*");
        sql.append(" FROM t_scm_order_dealer d,t_scm_order_main m,t_scm_order_detail dtl");
        sql.append(" WHERE d.order_id = m.order_id and d.dealer_order_id = dtl.dealer_order_id");
        // 条件处理
        getAdminOrderListConditionDeal(orderId, dealerOrderId, orderStatus, afterSellStatus, commentStatus,
                payStatus, payWay, goodsNameOrId, shopName, orderStartTime, orderEndTime,
                userName, mediaOrResId, sql, params);
        sql.append(" group by d.dealer_order_id) a");
        return supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());
    }

    public List<Map<String, Object>> getAdminDealerOrderList(String orderId, String dealerOrderId, Integer orderStatus, Integer afterSellStatus, Integer commentStatus,
                                                             Integer payStatus, Integer payWay, String goodsNameOrId, String shopName, String orderStartTime, String orderEndTime,
                                                             String userName, String mediaOrResId, Integer pageNum, Integer rows) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT d.order_id as orderId,d.dealer_order_id as dealerOrderId,d._status as orderStatus,");
        sql.append(" d.goods_amount as goodsAmount, d.plateform_discount as platformDiscount, d.dealer_discount as dealerDiscount, d.order_freight as orderFreight,d.coupon_discount as couponDiscount,");
        sql.append(" m.user_id as userId,d.dealer_id as dealerId,m.created_date as createdDate");
        sql.append(" FROM t_scm_order_dealer d,t_scm_order_main m,t_scm_order_detail dtl");
        sql.append(" WHERE d.order_id = m.order_id and d.dealer_order_id = dtl.dealer_order_id");
        // 条件处理
        getAdminOrderListConditionDeal(orderId, dealerOrderId, orderStatus, afterSellStatus, commentStatus,
                payStatus, payWay, goodsNameOrId, shopName, orderStartTime, orderEndTime,
                userName, mediaOrResId, sql, params);
        sql.append(" group by dealerOrderId ORDER BY m.order_id DESC, m.created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        return supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray());
    }

    private void getAdminOrderListConditionDeal(String orderId, String dealerOrderId, Integer orderStatus, Integer afterSellStatus, Integer commentStatus,
                                                Integer payStatus, Integer payWay, String goodsNameOrId, String shopName, String orderStartTime, String orderEndTime,
                                                String userName, String mediaOrResId, StringBuilder sql, List<Object> params) {
        if (StringUtils.isNotEmpty(dealerOrderId)) {
            sql.append(" AND d.dealer_order_id = ?");
            params.add(dealerOrderId);
        }
        if (StringUtils.isNotEmpty(orderId)) {
            sql.append(" AND d.order_id = ?");
            params.add(orderId);
        }
        // 订单状态:0.待付款 1.待发货 2.待收货 3.已完成 4.交易完成 5.交易关闭 -1.已取消
        if (null != orderStatus) {
            sql.append(" AND d._status =  ? ");
            params.add(orderStatus);
        }

        // 售后状态处理
        afterSaleStatusDeal(afterSellStatus, sql, params);
        // 评论状态:0.待评论 1.已评论
        if (commentStatus != null && commentStatus >= 0) {
            sql.append(" AND dtl.comment_status = ?");
            params.add(commentStatus);
        }
        // 支付状态:0.未支付 1.已支付
        if (null != payStatus) {
            if (payStatus == 0) {
                sql.append(" AND d._status in (0,-1) ");
            }
            if (payStatus == 1) {
                sql.append(" AND d._status in (1,2,3,4,5) ");
            }
        }
        // 支付方式:1.支付宝 2.微信
        if (null != payWay) {
            sql.append(" AND m.pay_way = ? ");
            params.add(payWay);
        }

        // 商品名称或编号
        if (StringUtils.isNotEmpty(goodsNameOrId)) {
            sql.append(" AND (dtl.goods_id = ? OR dtl.goods_name like ?)");
            params.add(goodsNameOrId);
            params.add("%" + goodsNameOrId + "%");
        }

        // 店铺名称
        if (StringUtils.isNotEmpty(shopName)) {
            List<String> dealerIds = dealerQuery.getDealerIdsByShopName(shopName);
            if (null != dealerIds && dealerIds.size() > 0) {
                sql.append(" AND d.dealer_id in (" + Utils.listParseString(dealerIds) + ") ");
            }
        }

        // 订单下单时间
        if (StringUtils.isNotEmpty(orderStartTime) && StringUtils.isNotEmpty(orderEndTime)) {
            sql.append(" AND (d.created_date BETWEEN ? AND ?)");
            params.add(orderStartTime + " 00:00:00");
            params.add(orderEndTime + " 23:59:59");
        }

        // 下单用户名或账号，精准匹配
        if (StringUtils.isNotEmpty(userName)) {
            String userId = orderService.getUserIdByUserName(userName);
            if (StringUtils.isNotEmpty(userId)) {
                sql.append(" AND m.user_id = ?");
                params.add(userId);
            }
        }

        // 媒体ID或广告位ID，精准匹配
        if (StringUtils.isNotEmpty(mediaOrResId)) {
            sql.append(" AND (dtl.media_res_id = ? OR dtl.media_id = ?)");
            params.add(mediaOrResId);
            params.add(mediaOrResId);
        }
    }

    /**
     * 售后状态条件处理
     *
     * @param afterSaleStatus
     * @param sql
     * @param params
     */
    private void afterSaleStatusDeal(Integer afterSaleStatus, StringBuilder sql, List<Object> params) {
        if (afterSaleStatus != null && (afterSaleStatus >= 20 && afterSaleStatus < 28)) {
            switch (afterSaleStatus) {
                case 20: //待商家同意
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status IN(?,?,?))");
                    params.add(0);
                    params.add(1);
                    params.add(2);
                    break;
                case 21://待顾客寄回商品
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af.order_type IN(0,1) AND af._status =?)");
                    params.add(4);
                    break;
                case 22://待商家确认退款
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE (af.order_type=0 AND af._status =?) OR (af.order_type=1 AND af._status =?) OR (af.order_type=2 AND af._status =?))");
                    params.add(8);
                    params.add(6);
                    params.add(4);
                    break;
                case 23://待商家发货
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af.order_type=2 AND af._status =?)");
                    params.add(6);
                    break;
                case 24://待顾客收货
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af.order_type=2 AND af._status =?)");
                    params.add(7);
                    break;
                case 25://售后已完成
                    sql.append(" AND d.dealer_order_id IN ( SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status >= ?)");
                    params.add(9);
                    break;
                case 26://售后已取消
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status = ?)");
                    params.add(-1);
                    break;
                case 27://商家已拒绝
                    sql.append(" AND d.dealer_order_id IN (SELECT af.dealer_order_id FROM t_scm_order_after_sell af WHERE af._status = ?)");
                    params.add(3);
                    break;
            }
        }
    }

    public Map<String, Object> getAdminOrderDetail(String dealerOrderId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT d.order_id as orderId,d.dealer_order_id as dealerOrderId,d._status as orderStatus,");
        sql.append(" d.goods_amount as goodsAmount, d.order_freight as orderFreight,");
        sql.append(" d.plateform_discount as platformDiscount, d.dealer_discount as dealerDiscount,d.coupon_discount as couponDiscount,");
        sql.append(" m.user_id as userId,d.dealer_id as dealerId,m.created_date as createdDate,m.pay_way as payWay,m.pay_no as payNo,");
        sql.append(" dtl.rev_person as revPerson,dtl.rev_phone as revPhone,dtl.noted,dtl.province,dtl.city,dtl.area_county as areaCounty,dtl.street_addr as streetAddress,");
        sql.append(" dtl.invoice_header as invoiceHeader,dtl.invoice_type as invoiceType,dtl.invoice_name as invoiceName,dtl.invoice_code as invoiceCode,dtl.comment_status as commentStatus");
        sql.append(" FROM t_scm_order_dealer d,t_scm_order_main m,t_scm_order_detail dtl");
        sql.append(" WHERE d.dealer_order_id=? and d.order_id = m.order_id and d.dealer_order_id = dtl.dealer_order_id group by dealerOrderId");
        Map<String, Object> map = supportJdbcTemplate.jdbcTemplate().queryForMap(sql.toString(), dealerOrderId);
        if (null != map) {
            StringBuilder dtlSql = new StringBuilder();
            dtlSql.append(" select dtl.goods_name as goodsName,dtl.goods_icon as goodsIcon,dtl.sku_name as skuName,dtl.media_id as mediaId,dtl.media_res_id as mediaResId,dtl.sell_num as sellNum,");
            dtlSql.append(" dtl.goods_unit as goodsUnit,dtl.discount_price as price,dtl.goods_amount as goodsAmount,dtl.freight as freight");
            dtlSql.append(" from t_scm_order_detail dtl where dtl.dealer_order_id=?");
            List<Map<String, Object>> dtlList = supportJdbcTemplate.jdbcTemplate().queryForList(dtlSql.toString(), dealerOrderId);
            if (null != dtlList && dtlList.size() > 0) {
                for (Map<String, Object> dtlMap : dtlList) {
                    String mediaId = null != dtlMap.get("mediaId") ? dtlMap.get("mediaId").toString() : null;
                    String mediaName = "";
                    if (StringUtils.isNotEmpty(mediaId)) {
                        mediaName = orderService.getMediaName(mediaId);
                    }
                    dtlMap.put("mediaName", mediaName);

                    String goodsIconStr = null != dtlMap.get("goodsIcon") ? String.valueOf(dtlMap.get("goodsIcon")) : null;
                    List<String> goodsIcons = JsonUtils.toList(goodsIconStr, String.class);
                    if (null != goodsIcons && goodsIcons.size() > 0) {
                        dtlMap.put("goodsIcon", goodsIcons.get(0));
                    }

                    Long goodsAmount = null == dtlMap.get("goodsAmount") ? 0 : Long.parseLong(dtlMap.get("goodsAmount").toString());
                    Long freight = null == dtlMap.get("freight") ? 0 : Long.parseLong(dtlMap.get("freight").toString());
                    Long price = null == dtlMap.get("price") ? 0 : Long.parseLong(dtlMap.get("price").toString());
                    dtlMap.put("goodsAmount", Utils.moneyFormatCN(goodsAmount));
                    dtlMap.put("freight", Utils.moneyFormatCN(freight));
                    dtlMap.put("price", Utils.moneyFormatCN(price));
                }
                map.put("dtlList", dtlList);
            }
        }
        return map;
    }
}
