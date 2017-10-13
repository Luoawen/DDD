package cn.m2c.scm.port.adapter.persistence.hibernate.brand;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.BrandApprove;
import cn.m2c.scm.domain.model.brand.BrandApproveRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

/**
 * 品牌审批信息
 */
@Repository
public class HibernateBrandApproveRepository extends HibernateSupperRepository implements BrandApproveRepository {
    @Override
    public BrandApprove getBrandApproveByApproveId(String approveId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand_approve where approve_id =:approve_id and status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(BrandApprove.class);
        query.setParameter("approve_id", approveId);
        return (BrandApprove) query.uniqueResult();
    }

    @Override
    public BrandApprove getBrandApproveByBrandId(String brandId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand_approve where brand_id =:brand_id and status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(BrandApprove.class);
        query.setParameter("brand_id", brandId);
        return (BrandApprove) query.uniqueResult();
    }

    @Override
    public void save(BrandApprove brandApprove) {
        this.session().save(brandApprove);
    }

    @Override
    public void remove(BrandApprove brandApprove) {
        this.session().delete(brandApprove);
    }
}
