package cn.m2c.scm.application.postage.data.representation;

import cn.m2c.scm.application.postage.data.bean.PostageModelBean;
import cn.m2c.scm.application.postage.data.bean.PostageModelRuleBean;
import cn.m2c.scm.application.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运费模板列表查询
 */
public class PostageModelDetailRepresentation {
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
     * 模板说明
     */
    private String modelDescription;

    /**
     * 模板规则
     */
    private List<Map> postageModelRules;

    public PostageModelDetailRepresentation(PostageModelBean postageModel) {
        this.modelId = postageModel.getModelId();
        this.modelName = postageModel.getModelName();
        this.chargeType = postageModel.getChargeType();
        this.goodsUserNum = postageModel.getGoodsUserNum();
        this.modelDescription = postageModel.getModelDescription();
        List<PostageModelRuleBean> rules = postageModel.getPostageModelRuleBeans();
        this.postageModelRules = new ArrayList<>();
        for (PostageModelRuleBean ruleBean : rules) {
            Map map = new HashMap<>();
            map.put("address", ruleBean.getAddress());
            map.put("firstWeight", ruleBean.getFirstWeight());
            map.put("firstPiece", ruleBean.getFirstPiece());
            map.put("firstPostage", Utils.moneyFormatCN(ruleBean.getFirstPostage()));
            map.put("continuedWeight", ruleBean.getContinuedWeight());
            map.put("continuedPiece", ruleBean.getContinuedPiece());
            map.put("continuedPostage", Utils.moneyFormatCN(ruleBean.getContinuedPostage()));
            map.put("defaultFlag", ruleBean.getDefaultFlag());
            map.put("cityCode", ruleBean.getCityCode());
            this.postageModelRules.add(map);
        }
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getGoodsUserNum() {
        return goodsUserNum;
    }

    public void setGoodsUserNum(Integer goodsUserNum) {
        this.goodsUserNum = goodsUserNum;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public List<Map> getPostageModelRules() {
        return postageModelRules;
    }

    public void setPostageModelRules(List<Map> postageModelRules) {
        this.postageModelRules = postageModelRules;
    }
}
