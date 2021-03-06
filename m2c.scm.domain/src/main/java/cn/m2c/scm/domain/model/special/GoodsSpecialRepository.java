package cn.m2c.scm.domain.model.special;

import java.util.List;
import java.util.Map;

/**
 * 特惠价
 */
public interface GoodsSpecialRepository {
    void save(GoodsSpecial goodsSpecial);

    GoodsSpecial queryGoodsSpecialBySpecialId(String specialId);

    GoodsSpecial queryEffectiveGoodsSpecialByGoodsId(String goodsId);

    List<GoodsSpecial> getStartGoodsSpecial();

    List<GoodsSpecial> getEndGoodsSpecial();

    Map getEffectiveGoodsSkuSpecial(List<String> skuIds);

    List<GoodsSpecial> queryGoodsSpecialByDealerId(String dealerId);

    List<GoodsSpecial> queryGoodsSpecialByGoodsId(String goodsId);
}
