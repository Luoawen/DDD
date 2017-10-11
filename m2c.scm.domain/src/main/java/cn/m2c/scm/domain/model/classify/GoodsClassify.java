package cn.m2c.scm.domain.model.classify;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;

/**
 * 商品分类
 */
public class GoodsClassify extends ConcurrencySafeEntity {
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

    /**
     * 1:正常，2：删除
     */
    private Integer status;

    public GoodsClassify() {
        super();
    }

    public GoodsClassify(String classifyId, String classifyName, String parentClassifyId) {
        this.classifyId = classifyId;
        this.classifyName = classifyName;
        this.parentClassifyId = parentClassifyId;
    }

    public void modifyClassifyName(String classifyName) {
        this.classifyName = classifyName;
    }

    public void deleteClassify() {
        this.status = 2;
    }

    public void modifyClassifyServiceRate(Float serviceRate) {
        this.serviceRate = serviceRate;
    }
}