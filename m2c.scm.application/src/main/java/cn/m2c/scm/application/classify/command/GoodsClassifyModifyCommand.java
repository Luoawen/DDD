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

    public GoodsClassifyModifyCommand(String classifyId, String classifyName) {
        this.classifyId = classifyId;
        this.classifyName = classifyName;
    }

    public String getClassifyId() {
        return classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }
}
