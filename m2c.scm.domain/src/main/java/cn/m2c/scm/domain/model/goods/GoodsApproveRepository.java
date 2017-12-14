package cn.m2c.scm.domain.model.goods;

import java.util.List;

/**
 * 商品审核
 */
public interface GoodsApproveRepository {
    GoodsApprove queryGoodsApproveById(String goodsId);

    void save(GoodsApprove goodsApprove);

    void remove(GoodsApprove goodsApprove);

    boolean goodsNameIsRepeat(String goodsId, String dealerId, String goodsName);

    boolean brandIsUser(String brandId);

    List<GoodsApprove> queryGoodsApproveByBrandId(String brandId);

    List<GoodsApprove> queryGoodsApproveByDealerId(String dealerId);

    boolean postageIdIsUser(String postageId);

	List<GoodsApprove> queryGoodsApproveByIdList(List goodsIds);
}
