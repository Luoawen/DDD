package cn.m2c.scm.application.goods.query.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.List;

/**
 * 商品
 */
public class GoodsBean {
    @ColumnAlias(value = "id")
    private Integer id;

    /**
     * 商品id
     */
    @ColumnAlias(value = "goods_id")
    private String goodsId;

    /**
     * 商家ID
     */
    @ColumnAlias(value = "dealer_id")
    private String dealerId;

    /**
     * 商家名称
     */
    @ColumnAlias(value = "dealer_name")
    private String dealerName;

    /**
     * 商品名称
     */
    @ColumnAlias(value = "goods_name")
    private String goodsName;

    /**
     * 商品副标题
     */
    @ColumnAlias(value = "goods_sub_title")
    private String goodsSubTitle;

    /**
     * 商品分类id
     */
    @ColumnAlias(value = "goods_classify_id")
    private String goodsClassifyId;

    /**
     * 商品品牌id
     */
    @ColumnAlias(value = "goods_brand_id")
    private String goodsBrandId;

    /**
     * 商品品牌名称
     */
    @ColumnAlias(value = "goods_brand_name")
    private String goodsBrandName;

    /**
     * 商品计量单位id
     */
    @ColumnAlias(value = "goods_unit_id")
    private String goodsUnitId;

    /**
     * 最小起订量
     */
    @ColumnAlias(value = "goods_min_quantity")
    private Integer goodsMinQuantity;

    /**
     * 运费模板id
     */
    @ColumnAlias(value = "goods_postage_id")
    private String goodsPostageId;

    /**
     * 商品条形码
     */
    @ColumnAlias(value = "goods_bar_code")
    private String goodsBarCode;

    /**
     * 关键词
     */
    @ColumnAlias(value = "goods_key_word")
    private String goodsKeyWord;

    /**
     * 商品保障
     */
    @ColumnAlias(value = "goods_guarantee")
    private String goodsGuarantee;

    /**
     * 商品主图  存储类型是[“url1”,"url2"]
     */
    @ColumnAlias(value = "goods_main_images")
    private String goodsMainImages;

    /**
     * 商品主图视频
     */
    @ColumnAlias(value = "goods_main_video")
    private String goodsMainVideo;
    
    /**商品主图视频时长*/
    @ColumnAlias(value = "goods_main_video_duration")
    private Integer goodsMainVideoDuration;
    
    /**商品主图视频大小*/
    @ColumnAlias(value = "goods_main_video_size")
    private Integer goodsMainVideoSize;
    
    /**
     * 商品描述
     */
    @ColumnAlias(value = "goods_desc")
    private String goodsDesc;

    /**
     * 1:手动上架,2:审核通过立即上架
     */
    @ColumnAlias(value = "goods_shelves")
    private Integer goodsShelves;

    /**
     * 商品状态，1：仓库中，2：出售中，3：已售罄
     */
    @ColumnAlias(value = "goods_status")
    private Integer goodsStatus;

    /**
     * 商品规格,格式：[{"itemName":"尺寸","itemValue":[{"spec_name":"L"},{"spec_name":"M"}]},{"itemName":"颜色","itemValue":[{"spec_name":"蓝色"},{"spec_name":"白色"}]}]
     */
    @ColumnAlias(value = "goods_specifications")
    private String goodsSpecifications;

    /**
     * 是否删除，1:正常，2：已删除
     */
    @ColumnAlias(value = "del_status")
    private Integer delStatus;

    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    @ColumnAlias(value = "sku_flag")
    private Integer skuFlag;

    private List<GoodsSkuBean> goodsSkuBeans;
    
    /**
     * 商品投放状态：0：未投放，1：投放
     */
    @ColumnAlias(value = "goods_launch_status")
    private Integer goodsLaunchStatus;

    /**
     * 商品识别图
     */
    private List<GoodsRecognizedBean> goodsRecognizedBeans;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsSubTitle() {
        return goodsSubTitle;
    }

    public void setGoodsSubTitle(String goodsSubTitle) {
        this.goodsSubTitle = goodsSubTitle;
    }

    public String getGoodsClassifyId() {
        return goodsClassifyId;
    }

    public void setGoodsClassifyId(String goodsClassifyId) {
        this.goodsClassifyId = goodsClassifyId;
    }

    public String getGoodsBrandId() {
        return goodsBrandId;
    }

    public void setGoodsBrandId(String goodsBrandId) {
        this.goodsBrandId = goodsBrandId;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public void setGoodsBrandName(String goodsBrandName) {
        this.goodsBrandName = goodsBrandName;
    }

    public String getGoodsUnitId() {
        return goodsUnitId;
    }

    public void setGoodsUnitId(String goodsUnitId) {
        this.goodsUnitId = goodsUnitId;
    }

    public Integer getGoodsMinQuantity() {
        return goodsMinQuantity;
    }

    public void setGoodsMinQuantity(Integer goodsMinQuantity) {
        this.goodsMinQuantity = goodsMinQuantity;
    }

    public String getGoodsPostageId() {
        return goodsPostageId;
    }

    public void setGoodsPostageId(String goodsPostageId) {
        this.goodsPostageId = goodsPostageId;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public String getGoodsKeyWord() {
        return goodsKeyWord;
    }

    public void setGoodsKeyWord(String goodsKeyWord) {
        this.goodsKeyWord = goodsKeyWord;
    }

    public String getGoodsGuarantee() {
        return goodsGuarantee;
    }

    public void setGoodsGuarantee(String goodsGuarantee) {
        this.goodsGuarantee = goodsGuarantee;
    }

    public List<GoodsRecognizedBean> getGoodsRecognizedBeans() {
        return goodsRecognizedBeans;
    }

    public void setGoodsRecognizedBeans(List<GoodsRecognizedBean> goodsRecognizedBeans) {
        this.goodsRecognizedBeans = goodsRecognizedBeans;
    }

    public String getGoodsMainImages() {
        return goodsMainImages;
    }

    public void setGoodsMainImages(String goodsMainImages) {
        this.goodsMainImages = goodsMainImages;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getGoodsShelves() {
        return goodsShelves;
    }

    public void setGoodsShelves(Integer goodsShelves) {
        this.goodsShelves = goodsShelves;
    }

    public Integer getGoodsStatus() {
        return goodsStatus;
    }

    public void setGoodsStatus(Integer goodsStatus) {
        this.goodsStatus = goodsStatus;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(String goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public List<GoodsSkuBean> getGoodsSkuBeans() {
        return goodsSkuBeans;
    }

    public void setGoodsSkuBeans(List<GoodsSkuBean> goodsSkuBeans) {
        this.goodsSkuBeans = goodsSkuBeans;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }

	public Integer getGoodsLaunchStatus() {
		return goodsLaunchStatus;
	}

	public void setGoodsLaunchStatus(Integer goodsLaunchStatus) {
		this.goodsLaunchStatus = goodsLaunchStatus;
	}

	public String getGoodsMainVideo() {
		return goodsMainVideo;
	}

	public void setGoodsMainVideo(String goodsMainVideo) {
		this.goodsMainVideo = goodsMainVideo;
	}

	public Integer getGoodsMainVideoDuration() {
		return goodsMainVideoDuration;
	}

	public void setGoodsMainVideoDuration(Integer goodsMainVideoDuration) {
		this.goodsMainVideoDuration = goodsMainVideoDuration;
	}

	public Integer getGoodsMainVideoSize() {
		return goodsMainVideoSize;
	}

	public void setGoodsMainVideoSize(Integer goodsMainVideoSize) {
		this.goodsMainVideoSize = goodsMainVideoSize;
	}
    
}