package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.ddd.common.auth.RequirePermissions;
import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.export.GoodsModel;
import cn.m2c.scm.application.goods.query.data.export.GoodsModelAll;
import cn.m2c.scm.application.goods.query.data.export.GoodsServiceRateModel;
import cn.m2c.scm.application.goods.query.data.export.GoodsServiceRateModelAll;
import cn.m2c.scm.application.goods.query.data.export.GoodsSupplyPriceModel;
import cn.m2c.scm.application.goods.query.data.export.GoodsSupplyPriceModelAll;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.application.utils.ExcelUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 商品导出
 *
 * @author ps
 */
@RestController
public class GoodsExportAgent {

    private final static Logger LOGGER = LoggerFactory.getLogger(GoodsExportAgent.class);

    @Autowired
    GoodsQueryApplication goodsQueryApplication;
    @Autowired
    GoodsClassifyQueryApplication goodsClassifyQueryApplication;
    @Autowired
    DealerQuery dealerQuery;
    @Autowired
    PostageModelQueryApplication postageModelQueryApplication;

    @RequestMapping(value = {"/web/goods/export", "/admin/goods/export"}, method = RequestMethod.GET)
    @RequirePermissions(value = {"scm:goodsStorage:export"})
    public void exportExcel(HttpServletResponse response,
                            String dealerId, String goodsClassifyId, Integer goodsStatus,
                            String condition, String startTime, String endTime, Integer recognizedStatus) throws Exception {
        String fileName = "商品库.xls";
        List<GoodsBean> goodsBeanList = goodsQueryApplication.searchGoodsExport(dealerId, goodsClassifyId, goodsStatus,
                condition, startTime, endTime, recognizedStatus);
        Integer settlementMode = null;
        if (StringUtils.isNotEmpty(dealerId)) {
            //结算模式 1：按供货价 2：按服务费率
            settlementMode = dealerQuery.getDealerCountMode(dealerId);
        }
        if (StringUtils.isNotEmpty(dealerId)) {//商家平台导出，没有商家名和平台sku
            if (null != goodsBeanList && goodsBeanList.size() > 0) {
                List<GoodsServiceRateModel> goodsServiceRateModels = new ArrayList<>();
                List<GoodsSupplyPriceModel> goodsSupplyPriceModels = new ArrayList<>();
                List<GoodsModel> goodsModels = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeanList) {
                    if (StringUtils.isEmpty(dealerId)) {
                        //结算模式 1：按供货价 2：按服务费率
                        settlementMode = dealerQuery.getDealerCountMode(goodsBean.getDealerId());
                    }
                    List<GoodsSkuBean> goodsSkuBeanList = goodsBean.getGoodsSkuBeans();
                    Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(goodsBean.getGoodsClassifyId());
                    Float serviceRate = null;
                    if (settlementMode == 2) {
                        serviceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                    }
                    String goodsPostageName = postageModelQueryApplication.getPostageModelNameByModelId(goodsBean.getGoodsPostageId());
                    for (GoodsSkuBean goodsSkuBean : goodsSkuBeanList) {
                        if (StringUtils.isNotEmpty(dealerId)) {
                            if (settlementMode == 2) {
                                GoodsServiceRateModel goodsServiceRateModel = new GoodsServiceRateModel(goodsBean, goodsSkuBean, goodsClassifyMap,
                                        serviceRate, goodsPostageName);
                                goodsServiceRateModels.add(goodsServiceRateModel);
                            } else {
                                GoodsSupplyPriceModel goodsSupplyPriceModel = new GoodsSupplyPriceModel(goodsBean, goodsSkuBean, goodsClassifyMap,
                                        goodsPostageName);
                                goodsSupplyPriceModels.add(goodsSupplyPriceModel);
                            }
                        } else {
                            GoodsModel goodsModel = new GoodsModel(goodsBean, goodsSkuBean, goodsClassifyMap, serviceRate,
                                    goodsPostageName, settlementMode);
                            goodsModels.add(goodsModel);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(dealerId)) {
                    if (settlementMode == 2) {
                        ExcelUtil.writeExcel(response, fileName, goodsServiceRateModels, GoodsServiceRateModel.class);
                    } else {
                        ExcelUtil.writeExcel(response, fileName, goodsSupplyPriceModels, GoodsSupplyPriceModel.class);
                    }
                } else {
                    ExcelUtil.writeExcel(response, fileName, goodsModels, GoodsModel.class);
                }
            } else {
                ExcelUtil.writeExcel(response, fileName, null, GoodsModel.class);
            }
        } else {//管理平台导出
            if (null != goodsBeanList && goodsBeanList.size() > 0) {
                List<GoodsServiceRateModelAll> goodsServiceRateModels = new ArrayList<>();
                List<GoodsSupplyPriceModelAll> goodsSupplyPriceModels = new ArrayList<>();
                List<GoodsModelAll> goodsModels = new ArrayList<>();
                for (GoodsBean goodsBean : goodsBeanList) {
                    if (StringUtils.isEmpty(dealerId)) {
                        //结算模式 1：按供货价 2：按服务费率
                        settlementMode = dealerQuery.getDealerCountMode(goodsBean.getDealerId());
                    }
                    List<GoodsSkuBean> goodsSkuBeanList = goodsBean.getGoodsSkuBeans();
                    Map goodsClassifyMap = goodsClassifyQueryApplication.getClassifyMap(goodsBean.getGoodsClassifyId());
                    Float serviceRate = null;
                    if (settlementMode == 2) {
                        serviceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                    }
                    String goodsPostageName = postageModelQueryApplication.getPostageModelNameByModelId(goodsBean.getGoodsPostageId());
                    for (GoodsSkuBean goodsSkuBean : goodsSkuBeanList) {
                        if (StringUtils.isNotEmpty(dealerId)) {
                            if (settlementMode == 2) {
                                GoodsServiceRateModelAll goodsServiceRateModel = new GoodsServiceRateModelAll(goodsBean, goodsSkuBean, goodsClassifyMap,
                                        serviceRate, goodsPostageName);
                                goodsServiceRateModels.add(goodsServiceRateModel);
                            } else {
                                GoodsSupplyPriceModelAll goodsSupplyPriceModel = new GoodsSupplyPriceModelAll(goodsBean, goodsSkuBean, goodsClassifyMap,
                                        goodsPostageName);
                                goodsSupplyPriceModels.add(goodsSupplyPriceModel);
                            }
                        } else {
                            GoodsModelAll goodsModel = new GoodsModelAll(goodsBean, goodsSkuBean, goodsClassifyMap, serviceRate,
                                    goodsPostageName, settlementMode);
                            goodsModels.add(goodsModel);
                        }
                    }
                }
                if (StringUtils.isNotEmpty(dealerId)) {
                    if (settlementMode == 2) {
                        ExcelUtil.writeExcel(response, fileName, goodsServiceRateModels, GoodsServiceRateModelAll.class);
                    } else {
                        ExcelUtil.writeExcel(response, fileName, goodsSupplyPriceModels, GoodsSupplyPriceModelAll.class);
                    }
                } else {
                    ExcelUtil.writeExcel(response, fileName, goodsModels, GoodsModelAll.class);
                }
            } else {
                ExcelUtil.writeExcel(response, fileName, null, GoodsModelAll.class);
            }
        }

    }
}
