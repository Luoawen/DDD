package cn.m2c.scm.application.comment.query.data.representation.app;

import cn.m2c.common.JsonUtils;
import cn.m2c.scm.application.comment.query.data.bean.GoodsCommentBean;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 评论展示
 */
public class AppCommentRepresentation {
    private String buyerIcon;
    private String buyerPhoneNumber;
    private String buyerName;
    private String skuName;
    private Integer goodsNum;
    private Integer starLevel;
    private String commentContent;
    private List commentImages;
    private String replyCommentContent;
    private String commentTime;
    private String dealerName;

    public AppCommentRepresentation(GoodsCommentBean bean) {
        this.buyerIcon = bean.getBuyerIcon();
        this.buyerPhoneNumber = bean.getBuyerPhoneNumber();
        this.buyerName = bean.getBuyerName();
        if (StringUtils.isEmpty(bean.getSkuName())) {
            this.skuName = "默认";
        } else {
            this.skuName = bean.getSkuName();
        }
        this.goodsNum = bean.getGoodsNum();
        this.starLevel = bean.getStarLevel();
        this.commentContent = bean.getCommentContent();

        String images = bean.getCommentImages();
        List<String> imageList = JsonUtils.toList(images, String.class);
        this.commentImages = imageList;
        if (null != bean.getGoodsReplyCommentBean()) {
            this.replyCommentContent = bean.getGoodsReplyCommentBean().getReplyContent();
        }

        SimpleDateFormat yyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd");
        this.commentTime = yyyMMddFormat.format(bean.getCreatedDate());
        this.dealerName = bean.getDealerName();
    }

    public String getBuyerIcon() {
        return buyerIcon;
    }

    public void setBuyerIcon(String buyerIcon) {
        this.buyerIcon = buyerIcon;
    }

    public String getBuyerPhoneNumber() {
        return buyerPhoneNumber;
    }

    public void setBuyerPhoneNumber(String buyerPhoneNumber) {
        this.buyerPhoneNumber = buyerPhoneNumber;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getStarLevel() {
        return starLevel;
    }

    public void setStarLevel(Integer starLevel) {
        this.starLevel = starLevel;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public List getCommentImages() {
        return commentImages;
    }

    public void setCommentImages(List commentImages) {
        this.commentImages = commentImages;
    }

    public String getReplyCommentContent() {
        return replyCommentContent;
    }

    public void setReplyCommentContent(String replyCommentContent) {
        this.replyCommentContent = replyCommentContent;
    }

    public String getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(String commentTime) {
        this.commentTime = commentTime;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }
}
