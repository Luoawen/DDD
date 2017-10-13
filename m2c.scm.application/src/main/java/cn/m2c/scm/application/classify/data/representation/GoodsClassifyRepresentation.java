package cn.m2c.scm.application.classify.data.representation;

import cn.m2c.scm.application.classify.data.bean.GoodsClassifyBean;

/**
 * 商品分类
 */
public class GoodsClassifyRepresentation {
    /**
     * 分类id
     */
    private String classifyId;

    /**
     * 分类名称
     */
    private String classifyName;

    /**
     * 父级分类id，顶级分类id为-1
     */
    private String parentClassifyId;

    /**
     * 服务费率
     */
    private Float serviceRate;

    public GoodsClassifyRepresentation(GoodsClassifyBean bean) {
        this.classifyId = bean.getClassifyId();
        this.classifyName = bean.getClassifyName();
        this.parentClassifyId = bean.getParentClassifyId();
        this.serviceRate = bean.getServiceRate();
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

    public String getParentClassifyId() {
        return parentClassifyId;
    }

    public void setParentClassifyId(String parentClassifyId) {
        this.parentClassifyId = parentClassifyId;
    }

    public Float getServiceRate() {
        return serviceRate;
    }

    public void setServiceRate(Float serviceRate) {
        this.serviceRate = serviceRate;
    }
}