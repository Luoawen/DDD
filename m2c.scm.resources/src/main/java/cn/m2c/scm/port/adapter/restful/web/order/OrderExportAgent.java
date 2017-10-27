package cn.m2c.scm.port.adapter.restful.web.order;

import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.dealerorder.query.DealerOrderAfterSellQuery;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.export.GoodsServiceRateModel;
import cn.m2c.scm.application.goods.query.data.export.GoodsSupplyPriceModel;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.data.bean.AfterSellOrderBean;
import cn.m2c.scm.application.order.data.export.SaleAfterExpModel;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.application.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 订单包括售后单 导出
 * @author 897766
 */
@RestController
@RequestMapping("/order/export")
public class OrderExportAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(OrderExportAgent.class);

    @Autowired
    SaleAfterOrderApp saleAfterApp;
    @Autowired
    OrderApplication orderApp;
    @Autowired
    DealerOrderAfterSellQuery saleAfterQuery;

    @RequestMapping(value = "/saleafter", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value="dealerId", required = false) String dealerId, 
                            @RequestParam(value="status", required = false) Integer status, 
                            @RequestParam(value="orderType", required = false) Integer orderType
                            ,@RequestParam(value="condition", required = false) String condition 
                            ,@RequestParam(value="startTime", required = false) String startTime
                            ,@RequestParam(value = "mediaInfo", required = false)String mediaInfo
                            ,@RequestParam(value="endTime", required = false) String endTime) throws Exception {
        List<SaleAfterExpModel> goodsBeanList = saleAfterQuery.orderSaleAfterExportQuery(orderType, dealerId, status,
                condition, startTime, endTime, mediaInfo);
        if (null != goodsBeanList && goodsBeanList.size() > 0) {
            String fileName = "售后单.xls";
            ExcelUtil.writeExcel(response, fileName, goodsBeanList, SaleAfterExpModel.class);
        }
    }
}
