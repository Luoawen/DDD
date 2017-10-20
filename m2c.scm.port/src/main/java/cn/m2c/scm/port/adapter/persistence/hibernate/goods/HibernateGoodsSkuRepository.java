package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.ddd.common.port.adapter.persistence.springJdbc.SupportJdbcTemplate;
import cn.m2c.scm.domain.model.goods.GoodsSku;
import cn.m2c.scm.domain.model.goods.GoodsSkuRepository;
import org.hibernate.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 商品sku
 */
@Repository
public class HibernateGoodsSkuRepository extends HibernateSupperRepository implements GoodsSkuRepository {
    @Autowired
    private SupportJdbcTemplate supportJdbcTemplate;

    public SupportJdbcTemplate getSupportJdbcTemplate() {
        return this.supportJdbcTemplate;
    }

    @Override
    public GoodsSku queryGoodsSkuById(String skuId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_sku where sku_id =:sku_id and del_status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSku.class);
        query.setParameter("sku_id", skuId);
        return (GoodsSku) query.uniqueResult();
    }

    @Override
    public int outInventory(String skuId, Integer num, Integer concurrencyVersion) {
        StringBuilder sql = new StringBuilder("UPDATE `m2c_scm`.`t_scm_goods_sku`");
        sql.append(" SET `available_num`=`available_num`-?,concurrency_version = concurrency_version + 1");
        sql.append(" WHERE  `sku_id` = ? AND concurrency_version = ?");
        int result = this.getSupportJdbcTemplate().jdbcTemplate().update(sql.toString(), new Object[]{num, skuId, concurrencyVersion});
        return result;
    }
}
