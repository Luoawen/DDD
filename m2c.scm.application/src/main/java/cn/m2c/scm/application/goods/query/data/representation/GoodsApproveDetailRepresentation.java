package cn.m2c.scm.application.goods.query.data.representation;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.goods.query.data.bean.GoodsApproveBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuApproveBean;
import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品详情
 */
public class GoodsApproveDetailRepresentation {
    private String goodsName;
    private String goodsSubTitle;
    private String goodsClassifyId;
    private List goodsClassifyIds;
    private String goodsClassify;
    private String goodsBrandId;
    private String goodsBrandName;
    private String goodsUnitId;
    private String goodsUnitName;
    private Integer goodsMinQuantity;
    private String goodsBarCode;
    private List<String> goodsKeyWord;
    private List<Map> goodsGuarantee;
    private List<Map> goodsSpecifications;
    private List<Map> goodsSKUs;
    private List<String> goodsMainImages;
    private String goodsDesc;
    private Integer approveStatus;//审核状态，1：审核中，2：审核不通过
    private String rejectReason;
    private Integer settlementMode;//结算模式 1：按供货价 2：按服务费率
    private Float serviceRate;//服务费率
    private Integer skuFlag;
    private String goodsPostageId;
    private Integer goodsShelves;
    private String goodsPostageName;

    public GoodsApproveDetailRepresentation(GoodsApproveBean bean, Map goodsClassifyMap,
                                            List<GoodsGuaranteeBean> goodsGuarantees, String goodsUnitName,
                                            Integer settlementMode, Float serviceRate, PostageModelBean postageModelBean) {
        this.goodsName = bean.getGoodsName();
        this.goodsSubTitle = bean.getGoodsSubTitle();
        this.goodsClassifyId = bean.getGoodsClassifyId();
        if (null != goodsClassifyMap) {
            this.goodsClassify = null == goodsClassifyMap.get("name") ? "" : (String) goodsClassifyMap.get("name");
            this.goodsClassifyIds = null == goodsClassifyMap.get("ids") ? null : (List) goodsClassifyMap.get("ids");
        }
        this.goodsBrandId = bean.getGoodsBrandId();
        this.goodsBrandName = bean.getGoodsBrandName();
        this.goodsUnitId = bean.getGoodsUnitId();
        this.goodsUnitName = goodsUnitName;
        this.goodsMinQuantity = bean.getGoodsMinQuantity();
        this.goodsBarCode = bean.getGoodsBarCode();
        this.goodsKeyWord = JsonUtils.toList(bean.getGoodsKeyWord(), String.class);
        if (null != goodsGuarantees && goodsGuarantees.size() > 0) {
            /*if (null == goodsGuarantee) {
                this.goodsGuarantee = new ArrayList<>();
            }
            for (GoodsGuaranteeBean guaranteeBean : goodsGuarantees) {
                this.goodsGuarantee.add(guaranteeBean.getGuaranteeDesc());
            }*/
            this.goodsGuarantee = JsonUtils.toList(JsonUtils.toStr(goodsGuarantees), Map.class);
        }
        this.goodsSpecifications = JsonUtils.toList(bean.getGoodsSpecifications(), Map.class);
        List<Map> list = new ArrayList<>();
        for (GoodsSkuApproveBean goodsSkuBean : bean.getGoodsSkuApproves()) {
            Map map = new HashMap<>();
            map.put("goodsId", goodsSkuBean.getGoodsId());
            map.put("skuId", goodsSkuBean.getSkuId());
            map.put("skuName", goodsSkuBean.getSkuName());
            map.put("availableNum", goodsSkuBean.getAvailableNum());
            map.put("weight", goodsSkuBean.getWeight());
            map.put("photographPrice", Utils.moneyFormatCN(goodsSkuBean.getPhotographPrice()));//拍获价
            if (null != goodsSkuBean.getMarketPrice()) {
                map.put("marketPrice", Utils.moneyFormatCN(goodsSkuBean.getMarketPrice()));//市场价
            } else {
                map.put("marketPrice", goodsSkuBean.getMarketPrice());
            }
            if (null != goodsSkuBean.getSupplyPrice()) {
                map.put("supplyPrice", Utils.moneyFormatCN(goodsSkuBean.getSupplyPrice()));//供货价
            } else {
                map.put("supplyPrice", goodsSkuBean.getSupplyPrice());
            }
            map.put("goodsCode", goodsSkuBean.getGoodsCode());
            map.put("showStatus", goodsSkuBean.getShowStatus());
            map.put("show", goodsSkuBean.isShow());
            list.add(map);
        }
        this.goodsSKUs = list;
        this.goodsMainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.goodsDesc = bean.getGoodsDesc();
        this.approveStatus = bean.getApproveStatus();
        this.rejectReason = bean.getRejectReason();
        this.settlementMode = settlementMode;
        this.serviceRate = serviceRate;
        this.skuFlag = bean.getSkuFlag();
        this.goodsPostageId = bean.getGoodsPostageId();
        this.goodsShelves = bean.getGoodsShelves();
        if (null != postageModelBean) {
            this.goodsPostageName = postageModelBean.getModelName();
        }
    }

