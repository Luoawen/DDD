package cn.m2c.scm.port.adapter.persistence.hibernate.classify;

import cn.m2c.common.RedisUtil;
import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.classify.GoodsClassify;
import cn.m2c.scm.domain.model.classify.GoodsClassifyRepository;
import cn.m2c.scm.domain.util.StringOptUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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

    public List<String> recursionQueryGoodsSubClassifyId(String parentClassifyId, List<String> resultList) {
        List<GoodsClassify> subGoodsClassifys = queryGoodsClassifiesByParentId(parentClassifyId);
        if (null != subGoodsClassifys && subGoodsClassifys.size() > 0) {
            for (GoodsClassify classify : subGoodsClassifys) {
                resultList.add(classify.classifyId());
                recursionQueryGoodsSubClassifyId(classify.classifyId(), resultList);
            }
        }
        return resultList;
    }

    private List<GoodsClassify> queryGoodsClassifiesByParentId(String parentId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_goods_classify where parent_classify_id =:parent_classify_id and status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(GoodsClassify.class);
        query.setParameter("parent_classify_id", parentId);
        return query.list();
    }

    private List<GoodsClassify> recursionQueryGoodsUpClassify(String classifyId, List<GoodsClassify> classifies) {
        GoodsClassify classify = getGoodsClassifyById(classifyId);
        if (null != classify) {
            classifies.add(classify);
            if (!"-1".equals(classify.parentClassifyId())) {
                return recursionQueryGoodsUpClassify(classify.parentClassifyId(), classifies);
            }
        }
        return classifies;
    }

    public String getMainUpClassifyName(String classifyId) {
        String key = "scm.goods.classify.parent.name.main." + classifyId;
        String nameCache = RedisUtil.get(key);
        if (StringUtils.isEmpty(nameCache)) {
            List<GoodsClassify> goodsClassifies = recursionQueryGoodsUpClassify(classifyId, new ArrayList<GoodsClassify>());
            if (null != goodsClassifies && goodsClassifies.size() > 0) {
                List<String> names = new ArrayList<>();
                for (GoodsClassify classify : goodsClassifies) {
                    names.add(classify.classifyName());
                }
                String goodsClassify = StringOptUtils.listJoinString(names);
                RedisUtil.setString(key, 4 * 3600, goodsClassify);
                return goodsClassify;
            }
            return null;
        } else {
            return nameCache;
        }
    }

    public Float queryServiceRateByClassifyId(String classifyId) {
        GoodsClassify classify = getGoodsClassifyById(classifyId);
        if (null != classify) {
            Float rate = classify.serviceRate();
            if (null == rate) {
                if (!"-1".equals(classify.parentClassifyId())) {//不是一级分类
                    //找上级分类
                    return queryServiceRateByClassifyId(classify.parentClassifyId());
                }
            } else {
                return rate;
            }
        }
        return null;
    }
}
