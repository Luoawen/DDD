package cn.m2c.scm.application.goods.query.data.representation.mini;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baidu.disconf.client.usertools.DisconfDataGetter;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import cn.m2c.scm.application.goods.query.data.representation.mini.MiniGoodsSkuRepresentation;
import cn.m2c.scm.application.special.data.bean.GoodsSkuSpecialBean;
import cn.m2c.scm.application.special.data.bean.GoodsSpecialBean;
import cn.m2c.scm.application.utils.Utils;

/**
 * 微信小程序商品详情
 */
public class MiniGoodsDetailRepresentation {
	//微信小程序host url
	private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url")
            .toString().trim();
    private String dealerId;
    private String dealerName;
    private String goodsId;
    private String classifyId;
    private String goodsName;
    private String goodsSubTitle;
    private String goodsUnitId;
    private String goodsUnitName;
    private Integer goodsMinQuantity;
    private List<Map> goodsGuarantee;
    private List<Map> goodsSpecifications;
    private List<MiniGoodsSkuRepresentation> goodsSKUs;
    private List<String> goodsMainImages;
    private String goodsMainVideo;
    private String mresId;
    private String goodsDescUrl;
    private Integer skuFlag;
    private String desc;

    
    public MiniGoodsDetailRepresentation(GoodsBean bean, List<GoodsGuaranteeBean> goodsGuaranteeBeans,
                                        String goodsUnitName, String mresId, GoodsSpecialBean goodsSpecialBean) {
        this.skuFlag = bean.getSkuFlag();
        this.dealerId = bean.getDealerId();
        this.dealerName = bean.getDealerName();
        this.goodsId = bean.getGoodsId();
        this.classifyId = bean.getGoodsClassifyId();
        this.goodsName = bean.getGoodsName();
        this.goodsSubTitle = bean.getGoodsSubTitle();
        this.goodsUnitId = bean.getGoodsUnitId();
        this.goodsUnitName = goodsUnitName;
        this.goodsMinQuantity = bean.getGoodsMinQuantity();
        this.goodsGuarantee = JsonUtils.toList(JsonUtils.toStr(goodsGuaranteeBeans), Map.class);
        this.goodsSpecifications = JsonUtils.toList(bean.getGoodsSpecifications(), Map.class);
        if (this.skuFlag == 0) {//是否是多规格：0：单规格，1：多规格
            List<Map> list = new ArrayList<>();
            Map map = new HashMap<>();
            map.put("itemName", "规格");
            List<Map> valueList = new ArrayList<>();
            Map valueMap = new HashMap<>();
            valueMap.put("spec_name", "默认");
            valueList.add(valueMap);
            map.put("itemValue", valueList);
            list.add(map);
            this.goodsSpecifications = list;
        }

        if (null != bean.getGoodsSkuBeans() && bean.getGoodsSkuBeans().size() > 0) {
            if (null == this.goodsSKUs) {
                this.goodsSKUs = new ArrayList<>();
            }
            for (GoodsSkuBean sukBean : bean.getGoodsSkuBeans()) {
                this.goodsSKUs.add(new MiniGoodsSkuRepresentation(sukBean));
            }
        }
        this.goodsMainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.goodsMainVideo = bean.getGoodsMainVideo();
        this.mresId = mresId;
        this.goodsDescUrl = M2C_HOST_URL + "/m2c.scm/goods/mini/desc?goodsId=" + this.goodsId;

        this.desc = bean.getGoodsDesc();

        // 特惠价
        if (null != goodsSpecialBean) {
            if (null != this.goodsSKUs && this.goodsSKUs.size() > 0) {
                List<GoodsSkuSpecialBean> skuSpecials = goodsSpecialBean.getGoodsSpecialSkuBeans();
                for (MiniGoodsSkuRepresentation sku : this.goodsSKUs) {
                    if (null != skuSpecials && skuSpecials.size() > 0) {
                        for (GoodsSkuSpecialBean skuSpecialBean : skuSpecials) {
                            if (skuSpecialBean.getSkuId().equals(sku.getSkuId())) {
                                sku.setStrSpecialPrice(Utils.moneyFormatCN(skuSpecialBean.getSpecialPrice()));
                            }
                        }
                    }
                }
            }
        }
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

    public List<Map> getGoodsGuarantee() {
        return goodsGuarantee;
    }

    public void setGoodsGuarantee(List<Map> goodsGuarantee) {
        this.goodsGuarantee = goodsGuarantee;
    }

    public List<Map> getGoodsSpecifications() {
        return goodsSpecifications;
    }

    public void setGoodsSpecifications(List<Map> goodsSpecifications) {
        this.goodsSpecifications = goodsSpecifications;
    }

    public List<MiniGoodsSkuRepresentation> getGoodsSKUs() {
        return goodsSKUs;
    }

    public void setGoodsSKUs(List<MiniGoodsSkuRepresentation> goodsSKUs) {
        this.goodsSKUs = goodsSKUs;
    }

    public List<String> getGoodsMainImages() {
        return goodsMainImages;
    }

    public void setGoodsMainImages(List<String> goodsMainImages) {
        this.goodsMainImages = goodsMainImages;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMresId() {
        return mresId;
    }

    public void setMresId(String mresId) {
        this.mresId = mresId;
    }

    public String getGoodsDescUrl() {
        return goodsDescUrl;
    }

    public void setGoodsDescUrl(String goodsDescUrl) {
        this.goodsDescUrl = goodsDescUrl;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public Integer getSkuFlag() {
        return skuFlag;
    }

    public void setSkuFlag(Integer skuFlag) {
        this.skuFlag = skuFlag;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

	public String getGoodsMainVideo() {
		return goodsMainVideo;
	}

	public void setGoodsMainVideo(String goodsMainVideo) {
		this.goodsMainVideo = goodsMainVideo;
	}

    /*public Map getGoodsSpecial() {
        return goodsSpecial;
    }

    public void setGoodsSpecial(Map goodsSpecial) {
        this.goodsSpecial = goodsSpecial;
    }
    
    public String getCustomerTel() {
        return customerTel;
    }

    public void setCustomerTel(String customerTel) {
        this.customerTel = customerTel;
    }
    
    public Map getPhotographGetCoupon() {
        return photographGetCoupon;
    }

    public void setPhotographGetCoupon(Map photographGetCoupon) {
        this.photographGetCoupon = photographGetCoupon;
    }
    
    public String getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public List<Map> getGoodsTags() {
        return goodsTags;
    }

    public void setGoodsTags(List<Map> goodsTags) {
        this.goodsTags = goodsTags;
    }
    
    public List<Map> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<Map> coupons) {
        this.coupons = coupons;
    }
    
    public Map getGoodsComment() {
        return goodsComment;
    }

    public void setGoodsComment(Map goodsComment) {
        this.goodsComment = goodsComment;
    }

    public List<Map> getFullCuts() {
        return fullCuts;
    }

    public void setFullCuts(List<Map> fullCuts) {
        this.fullCuts = fullCuts;
    }*/
}
