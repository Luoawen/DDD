package cn.m2c.scm.domain.model.postage;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

import java.util.List;

/**
 * 运费模板
 *
 * @author owen
 * @version 1.0.0 2017-09-30
 */
public class PostageModel extends ConcurrencySafeEntity {
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
     * 1:正常 2：删除
     */
    private Integer modelStatus;

    /**
     * 模板说明
     */
    private String modelDescription;

    /**
     * 模板规则
     */
    private List<PostageModelRule> postageModelRules;

    public PostageModel() {
        super();
    }
}