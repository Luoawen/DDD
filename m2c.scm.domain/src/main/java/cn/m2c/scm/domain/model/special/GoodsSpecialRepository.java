package cn.m2c.scm.domain.model.special;

import java.util.List;

/**
 * 特惠价
 */
public interface GoodsSpecialRepository {
    void save(GoodsSpecial goodsSpecial);

    GoodsSpecial queryGoodsSpecialBySpecialId(String specialId);

    GoodsSpecial queryGoodsSpecialByGoodsId(String goodsId);

    List<GoodsSpecial> getStartGoodsSpecial();

    List<GoodsSpecial> getEndGoodsSpecial();
}
