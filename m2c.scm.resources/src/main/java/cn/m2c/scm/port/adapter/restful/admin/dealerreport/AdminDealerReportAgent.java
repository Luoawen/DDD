package cn.m2c.scm.port.adapter.restful.admin.dealerreport;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.dealer.data.bean.DealerBean;
import cn.m2c.scm.application.dealer.query.DealerQuery;
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

import java.util.List;
import java.util.Map;

/**
 * 商家管理平台首页
 */
@RestController
@RequestMapping("/admin/dealer/report")
public class AdminDealerReportAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDealerReportAgent.class);

    @Autowired
    DealerReportQueryApplication dealerReportQueryApplication;
    @Autowired
    DealerQuery dealerQuery;

    /**
     * 商家销售榜
     *
     * @return
     */
    @RequestMapping(value = "/month/sell/top", method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerMonthSellTop(
            @RequestParam(value = "month", required = false) String month // 格式：yyyyMM
    ) {
        MResult result = new MResult(MCode.V_1);
        try {
            Integer startTime = Integer.parseInt(month + "01");
            Integer endTime = Integer.parseInt(month + "31");
            List<Map<String, Object>> list = dealerReportQueryApplication.getDealerMonthSellTop(startTime, endTime);
            if (null != list && list.size() > 0) {
                for (Map<String, Object> map : list) {
                    DealerBean dealerBean = dealerQuery.getDealer(map.get("dealerId").toString());
                    map.put("money", Utils.moneyFormatCN(Long.parseLong(map.get("money").toString())));
                    map.put("dealerName", null != dealerBean ? dealerBean.getDealerName() : null);
                }
            }
            result.setContent(list);
            result.setStatus(MCode.V_200);
        } catch (Exception e) {
            LOGGER.error("getDealerMonthSellTop Exception e:", e);
            result = new MResult(MCode.V_400, "获取商家销售榜失败");
        }
        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
