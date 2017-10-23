package cn.m2c.scm.application.classify.data.bean;

import cn.m2c.ddd.common.persistence.orm.ColumnAlias;

/**
 * 商品分类
 */
public class GoodsClassifyBean {
    /**
     * 分类id
     */
    @ColumnAlias(value = "classify_id")
    private String classifyId;

    /**
     * 分类名称
     */
    @ColumnAlias(value = "classify_name")
    private String classifyName;

    /**
     * 父级分类id，顶级分类id为-1
     */
    @ColumnAlias(value = "parent_classify_id")
    private String parentClassifyId;

    /**
     * 服务费率
     */
    @ColumnAlias(value = "service_rate")
    private Float serviceRate;

    /**
     * 1:正常，2：删除
     */
    @ColumnAlias(value = "status")
    private Integer status;

    @ColumnAlias(value = "level")
    private Integer level;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}