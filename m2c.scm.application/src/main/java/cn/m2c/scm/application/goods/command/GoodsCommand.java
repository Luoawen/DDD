package cn.m2c.scm.application.goods.command;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.util.GetMapValueUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 商品审核
 */
public class GoodsCommand extends AssertionConcern implements Serializable {
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
     * 商品规格
     */
    private String goodsSKUs;

    private String goodsSpecifications;

    /**
     * key-value   skuId-goodsCode
     */
    private Map<String, String> codeMap;

    private Integer skuFlag;
    
    public GoodsCommand(String goodsId, String dealerId, String dealerName, String goodsName, String goodsSubTitle,
                        String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                        String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                        String goodsMainImages, String goodsMainVideo, String goodsDesc, Integer goodsShelves, String goodsSpecifications, String goodsSKUs, Integer skuFlag) {
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
        this.goodsDesc = goodsDesc;
        this.goodsShelves = goodsShelves;
        this.goodsSpecifications = goodsSpecifications;
        this.goodsSKUs = goodsSKUs;
        this.skuFlag = skuFlag;
    }

    public GoodsCommand(String goodsId, String dealerId, String goodsName, String goodsSubTitle,
                        String goodsClassifyId, String goodsBrandId, String goodsBrandName, String goodsUnitId, Integer goodsMinQuantity,
                        String goodsPostageId, String goodsBarCode, String goodsKeyWord, String goodsGuarantee,
                        String goodsMainImages, String goodsMainVideo, String goodsDesc, String goodsSpecifications, String goodsSKUs) throws NegativeException {
        this.goodsId = goodsId;
        this.dealerId = dealerId;
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
        this.goodsMainVideo = goodsMainVideo;//商品主图
        this.goodsDesc = goodsDesc;
        this.goodsSpecifications = goodsSpecifications;
        this.goodsSKUs = goodsSKUs;
        this.skuFlag = skuFlag;

        List<Map> skuList = JsonUtils.toList(goodsSKUs, Map.class);
        List<String> goodsCodes = new ArrayList<>();
        Map<String, String> codeMap = new HashMap<>();
        if (null != skuList && skuList.size() > 0) {
            for (Map map : skuList) {
                String goodsCode = GetMapValueUtils.getStringFromMapKey(map, "goodsCode");
                if (StringUtils.isNotEmpty(goodsCode)) {
                    goodsCodes.add(goodsCode);
                    String skuId = GetMapValueUtils.getStringFromMapKey(map, "skuId");
                    codeMap.put(skuId, goodsCode);
                }
            }
        }
        if (null != goodsCodes && goodsCodes.size() > 0) {
            Set codeSet = new HashSet<>();
            codeSet.addAll(goodsCodes);
            if (goodsCodes.size() != codeSet.size()) {
                throw new NegativeException(MCode.V_1, "商品编码重复");
            }
        }
        this.codeMap = codeMap;
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

    public String getGoodsGuarantee() {
        return goodsGuarantee;
    }

    public String getGoodsMainImages() {
        return goodsMainImages;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public Integer getGoodsShelves() {
        return goodsShelves;
    }

    public String getGoodsSKUs() {
        return goodsSKUs;
    }

    public String getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public String getGoodsBrandName() {
        return goodsBrandName;
    }

    public Map<String, String> getCodeMap() {
        return codeMap;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }
    
    public String getGoodsMainVideo() {
    	return goodsMainVideo;
    }
}
