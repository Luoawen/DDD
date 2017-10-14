package cn.m2c.scm.domain.model.goods;

/**
 * 商品审核
 */
public interface GoodsApproveRepository {
    GoodsApprove queryGoodsApproveById(String goodsId);

    void save(GoodsApprove goodsApprove);

    void remove(GoodsApprove goodsApprove);
}
