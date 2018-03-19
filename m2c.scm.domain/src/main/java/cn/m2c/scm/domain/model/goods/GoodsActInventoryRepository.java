package cn.m2c.scm.domain.model.goods;

import java.util.List;

/**
 * 商品活动库存数据仓库
 */
public interface GoodsActInventoryRepository {
    void save(GoodsActInventory goodsActInventory);

    List<GoodsActInventory> getGoodsActInventoriesByActId(String actId);

    List<GoodsActInventory> getGoodsActInventoriesReturn();
}
