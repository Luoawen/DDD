package cn.m2c.scm.domain.model.classify;

/**
 * 商品分类
 */
public interface GoodsClassifyRepository {
    GoodsClassify getGoodsClassifyById(String classifyId);

    void save(GoodsClassify goodsClassify);
}