    public String getGoodsPostageName() {
        return goodsPostageName;
    }

    public void setGoodsPostageName(String goodsPostageName) {
        this.goodsPostageName = goodsPostageName;
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

    public String getGoodsClassify() {
        return goodsClassify;
    }

    public void setGoodsClassify(String goodsClassify) {
        this.goodsClassify = goodsClassify;
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

    public String getGoodsUnitName() {
        return goodsUnitName;
    }

    public void setGoodsUnitName(String goodsUnitName) {
        this.goodsUnitName = goodsUnitName;
    }

    public Integer getGoodsMinQuantity() {
        return goodsMinQuantity;
    }

    public void setGoodsMinQuantity(Integer goodsMinQuantity) {
        this.goodsMinQuantity = goodsMinQuantity;
    }

    public String getGoodsBarCode() {
        return goodsBarCode;
    }

    public void setGoodsBarCode(String goodsBarCode) {
        this.goodsBarCode = goodsBarCode;
    }

    public List<Map> getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(List<Map> goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public List<Map> getGoodsSKUs() {
        return goodsSKUs;
    }

    public void setGoodsSKUs(List<Map> goodsSKUs) {
        this.goodsSKUs = goodsSKUs;
    }

    public List<String> getGoodsMainImages() {
        return goodsMainImages;
    }

    public void setGoodsMainImages(List<String> goodsMainImages) {
        this.goodsMainImages = goodsMainImages;
    }

    public String getGoodsDesc() {
        return goodsDesc;
    }

    public void setGoodsDesc(String goodsDesc) {
        this.goodsDesc = goodsDesc;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public List<String> getGoodsKeyWord() {
        return goodsKeyWord;
    }

    public void setGoodsKeyWord(List<String> goodsKeyWord) {
        this.goodsKeyWord = goodsKeyWord;
    }

    public Integer getSettlementMode() {
        return settlementMode;
    }

    public void setSettlementMode(Integer settlementMode) {
        this.settlementMode = settlementMode;
    }

    public Float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Float serviceRate) {
        this.serviceRate = serviceRate;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }

    public List<Map> getGoodsGuarantee() {
        return goodsGuarantee;
    }

    public void setGoodsGuarantee(List<Map> goodsGuarantee) {
        this.goodsGuarantee = goodsGuarantee;
    }

    public String getGoodsPostageId() {
        return goodsPostageId;
    }

    public void setGoodsPostageId(String goodsPostageId) {
        this.goodsPostageId = goodsPostageId;
    }

    public List getGoodsClassifyIds() {
        return goodsClassifyIds;
    }

    public void setGoodsClassifyIds(List goodsClassifyIds) {
        this.goodsClassifyIds = goodsClassifyIds;
    }

    public Integer getGoodsShelves() {
        return goodsShelves;
    }

    public void setGoodsShelves(Integer goodsShelves) {
        this.goodsShelves = goodsShelves;
    }
}
