package cn.m2c.scm.domain.model.goods;

/**
 * 规格值
 */
public interface GoodsSpecValueRepository {
    GoodsSpecValue queryGoodsSpecValueById(String specId);

    void save(GoodsSpecValue goodsSpecValue);
}
