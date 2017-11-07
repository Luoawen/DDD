package cn.m2c.scm.application.goods.query.data.export;

import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.utils.ExcelField;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 导出
 */
public class GoodsSupplyPriceModel {
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
    private String photographPrice;
    @ExcelField(title = "供货价/元")
    private String supplyPrice;
    @ExcelField(title = "商品库存")
    private Integer availableNum;
    @ExcelField(title = "商品销量")
    private Integer sellerNum;
    @ExcelField(title = "商品状态")
    private String goodsStatus;
    @ExcelField(title = "运费模板")
    private String goodsPostageName;


    public GoodsSupplyPriceModel(GoodsBean goodsBean, GoodsSkuBean goodsSkuBean, Map goodsClassifyMap, String goodsPostageName) {
        this.dealerName = goodsBean.getDealerName();
        this.goodsName = goodsBean.getGoodsName();
        this.goodsBarCode = goodsBean.getGoodsBarCode();
        if (null != goodsClassifyMap) {
            this.goodsClassify = null == goodsClassifyMap.get("name") ? "" : (String) goodsClassifyMap.get("name");
        }
        this.goodsBrandName = goodsBean.getGoodsBrandName();
        this.goodsCode = goodsSkuBean.getGoodsCode();
        this.goodsSkuId = goodsSkuBean.getSkuId();
        this.goodsSkuName = goodsSkuBean.getSkuName();
        DecimalFormat df = new DecimalFormat(".00");
        this.photographPrice = df.format(goodsSkuBean.getPhotographPrice().floatValue()/100);
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
        this.supplyPrice = df.format(goodsSkuBean.getSupplyPrice()/100);
    }
}
