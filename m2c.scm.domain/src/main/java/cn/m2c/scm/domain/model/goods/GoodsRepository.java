package cn.m2c.scm.domain.model.goods;

import java.util.List;

/**
 * 商品
 */
public interface GoodsRepository {
    Goods queryGoodsById(String goodsId);

    boolean goodsNameIsRepeat(String goodsId, String dealerId, String goodsName);

    void save(Goods goods);

    Goods queryGoodsById(Integer goodsId);

    boolean brandIsUser(String brandId);

    List<Goods> queryGoodsByBrandId(String brandId);

    List<Goods> queryGoodsByDealerId(String dealerId);

    boolean classifyIdIsUser(List<String> classifyIds);

    boolean postageIdIsUser(String postageId);

	List<Goods> queryGoodsByIdList(List goodsIds);

}
