package cn.m2c.scm.domain.model.goods.event;

import cn.m2c.ddd.common.domain.model.DomainEvent;

import java.util.Date;

/**
 * 商家管理平台审核同意商品
 */
public class GoodsApproveAgreeEvent implements DomainEvent {
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
     * 商品品牌名称
     */
    private String goodsBrandName;

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
    private String goodsGuarantee;

    /**
     * 商品主图  存储类型是[“url1”,"url2"]
     */
    private String goodsMainImages;

    /**
     * 商品主图视频
     */
    private String goodsMainVideo;
    
    /**商品主图视频时长*/
    private Integer goodsMainVideoDuration;
    
    /**商品主图视频大小*/
    private Integer goodsMainVideoSize;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 1:手动上架,2:审核通过立即上架
     */
    private Integer goodsShelves;

    /**
     * 商品规格
     */
    private String goodsSkuApproves;

    private String goodsSpecifications;

    private Integer skuFlag;

    /**
     * 变更原因
     */
    private String changeReason;

    private String changeInfo;

    private Date occurredOn;
    private int eventVersion;

    public GoodsApproveAgreeEvent(String goodsId, String dealerId, String dealerName, String goodsName,
                                  String goodsSubTitle, String goodsClassifyId, String goodsBrandId, String goodsBrandName,
                                  String goodsUnitId, Integer goodsMinQuantity, String goodsPostageId,
                                  String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                                  String goodsMainImages, String goodsMainVideo, Integer goodsMainVideoDuration,
                                  Integer goodsMainVideoSize, String goodsDesc,
                                  Integer goodsShelves, String goodsSpecifications, String goodsSkuApproves,
                                  Integer skuFlag, String changeReason, String changeInfo) {
        this.goodsId = goodsId;
        this.dealerId = dealerId;
        this.dealerName = dealerName;
        this.goodsName = goodsName;
        this.goodsSubTitle = goodsSubTitle;
        this.goodsClassifyId = goodsClassifyId;
        this.goodsBrandId = goodsBrandId;
        this.goodsBrandName = goodsBrandName;
        this.goodsUnitId = goodsUnitId;
        this.goodsMinQuantity = goodsMinQuantity;
        this.goodsPostageId = goodsPostageId;
        this.goodsBarCode = goodsBarCode;
        this.goodsKeyWord = goodsKeyWord;
        this.goodsGuarantee = goodsGuarantee;
        this.goodsMainImages = goodsMainImages;
        this.goodsMainVideo = goodsMainVideo;
        this.goodsMainVideoDuration = goodsMainVideoDuration;
        this.goodsMainVideoSize = goodsMainVideoSize;
        this.goodsDesc = goodsDesc;
        this.goodsShelves = goodsShelves;
        this.goodsSpecifications = goodsSpecifications;
        this.goodsSkuApproves = goodsSkuApproves;
        this.skuFlag = skuFlag;
        this.changeReason = changeReason;
        this.changeInfo = changeInfo;
        this.occurredOn = new Date();
        this.eventVersion = 1;
    }

    @Override
    public int eventVersion() {
        return this.eventVersion;
    }

    @Override
    public Date occurredOn() {
        return this.occurredOn;
    }
}
