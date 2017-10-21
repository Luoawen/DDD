package cn.m2c.scm.application.goods.query.data.export;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.ExcelField;

/**
 * 导出
 */
public class GoodsServiceRateModel {
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
    @ExcelField(title = "服务费率/%")
    private Float serviceRate;
    @ExcelField(title = "商品库存")
    private Integer availableNum;
    @ExcelField(title = "商品销量")
    private Integer sellerNum;
    @ExcelField(title = "商品状态")
    private String goodsStatus;
    @ExcelField(title = "运费模板")
    private String goodsPostageName;

    public GoodsServiceRateModel(GoodsBean goodsBean, GoodsSkuBean goodsSkuBean, String goodsClassify, Float serviceRate, String goodsPostageName) {
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
        //商品状态，1：仓库中，2：出售中，3：已售罄
        if (goodsBean.getGoodsStatus() == 1) {
            this.goodsStatus = "仓库中";
        } else if (goodsBean.getGoodsStatus() == 2) {
            this.goodsStatus = "出售中";
        } else {
            this.goodsStatus = "已售罄";
        }
        this.goodsPostageName = goodsPostageName;
        this.serviceRate = serviceRate;
    }
}
