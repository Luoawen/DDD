package cn.m2c.scm.application.dealerorder.query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.application.dealerorder.data.bean.DealerGoodsBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderGoodsInfoBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderQB;
import cn.m2c.scm.application.order.data.bean.DealerOrderDetailBean;
import cn.m2c.scm.application.order.data.bean.GoodsInfoBean;

@Repository
public class DealerOrderQuery {

    private static final Logger LOGGER = LoggerFactory.getLogger(DealerOrderQuery.class);

    @Resource
    private SupportJdbcTemplate supportJdbcTemplate;

    /**
     * 获取订单列表
     *
     * @param dealerOrderId   商家订单号
     * @param orderStatus     订单状态
     * @param afterSellStatus 售后状态
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param condition       搜索条件(goodsName,dealerOrderId,payNo,revPhone)
     * @param payWay          支付方式
     * @param commentStatus   评论状态
     * @param mediaInfo       广告位
     * @param invoice         开发票
     * @param orderClassify   订单类型
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    public List<DealerOrderBean> dealerOrderQuery(String dealerId, String dealerOrderId, Integer orderStatus, Integer afterSellStatus,
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
            if ("1".equals(mediaInfo)) { //有广告位
                sql.append(" AND detail.media_id != '' ");
            }
            if ("0".equals(mediaInfo)) { // 无广告位
                sql.append(" AND detail.meidia_id = '' ");
            }
        }
        if (invoice != null) {
            sql.append(" AND dealer.invoice_type = ? ");
            params.add(invoice);
        }
        sql.append(" ORDER BY dealer.created_date DESC ");
        sql.append(" LIMIT ?,?");
        params.add(rows * (pageNum - 1));
        params.add(rows);
        List<DealerOrderBean> beanList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerOrderBean.class, params.toArray());
        List<DealerOrderGoodsInfoBean> goodsInfoList = dealerOrderGoodsInfoQuery(dealerOrderId);
        long orderTotalMoney = 0;

        /**
         * 计算出订单总额
         */
        for (DealerOrderGoodsInfoBean goodsInfoBean : goodsInfoList) {
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
     *
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
    public Integer dealerOrderTotalQuery(String dealerId, String dealerOrderId, Integer orderStatus, Integer afterSellStatus,
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
        if (invoice != null) {
            sql.append(" AND dealer.invoice_type = ? ");
            params.add(invoice);
        }
        sql.append(" AND dealer.order_id = main.order_id ");
        return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());

    }

    /**
     * 获取订单列表
     *
     * @param dealerOrderId   商家订单号
     * @param orderStatus     订单状态
     * @param afterSellStatus 售后状态
     * @param startTime       开始时间
     * @param endTime         结束时间
     * @param condition       搜索条件(goodsName,dealerOrderId,payNo,revPhone)
     * @param payWay          支付方式
     * @param commentStatus   评论状态
     * @param mediaInfo       广告位
     * @param invoice         开发票
     * @param orderClassify   订单类型
     * @param pageNum         第几页
     * @param rows            每页多少行
     * @return
     */
    public List<DealerOrderQB> dealerOrderQuery1(String dealerId,
                                                 Integer orderStatus, Integer afterSellStatus,
                                                 String startTime, String endTime, String condition,
                                                 Integer payWay, Integer hasComment, Integer orderType, Integer hasMedia,
                                                 Integer hasInvoice, Integer pageNum, Integer rows) {

        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT dtl.sku_id, dtl.sku_name, dtl.goods_name, dtl.goods_title, a.dealer_id, a.created_date, dtl.discount_price, \r\n")
                .append("dtl.sell_num, af._status afStatus, a._status, om.pay_no, a.dealer_order_id, dtl.goods_icon, a.created_date,\r\n")
                .append(" a.rev_person, a.rev_phone, a.goods_amount, a.order_freight, a.plateform_discount, a.dealer_discount, a.order_id, af.after_sell_order_id\r\n")
                .append(" FROM t_scm_order_dealer a \r\n")
                .append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_after_sell af ON af.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_main om ON a.order_id = om.order_id\r\n")
                .append(" WHERE a.dealer_id = ?  \r\n");

        params.add(dealerId);

        if (!StringUtils.isEmpty(condition)) {
            sql.append(" AND (dtl.goods_name LIKE CONCAT('%',?,'%') OR a.dealer_order_id LIKE CONCAT('%',?,'%') OR om.pay_no LIKE CONCAT('%',?,'%') OR\r\n");
            sql.append(" a.rev_phone LIKE CONCAT('%',?,'%'))\r\n");
            params.add(condition);
            params.add(condition);
            params.add(condition);
            params.add(condition);
        }

        if (orderStatus != null && orderStatus >= 0) {
            sql.append(" AND a._status=?\r\n");
            params.add(orderStatus);
        }
        if (hasInvoice != null && hasInvoice >= 0) {
            sql.append(" AND a.invoice_type = ?\r\n");
            params.add(hasInvoice);
        }

        if (hasComment != null && hasComment >= 0) {
            sql.append(" AND dtl.comment_status = ?\r\n");
            params.add(hasComment);
        }

        if (afterSellStatus != null && afterSellStatus >= 0) {
            sql.append(" AND af._status = ?\r\n");
            params.add(afterSellStatus);
        }

        if (orderType != null && orderType >= 0) {
            sql.append(" AND om.order_type=?\r\n");
            params.add(orderType);
        }

        if (payWay != null && payWay > 0) {
            sql.append(" AND om.pay_way=?\r\n");
            params.add(payWay);
        }
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.append(" AND a.created_date BETWEEN ? AND ?\r\n");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" ORDER BY a.dealer_order_id DESC, a.created_date DESC");

