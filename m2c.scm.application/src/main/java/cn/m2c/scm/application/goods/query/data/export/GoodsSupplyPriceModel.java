package cn.m2c.scm.application.goods.query.data.export;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.ExcelField;

/**
 * 导出
 */
public class GoodsSupplyPriceModel extends GoodsModel {
    @ExcelField(title = "供货价/元")
    private Long supplyPrice;


    public GoodsSupplyPriceModel(GoodsBean goodsBean, GoodsSkuBean goodsSkuBean, String goodsClassify, String goodsPostageName) {
        super(goodsBean, goodsSkuBean, goodsClassify, goodsPostageName);
        this.supplyPrice = goodsSkuBean.getSupplyPrice();
    }
}
