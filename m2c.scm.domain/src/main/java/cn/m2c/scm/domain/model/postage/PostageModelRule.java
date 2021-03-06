package cn.m2c.scm.domain.model.postage;

import cn.m2c.ddd.common.domain.model.IdentifiedValueObject;

/**
 * 运费模板规则
 *
 * @author owen
 * @version 1.0.0 2017-09-30
 */
public class PostageModelRule extends IdentifiedValueObject {

    /**
     * 运费模板
     */
    private PostageModel postageModel;

    /**
     * 运费模板规则id
     */
    private String ruleId;

    /**
     * 配送地址
     */
    private String address;

    /**
     * 配送地址城市编码
     */
    private String cityCode;

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

    public PostageModelRule() {
        super();
    }

    public PostageModelRule(PostageModel postageModel, String ruleId, String address, String cityCode,
                            Float firstWeight, Integer firstPiece, Long firstPostage, Float continuedWeight, Integer continuedPiece, Long continuedPostage, Integer defaultFlag) {
        this.postageModel = postageModel;
        this.ruleId = ruleId;
        this.address = address;
        this.cityCode = cityCode;
        this.firstWeight = firstWeight;
        this.firstPiece = firstPiece;
        this.firstPostage = firstPostage;
        this.continuedWeight = continuedWeight;
        this.continuedPiece = continuedPiece;
        this.continuedPostage = continuedPostage;
        this.defaultFlag = defaultFlag;
    }
}