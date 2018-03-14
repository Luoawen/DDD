package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsActInventory;
import cn.m2c.scm.domain.model.goods.GoodsActInventoryRepository;
import org.springframework.stereotype.Repository;

/**
 * 商品活动库存数据仓库
 */
@Repository
public class HibernateGoodsActInventoryRepository extends HibernateSupperRepository implements GoodsActInventoryRepository {
    @Override
    public void save(GoodsActInventory goodsActInventory) {
        this.session().save(goodsActInventory);
    }
}
