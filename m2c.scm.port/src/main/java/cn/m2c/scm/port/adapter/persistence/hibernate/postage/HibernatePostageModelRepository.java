package cn.m2c.scm.port.adapter.persistence.hibernate.postage;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.postage.PostageModel;
import cn.m2c.scm.domain.model.postage.PostageModelRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Service;

/**
 * 运费模板
 */
@Service
public class HibernatePostageModelRepository extends HibernateSupperRepository implements PostageModelRepository {
    @Override
    public PostageModel getPostageModel(String modelId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_postage_model where model_id =:model_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(PostageModel.class);
        query.setParameter("model_id", modelId);
        return (PostageModel) query.uniqueResult();
    }

    @Override
    public void save(PostageModel postageModel) {
        this.session().save(postageModel);
    }
}
