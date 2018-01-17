package cn.m2c.scm.application.postage.command;

import cn.m2c.common.JsonUtils;
import cn.m2c.common.MCode;
import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.NegativeException;
import cn.m2c.scm.domain.util.GetMapValueUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        if(chargeType == 0 || chargeType == 1) {//0按重量，1按件数
        	if (StringUtils.isEmpty(postageModelRule)) {
                throw new NegativeException(MCode.V_1, "请输入完整的运费模板规则");
            }

            List<Map> ruleList = JsonUtils.toList(postageModelRule, Map.class);
            if (null != ruleList && ruleList.size() > 0) {
                Set set = new HashSet<>();
                Integer size = 0;
                for (Map map : ruleList) {
                    Integer defaultFlag = GetMapValueUtils.getIntFromMapKey(map, "defaultFlag");
                    if (null != defaultFlag && defaultFlag == 1) {
                        String cityCode = GetMapValueUtils.getStringFromMapKey(map, "cityCode");
                        if (StringUtils.isEmpty(cityCode)) {
                            throw new NegativeException(MCode.V_1, "配送城市编码为空");
                        }
                        String[] cityCodeArr = cityCode.split(",");
                        Set<String> cityCodeSet = new HashSet<>(Arrays.asList(cityCodeArr));
                        size = size + cityCodeSet.size();
                        set.addAll(cityCodeSet);
                    }

                    Long firstPostage = new BigDecimal(GetMapValueUtils.getFloatFromMapKey(map, "firstPostage") * 10000).longValue();
                    Long continuedPostage = new BigDecimal(GetMapValueUtils.getFloatFromMapKey(map, "continuedPostage") * 10000).longValue();
                    map.put("firstPostage", firstPostage);
                    map.put("continuedPostage", continuedPostage);
                }
                if (!size.equals(set.size())) { //城市编码冲突
                    throw new NegativeException(MCode.V_1, "运费模板指定地区规则冲突");
                }
            }
            this.postageModelRule = JsonUtils.toStr(ruleList);
        }
        this.dealerId = dealerId;
        this.modelId = modelId;
        this.modelName = modelName;
        this.chargeType = chargeType;
        this.modelDescription = modelDescription;
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
