package cn.m2c.scm.application.goods.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;
import java.util.List;

/**
 * 商品审核
 */
public class GoodsApproveCommand extends AssertionConcern implements Serializable {
    /**
     * 商品审核id
     */
    private String approveId;

    /**
     * 商品id
     */
    private String goodsId;

    /**
     * 商家ID
     */
    private String dealerId;

    /**
     * 商家名称
     */
    private String dealerName;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品副标题
     */
    private String goodsSubTitle;

    /**
     * 商品分类id
     */
    private String goodsClassifyId;

    /**
     * 商品品牌id
     */
    private String goodsBrandId;

    /**
     * 商品计量单位id
     */
    private String goodsUnitId;

    /**
     * 最小起订量
     */
    private Integer goodsMinQuantity;

    /**
     * 运费模板id
     */
    private String goodsPostageId;

    /**
     * 商品条形码
     */
    private String goodsBarCode;

    /**
     * 关键词
     */
    private String goodsKeyWord;

    /**
     * 商品保障
     */
    private List goodsGuarantee;

    /**
     * 商品主图  存储类型是[“url1”,"url2"]
     */
    private List goodsMainImages;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 1:手动上架,2:审核通过立即上架
     */
    private Integer goodsShelves;

    /**
     * 商品規格
     */
    private String goodsSkuApproves;

    public GoodsApproveCommand(String approveId, String dealerId, String dealerName, String goodsName, String goodsSubTitle,
                               String goodsClassifyId, String goodsBrandId, String goodsUnitId, Integer goodsMinQuantity,
                               String goodsPostageId, String goodsBarCode, String goodsKeyWord, List goodsGuarantee,
                               List goodsMainImages, String goodsDesc, Integer goodsShelves, String goodsSkuApproves) {
        this.approveId = approveId;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.goodsName = goodsName;
        this.goodsSubTitle = goodsSubTitle;
        this.goodsClassifyId = goodsClassifyId;
        this.goodsBrandId = goodsBrandId;
        this.goodsUnitId = goodsUnitId;
        this.goodsMinQuantity = goodsMinQuantity;
        this.goodsPostageId = goodsPostageId;
        this.goodsBarCode = goodsBarCode;
        this.goodsKeyWord = goodsKeyWord;
        this.goodsGuarantee = goodsGuarantee;
        this.goodsMainImages = goodsMainImages;
        this.goodsDesc = goodsDesc;
        this.goodsShelves = goodsShelves;
        this.goodsSkuApproves = goodsSkuApproves;
    }

    public String getApproveId() {
        return approveId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public String getGoodsSubTitle() {
        return goodsSubTitle;
    }

    public String getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public String getGoodsBrandId() {
        return goodsBrandId;
    }

    public String getGoodsUnitId() {
        return goodsUnitId;
    }

    public Integer getGoodsMinQuantity() {
        return goodsMinQuantity;
    }

    public String getGoodsPostageId() {
        return goodsPostageId;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public String getGoodsKeyWord() {
        return goodsKeyWord;
    }

    public List getGoodsGuarantee() {
        return goodsGuarantee;
    }

    public List getGoodsMainImages() {
        return goodsMainImages;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public Integer getGoodsShelves() {
        return goodsShelves;
    }

    public String getGoodsSkuApproves() {
        return goodsSkuApproves;
    }
}
