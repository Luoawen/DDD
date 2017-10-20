package cn.m2c.scm.port.adapter.persistence.hibernate.classify;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.classify.GoodsClassify;
import cn.m2c.scm.domain.model.classify.GoodsClassifyRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public boolean goodsClassifyNameIsRepeat(String classifyId, String classifyName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_classify where status = 1 AND classify_name =:classify_name");
        if (StringUtils.isNotEmpty(classifyId)) {
            sql.append(" and classify_id <> :classify_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsClassify.class);
        query.setParameter("classify_name", classifyName);
        if (StringUtils.isNotEmpty(classifyId)) {
            query.setParameter("classify_id", classifyId);
        }
        List<Brand> list = query.list();
        if (null != list && list.size() > 0) {
            return true;
        }
        return false;
    }
}
