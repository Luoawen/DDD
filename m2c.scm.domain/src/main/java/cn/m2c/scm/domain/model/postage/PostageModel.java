package cn.m2c.scm.domain.model.postage;

import cn.m2c.common.JsonUtils;
import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.scm.domain.IDGenerator;
import cn.m2c.scm.domain.util.GetMapValueUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * 商品使用数量
     */
    private Integer goodsUserNum;

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

    public PostageModel(String dealerId, String modelId, String modelName, Integer chargeType,
                        String modelDescription, String postageModelRule) {
        this.dealerId = dealerId;
        this.modelId = modelId;
        this.modelName = modelName;
        this.chargeType = chargeType;
        this.modelStatus = 1;
        this.modelDescription = modelDescription;

        //  模板规则
        List<Map> ruleList = JsonUtils.toList(postageModelRule, Map.class);
        if (null != ruleList && ruleList.size() > 0) {
            for (Map map : ruleList) {
                if (null == this.postageModelRules) {
                    this.postageModelRules = new ArrayList<>();
                }
                this.postageModelRules.add(createPostageModelRule(map));
            }
        }
    }

    public void modifyPostageModel(String dealerId, String modelId, String modelName, Integer chargeType,
                                   String modelDescription, String postageModelRule) {
        this.modelName = modelName;
        this.chargeType = chargeType;
        this.modelDescription = modelDescription;

        //  模板规则
        List<Map> ruleList = JsonUtils.toList(postageModelRule, Map.class);
        if (null != ruleList && ruleList.size() > 0) {
            if (null != this.postageModelRules && this.postageModelRules.size() > 0) {
                this.postageModelRules.clear();
            } else {
                this.postageModelRules = new ArrayList<>();
            }

            for (Map map : ruleList) {
                this.postageModelRules.add(createPostageModelRule(map));
            }
        }
    }

    public void deletePostageModel() {
        this.modelStatus = 2;
    }

    private PostageModelRule createPostageModelRule(Map map) {
        String address = GetMapValueUtils.getStringFromMapKey(map, "address");
        String addressStructure = GetMapValueUtils.getStringFromMapKey(map, "addressStructure");
        String cityCode = GetMapValueUtils.getStringFromMapKey(map, "cityCode");
        Float firstWeight = GetMapValueUtils.getFloatFromMapKey(map, "firstWeight");
        Integer firstPiece = GetMapValueUtils.getIntFromMapKey(map, "firstPiece");
        Long firstPostage = GetMapValueUtils.getLongFromMapKey(map, "firstPostage");
        Float continuedWeight = GetMapValueUtils.getFloatFromMapKey(map, "continuedWeight");
        Integer continuedPiece = GetMapValueUtils.getIntFromMapKey(map, "continuedPiece");
        Long continuedPostage = GetMapValueUtils.getLongFromMapKey(map, "continuedPostage");
        Integer defaultFlag = GetMapValueUtils.getIntFromMapKey(map, "defaultFlag");
        PostageModelRule rule = new PostageModelRule(this, IDGenerator.get(IDGenerator.SCM_POSTAGE_RULE_PREFIX_TITLE),
                address, addressStructure, cityCode,
                firstWeight, firstPiece, firstPostage, continuedWeight, continuedPiece, continuedPostage, defaultFlag);
        return rule;
    }
}