package cn.m2c.scm.port.adapter.restful.web.dealerreport;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.dealerreport.data.bean.DealerDayReportBean;
import cn.m2c.scm.application.dealerreport.data.bean.representation.DealerNearlyReportRepresentation;
import cn.m2c.scm.application.dealerreport.query.DealerReportQueryApplication;
import cn.m2c.scm.application.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商家首页
 */
@RestController
@RequestMapping("/web/dealer/report")
public class DealerReportAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(DealerReportAgent.class);

    @Autowired
    DealerReportQueryApplication dealerReportQueryApplication;

    /**
     * 获取某一天的数据概要
     *
     * @param dealerId 商家id
     * @param timeType 1：今天，2：昨天
     * @return
     */
    @RequestMapping(value = "/day", method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerDayReport(
            @RequestParam(value = "dealerId", required = false) String dealerId,
            @RequestParam(value = "timeType", required = false) Integer timeType) {
        MResult result = new MResult(MCode.V_1);
        try {
            List<DealerDayReportBean> list = dealerReportQueryApplication.getDealerReportByTimeType(dealerId, timeType);
            result.setContent(getDealerDayReportData(list));
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getDealerDayReport Exception e:", e);
            result = new MResult(MCode.V_400, "获取商家某一天的数据概要失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 获取本月累计销售金额
     *
     * @param dealerId 商家id
     * @return
     */
    @RequestMapping(value = "/this/month/sell/money", method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerThisMonthSellMoney(
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        try {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMM");
            String month = df.format(cal.getTime());
            Integer startDay = Integer.parseInt(month + "01");
            Integer endDay = Integer.parseInt(month + cal.getActualMaximum(Calendar.DATE));
            List<DealerDayReportBean> list = dealerReportQueryApplication.getDealerReportByDaySection(dealerId, startDay, endDay);
            Map map = getDealerDayReportData(list);
            result.setContent(map.get("sellMoney"));
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getDealerThisMonthSellMoney Exception e:", e);
            result = new MResult(MCode.V_400, "获取本月累计销售金额失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    /**
     * 获取近7日销售金额统计
     *
     * @param dealerId 商家id
     * @return
     */
    @RequestMapping(value = "/7/days/sell/money", method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerNearly7DaysReport(
            @RequestParam(value = "dealerId", required = false) String dealerId) {
        MResult result = new MResult(MCode.V_1);
        try {
            // 查询前第八天销量算环比
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -7);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Integer eightDay = Integer.parseInt(df.format(cal.getTime()));
            List<DealerDayReportBean> eightList = dealerReportQueryApplication.getDealerReportByDay(dealerId, eightDay);
            Map eightMap = getDealerDayReportData(eightList);
            Long eightSellMoney = new BigDecimal(Float.parseFloat(eightMap.get("sellMoney").toString()) * 10000).longValue();

            List<Integer> days = nearly7DaysTime();
            List<DealerDayReportBean> list = dealerReportQueryApplication.getDealerReportByDaySection(dealerId, days.get(0), days.get(days.size() - 1));
            List<DealerNearlyReportRepresentation> resultList = new ArrayList<>();
            Map<Integer, Long> tempMap = new HashMap<>();
            for (int i = 0; i < days.size(); i++) {
                Date date = new SimpleDateFormat("yyyyMMdd").parse(String.valueOf(days.get(i)));
                String time = new SimpleDateFormat("MM.dd").format(date);
                Long sellMoney = 0L;
                if (null != list && list.size() > 0) {
                    for (DealerDayReportBean bean : list) {
                        if (days.get(i).equals(bean.getDay())) {
                            sellMoney = sellMoney + bean.getSellMoney();
                        }
                    }
                }

                /**
                 * 环比=[（本次金额-上次金额）/ 上次金额  ] * 100% ，环比为与前天对比的数据
                 本次比上次有增长趋势，环比为正，数据展示+12.12%,
                 否则，环比为负，数据展示-12.12%
                 无变化，环比为0%
                 */
                Integer ratio = null;
                Integer ratioFlag = 1; // 0:没有环比，1:有环比
                if (i == 0) {
                    if (eightSellMoney != 0) {
                        ratio = getRatio(sellMoney, eightSellMoney);
                    } else {
                        ratioFlag = 0;
                    }
                } else {
                    // 前一天金额
                    Long preDayMoney = tempMap.get(days.get(i - 1));
                    if (preDayMoney != 0) {
                        ratio = getRatio(sellMoney, preDayMoney);
                    } else {
                        ratioFlag = 0;
                    }
                }

                tempMap.put(days.get(i), sellMoney);
                DealerNearlyReportRepresentation rep = new DealerNearlyReportRepresentation(time, ratioFlag, ratio, sellMoney);
                resultList.add(rep);
            }
            result.setContent(resultList);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getDealerNearly7DaysReport Exception e:", e);
            result = new MResult(MCode.V_400, "获取近7日销售金额失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }

    private Integer getRatio(Long sellMoney, Long preSellMoney) {
        BigDecimal sellMoneyDecimal = new BigDecimal(sellMoney);
        BigDecimal preSellMoneyDecimal = new BigDecimal(preSellMoney);
        BigDecimal ratioDec = ((sellMoneyDecimal.subtract(preSellMoneyDecimal)).divide(preSellMoneyDecimal, 2, BigDecimal.ROUND_HALF_UP)).multiply(new BigDecimal(100));
        return ratioDec.intValue();
    }

    private Map getDealerDayReportData(List<DealerDayReportBean> list) {
        Map map = new HashMap<>();
        Integer orderNum = 0;
        Integer orderRefundNum = 0;
        Integer goodsAddNum = 0;
        Long sellMoney = 0L;
        Long refundMoney = 0L;
        Integer goodsCommentNum = 0;
        if (null != list && list.size() > 0) {
            for (DealerDayReportBean bean : list) {
                orderNum = orderNum + bean.getOrderNum();
                orderRefundNum = orderRefundNum + bean.getOrderRefundNum();
                goodsAddNum = goodsAddNum + bean.getGoodsAddNum();
                sellMoney = sellMoney + bean.getSellMoney();
                refundMoney = refundMoney + bean.getRefundMoney();
                goodsCommentNum = goodsCommentNum + bean.getGoodsCommentNum();
            }
        }
        map.put("orderNum", orderNum);
        map.put("orderRefundNum", orderRefundNum);
        map.put("goodsAddNum", goodsAddNum);
        map.put("sellMoney", Utils.moneyFormatCN(sellMoney));
        map.put("refundMoney", Utils.moneyFormatCN(refundMoney));
        map.put("goodsCommentNum", goodsCommentNum);
        return map;
    }

    private List<Integer> nearly7DaysTime() {
        List<Integer> list = new ArrayList<>();
        for (int i = -6; i < 1; i++) {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, i);
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
            Integer day = Integer.parseInt(df.format(cal.getTime()));
            list.add(day);
        }
        return list;
    }
}
