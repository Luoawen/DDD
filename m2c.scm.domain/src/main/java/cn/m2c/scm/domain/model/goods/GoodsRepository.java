package cn.m2c.scm.domain.model.goods;

import cn.m2c.scm.domain.NegativeException;

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

    /**
     * 商品销量排行榜
     *
     * @param dealerId
     * @param goodsId
     * @param goodsName
     * @param goodsNum
     */
    void saveGoodsSalesList(Integer month, String dealerId, String goodsId, String goodsName, Integer goodsNum) throws NegativeException;

    /**
     * 查询商家含有制定保障的商品
     * @param dealerId
     * @param guaranteeId
     * @return
     */
	List<Goods> queryGoodsByDealerIdAndGuaranteeId(String dealerId, String guaranteeId);

    Goods queryGoodsByGoodsId(String goodsId);
}
