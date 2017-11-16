package cn.m2c.scm.domain.model.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.domain.model.goods.event.GoodsAddEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsApproveAddEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsChangedEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsDeleteEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsOffShelfEvent;
import cn.m2c.scm.domain.model.goods.event.GoodsUpShelfEvent;
import cn.m2c.scm.domain.util.GetMapValueUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 商品
 */
public class Goods extends ConcurrencySafeEntity {
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
     * 识别图片id
     */
    private String recognizedId;

    /**
     * 识别图片url
     */
    private String recognizedUrl;

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
     * 商品状态，1：仓库中，2：出售中，3：已售罄
     */
    private Integer goodsStatus;

    /**
     * 商品规格,格式：[{"itemName":"尺寸","itemValue":["L","M"]},{"itemName":"颜色","itemValue":["蓝色","白色"]}]
     */
    private String goodsSpecifications;

    /**
     * 商品规格
     */
    private List<GoodsSku> goodsSKUs;

    /**
     * 是否删除，1:正常，2：已删除
     */
    private Integer delStatus;

    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    private Integer skuFlag;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 商品投放状态：0：未投放，1：投放
     */
    private Integer goodsLaunchStatus;

    public Goods() {
        super();
    }

    public Goods(String goodsId, String dealerId, String dealerName, String goodsName, String goodsSubTitle,
                 String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                 String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                 String goodsMainImages, String goodsDesc, Integer goodsShelves, String goodsSpecifications, String goodsSKUs, Integer skuFlag) {
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
        this.goodsDesc = goodsDesc;
        this.goodsShelves = goodsShelves;
        this.skuFlag = skuFlag;
        if (this.goodsShelves == 1) {//1:手动上架,2:审核通过立即上架
            this.goodsStatus = 1;
        } else {
            this.goodsStatus = 2;
            DomainEventPublisher
                    .instance()
                    .publish(new GoodsUpShelfEvent(this.goodsId, this.goodsPostageId));
        }
        this.goodsSpecifications = goodsSpecifications;

        this.createdDate = new Date();
        if (null == this.goodsSKUs) {
            this.goodsSKUs = new ArrayList<>();
        } else {
            this.goodsSKUs.clear();
        }
        List<Map> skuList = ObjectSerializer.instance().deserialize(goodsSKUs, List.class);
        if (null != skuList && skuList.size() > 0) {
            for (Map map : skuList) {
                this.goodsSKUs.add(createGoodsSku(map));
            }
        }

        // 已售罄
        soldOut();

        DomainEventPublisher
                .instance()
                .publish(new GoodsAddEvent(this.goodsId, this.goodsUnitId, getStandardId(goodsSpecifications)));
    }

    /**
     * [{"itemName":"发送","itemValue":[{"spec_name":"16G"},{"spec_name":"32G"}],"state1":"","standardId":"GG1B487E5857C44367AB50C05A5E4B5A5E"},
     * {"itemName":"规格是嘛","itemValue":[{"spec_name":"红"},{"spec_name":"黑"}],"state1":"","standardId":"GGF01849F898A54B5E9FB565388272C2FA"}]
     *
     * @return
     */
    private List<String> getStandardId(String goodsSpecifications) {
        List<String> standardIds = new ArrayList<>();
        if (StringUtils.isNotEmpty(goodsSpecifications)) {
            List<Map> list = JsonUtils.toList(goodsSpecifications, Map.class);
            if (null != list && list.size() > 0) {
                for (Map map : list) {
                    if (map.containsKey("standardId")) {
                        String standardId = null != map.get("standardId") ? (String) map.get("standardId") : "";
                        standardIds.add(standardId);
                    }
                }
            }
        }
        return standardIds;
    }

