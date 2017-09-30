package cn.m2c.scm.application.postage.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 运费模板
 */
public class PostageModelCommand extends AssertionConcern implements Serializable {
    /**
     * 经销商id
     */
    private String dealerId;

    /**
     * 运费模板id
     */
    private String modelId;

    /**
     * 运费模块名称
     */
    private String modelName;

    /**
     * 计费方式,0:按重量,1:按件数
     */
    private Integer chargeType;

    /**
     * 模板说明
     */
    private String modelDescription;

    /**
     * 模板规则
     */
    private String postageModelRule;

    public PostageModelCommand(String dealerId, String modelId, String modelName, Integer chargeType, String modelDescription, String postageModelRule) {
        this.dealerId = dealerId;
        this.modelId = modelId;
        this.modelName = modelName;
        this.chargeType = chargeType;
        this.modelDescription = modelDescription;
        this.postageModelRule = postageModelRule;
    }

    public String getDealerId() {
        return dealerId;
    }

    public String getModelId() {
        return modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public String getPostageModelRule() {
        return postageModelRule;
    }
}
