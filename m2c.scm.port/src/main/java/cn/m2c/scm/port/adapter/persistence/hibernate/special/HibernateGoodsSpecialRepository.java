package cn.m2c.scm.port.adapter.persistence.hibernate.special;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.special.GoodsSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecialRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 特惠价
 */
@Repository
public class HibernateGoodsSpecialRepository extends HibernateSupperRepository implements GoodsSpecialRepository {
    @Override
    public void save(GoodsSpecial goodsSpecial) {
        this.session().save(goodsSpecial);
    }

    @Override
    public GoodsSpecial queryGoodsSpecialBySpecialId(String specialId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where special_id =:special_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("special_id", specialId);
        return (GoodsSpecial) query.uniqueResult();
    }

    @Override
    public GoodsSpecial queryGoodsSpecialByGoodsId(String goodsId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where goods_id =:goods_id and status <> 2");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("goods_id", goodsId);
        return (GoodsSpecial) query.uniqueResult();
    }
}
