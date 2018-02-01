package cn.m2c.scm.domain.model.classify;

import java.util.List;

/**
 * 商品分类
 */
public interface GoodsClassifyRepository {
    GoodsClassify getGoodsClassifyById(String classifyId);

    void save(GoodsClassify goodsClassify);

    boolean goodsClassifyNameIsRepeat(String classifyId, String classifyName);

    List<String> recursionQueryGoodsSubClassifyId(String parentClassifyId, List<String> resultList);

    String getMainUpClassifyName(String classifyId);

    Float queryServiceRateByClassifyId(String classifyId);
}
