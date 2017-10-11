package cn.m2c.scm.port.adapter.persistence.hibernate.classify;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.classify.GoodsClassify;
import cn.m2c.scm.domain.model.classify.GoodsClassifyRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 商品分类
 */
@Repository
public class HibernateGoodsClassifyRepository extends HibernateSupperRepository implements GoodsClassifyRepository {
    @Override
    public GoodsClassify getGoodsClassifyById(String classifyId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_classify where classify_id =:classify_id and status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsClassify.class);
        query.setParameter("classify_id", classifyId);
        return (GoodsClassify) query.uniqueResult();
    }

    @Override
    public void save(GoodsClassify goodsClassify) {
        this.session().save(goodsClassify);
    }
}
