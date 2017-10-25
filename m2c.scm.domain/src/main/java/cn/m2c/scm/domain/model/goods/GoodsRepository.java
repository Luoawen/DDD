package cn.m2c.scm.domain.model.goods;

/**
 * 商品
 */
public interface GoodsRepository {
    Goods queryGoodsById(String goodsId);

    boolean goodsNameIsRepeat(String goodsId, String dealerId, String goodsName);

    void save(Goods goods);

    Goods queryGoodsById(Integer goodsId);

    boolean brandIsUser(String brandId);
}
