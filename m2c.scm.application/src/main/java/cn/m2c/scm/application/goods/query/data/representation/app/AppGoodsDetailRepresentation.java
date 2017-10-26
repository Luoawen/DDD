package cn.m2c.scm.application.goods.query.data.representation.app;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsGuaranteeBean;
import cn.m2c.scm.application.goods.query.data.bean.GoodsSkuBean;
import com.baidu.disconf.client.usertools.DisconfDataGetter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * app商品详情
 */
public class AppGoodsDetailRepresentation {
    private static final String M2C_HOST_URL = DisconfDataGetter.getByFileItem("constants.properties", "m2c.host.url").toString().trim();
    private String dealerId;
    private String goodsId;
    private String classifyId;
    private String goodsName;
    private String goodsSubTitle;
    private String goodsUnitId;
    private String goodsUnitName;
    private Integer goodsMinQuantity;
    private List<Map> goodsGuarantee;
    private List<Map> goodsSpecifications;
    private List<AppGoodsSkuRepresentation> goodsSKUs;
    private List<String> goodsMainImages;
    private String mresId;
    private String goodsDescUrl;
    private Map goodsComment;
    private List<Map> fullCuts;

    public AppGoodsDetailRepresentation(GoodsBean bean, List<GoodsGuaranteeBean> goodsGuaranteeBeans,
                                        String goodsUnitName, String mresId, Integer commentTotal,
                                        GoodsCommentBean goodsCommentBean, List<Map> fullCuts) {
        this.dealerId = dealerId;
        this.goodsId = bean.getGoodsId();
        this.classifyId = bean.getGoodsClassifyId();
        this.goodsName = bean.getGoodsName();
        this.goodsSubTitle = bean.getGoodsSubTitle();
        this.goodsUnitId = bean.getGoodsUnitId();
        this.goodsUnitName = goodsUnitName;
        this.goodsMinQuantity = bean.getGoodsMinQuantity();
        this.goodsGuarantee = JsonUtils.toList(JsonUtils.toStr(goodsGuaranteeBeans), Map.class);
        this.goodsSpecifications = JsonUtils.toList(bean.getGoodsSpecifications(), Map.class);
        if (null != bean.getGoodsSkuBeans() && bean.getGoodsSkuBeans().size() > 0) {
            if (null == this.goodsSKUs) {
                this.goodsSKUs = new ArrayList<>();
            }
            for (GoodsSkuBean sukBean : bean.getGoodsSkuBeans()) {
                this.goodsSKUs.add(new AppGoodsSkuRepresentation(sukBean));
            }
        }
        this.goodsMainImages = JsonUtils.toList(bean.getGoodsMainImages(), String.class);
        this.mresId = mresId;
        this.goodsDescUrl = M2C_HOST_URL + "/m2c.scm/goods/app/desc?goodsId=" + this.goodsId;

        if (null == this.goodsComment) {
            this.goodsComment = new HashMap<>();
        }
        this.goodsComment.put("commentTotal", commentTotal);
        if (null != goodsCommentBean) {
            this.goodsComment.put("buyerIcon", goodsCommentBean.getBuyerIcon());
            this.goodsComment.put("buyerPhoneNumber", goodsCommentBean.getBuyerPhoneNumber());
            this.goodsComment.put("buyerName", goodsCommentBean.getBuyerName());
            this.goodsComment.put("skuName", goodsCommentBean.getSkuName());
            this.goodsComment.put("goodsNum", goodsCommentBean.getGoodsNum());
            this.goodsComment.put("starLevel", goodsCommentBean.getStarLevel());
            this.goodsComment.put("commentContent", goodsCommentBean.getCommentContent());

            String images = goodsCommentBean.getCommentImages();
            List<String> imageList = JsonUtils.toList(images, String.class);
            this.goodsComment.put("commentImages", imageList);

            String replyCommentContent = "";
            if (null != goodsCommentBean.getGoodsReplyCommentBean()) {
                replyCommentContent = goodsCommentBean.getGoodsReplyCommentBean().getReplyContent();
            }
            this.goodsComment.put("replyCommentContent", replyCommentContent);
        }
        this.fullCuts = fullCuts;
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

    public List<AppGoodsSkuRepresentation> getGoodsSKUs() {
        return goodsSKUs;
    }

    public void setGoodsSKUs(List<AppGoodsSkuRepresentation> goodsSKUs) {
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
}
