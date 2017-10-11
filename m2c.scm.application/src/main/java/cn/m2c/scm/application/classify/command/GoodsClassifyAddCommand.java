package cn.m2c.scm.application.classify.command;

import cn.m2c.ddd.common.AssertionConcern;
import cn.m2c.scm.domain.IDGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * 商品分类
 */
public class GoodsClassifyAddCommand extends AssertionConcern implements Serializable {
    /**
     * 父级分类名称
     */
    private String parentClassifyName;

    /**
     * 子分类名称,list的json字符串
     */
    private String subClassifyNames;

    /**
     * 父级分类id，顶级分类id为-1
     */
    private String parentClassifyId;

    public GoodsClassifyAddCommand(String parentClassifyName, String subClassifyNames, String parentClassifyId) {
        this.parentClassifyName = parentClassifyName;
        this.subClassifyNames = subClassifyNames;
        this.parentClassifyId = parentClassifyId;
        if (StringUtils.isNotEmpty(parentClassifyName)) {
            this.parentClassifyId = IDGenerator.get(IDGenerator.SCM_GOODS_CLASSIFY_PREFIX_TITLE);
        }
    }

    public String getSubClassifyNames() {
        return subClassifyNames;
    }

    public String getParentClassifyId() {
        return parentClassifyId;
    }

    public String getParentClassifyName() {
        return parentClassifyName;
    }
}
