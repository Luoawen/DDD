package cn.m2c.scm.application.goods.query.data.export;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.ExcelField;

/**
 * 导出
 */
public class GoodsModel {
    @ExcelField(title = "商家名称")
    private String dealerName;
    @ExcelField(title = "商品名称")
    private String goodsName;
    @ExcelField(title = "商品条形码")
    private String goodsBarCode;
    @ExcelField(title = "商品分类")
    private String goodsClassify;
    @ExcelField(title = "商品品牌")
    private String goodsBrandName;
    @ExcelField(title = "商家SKU")
    private String goodsCode;
    @ExcelField(title = "平台SKU")
    private String goodsSkuId;
    @ExcelField(title = "规格")
    private String goodsSkuName;
    @ExcelField(title = "拍获价/元")
    private Long photographPrice;
    @ExcelField(title = "商品库存")
    private Integer availableNum;
    @ExcelField(title = "商品销量")
    private Integer sellerNum;
    @ExcelField(title = "商品状态")
    private Integer goodsStatus;
    @ExcelField(title = "运费模板")
    private String goodsPostageName;

    public GoodsModel(GoodsBean goodsBean, GoodsSkuBean goodsSkuBean, String goodsClassify, String goodsPostageName) {
        this.dealerName = goodsBean.getDealerName();
        this.goodsName = goodsBean.getGoodsName();
        this.goodsBarCode = goodsBean.getGoodsBarCode();
        this.goodsClassify = goodsClassify;
        this.goodsBrandName = goodsBean.getGoodsBrandName();
        this.goodsCode = goodsSkuBean.getGoodsCode();
        this.goodsSkuId = goodsSkuBean.getSkuId();
        this.goodsSkuName = goodsSkuBean.getSkuName();
        this.photographPrice = goodsSkuBean.getPhotographPrice();
        this.availableNum = goodsSkuBean.getAvailableNum();
        this.sellerNum = goodsSkuBean.getSellerNum();
        this.goodsStatus = goodsBean.getGoodsStatus();
        this.goodsPostageName = goodsPostageName;
    }
}
