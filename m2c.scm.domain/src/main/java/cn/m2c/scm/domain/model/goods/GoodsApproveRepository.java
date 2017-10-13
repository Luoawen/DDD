package cn.m2c.scm.domain.model.goods;

/**
 * 商品审核
 */
public interface GoodsApproveRepository {
    GoodsApprove queryGoodsApproveById(String approveId);

    GoodsApprove queryGoodsApproveByGoodsId(String goodsId);

    void save(GoodsApprove goodsApprove);
}
