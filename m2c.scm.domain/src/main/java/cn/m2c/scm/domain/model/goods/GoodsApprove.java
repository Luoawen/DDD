package cn.m2c.scm.domain.model.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.domain.model.goods.event.GoodsApproveAgreeEvent;
import cn.m2c.scm.domain.util.GetMapValueUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 商品审核
 */
public class GoodsApprove extends ConcurrencySafeEntity {
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
    
    /**商品主图时长*/
    private Integer goodsMainVideoDuration;
    
    /**商品主图大小*/
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
     * 审核状态，1：审核中，2：审核不通过
     */
    private Integer approveStatus;

    /**
     * 拒绝原因
     */
    private String rejectReason;

    /**
     * 商品规格,格式：[{"itemName":"尺寸","itemValue":["L","M"]},{"itemName":"颜色","itemValue":["蓝色","白色"]}]
     */
    private String goodsSpecifications;

    /**
     * 商品规格
     */
    private List<GoodsSkuApprove> goodsSkuApproves;

    /**
     * 是否删除，1:正常，2：已删除
     */
    private Integer delStatus;

    /**
     * 是否是多规格：0：单规格，1：多规格
     */
    private Integer skuFlag;

    /**
     * 变更原因
     */
    private String changeReason;

    private List<GoodsApproveHistory> goodsApproveHistories;

    public GoodsApprove() {
        super();
    }

    public GoodsApprove(String goodsId, String dealerId, String dealerName, String goodsName, String goodsSubTitle,
                        String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                        String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                        String goodsMainImages, String goodsMainVideo, Integer goodsMainVideoDuration, Integer goodsMainVideoSize,
                        String goodsDesc, Integer goodsShelves, String goodsSpecifications, String goodsSkuApproves,
                        Integer skuFlag, String changeGoodsInfo) {
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
        this.goodsMainVideo = goodsMainVideo;//主图视频
        this.goodsMainVideoDuration = goodsMainVideoDuration;//商品主图时长
        this.goodsMainVideoSize = goodsMainVideoSize;//商品主图大小
        
        this.goodsDesc = goodsDesc;
        if (null != goodsShelves) {
            this.goodsShelves = goodsShelves;
        }
        this.approveStatus = 1;
        this.skuFlag = skuFlag;
        if (null != skuFlag && skuFlag == 1) {//是否是多规格：0：单规格，1：多规格
            this.goodsSpecifications = goodsSpecifications;
        }
        //商品规格格式：[{"skuId":20171014125226158648","skuName":"L,红","supplyPrice":4000,
        // "weight":20.5,"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2}]
        if (null == this.goodsSkuApproves) {
            this.goodsSkuApproves = new ArrayList<>();
        } else {
            this.goodsSkuApproves.clear();
        }
        List<Map> skuList = JsonUtils.toList(goodsSkuApproves, Map.class);
        if (null != skuList && skuList.size() > 0) {
            for (int i = 0; i < skuList.size(); i++) {
                this.goodsSkuApproves.add(createGoodsSkuApprove(skuList.get(i)));
            }
        }

        // 变更历史
        this.goodsApproveHistories = new ArrayList<>();
        dealGoodsApproveHistory(changeGoodsInfo);
    }

