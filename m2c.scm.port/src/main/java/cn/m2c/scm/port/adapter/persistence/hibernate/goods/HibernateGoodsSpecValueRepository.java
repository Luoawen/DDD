package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.GoodsSpecValue;
import cn.m2c.scm.domain.model.goods.GoodsSpecValueRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 商品规格值
 */
@Repository
public class HibernateGoodsSpecValueRepository extends HibernateSupperRepository implements GoodsSpecValueRepository {

    @Override
    public GoodsSpecValue queryGoodsSpecValueById(String specId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_spec_value where spec_id =:spec_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecValue.class);
        query.setParameter("spec_id", specId);
        return (GoodsSpecValue) query.uniqueResult();
    }

    @Override
    public boolean isRepeatGoodsSpecValueName(String dealerId, String standardId, String name) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_spec_value where dealer_id =:dealer_id and standard_id =:standard_id and spec_value =:spec_value");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsSpecValue.class);
        query.setParameter("dealer_id", dealerId);
        query.setParameter("standard_id", standardId);
        query.setParameter("spec_value", name);
        return null != query.list() && query.list().size() > 0;
    }

    @Override
    public void save(GoodsSpecValue goodsSpecValue) {
        this.session().save(goodsSpecValue);
    }
}
