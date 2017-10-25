package cn.m2c.scm.application.classify.command;

import cn.m2c.ddd.common.AssertionConcern;

import java.io.Serializable;

/**
 * 商品分类
 */
public class GoodsClassifyAddCommand extends AssertionConcern implements Serializable {
    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 子分类名称,list的json字符串
     */
    private String subClassifyNames;

    /**
     * 父级分类id，顶级分类id为-1
     */
    private String parentClassifyId;

    private Integer level;

    public GoodsClassifyAddCommand(String classifyName, String subClassifyNames, String parentClassifyId, Integer level) {
        this.classifyName = classifyName;
        this.subClassifyNames = subClassifyNames;
        this.parentClassifyId = parentClassifyId;
        this.level = level;
    }

    public String getSubClassifyNames() {
        return subClassifyNames;
    }

    public String getParentClassifyId() {
        return parentClassifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public Integer getLevel() {
        return level;
    }
}