        sql.append(" LIMIT ?,?");

        params.add(rows * (pageNum - 1));
        params.add(rows);

        List<Map<String, Object>> beanList = this.supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray());//(sql.toString(), HashMap.class, params.toArray());

        List<DealerOrderQB> rs = new ArrayList<DealerOrderQB>();

        String dealerOrderId = null;
        DealerOrderQB midBean = null;
        for (Map<String, Object> item : beanList) {

            String ordIdTemp = (String) item.get("dealer_order_id");
            if (!ordIdTemp.equals(dealerOrderId)) {
                midBean = new DealerOrderQB();
                midBean.setDealerId((String) item.get("dealer_id"));
                midBean.setDealerOrderId(ordIdTemp);
                Timestamp a = (Timestamp) item.get("created_date");
                midBean.setCreatedDate(a == null ? null : a.getTime());
                midBean.setOrderStatus((Integer) item.get("_status"));
                midBean.setPayNo((String) item.get("pay_no"));
                midBean.setRevPerson((String) item.get("rev_person"));
                midBean.setRevPhone((String) item.get("rev_phone"));
                midBean.setGoodsMoney((long) item.get("goods_amount"));
                midBean.setOrderFreight((long) item.get("order_freight"));
                midBean.setPlateDiscount((long) item.get("plateform_discount"));
                midBean.setDealerDiscount((long) item.get("dealer_discount"));
                midBean.setOrderId((String) item.get("order_id"));

                List<DealerGoodsBean> goodses = new ArrayList<>();
                midBean.setGoodsList(goodses);
                rs.add(midBean);
                dealerOrderId = ordIdTemp;
            }

            DealerGoodsBean dgb = new DealerGoodsBean();
            dgb.setSkuName((String) item.get("sku_name"));
            dgb.setGoodsName((String) item.get("goods_name"));
            dgb.setGoodsTitle((String) item.get("goods_title"));
            dgb.setSkuId((String) item.get("sku_id"));
            dgb.setDiscountPrice((long) item.get("discount_price"));
            dgb.setSellNum((Integer) item.get("sell_num"));
            dgb.setAfStatus((Integer) item.get("afStatus"));
            dgb.setGoodsImage((String) item.get("goods_icon"));
            dgb.setSaleAfterNo((String) item.get("after_sell_order_id"));

            midBean.getGoodsList().add(dgb);
        }
        return rs;
    }

    /**
     * 获取商家订单总数
     *
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
    public Integer dealerOrderTotalQuery1(String dealerId,Integer orderStatus, Integer afterSellStatus,
                                          String startTime, String endTime, String condition,
                                          Integer payWay, Integer hasComment, Integer orderType, Integer hasMedia,
                                          Integer hasInvoice) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT count(1)")
                .append(" FROM t_scm_order_dealer a \r\n")
                .append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_after_sell af ON af.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_main om ON a.order_id = om.order_id\r\n")
                .append(" WHERE a.dealer_id = ?  \r\n");

        params.add(dealerId);

        if (!StringUtils.isEmpty(condition)) {
            sql.append(" AND (dtl.goods_name LIKE CONCAT('%',?,'%') OR a.dealer_order_id LIKE CONCAT('%',?,'%') OR om.pay_no LIKE CONCAT('%',?,'%') OR\r\n");
            sql.append(" a.rev_phone LIKE CONCAT('%',?,'%'))\r\n");
            params.add(condition);
            params.add(condition);
            params.add(condition);
            params.add(condition);
        }

        if (orderStatus != null && orderStatus >= 0) {
            sql.append(" AND a._status=?\r\n");
            params.add(orderStatus);
        }
        if (hasInvoice != null && hasInvoice >= 0) {
            sql.append(" AND a.invoice_type = ?\r\n");
            params.add(hasInvoice);
        }

        if (hasComment != null && hasComment >= 0) {
            sql.append(" AND dtl.comment_status = ?\r\n");
            params.add(hasComment);
        }

        if (afterSellStatus != null && afterSellStatus >= 0) {
            sql.append(" AND af._status = ?\r\n");
            params.add(afterSellStatus);
        }

        if (orderType != null && orderType >= 0) {
            sql.append(" AND om.order_type=?\r\n");
            params.add(orderType);
        }

        if (payWay != null && payWay > 0) {
            sql.append(" AND om.pay_way=?\r\n");
            params.add(payWay);
        }
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.append(" AND a.created_date BETWEEN ? AND ?\r\n");
            params.add(startTime);
            params.add(endTime);
        }
        System.out.println("SHOW  sQL-------------------------->"+sql);
        return this.supportJdbcTemplate.jdbcTemplate().queryForObject(sql.toString(), Integer.class, params.toArray());

    }

    /**
     * 获取商家订单商品信息
     *
     * @param dealerOrderId
     * @return
     */
    public List<DealerOrderGoodsInfoBean> dealerOrderGoodsInfoQuery(String dealerOrderId) {
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT  ");
        sql.append(" goods.goods_main_images,detail.goods_name,detail.stantard_name, ");
        sql.append(" detail.discount_price,detail.sell_num, after._status");
        sql.append(" FROM t_scm_order_dealer dealer ");
        sql.append(" LEFT JOIN t_scm_order_detail detail ON dealer.dealer_order_id = detail.dealer_order_id ");
        sql.append(" LEFT JOIN t_scm_goods goods ON detail.goods_id = goods.goods_id ");
        sql.append(" LEFT JOIN t_scm_order_after_sell after ON dealer.dealer_order_id = after.dealer_order_id ");
        sql.append(" WHERE 1 = 1 AND dealer.dealer_order_id = ? ");
        List<DealerOrderGoodsInfoBean> goodsInfoList = this.supportJdbcTemplate.queryForBeanList(sql.toString(), DealerOrderGoodsInfoBean.class, dealerOrderId);
        return goodsInfoList;
    }

    /**
     * 查询商家订单详情
     *
     * @param dealerId
     * @return
     */
    public DealerOrderDetailBean dealerOrderDetailQuery(String dealerOrderId, String dealerId) {
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
        System.out.println("商家订单  SHOW SQL-------------------------" + sql);
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

    /**
     * 导出
     *
     * @param dealerId
     * @param orderStatus
     * @param afterSellStatus
     * @param startTime
     * @param endTime
     * @param condition
     * @param payWay
     * @param hasComment
     * @param orderType
     * @param hasMedia
     * @param hasInvoice
     * @return
     */
    public List<DealerOrderQB> dealerOrderQueryExport(String dealerId,
                                                      Integer orderStatus, Integer afterSellStatus,
                                                      String startTime, String endTime, String condition,
                                                      Integer payWay, Integer hasComment, Integer orderType, Integer hasMedia,
                                                      Integer hasInvoice) {

        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT dtl.sku_id, dtl.sku_name, dtl.goods_name, dtl.goods_title, a.dealer_id, a.created_date, dtl.discount_price, \r\n")
                .append("dtl.sell_num, af._status afStatus, a._status, om.pay_no, a.dealer_order_id, dtl.goods_icon, a.created_date,\r\n")
                .append(" a.rev_person, a.rev_phone, a.goods_amount, a.order_freight, a.plateform_discount, a.dealer_discount, a.order_id,\r\n")
                .append(" om.pay_time,dtl.freight,a.province,a.city,a.area_county,af.after_sell_order_id,af.order_type,af.sell_num as after_num,af.back_money\r\n")
                .append(" FROM t_scm_order_dealer a \r\n")
                .append(" LEFT OUTER JOIN t_scm_order_detail dtl ON dtl.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_after_sell af ON af.dealer_order_id = a.dealer_order_id\r\n")
                .append(" LEFT OUTER JOIN t_scm_order_main om ON a.order_id = om.order_id\r\n")
                .append(" WHERE a.dealer_id = ?  \r\n");

        params.add(dealerId);

        if (!StringUtils.isEmpty(condition)) {
            sql.append(" AND (dtl.goods_name LIKE CONCAT('%',?,'%') OR a.dealer_order_id LIKE CONCAT('%',?,'%') OR om.pay_no LIKE CONCAT('%',?,'%') OR\r\n");
            sql.append(" a.rev_phone LIKE CONCAT('%',?,'%'))\r\n");
            params.add(condition);
            params.add(condition);
            params.add(condition);
            params.add(condition);
        }

        if (orderStatus != null && orderStatus >= 0) {
            sql.append(" AND a._status=?\r\n");
            params.add(orderStatus);
        }
        if (hasInvoice != null && hasInvoice >= 0) {
            sql.append(" AND a.invoice_type = ?\r\n");
            params.add(hasInvoice);
        }

        if (hasComment != null && hasComment >= 0) {
            sql.append(" AND dtl.comment_status = ?\r\n");
            params.add(hasComment);
        }

        if (afterSellStatus != null && afterSellStatus >= 0) {
            sql.append(" AND af._status = ?\r\n");
            params.add(afterSellStatus);
        }

        if (orderType != null && orderType >= 0) {
            sql.append(" AND om.order_type=?\r\n");
            params.add(orderType);
        }

        if (payWay != null && payWay > 0) {
            sql.append(" AND om.pay_way=?\r\n");
            params.add(payWay);
        }
        if (!StringUtils.isEmpty(startTime) && !StringUtils.isEmpty(endTime)) {
            sql.append(" AND a.created_date BETWEEN ? AND ?\r\n");
            params.add(startTime);
            params.add(endTime);
        }
        sql.append(" ORDER BY a.dealer_order_id DESC, a.created_date DESC");

        List<Map<String, Object>> beanList = this.supportJdbcTemplate.jdbcTemplate().queryForList(sql.toString(), params.toArray());

        List<DealerOrderQB> rs = new ArrayList<DealerOrderQB>();

        String dealerOrderId = null;
        DealerOrderQB midBean = null;
        for (Map<String, Object> item : beanList) {

            String ordIdTemp = (String) item.get("dealer_order_id");
            if (!ordIdTemp.equals(dealerOrderId)) {
                midBean = new DealerOrderQB();
                midBean.setDealerId((String) item.get("dealer_id"));
                midBean.setDealerOrderId(ordIdTemp);
                Timestamp a = (Timestamp) item.get("created_date");
                midBean.setCreatedDate(a == null ? null : a.getTime());
                midBean.setOrderStatus((Integer) item.get("_status"));
                midBean.setPayNo((String) item.get("pay_no"));
                midBean.setRevPerson((String) item.get("rev_person"));
                midBean.setRevPhone((String) item.get("rev_phone"));
                midBean.setGoodsMoney((long) item.get("goods_amount"));
                midBean.setOrderFreight((long) item.get("order_freight"));
                midBean.setPlateDiscount((long) item.get("plateform_discount"));
                midBean.setDealerDiscount((long) item.get("dealer_discount"));
                midBean.setOrderId((String) item.get("order_id"));
                Timestamp payTime = (Timestamp) item.get("pay_time");
                midBean.setPayTime(payTime == null ? null : payTime.getTime());
                midBean.setRevAddress(new StringBuffer().append(item.get("province")).append(item.get("city")).append(item.get("area_county")).toString());
                midBean.setAfterMoney(null == item.get("back_money") ? 0l : (Long) item.get("back_money"));
                midBean.setAfterNum(null == item.get("after_num") ? null : (Integer) item.get("after_num"));
                midBean.setAfterOrderType(null == item.get("order_type") ? null : (Integer) item.get("order_type"));
                midBean.setAfterSellDealerOrderId(null == item.get("after_sell_order_id") ? null : (String) item.get("after_sell_order_id"));
                List<DealerGoodsBean> goodses = new ArrayList<>();
                midBean.setGoodsList(goodses);
                rs.add(midBean);
                dealerOrderId = ordIdTemp;
            }

            DealerGoodsBean dgb = new DealerGoodsBean();
            dgb.setSkuName((String) item.get("sku_name"));
            dgb.setGoodsName((String) item.get("goods_name"));
            dgb.setGoodsTitle((String) item.get("goods_title"));
            dgb.setSkuId((String) item.get("sku_id"));
            dgb.setDiscountPrice((long) item.get("discount_price"));
            dgb.setSellNum((Integer) item.get("sell_num"));
            dgb.setAfStatus((Integer) item.get("afStatus"));
            item.get("goods_icon").getClass().getTypeName();
            dgb.setGoodsImage((String) item.get("goods_icon"));

            midBean.getGoodsList().add(dgb);
        }
        return rs;
    }

}
