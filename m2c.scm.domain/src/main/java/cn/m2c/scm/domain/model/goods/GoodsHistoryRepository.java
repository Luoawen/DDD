package cn.m2c.scm.domain.model.goods;

/**
 * 商品历史记录
 */
public interface GoodsHistoryRepository {
    void save(GoodsHistory goodsHistory);
}
