package cn.m2c.scm.port.adapter.restful.web.order;

import cn.m2c.common.MCode;
import cn.m2c.common.MPager;
import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.dealerorder.data.bean.DealerGoodsBean;
import cn.m2c.scm.application.dealerorder.data.bean.DealerOrderQB;
import cn.m2c.scm.application.dealerorder.data.bean.OrderDtlBean;
import cn.m2c.scm.application.dealerorder.query.DealerOrderAfterSellQuery;
import cn.m2c.scm.application.dealerorder.query.DealerOrderQuery;
import cn.m2c.scm.application.order.OrderApplication;
import cn.m2c.scm.application.order.SaleAfterOrderApp;
import cn.m2c.scm.application.order.data.bean.ImportFailedOrderBean;
import cn.m2c.scm.application.order.data.bean.OrderExpressBean;
import cn.m2c.scm.application.order.data.export.DealerOrderExpModel;
import cn.m2c.scm.application.order.data.export.MngOrderExpModel;
import cn.m2c.scm.application.order.data.export.SaleAfterExpModel;
import cn.m2c.scm.application.order.query.OrderQueryApplication;
import cn.m2c.scm.application.utils.ExcelUtil;
import cn.m2c.scm.domain.NegativeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 订单包括售后单 导出
 *
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
    @Autowired
    DealerOrderQuery dealerOrderQuery;
    @Autowired
    OrderQueryApplication orderAppQuery;

    /**
     * 售后单导出
     * @param response
     * @param dealerId
     * @param status
     * @param orderType
     * @param condition
     * @param startTime
     * @param mediaInfo
     * @param endTime
     * @throws Exception
     */
    @RequestMapping(value = "/saleafter", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response,
                            @RequestParam(value = "dealerId", required = false) String dealerId,
                            @RequestParam(value = "status", required = false) Integer status,
                            @RequestParam(value = "orderType", required = false) Integer orderType
            , @RequestParam(value = "condition", required = false) String condition
            , @RequestParam(value = "startTime", required = false) String startTime
            , @RequestParam(value = "mediaInfo", required = false) String mediaInfo
            , @RequestParam(value = "endTime", required = false) String endTime) throws Exception {
        List<SaleAfterExpModel> goodsBeanList = saleAfterQuery.orderSaleAfterExportQuery(orderType, dealerId, status,
                condition, startTime, endTime, mediaInfo);
        String fileName = "售后单.xls";
        if (null != goodsBeanList && goodsBeanList.size() > 0) {
            ExcelUtil.writeExcel(response, fileName, goodsBeanList, SaleAfterExpModel.class);
        }else {
        	ExcelUtil.writeExcel(response, fileName, null, SaleAfterExpModel.class);
        }
    }

    /**
     * 订货单导出
     * @param response
     * @param dealerId
     * @param dealerOrderId
     * @param orderStatus
     * @param afterSellStatus
     * @param startTime
     * @param endTime
     * @param condition
     * @param payWay
     * @param commentStatus
     * @param hasMedia
     * @param orderClassify
     * @param invoice
     */
    @RequestMapping(value = {"/dealerorderlist", "/web/dealerorderlist"}, method = RequestMethod.GET)
    public void getDealerOrderList(HttpServletResponse response,
                                   @RequestParam(value = "dealerId", required = false) String dealerId,
                                   @RequestParam(value = "dealerOrderId", required = false) String dealerOrderId,
                                   @RequestParam(value = "orderStatus", required = false) Integer orderStatus,
                                   @RequestParam(value = "afterSellStatus", required = false) Integer afterSellStatus,
                                   @RequestParam(value = "startTime", required = false) String startTime,
                                   @RequestParam(value = "endTime", required = false) String endTime,
                                   @RequestParam(value = "condition", required = false) String condition,
                                   @RequestParam(value = "payWay", required = false) Integer payWay,
                                   @RequestParam(value = "commentStatus", required = false) Integer commentStatus,
                                   @RequestParam(value = "hasMedia", required = false) Integer hasMedia,
                                   @RequestParam(value = "orderClassify", required = false) Integer orderClassify,
                                   @RequestParam(value = "invoice", required = false) Integer invoice) {
        //DealerOrderQB 里面所有金额都除了100, 所以再导出时只需再除100即可！！！
    	List<DealerOrderQB> dealerOrderList = dealerOrderQuery.dealerOrderQueryExport(dealerId,
                orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, orderClassify,
                hasMedia, invoice);
        String fileName = "订货单.xls";
        if (null != dealerOrderList && dealerOrderList.size() > 0) {
            List<DealerOrderExpModel> list = new ArrayList<>();
            for (DealerOrderQB dealerOrderQB : dealerOrderList) {
                List<DealerGoodsBean> goodsBeans = dealerOrderQB.getGoodsList();
                if (null != goodsBeans && goodsBeans.size() > 0) {
                    for (DealerGoodsBean goodsBean : goodsBeans) {
                        list.add(new DealerOrderExpModel(goodsBean, dealerOrderQB));
                    }
                }
            }
            
            if (null != list && list.size() > 0) {
            	try {
            		ExcelUtil.writeExcel(response, fileName, list, DealerOrderExpModel.class);
            	 } catch (IOException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
            }
        }else {
        	try {
        		ExcelUtil.writeExcel(response, fileName, null, DealerOrderExpModel.class);
        	} catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 管理平台订货单详情导出
     * @param response
     * @param orderStatus
     * @param afterSellStatus
     * @param startTime
     * @param orderId
     * @param endTime
     * @param condition
     * @param payWay
     * @param commentStatus
     * @param hasMedia
     */
    @RequestMapping(value = "/admin/all/orderDtl", method = RequestMethod.GET)
    @RequirePermissions(value = {"scm:order:export"})
    public void getAllOrderDtl(HttpServletResponse response,
    		@RequestParam(value = "orderStatus",required = false)Integer orderStatus,
			@RequestParam(value = "afterSellStatus",required = false)Integer afterSellStatus,
			@RequestParam(value = "startTime",required = false)String startTime,
			@RequestParam(value = "endTime",required = false)String endTime,
			@RequestParam(value = "condition",required = false)String condition,
			@RequestParam(value = "payWay",required = false)Integer payWay,
			@RequestParam(value = "commentStatus", required = false) Integer commentStatus,
			@RequestParam(value = "mediaInfo", required = false) Integer hasMedia
            ) {
        List<OrderDtlBean> dealerOrderList = dealerOrderQuery.mngOrderQueryExport(orderStatus, afterSellStatus, startTime, endTime, condition, payWay, commentStatus, 
                hasMedia);
        String fileName = "订货单详情.xls";
        if (null != dealerOrderList && dealerOrderList.size() > 0) {
            List<MngOrderExpModel> list = new ArrayList<>();
            for (OrderDtlBean dtl : dealerOrderList) {
                list.add(new MngOrderExpModel(dtl));
            }
            
            if (null != list && list.size() > 0) {
            	try {
            		ExcelUtil.writeExcel(response, fileName, list, MngOrderExpModel.class);
            	 } catch (IOException e) {
                     e.printStackTrace();
                 } catch (IllegalAccessException e) {
                     e.printStackTrace();
                 }
            }
        }
        else {
        	try {
        		ExcelUtil.writeExcel(response, fileName, null, MngOrderExpModel.class);
        	} catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 导出批量发货模板
     * @param response
     * @param dealerId
     */
    @RequestMapping(value = {"/web/outputmodel","/outputmodel"},method = RequestMethod.GET)
    public void outPutModel(
    		HttpServletResponse response,
    		@RequestParam(value = "dealerId", required = false) String dealerId) {
    	
    	try {
			List<DealerOrderQB> dealerOrderList = dealerOrderQuery.dealerOrderQuery1(dealerId,
			        1, null, null, null, null, null, null, null,
			        null, null, null, null);
			List<OrderExpressBean> allExpress = orderAppQuery.getAllExpress();
			orderApp.exportShipModel(response,allExpress,dealerOrderList);
		} catch (NegativeException e) {
			LOGGER.error("导出批量发货模板出错" + e.getMessage(), e);
		}
    }
    
    /**
     * 导出批量发货模板
     * @param response
     * @param dealerId
     */
    @RequestMapping(value = {"/web/failmodel","/failmodel"},method = RequestMethod.GET)
    public void outputFailedModel(
    		HttpServletResponse response,
    		@RequestParam(value = "expressFlag", required = false) String expressFlag) {
    	try {
			List<ImportFailedOrderBean> orderModelInfo = dealerOrderQuery.getImportOrderModelInfo(expressFlag);
			
			orderApp.createListBox(response, null, null, orderModelInfo);
		} catch (NegativeException e) {
			LOGGER.error("导出批量发货失败数据出错" + e.getMessage(), e);
		}
    }
}
