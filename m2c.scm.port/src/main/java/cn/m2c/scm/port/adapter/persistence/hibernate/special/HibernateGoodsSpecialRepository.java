package cn.m2c.scm.port.adapter.persistence.hibernate.special;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.special.GoodsSpecial;
import cn.m2c.scm.domain.model.special.GoodsSpecialRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<GoodsSpecial> getStartGoodsSpecial() {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where start_time <= :start_time status = 0");
        Date currentTime = new Date(System.currentTimeMillis());
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("start_time", currentTime);
        return query.list();
    }

    @Override
    public List<GoodsSpecial> getEndGoodsSpecial() {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_special where end_time <= :end_time status = 1");
        Date currentTime = new Date(System.currentTimeMillis());
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecial.class);
        query.setParameter("end_time", currentTime);
        return query.list();
    }
}