    private void dealGoodsApproveHistory(String changeGoodsInfo) {
        if (StringUtils.isNotEmpty(changeGoodsInfo)) {
            Map map = JsonUtils.toMap(changeGoodsInfo);
            if (null != map) {
                Date nowDate = new Date();
                this.changeReason = null != map.get("changeReason") ? map.get("changeReason").toString() : null;
                String oldGoodsClassifyId = null != map.get("oldGoodsClassifyId") ? map.get("oldGoodsClassifyId").toString() : null;
                String newGoodsClassifyId = null != map.get("newGoodsClassifyId") ? map.get("newGoodsClassifyId").toString() : null;
                // 增加的sku
                List<Map> addGoodsSkuList = null != map.get("addGoodsSkuList") ? (List<Map>) map.get("addGoodsSkuList") : null;
                // 变更的拍获价
                List<Map> photographPriceChangeList = null != map.get("photographPriceChangeList") ? (List<Map>) map.get("photographPriceChangeList") : null;
                // 变更的供货价
                List<Map> supplyPriceChangeList = null != map.get("supplyPriceChangeList") ? (List<Map>) map.get("supplyPriceChangeList") : null;

                String historyNo = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                // 分类
                if (StringUtils.isNotEmpty(oldGoodsClassifyId) && StringUtils.isNotEmpty(newGoodsClassifyId)) {
                    String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                    String newServiceRate = null != map.get("newServiceRate") ? map.get("newServiceRate").toString() : null;
                    String settlementMode = null != map.get("settlementMode") ? map.get("settlementMode").toString() : null;
                    String oldServiceRate = null != map.get("oldServiceRate") ? map.get("oldServiceRate").toString() : null;
                    String oldClassifyName = null != map.get("oldClassifyName") ? map.get("oldClassifyName").toString() : null;
                    String newClassifyName = null != map.get("newClassifyName") ? map.get("newClassifyName").toString() : null;
                    Map before = new HashMap<>();
                    before.put("goodsClassifyId", oldGoodsClassifyId);
                    before.put("goodsClassifyName", oldClassifyName);
                    before.put("serviceRate", oldServiceRate);
                    before.put("settlementMode", settlementMode);
                    Map after = new HashMap<>();
                    after.put("goodsClassifyId", newGoodsClassifyId);
                    after.put("goodsClassifyName", newClassifyName);
                    after.put("serviceRate", newServiceRate);

                    GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                            1, JsonUtils.toStr(before),
                            JsonUtils.toStr(after), this.changeReason, nowDate);
                    this.goodsApproveHistories.add(history);
                }

                // 增加sku
                if (null != addGoodsSkuList && addGoodsSkuList.size() > 0) {
                    String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                    GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                            4, "",
                            JsonUtils.toStr(addGoodsSkuList), this.changeReason, nowDate);
                    this.goodsApproveHistories.add(history);
                }

                // 拍获价
                if (null != photographPriceChangeList && photographPriceChangeList.size() > 0) {
                    for (Map photographPriceMap : photographPriceChangeList) {
                        String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                        Map before = new HashMap<>();
                        before.put("photographPrice", photographPriceMap.get("oldPhotographPrice"));
                        before.put("skuId", photographPriceMap.get("skuId"));
                        before.put("skuName", photographPriceMap.get("skuName"));

                        Map after = new HashMap<>();
                        after.put("photographPrice", photographPriceMap.get("newPhotographPrice"));


                        GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                                2, JsonUtils.toStr(before),
                                JsonUtils.toStr(after), this.changeReason, nowDate);
                        this.goodsApproveHistories.add(history);
                    }
                }

