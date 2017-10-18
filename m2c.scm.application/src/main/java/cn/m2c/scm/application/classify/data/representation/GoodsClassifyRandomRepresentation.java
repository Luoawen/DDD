package cn.m2c.scm.application.classify.data.representation;

import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;

/**
 * 随机取分类
 */
public class GoodsClassifyRandomRepresentation {
    /**
     * 分类id
     */
    private String classifyId;

    /**
     * 分类名称
     */
    private String classifyName;

    public GoodsClassifyRandomRepresentation(GoodsClassifyBean bean) {
        this.classifyId = bean.getClassifyId();
        this.classifyName = bean.getClassifyName();
    }

    public String getClassifyId() {
        return classifyId;
    }

    public void setClassifyId(String classifyId) {
        this.classifyId = classifyId;
    }

    public String getClassifyName() {
        return classifyName;
    }

    public void setClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }
}
