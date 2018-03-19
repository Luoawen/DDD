package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsActInventory;
import cn.m2c.scm.domain.model.goods.GoodsActInventoryRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商品活动库存数据仓库
 */
@Repository
public class HibernateGoodsActInventoryRepository extends HibernateSupperRepository implements GoodsActInventoryRepository {
    @Override
    public void save(GoodsActInventory goodsActInventory) {
        this.session().save(goodsActInventory);
    }

    @Override
    public List<GoodsActInventory> getGoodsActInventoriesByActId(String actId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_activity_inventory where activity_id=:activity_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsActInventory.class);
        query.setParameter("activity_id", actId);
        return query.list();
    }

    @Override
    public List<GoodsActInventory> getGoodsActInventoriesReturn() {
        StringBuilder sql = new StringBuilder("SELECT * FROM t_scm_goods_activity_inventory t WHERE t.create_time < NOW()-INTERVAL 1 HOUR and t.`status` = 0");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsActInventory.class);
        return query.list();
    }
}