                // 供货价
                if (null != supplyPriceChangeList && supplyPriceChangeList.size() > 0) {
                    for (Map supplyPriceMap : supplyPriceChangeList) {
                        String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                        Map before = new HashMap<>();
                        before.put("supplyPrice", supplyPriceMap.get("oldSupplyPrice"));
                        before.put("skuId", supplyPriceMap.get("skuId"));
                        before.put("skuName", supplyPriceMap.get("skuName"));

                        Map after = new HashMap<>();
                        after.put("supplyPrice", supplyPriceMap.get("newSupplyPrice"));

                        GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                                3, JsonUtils.toStr(before),
                                JsonUtils.toStr(after), this.changeReason, nowDate);
                        this.goodsApproveHistories.add(history);
                    }
                }

            }
        }
    }

    private GoodsSkuApprove createGoodsSkuApprove(Map map) {
        String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
        String skuName = GetMapValueUtils.getStringFromMapKey(map, "skuName");
        Integer availableNum = GetMapValueUtils.getIntFromMapKey(map, "availableNum");
        Float weight = GetMapValueUtils.getFloatFromMapKey(map, "weight");
        Long photographPrice = GetMapValueUtils.getLongFromMapKey(map, "photographPrice");
        Long marketPrice = GetMapValueUtils.getLongFromMapKey(map, "marketPrice");
        Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
        String goodsCode = GetMapValueUtils.getStringFromMapKey(map, "goodsCode");
        Integer showStatus = 2; //是否对外展示，1：不展示，2：展示
        if (null != this.skuFlag && this.skuFlag == 1) {
            Boolean isShow = GetMapValueUtils.getBooleanFromMapKey(map, "showStatus");
            if (!isShow) {
                showStatus = 1;
            }
        }
        GoodsSkuApprove goodsSkuApprove = new GoodsSkuApprove(this, skuId, skuName, availableNum,
                weight, photographPrice, marketPrice, supplyPrice, goodsCode,
                showStatus);
        return goodsSkuApprove;
    }

    /**
     * 同意审核
     */
    public void agree(Map changeInfo) {
        List<Map> goodsSkuMaps = new ArrayList<>();
        for (GoodsSkuApprove goodsSkuApprove : this.goodsSkuApproves) {
            goodsSkuMaps.add(goodsSkuApprove.convertToMap());
        }
        String goodsSKUs = ObjectSerializer.instance().serialize(goodsSkuMaps);
        DomainEventPublisher
                .instance()
                .publish(new GoodsApproveAgreeEvent(this.goodsId, this.dealerId, this.dealerName, this.goodsName,
                        this.goodsSubTitle, this.goodsClassifyId, this.goodsBrandId, this.goodsBrandName,
                        this.goodsUnitId, this.goodsMinQuantity, this.goodsPostageId,
                        this.goodsBarCode, this.goodsKeyWord, this.goodsGuarantee,
                        this.goodsMainImages, this.goodsMainVideo, this.goodsMainVideoDuration,
                        this.goodsMainVideoSize, this.goodsDesc,
                        this.goodsShelves, this.goodsSpecifications, goodsSKUs, this.skuFlag,
                        this.changeReason, JsonUtils.toStr(changeInfo)));
    }

    /**
     * 拒绝审核
     *
     * @param rejectReason
     */
    public void reject(String rejectReason) {
        this.approveStatus = 2;
        this.rejectReason = rejectReason;
    }

    /**
     * 修改审核记录
     *
     * @param isCover true:修改商品库，存在审核记录，覆盖之前审核记录 false:直接修改商品审核库
     */
    public void modifyGoodsApprove(String goodsName, String goodsSubTitle,
                                   String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                                   String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                                   String goodsMainImages, String goodsMainVideo, Integer goodsMainVideoDuration, Integer goodsMainVideoSize,
                                   String goodsDesc, String goodsSpecifications, String goodsSkuApproves, boolean isCover,
                                   String changeGoodsInfo, boolean isModifyApprove, Map goodsInfoMap) {
        this.goodsName = goodsName;
        this.goodsSubTitle = goodsSubTitle;
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
        this.approveStatus = 1;
        this.rejectReason = null;
        if (null != this.skuFlag && this.skuFlag == 1) {//是否是多规格：0：单规格，1：多规格
            this.goodsSpecifications = goodsSpecifications;
        }

        if (null == this.goodsApproveHistories) {
            this.goodsApproveHistories = new ArrayList<>();
        }

        // 分类
        String historyNo = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
        Date nowDate = new Date();
        String newServiceRate = "";
        Integer settlementMode = null;
        if (null != goodsInfoMap) {
            newServiceRate = null != goodsInfoMap.get("newServiceRate") ? goodsInfoMap.get("newServiceRate").toString() : null;
            settlementMode = null != goodsInfoMap.get("settlementMode") ? Integer.parseInt(goodsInfoMap.get("settlementMode").toString()) : null;
        }
        if (isModifyApprove && !this.goodsClassifyId.equals(goodsClassifyId)) {
            String oldGoodsClassifyId = "";
            String oldServiceRate = "";
            String oldClassifyName = "";
            String newClassifyName = "";
            if (null != goodsInfoMap) {
                oldGoodsClassifyId = goodsInfoMap.get("goodsClassifyId").toString();
                oldServiceRate = null != goodsInfoMap.get("oldServiceRate") ? goodsInfoMap.get("oldServiceRate").toString() : null;
                oldClassifyName = null != goodsInfoMap.get("oldClassifyName") ? goodsInfoMap.get("oldClassifyName").toString() : null;
                newClassifyName = null != goodsInfoMap.get("newClassifyName") ? goodsInfoMap.get("newClassifyName").toString() : null;
            }
            Map before = new HashMap<>();
            before.put("goodsClassifyId", oldGoodsClassifyId);
            before.put("goodsClassifyName", oldClassifyName);
            before.put("serviceRate", oldServiceRate);
            before.put("settlementMode", settlementMode);
            Map after = new HashMap<>();
            after.put("goodsClassifyId", goodsClassifyId);
            after.put("goodsClassifyName", newClassifyName);
            after.put("serviceRate", newServiceRate);

            boolean isExistClassifyHistory = false;
            if (null != this.goodsApproveHistories && this.goodsApproveHistories.size() > 0) {
                for (GoodsApproveHistory history : this.goodsApproveHistories) {
                    if (history.changeType() == 1) { // 修改了分类
                        history.modifyApproveHistory(JsonUtils.toStr(after), nowDate);
                        isExistClassifyHistory = true;
                    }
                }
            }
            if (!isExistClassifyHistory) {
                // 商品审核库修改分类
                String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                        1, JsonUtils.toStr(before),
                        JsonUtils.toStr(after), this.changeReason, nowDate);
                this.goodsApproveHistories.add(history);
            }
        }
        this.goodsClassifyId = goodsClassifyId;

        //商品规格格式：[{"skuId":20171014125226158648","skuName":"L,红","supplyPrice":4000,
        // "weight":20.5,"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2}]
        if (null == this.goodsSkuApproves) {
            this.goodsSkuApproves = new ArrayList<>();
        }
        List<Map> skuList = JsonUtils.toList(goodsSkuApproves, Map.class);
        if (null != skuList && skuList.size() > 0) {
            List<Map> skuAddList = new ArrayList<>();
            if (isCover) {
                this.goodsSkuApproves.clear();
                List<GoodsSkuApprove> tempGoodsSkuApproves = new ArrayList<>();
                for (Map map : skuList) {
                    GoodsSkuApprove skuApprove = createGoodsSkuApprove(map);
                    tempGoodsSkuApproves.add(skuApprove);
                }
                this.goodsSkuApproves.addAll(tempGoodsSkuApproves);
            } else {
                for (Map map : skuList) {
                    String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                    // 判断商品规格sku是否存在,存在就修改供货价和拍获价，不存在就增加商品sku
                    GoodsSkuApprove goodsSkuApprove = getGoodsSkuApprove(skuId);
                    if (null == goodsSkuApprove) {// 增加规格
                        GoodsSkuApprove skuApprove = createGoodsSkuApprove(map);
                        this.goodsSkuApproves.add(skuApprove);
                        if (isModifyApprove) {
                            // 商品审核库增加规格
                            Map skuMap = skuApprove.convertToMap();
                            skuMap.put("serviceRate", newServiceRate);
                            skuMap.put("settlementMode", settlementMode);
                            skuAddList.add(skuMap);
                        }
                    } else { //修改
                        String skuName = GetMapValueUtils.getStringFromMapKey(map, "skuName");
                        Integer availableNum = GetMapValueUtils.getIntFromMapKey(map, "availableNum");
                        Float weight = GetMapValueUtils.getFloatFromMapKey(map, "weight");
                        Long photographPrice = GetMapValueUtils.getLongFromMapKey(map, "photographPrice");
                        Long marketPrice = GetMapValueUtils.getLongFromMapKey(map, "marketPrice");
                        Long supplyPrice = GetMapValueUtils.getLongFromMapKey(map, "supplyPrice");
                        String goodsCode = GetMapValueUtils.getStringFromMapKey(map, "goodsCode");
                        Integer showStatus = 2; //是否对外展示，1：不展示，2：展示
                        if (null != this.skuFlag && this.skuFlag == 1) {
                            Boolean isShow = GetMapValueUtils.getBooleanFromMapKey(map, "showStatus");
                            if (!isShow) {
                                showStatus = 1;
                            }
                        }

                        // 商品审核库修改拍获价
                        if (isModifyApprove && goodsSkuApprove.isModifyPhotographPrice(photographPrice)) {
                            String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                            Map photographPriceMap = goodsSkuApprove.getChangePhotographPrice(photographPrice);
                            Map before = new HashMap<>();
                            // 取商品库价格
                            Long oldPhotographPrice = getOldPhotographPrice(goodsInfoMap, skuId);
                            before.put("photographPrice", null == oldPhotographPrice ? photographPriceMap.get("oldPhotographPrice") : oldPhotographPrice);
                            before.put("skuId", photographPriceMap.get("skuId"));
                            before.put("skuName", photographPriceMap.get("skuName"));

                            Map after = new HashMap<>();
                            after.put("photographPrice", photographPriceMap.get("newPhotographPrice"));

                            boolean isExistPhotographPriceHistory = false;
                            if (null != this.goodsApproveHistories && this.goodsApproveHistories.size() > 0) {
                                for (GoodsApproveHistory history : this.goodsApproveHistories) {
                                    if (history.isModifyPhotographPrice(skuId)) { // 修改了拍获价
                                        history.modifyApproveHistory(JsonUtils.toStr(after), nowDate);
                                        isExistPhotographPriceHistory = true;
                                    }
                                }
                            }
                            if (!isExistPhotographPriceHistory) {
                                GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                                        2, JsonUtils.toStr(before),
                                        JsonUtils.toStr(after), this.changeReason, nowDate);
                                this.goodsApproveHistories.add(history);
                            }
                        }
                        // 商品审核库修改供货价
                        if (isModifyApprove && goodsSkuApprove.isModifySupplyPrice(supplyPrice)) {
                            String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                            Map supplyPriceMap = goodsSkuApprove.getChangeSupplyPrice(supplyPrice);
                            Map before = new HashMap<>();
                            Long oldSupplyPrice = getOldSupplyPrice(goodsInfoMap, skuId);

                            before.put("supplyPrice", null == oldSupplyPrice ? supplyPriceMap.get("oldSupplyPrice") : oldSupplyPrice);
                            before.put("skuId", supplyPriceMap.get("skuId"));
                            before.put("skuName", supplyPriceMap.get("skuName"));

                            Map after = new HashMap<>();
                            after.put("supplyPrice", supplyPriceMap.get("newSupplyPrice"));


                            boolean isExistSupplyPriceHistory = false;
                            if (null != this.goodsApproveHistories && this.goodsApproveHistories.size() > 0) {
                                for (GoodsApproveHistory history : this.goodsApproveHistories) {
                                    if (history.isModifySupplyPrice(skuId)) { // 修改了供货价
                                        history.modifyApproveHistory(JsonUtils.toStr(after), nowDate);
                                        isExistSupplyPriceHistory = true;
                                    }
                                }
                            }
                            if (!isExistSupplyPriceHistory) {
                                GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                                        3, JsonUtils.toStr(before),
                                        JsonUtils.toStr(after), this.changeReason, nowDate);
                                this.goodsApproveHistories.add(history);
                            }
                        }

                        goodsSkuApprove.modifyGoodsSkuApprove(skuName, availableNum, weight, photographPrice,
                                marketPrice, supplyPrice, goodsCode, showStatus);
                    }
                }

                if (null != skuAddList && skuAddList.size() > 0) {
                    boolean isExistAddSkuHistory = false;
                    if (null != this.goodsApproveHistories && this.goodsApproveHistories.size() > 0) {
                        for (GoodsApproveHistory history : this.goodsApproveHistories) {
                            if (history.changeType() == 4) { // 增加了sku
                                List<Map> list = JsonUtils.toList(history.afterContent(), Map.class);
                                list.addAll(skuAddList);
                                history.modifyApproveHistory(JsonUtils.toStr(list), nowDate);
                                isExistAddSkuHistory = true;
                            }
                        }
                    }
                    if (!isExistAddSkuHistory) {
                        String historyId = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
                        GoodsApproveHistory history = new GoodsApproveHistory(historyId, historyNo, this, this.goodsId,
                                4, "",
                                JsonUtils.toStr(skuAddList), this.changeReason, nowDate);
                        this.goodsApproveHistories.add(history);
                    }
                }
            }
        }

        // 商品库修改变更历史
        if (isCover && StringUtils.isNotEmpty(changeGoodsInfo)) {
            if (null != this.goodsApproveHistories && this.goodsApproveHistories.size() > 0) {
                this.goodsApproveHistories.clear();
            }
            dealGoodsApproveHistory(changeGoodsInfo);
        }
    }

    private Long getOldPhotographPrice(Map goodsInfoMap, String skuId) {
        List<Map> skuMaps = (List<Map>) goodsInfoMap.get("goodsSKUs");
        if (null != skuMaps && skuMaps.size() > 0) {
            for (Map map : skuMaps) {
                String sku = map.get("skuId").toString();
                if (sku.equals(skuId)) {
                    return Long.parseLong(String.valueOf(map.get("photographPrice")));
                }
            }
        }
        return null;
    }

    private Long getOldSupplyPrice(Map goodsInfoMap, String skuId) {
        List<Map> skuMaps = (List<Map>) goodsInfoMap.get("goodsSKUs");
        if (null != skuMaps && skuMaps.size() > 0) {
            for (Map map : skuMaps) {
                String sku = map.get("skuId").toString();
                if (sku.equals(skuId) && null != map.get("supplyPrice")) {
                    return Long.parseLong(String.valueOf(map.get("supplyPrice")));
                }
            }
        }
        return null;
    }

    /**
     * 根据skuId获取商品规格
     *
     * @param skuId
     * @return
     */
    private GoodsSkuApprove getGoodsSkuApprove(String skuId) {
        GoodsSkuApprove goodsSkuApprove = null;
        List<GoodsSkuApprove> goodsSKUs = this.goodsSkuApproves;
        for (GoodsSkuApprove sku : goodsSKUs) {
            goodsSkuApprove = sku.getGoodsSKUApprove(skuId);
            if (null != goodsSkuApprove) {
                return goodsSkuApprove;
            }
        }
        return goodsSkuApprove;
    }

    public void remove() {
        this.delStatus = 2;
        this.goodsId = new StringBuffer("DEL_").append(this.id()).append("_").append(this.goodsId).toString();
        for (GoodsSkuApprove sku : this.goodsSkuApproves) {
            sku.remove();
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
     * 删除审核中商品指定保障
     *
     * @param guaranteeId
     */
    public void delGoodsApproveGuarantee(String guaranteeId) {
        List list = ObjectSerializer.instance().deserialize(this.goodsGuarantee, List.class);
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String goodsApproveGuarantee = it.next();
            if (goodsApproveGuarantee.equals(guaranteeId)) {
                it.remove();
            }
        }
        this.goodsGuarantee = JsonUtils.toStr(list);
    }

    public Integer getId() {
        return Integer.parseInt(String.valueOf(this.id()));
    }

    public String goodsId() {
        return goodsId;
    }

    public String goodsClassifyId() {
        return goodsClassifyId;
    }

    public String dealerId() {
        return dealerId;
    }
}