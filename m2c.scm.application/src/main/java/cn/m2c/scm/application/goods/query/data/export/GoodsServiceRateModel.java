package cn.m2c.scm.application.goods.query.data.export;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.ExcelField;

/**
 * 导出
 */
public class GoodsServiceRateModel extends GoodsModel {

    @ExcelField(title = "服务费率/%")
    private Float serviceRate;

    public GoodsServiceRateModel(GoodsBean goodsBean, GoodsSkuBean goodsSkuBean, String goodsClassify, Float serviceRate, String goodsPostageName) {
        super(goodsBean, goodsSkuBean, goodsClassify, goodsPostageName);
        this.serviceRate = serviceRate;
    }
}
