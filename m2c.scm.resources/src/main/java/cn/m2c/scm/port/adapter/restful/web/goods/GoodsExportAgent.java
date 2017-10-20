package cn.m2c.scm.port.adapter.restful.web.goods;

import cn.m2c.scm.application.classify.query.GoodsClassifyQueryApplication;
import cn.m2c.scm.application.dealer.query.DealerQuery;
import cn.m2c.scm.application.goods.query.GoodsQueryApplication;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.export.GoodsModel;
import cn.m2c.scm.application.postage.query.PostageModelQueryApplication;
import cn.m2c.scm.application.utils.ExcelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品导出
 *
 * @author ps
 */
@RestController
@RequestMapping("/goods")
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

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response,
                            String dealerId, String goodsClassifyId, Integer goodsStatus,
                            String condition, String startTime, String endTime) throws Exception {
        List<GoodsBean> goodsBeanList = goodsQueryApplication.searchGoodsExport(dealerId, goodsClassifyId, goodsStatus,
                condition, startTime, endTime);
        if (null != goodsBeanList && goodsBeanList.size() > 0) {
            List<GoodsModel> goodsModels = new ArrayList<>();
            for (GoodsBean goodsBean : goodsBeanList) {
                List<GoodsSkuBean> goodsSkuBeanList = goodsBean.getGoodsSkuBeans();
                String goodsClassify = goodsClassifyQueryApplication.getClassifyNames(goodsBean.getGoodsClassifyId());
                //结算模式 1：按供货价 2：按服务费率
                Integer settlementMode = dealerQuery.getDealerCountMode(goodsBean.getDealerId());
                Float serviceRate = null;
                if (settlementMode == 2) {
                    serviceRate = goodsClassifyQueryApplication.queryServiceRateByClassifyId(goodsBean.getGoodsClassifyId());
                }
                String goodsPostageName = postageModelQueryApplication.getPostageModelNameByModelId(goodsBean.getGoodsPostageId());
                for (GoodsSkuBean goodsSkuBean : goodsSkuBeanList) {
                    GoodsModel goodsModel = new GoodsModel(goodsBean, goodsSkuBean, goodsClassify,
                            serviceRate, goodsPostageName);
                    goodsModels.add(goodsModel);
                }
                String fileName = "商品库.xls";
                ExcelUtil.writeExcel(response, fileName, goodsModels, GoodsModel.class);
            }
        }

    }
}
