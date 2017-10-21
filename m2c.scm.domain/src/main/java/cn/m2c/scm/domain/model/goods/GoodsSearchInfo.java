package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ValueObject;

import java.util.Date;

/**
 * 商品搜索条件信息
 */
public class GoodsSearchInfo extends ValueObject {
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
     * 商品品牌名称
     */
    private String goodsBrandName;

    /**
     * 商品条形码
     */
    private String goodsBarCode;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 关键词
     */
    private String goodsKeyWord;

    /**
     * 商品状态，1：仓库中，2：出售中，3：已售罄
     */
    private Integer goodsStatus;

    /**
     * 商品的创建时间
     */
    private Date goodsCreatedDate;

    public GoodsSearchInfo() {
        super();
    }

    public GoodsSearchInfo(String dealerId, String dealerName, String goodsName, String goodsSubTitle, String goodsClassifyId,
                           String goodsBrandId, String goodsBrandName, String goodsBarCode, String goodsDesc, String goodsKeyWord,
                           Integer goodsStatus, Date goodsCreatedDate) {
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.goodsName = goodsName;
        this.goodsSubTitle = goodsSubTitle;
        this.goodsClassifyId = goodsClassifyId;
        this.goodsBrandId = goodsBrandId;
        this.goodsBrandName = goodsBrandName;
        this.goodsBarCode = goodsBarCode;
        this.goodsDesc = goodsDesc;
        this.goodsKeyWord = goodsKeyWord;
        this.goodsStatus = goodsStatus;
        this.goodsCreatedDate = goodsCreatedDate;
    }

    /**
     * 上架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void upShelf() {
        this.goodsStatus = 2;
    }

    /**
     * 下架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void offShelf() {
        this.goodsStatus = 1;
    }

    public void soldOut() {
        this.goodsStatus = 3;
    }
}
