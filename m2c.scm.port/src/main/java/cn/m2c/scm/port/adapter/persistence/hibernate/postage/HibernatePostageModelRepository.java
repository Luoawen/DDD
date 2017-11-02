package cn.m2c.scm.port.adapter.persistence.hibernate.postage;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.postage.PostageModel;
import cn.m2c.scm.domain.model.postage.PostageModelRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 运费模板
 */
@Repository
public class HibernatePostageModelRepository extends HibernateSupperRepository implements PostageModelRepository {
    @Override
    public PostageModel getPostageModelById(String modelId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_postage_model where model_id =:model_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(PostageModel.class);
        query.setParameter("model_id", modelId);
        return (PostageModel) query.uniqueResult();
    }

    @Override
    public void save(PostageModel postageModel) {
        this.session().save(postageModel);
    }

    @Override
    public boolean postageNameIsRepeat(String modelId, String dealerId, String modelName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_postage_model where dealer_id=:dealer_id AND model_name =:model_name AND model_status = 1 ");
        if (StringUtils.isNotEmpty(modelId)) {
            sql.append(" and model_id <> :model_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(PostageModel.class);
        query.setParameter("dealer_id", dealerId);
        query.setParameter("model_name", modelName);
        if (StringUtils.isNotEmpty(modelId)) {
            query.setParameter("model_id", modelId);
        }
        List<PostageModel> list = query.list();
        return null != list && list.size() > 0;
    }
}
