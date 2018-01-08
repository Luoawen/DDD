package cn.m2c.scm.port.adapter.restful.admin.order;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.scm.application.order.query.OrderQuery;
import cn.m2c.scm.application.shop.data.bean.ShopBean;
import cn.m2c.scm.application.shop.query.ShopQuery;
import cn.m2c.scm.application.utils.OrderUtils;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.service.order.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理平台订单
 */
@RestController
@RequestMapping("/admin/order")
public class AdminOrderAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminOrderAgent.class);

    @Autowired
    OrderQuery orderQuery;
    @Autowired
    OrderService orderService;
    @Autowired
    ShopQuery shopQuery;

    /**
     * 后台运营管理平台订单列表
     *
     * @param orderId
     * @param dealerOrderId
     * @param orderStatus
     * @param afterSellStatus
     * @param commentStatus
     * @param payStatus
     * @param payWay
     * @param goodsNameOrId
     * @param shopName
     * @param orderStartTime
     * @param orderEndTime
     * @param userName
     * @param mediaOrResId
     * @param pageNum
     * @param rows
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<MPager> getOrderList(
            @RequestParam(value = "orderId", required = false) String orderId, // 平台订单号
            @RequestParam(value = "dealerOrderId", required = false) String dealerOrderId, // 商家订单号
            @RequestParam(value = "orderStatus", required = false) Integer orderStatus, // 订单状态:0.待付款 1.待发货 2.待收货 3.已完成 4.交易完成 5.交易关闭 -1.已取消
            @RequestParam(value = "afterSellStatus", required = false) Integer afterSellStatus, // 订单售后状态:20.待商家同意 21.待顾客寄回商品 22.待商家确认退款 23.待商家发货 24.待顾客收货 25.售后已完成 26.售后已取消 27.商家已拒绝
            @RequestParam(value = "commentStatus", required = false) Integer commentStatus, // 评论状态:0.待评论 1.已评论
            @RequestParam(value = "payStatus", required = false) Integer payStatus, // 支付状态:0.未支付 1.已支付
            @RequestParam(value = "payWay", required = false) Integer payWay, // 支付方式:1.支付宝 2.微信
            @RequestParam(value = "goodsNameOrId", required = false) String goodsNameOrId, // 商品名称或编号
            @RequestParam(value = "shopName", required = false) String shopName, // 店铺名称
            @RequestParam(value = "orderStartTime", required = false) String orderStartTime, // 订单下单开始时间
            @RequestParam(value = "orderEndTime", required = false) String orderEndTime, // 订单下单结束时间
            @RequestParam(value = "userName", required = false) String userName, // 下单用户名或账号，精准匹配
            @RequestParam(value = "mediaOrResId", required = false) String mediaOrResId, // 媒体ID或广告位ID，精准匹配
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum, // 第几页
            @RequestParam(value = "rows", required = false, defaultValue = "5") Integer rows // 每页多少行
    ) {
        MPager result = new MPager(MCode.V_1);
        try {
            Integer total = orderQuery.getAdminDealerOrderTotal(orderId, dealerOrderId, orderStatus, afterSellStatus, commentStatus,
                    payStatus, payWay, goodsNameOrId, shopName, orderStartTime, orderEndTime,
                    userName, mediaOrResId);
            if (total > 0) {
                List<Map<String, Object>> list = orderQuery.getAdminDealerOrderList(orderId, dealerOrderId, orderStatus, afterSellStatus, commentStatus,
                        payStatus, payWay, goodsNameOrId, shopName, orderStartTime, orderEndTime,
                        userName, mediaOrResId, pageNum, rows);
                if (null != list && list.size() > 0) {
                    List<Map> resultList = new ArrayList<>();
                    for (Map<String, Object> map : list) {
                        Map resultMap = new HashMap<>();
                        // 订单号
                        resultMap.put("orderId", map.get("orderId"));
                        // 商家订单号
                        resultMap.put("dealerOrderId", map.get("dealerOrderId"));
                        // 订单状态
                        resultMap.put("orderStatus", map.get("orderStatus"));
                        resultMap.put("orderStatusStr", OrderUtils.getStatusStr(Integer.parseInt(map.get("orderStatus").toString())));

                        Long dealerAmount = null == map.get("dealerAmount") ? 0 : Long.parseLong(map.get("dealerAmount").toString());
                        Long platformDiscount = null == map.get("platformDiscount") ? 0 : Long.parseLong(map.get("platformDiscount").toString());
                        Long dealerDiscount = null == map.get("dealerDiscount") ? 0 : Long.parseLong(map.get("dealerDiscount").toString());
                        Long orderFreight = null == map.get("orderFreight") ? 0 : Long.parseLong(map.get("orderFreight").toString());
                        Long orderMoney = dealerAmount + orderFreight - platformDiscount - dealerDiscount;
                        // 订单总额
                        resultMap.put("orderMoney", Utils.moneyFormatCN(orderMoney));
                        // 下单用户
                        resultMap.put("userName", orderService.getUserMobileByUserId(map.get("userId").toString()));
                        // 支付状态
                        resultMap.put("payStatusStr", OrderUtils.getPayStatusStr(Integer.parseInt(map.get("orderStatus").toString())));
                        // 店铺名称
                        ShopBean shop = shopQuery.getShop(map.get("dealerId").toString());
                        resultMap.put("shopName", null != shop ? shop.getShopName() : null);
                        // 下单时间
                        Date date = (Date) map.get("created_date");
                        resultMap.put("createTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                        resultList.add(resultMap);
                    }
                    result.setContent(resultList);
                }
            }
            result.setPager(total, pageNum, rows);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {

        }
        return new ResponseEntity<MPager>(result, HttpStatus.OK);
    }
}
