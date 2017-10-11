package cn.m2c.scm.application.classify.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商品分类
 */
public class GoodsClassifyModifyCommand extends AssertionConcern implements Serializable {
    /**
     * 分类id
     */
    private String classifyId;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 服务费率
     */
    private Float serviceRate;

    public GoodsClassifyModifyCommand(String classifyId, String classifyName) {
        this.classifyId = classifyId;
        this.classifyName = classifyName;
    }

    public GoodsClassifyModifyCommand(String classifyId, Float serviceRate) {
        this.classifyId = classifyId;
        this.serviceRate = serviceRate;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public Float getServiceRate() {
        return serviceRate;
    }
}
