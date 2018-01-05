package cn.m2c.scm.port.adapter.restful.admin.dealerreport;

import cn.m2c.common.MCode;
import cn.m2c.common.MResult;
import cn.m2c.scm.application.dealerreport.query.DealerReportQueryApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商家管理平台首页
 */
@RestController
@RequestMapping("/admin/dealer/report")
public class AdminDealerReportAgent {
    private static final Logger LOGGER = LoggerFactory.getLogger(AdminDealerReportAgent.class);

    @Autowired
    DealerReportQueryApplication dealerReportQueryApplication;

    /**
     * 商家销售榜
     *
     * @return
     */
    @RequestMapping(value = "/month/sell/top", method = RequestMethod.GET)
    public ResponseEntity<MResult> getDealerMonthSellTop() {
        MResult result = new MResult(MCode.V_1);

        return new ResponseEntity<MResult>(result, HttpStatus.OK);
    }
}
