package cn.m2c.scm.application.postage.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 运费模板
 */
public class PostageModelRuleBean {

    /**
     * 运费模板规则id
     */
    @ColumnAlias(value = "rule_id")
    private String ruleId;

    /**
     * 配送地址
     */
    @ColumnAlias(value = "address")
    private String address;

    /**
     * 配送地址城市编码
     */
    @ColumnAlias(value = "city_code")
    private String cityCode;

    /**
     * 首重
     */
    @ColumnAlias(value = "first_weight")
    private Float firstWeight;

    /**
     * 首件
     */
    @ColumnAlias(value = "first_piece")
    private Integer firstPiece;

    /**
     * 首重/件的运费
     */
    @ColumnAlias(value = "first_postage")
    private Long firstPostage;

    /**
     * 续重
     */
    @ColumnAlias(value = "continued_weight")
    private Float continuedWeight;

    /**
     * 续件
     */
    @ColumnAlias(value = "continued_piece")
    private Integer continuedPiece;

    /**
     * 续重/件的运费
     */
    @ColumnAlias(value = "continued_postage")
    private Long continuedPostage;

    /**
     * 全国（默认运费），0：是，1：不是
     */
    @ColumnAlias(value = "default_flag")
    private Integer defaultFlag;

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

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
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
}
