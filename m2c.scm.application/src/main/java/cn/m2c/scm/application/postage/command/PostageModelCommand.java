package cn.m2c.scm.application.postage.command;

import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import org.apache.commons.lang3.StringUtils;

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

    public PostageModelCommand(String dealerId, String modelId) throws NegativeException {
        if (StringUtils.isEmpty(dealerId)) {
            throw new NegativeException(MCode.V_1, "商家ID为空");
        }
        if (StringUtils.isEmpty(modelId)) {
            throw new NegativeException(MCode.V_1, "运费模板ID为空");
        }
        this.dealerId = dealerId;
        this.modelId = modelId;
    }

    public PostageModelCommand(String dealerId, String modelId, String modelName, Integer chargeType, String modelDescription, String postageModelRule) throws NegativeException {
        if (StringUtils.isEmpty(dealerId)) {
            throw new NegativeException(MCode.V_1, "商家ID为空");
        }
        if (StringUtils.isEmpty(modelId)) {
            throw new NegativeException(MCode.V_1, "运费模板ID为空");
        }
        if (StringUtils.isEmpty(modelName)) {
            throw new NegativeException(MCode.V_1, "运费模板名称为空");
        }
        if (null == chargeType) {
            throw new NegativeException(MCode.V_1, "运费模板计费方式为空");
        }
        String a[] = postageModelRule.split(",");
        if ("\"index\":\"\"}]".equals(a[5]) || "\"continuedPostage\":\"\"".equals(a[4]) ||
        		"\"continuedWeight\":\"\"".equals(a[3]) || "\"firstPostage\":\"\"".equals(a[2]) || "\"firstWeight\":\"\"".equals(a[1])) {
        	throw new NegativeException(MCode.V_1, "请输入完整的运费模板规则");
		}
        
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
