package cn.m2c.scm.application.postage.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

import java.util.List;

/**
 * 运费模板
 */
public class PostageModelBean {
    /**
     * 主键id
     */
    @ColumnAlias(value = "id")
    private Integer id;

    /**
     * 经销商id
     */
    @ColumnAlias(value = "dealer_id")
    private String dealerId;

    /**
     * 运费模板id
     */
    @ColumnAlias(value = "model_id")
    private String modelId;

    /**
     * 运费模块名称
     */
    @ColumnAlias(value = "model_name")
    private String modelName;

    /**
     * 计费方式,0:按重量,1:按件数
     */
    @ColumnAlias(value = "charge_type")
    private Integer chargeType;

    /**
     * 商品使用数量
     */
    @ColumnAlias(value = "goods_user_num")
    private Integer goodsUserNum;

    /**
     * 1:正常 2：删除
     */
    @ColumnAlias(value = "model_status")
    private Integer modelStatus;

    /**
     * 模板说明
     */
    @ColumnAlias(value = "model_description")
    private String modelDescription;

    private List<PostageModelRuleBean> postageModelRuleBeans;

    public List<PostageModelRuleBean> getPostageModelRuleBeans() {
        return postageModelRuleBeans;
    }

    public void setPostageModelRuleBeans(List<PostageModelRuleBean> postageModelRuleBeans) {
        this.postageModelRuleBeans = postageModelRuleBeans;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
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

    public Integer getModelStatus() {
        return modelStatus;
    }

    public void setModelStatus(Integer modelStatus) {
        this.modelStatus = modelStatus;
    }

    public String getModelDescription() {
        return modelDescription;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public Integer getGoodsUserNum() {
        return goodsUserNum;
    }

    public void setGoodsUserNum(Integer goodsUserNum) {
        this.goodsUserNum = goodsUserNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
