package cn.m2c.scm.domain.model.classify;

import cn.m2c.ddd.common.domain.model.ConcurrencySafeEntity;
import cn.m2c.ddd.common.domain.model.DomainEventPublisher;
import cn.m2c.scm.domain.model.classify.event.GoodsClassifyModifyEvent;

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
     * 层级，1：一级分类,2：二级分类,3：三级分类...
     */
    private Integer level;

    /**
     * 1:正常，2：删除
     */
    private Integer status;

    public GoodsClassify() {
        super();
    }

    public GoodsClassify(String classifyId, String classifyName, String parentClassifyId, Integer level) {
        this.classifyId = classifyId;
        this.classifyName = classifyName;
        this.parentClassifyId = parentClassifyId;
        this.level = level;
    }

    public void modifyClassifyName(String classifyName) {
        this.classifyName = classifyName;
        DomainEventPublisher
                .instance()
                .publish(new GoodsClassifyModifyEvent(this.classifyId, this.classifyName));
    }

    public void deleteClassify() {
        this.status = 2;
    }

    public void modifyClassifyServiceRate(Float serviceRate) {
        this.serviceRate = serviceRate;
    }

    public String classifyId() {
        return classifyId;
    }

    public Boolean isTopClassify() {
        return 1 == this.level && this.parentClassifyId.equals("-1");
    }

    public Boolean isSecondClassify() {
        return 2 == this.level;
    }

    public Boolean isThreeClassify() {
        return 3 == this.level;
    }

    public Float serviceRate() {
        return serviceRate;
    }

    public String parentClassifyId() {
        return parentClassifyId;
    }

    public String classifyName() {
        return classifyName;
    }
}