    private GoodsSku createGoodsSku(Map map) {
        String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
        String skuName = GetMapValueUtils.getStringFromMapKey(map, "skuName");
        Integer availableNum = GetMapValueUtils.getIntFromMapKey(map, "availableNum");
        Float weight = GetMapValueUtils.getFloatFromMapKey(map, "weight");
        Long photographPrice = GetMapValueUtils.getLongFromMapKey(map, "photographPrice");
        Long marketPrice = GetMapValueUtils.getLongFromMapKey(map, "marketPrice");
        Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
        String goodsCode = GetMapValueUtils.getStringFromMapKey(map, "goodsCode");
        Integer showStatus = GetMapValueUtils.getIntFromMapKey(map, "showStatus");
        GoodsSku goodsSku = new GoodsSku(this, skuId, skuName, availableNum, availableNum, weight,
                photographPrice, marketPrice, supplyPrice, goodsCode, showStatus);
        return goodsSku;
    }

    /**
     * 修改商品需要审核的供货价和拍获价或增加sku
     *
     * @param goodsSKUs
     */
    public void modifyApproveGoodsSku(String goodsSKUs) {
        List<Map> skuList = ObjectSerializer.instance().deserialize(goodsSKUs, List.class);
        if (null != skuList && skuList.size() > 0) {
            if (null == this.goodsSKUs) {
                this.goodsSKUs = new ArrayList<>();
            }
            for (Map map : skuList) {
                String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                // 判断商品规格sku是否存在,存在就修改供货价和拍获价，不存在就增加商品sku
                GoodsSku goodsSku = getGoodsSKU(skuId);
                if (null == goodsSku) {// 增加规格
                    this.goodsSKUs.add(createGoodsSku(map));
                } else { // 修改供货价和拍获价
                    Long photographPrice = GetMapValueUtils.getLongFromMapKey(map, "photographPrice");
                    Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
                    goodsSku.modifyApprovePrice(photographPrice, supplyPrice);
                }
            }
        }
    }

    /**
     * 根据skuId获取商品规格
     *
     * @param skuId
     * @return
     */
    private GoodsSku getGoodsSKU(String skuId) {
        GoodsSku goodsSku = null;
        List<GoodsSku> goodsSKUs = this.goodsSKUs;
        for (GoodsSku sku : goodsSKUs) {
            goodsSku = sku.getGoodsSKU(skuId);
            if (null != goodsSku) {
                return goodsSku;
            }
        }
        return goodsSku;
    }

    /**
     * 修改商品
     */
    public void modifyGoods(String goodsName, String goodsSubTitle,
                            String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                            String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                            String goodsMainImages, String goodsDesc, String goodsSpecifications, String goodsSKUs) {
        String oldGoodsUnitId = this.goodsUnitId;
        String newGoodsUnitId = goodsUnitId;
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
        this.goodsDesc = goodsDesc;

        if (null != this.skuFlag && this.skuFlag == 1) {//是否是多规格：0：单规格，1：多规格
            this.goodsSpecifications = goodsSpecifications;
        }
        List<Map> skuList = ObjectSerializer.instance().deserialize(goodsSKUs, List.class);
        if (null != skuList && skuList.size() > 0) {
            //修改供货价、拍获价、规格需要审批
            boolean isNeedApprove = false;
            boolean goodsNumThanZero = false;
            for (Map map : skuList) {
                String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                // 判断商品规格sku是否存在,存在就修改供货价和拍获价，不存在就增加商品sku
                GoodsSku goodsSku = getGoodsSKU(skuId);
                if (null == goodsSku) {// 增加了规格
                    isNeedApprove = true;
                } else {
                    Integer availableNum = GetMapValueUtils.getIntFromMapKey(map, "availableNum");
                    if (availableNum > 0) {
                        goodsNumThanZero = true;
                    }
                    Float weight = GetMapValueUtils.getFloatFromMapKey(map, "weight");
                    Long marketPrice = GetMapValueUtils.getLongFromMapKey(map, "marketPrice");
                    String goodsCode = GetMapValueUtils.getStringFromMapKey(map, "goodsCode");

                    Integer showStatus = 2; //是否对外展示，1：不展示，2：展示
                    //Integer showStatus = GetMapValueUtils.getIntFromMapKey(map, "showStatus");
                    if (null != this.skuFlag && this.skuFlag == 1) {
                        Boolean isShow = GetMapValueUtils.getBooleanFromMapKey(map, "showStatus");
                        if (!isShow) {
                            showStatus = 1;
                        }
                    }

                    // 修改商品规格不需要审批的信息
                    goodsSku.modifyNotApproveGoodsSku(availableNum, weight, marketPrice, goodsCode, showStatus);

                    // 判断供货价和拍获价是否修改
                    Long photographPrice = GetMapValueUtils.getLongFromMapKey(map, "photographPrice");
                    Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
                    if (goodsSku.isModifyNeedApprovePrice(photographPrice, supplyPrice)) { //修改了供货价和拍获价
                        isNeedApprove = true;
                    }
                }
            }
            if (goodsNumThanZero && this.goodsStatus == 3) {
                this.goodsStatus = 2;
            }
            if (isNeedApprove) {//发布事件，增加一条待审核商品记录
                DomainEventPublisher
                        .instance()
                        .publish(new GoodsApproveAddEvent(this.goodsId, this.dealerId, this.dealerName, this.goodsName,
                                this.goodsSubTitle, this.goodsClassifyId, this.goodsBrandId, this.goodsBrandName, this.goodsUnitId,
                                this.goodsMinQuantity, this.goodsPostageId, this.goodsBarCode,
                                this.goodsKeyWord, this.goodsGuarantee, this.goodsMainImages, this.goodsDesc, this.goodsSpecifications,
                                goodsSKUs, this.skuFlag));
            }
        }

        DomainEventPublisher.instance().publish(new GoodsChangedEvent(this.goodsId, this.goodsName, this.dealerId, this.dealerName,
                oldGoodsUnitId, newGoodsUnitId));
    }

