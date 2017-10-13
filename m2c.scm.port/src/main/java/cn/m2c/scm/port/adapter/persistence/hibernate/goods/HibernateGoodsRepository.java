package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.Goods;
import cn.m2c.scm.domain.model.goods.GoodsRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 商品
 */
@Repository
public class HibernateGoodsRepository extends HibernateSupperRepository implements GoodsRepository {
    @Override
    public Goods queryGoodsById(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods where goods_id =:goods_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Goods.class);
        query.setParameter("goods_id", goodsId);
        return (Goods) query.uniqueResult();
    }

    @Override
    public void save(Goods goods) {
        this.session().save(goods);
    }
}
