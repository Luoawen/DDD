package cn.m2c.scm.domain.model.special;

/**
 * 特惠价
 */
public interface GoodsSpecialRepository {
    void save(GoodsSpecial goodsSpecial);

    GoodsSpecial queryGoodsSpecialBySpecialId(String specialId);

    GoodsSpecial queryGoodsSpecialByGoodsId(String goodsId);
}
