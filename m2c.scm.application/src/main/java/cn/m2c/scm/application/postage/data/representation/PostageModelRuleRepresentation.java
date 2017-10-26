package cn.m2c.scm.application.postage.data.representation;

import cn.m2c.scm.application.postage.data.bean.PostageModelRuleBean;

/**
 * 模板规则
 */
public class PostageModelRuleRepresentation {
    /**
     * 运费模板规则id
     */
    private String ruleId;

    /**
     * 配送地址
     */
    private String address;

    /**
     * 首重
     */
    private Float firstWeight;

    /**
     * 首件
     */
    private Integer firstPiece;

    /**
     * 首重/件的运费
     */
    private Long firstPostage;

    /**
     * 续重
     */
    private Float continuedWeight;

    /**
     * 续件
     */
    private Integer continuedPiece;

    /**
     * 续重/件的运费
     */
    private Long continuedPostage;

    /**
     * 全国（默认运费），0：是，1：不是
     */
    private Integer defaultFlag;

    /**
     * 计费方式,0:按重量,1:按件数
     */
    private Integer chargeType;

    public PostageModelRuleRepresentation(PostageModelRuleBean bean, Integer chargeType) {
        this.ruleId = bean.getRuleId();
        this.address = bean.getAddress();
        this.firstWeight = bean.getFirstWeight();
        this.firstPiece = bean.getFirstPiece();
        this.firstPostage = bean.getFirstPostage();
        this.continuedWeight = bean.getContinuedWeight();
        this.continuedPiece = bean.getContinuedPiece();
        this.continuedPostage = bean.getContinuedPostage();
        this.defaultFlag = bean.getDefaultFlag();
        this.chargeType = chargeType;
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getFirstWeight() {
        return firstWeight;
    }

    public void setFirstWeight(Float firstWeight) {
        this.firstWeight = firstWeight;
    }

    public Integer getFirstPiece() {
        return firstPiece;
    }

    public void setFirstPiece(Integer firstPiece) {
        this.firstPiece = firstPiece;
    }

    public Long getFirstPostage() {
        return firstPostage;
    }

    public void setFirstPostage(Long firstPostage) {
        this.firstPostage = firstPostage;
    }

    public Float getContinuedWeight() {
        return continuedWeight;
    }

    public void setContinuedWeight(Float continuedWeight) {
        this.continuedWeight = continuedWeight;
    }

    public Integer getContinuedPiece() {
        return continuedPiece;
    }

    public void setContinuedPiece(Integer continuedPiece) {
        this.continuedPiece = continuedPiece;
    }

    public Long getContinuedPostage() {
        return continuedPostage;
    }

    public void setContinuedPostage(Long continuedPostage) {
        this.continuedPostage = continuedPostage;
    }

    public Integer getDefaultFlag() {
        return defaultFlag;
    }

    public void setDefaultFlag(Integer defaultFlag) {
        this.defaultFlag = defaultFlag;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }
}
