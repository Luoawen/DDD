package cn.m2c.scm.domain.model.goods;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.ddd.common.serializer.ObjectSerializer;
import cn.m2c.scm.domain.model.goods.event.GoodsApproveAgreeEvent;
import cn.m2c.scm.domain.util.GetMapValueUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    public GoodsApprove() {
        super();
    }

    public GoodsApprove(String goodsId, String dealerId, String dealerName, String goodsName, String goodsSubTitle,
                        String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                        String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                        String goodsMainImages, String goodsDesc, Integer goodsShelves, String goodsSpecifications, String goodsSkuApproves,
                        Integer skuFlag) {
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
    public void agree() {
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
                        this.goodsMainImages, this.goodsMainVideo, this.goodsDesc,
                        this.goodsShelves, this.goodsSpecifications, goodsSKUs, this.skuFlag));
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

    public void modifyGoodsApprove(String goodsName, String goodsSubTitle,
                                   String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                                   String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                                   String goodsMainImages, String goodsDesc, String goodsSpecifications, String goodsSkuApproves) {
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
        this.approveStatus = 1;
        this.rejectReason = null;
        if (null != this.skuFlag && this.skuFlag == 1) {//是否是多规格：0：单规格，1：多规格
            this.goodsSpecifications = goodsSpecifications;
        }
        //商品规格格式：[{"skuId":20171014125226158648","skuName":"L,红","supplyPrice":4000,
        // "weight":20.5,"availableNum":200,"goodsCode":"111111","marketPrice":6000,"photographPrice":5000,"showStatus":2}]
        if (null == this.goodsSkuApproves) {
            this.goodsSkuApproves = new ArrayList<>();
        }
        List<Map> skuList = JsonUtils.toList(goodsSkuApproves, Map.class);
        if (null != skuList && skuList.size() > 0) {
            for (Map map : skuList) {
                String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                // 判断商品规格sku是否存在,存在就修改供货价和拍获价，不存在就增加商品sku
                GoodsSkuApprove goodsSkuApprove = getGoodsSkuApprove(skuId);
                if (null == goodsSkuApprove) {// 增加规格
                    this.goodsSkuApproves.add(createGoodsSkuApprove(map));
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
                    goodsSkuApprove.modifyGoodsSkuApprove(skuName, availableNum, weight, photographPrice,
                            marketPrice, supplyPrice, goodsCode, showStatus);
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
     * @param guaranteeId
     */
	public void delGoodsApproveGuarantee(String guaranteeId) {
		List list = ObjectSerializer.instance().deserialize(this.goodsGuarantee, List.class);
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			String goodsApproveGuarantee = it.next();
			if(goodsApproveGuarantee.equals(guaranteeId)) {
				it.remove();
			}
		}
		this.goodsGuarantee = list.toString();
	}
}