    /**
     * 删除商品
     */
    public void remove() {
        this.delStatus = 2;
        DomainEventPublisher
                .instance()
                .publish(new GoodsDeleteEvent(this.goodsId, this.goodsUnitId, getStandardId(goodsSpecifications)));
    }

    /**
     * 上架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void upShelf() {
        this.goodsStatus = 2;
        Integer total = 0;
        for (GoodsSku goodsSku : this.goodsSKUs) {
            total = total + goodsSku.availableNum();
        }
        if (total <= 0) {
            this.goodsStatus = 3;
        }
        DomainEventPublisher
                .instance()
                .publish(new GoodsUpShelfEvent(this.goodsId, this.goodsPostageId));
    }

    /**
     * 下架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void offShelf() {
        this.goodsStatus = 1;
        DomainEventPublisher
                .instance()
                .publish(new GoodsOffShelfEvent(this.goodsId, this.goodsPostageId));
    }

    /**
     * 修改商品识别图
     */
    public void modifyRecognized(String recognizedId, String recognizedUrl) {
        this.recognizedId = StringUtils.isEmpty(recognizedId) ? null : recognizedId;
        this.recognizedUrl = StringUtils.isEmpty(recognizedUrl) ? null : recognizedUrl;
    }

    public Integer getId() {
        return Integer.parseInt(String.valueOf(this.id()));
    }

    /**
     * 上架,商品状态，1：仓库中，2：出售中，3：已售罄
     */
    public void soldOut() {
        if (this.goodsStatus == 2) {
            Integer total = 0;
            for (GoodsSku goodsSku : this.goodsSKUs) {
                total = total + goodsSku.availableNum();
            }
            if (total <= 0) {
                this.goodsStatus = 3;
            }
        }
    }

    /**
     * 修改商品品牌名称
     */
    public void modifyBrandName(String brandName) {
        this.goodsBrandName = brandName;
    }

    /**
     * 修改商品供应商名称
     */
    public void modifyDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    /**
     * 修改商品投放状态
     */
    public void launchGoods() {
        this.goodsLaunchStatus = 1;
    }

    public String recognizedId() {
        return recognizedId;
    }

    public String recognizedUrl() {
        return recognizedUrl;
    }

    public Integer goodsStatus() {
        return goodsStatus;
    }

    public void modifyGoodsMainImages(List<String> images) {
        this.goodsMainImages = JsonUtils.toStr(images);
    }
}