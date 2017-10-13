package cn.m2c.scm.domain.model.goods;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

import java.util.List;

/**
 * 商品审核
 */
public class GoodsApprove extends ConcurrencySafeEntity {

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
    private String goodsGuarantee;

    /**
     * 商品主图  存储类型是[“url1”,"url2"]
     */
    private String goodsMainImages;

    /**
     * 商品主图视频
     */
    private String goodsMainVideo;

    /**
     * 商品描述
     */
    private String goodsDesc;

    /**
     * 1:手动上架,2:审核通过立即上架
     */
    private Integer goodsShelves;

    /**
     * 审核状态，1：审核中，2：审核不通过
     */
    private Integer approveStatus;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 商品规格
     */
    private List<GoodsSkuApprove> goodsSkuApproves;

    public GoodsApprove() {
        super();
    }
}