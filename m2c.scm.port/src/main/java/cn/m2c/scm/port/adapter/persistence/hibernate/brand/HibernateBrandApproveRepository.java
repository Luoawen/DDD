package cn.m2c.scm.port.adapter.persistence.hibernate.brand;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.application.utils.Utils;
import cn.m2c.scm.domain.model.brand.BrandApprove;
import cn.m2c.scm.domain.model.brand.BrandApproveRepository;
import cn.m2c.scm.domain.model.goods.Goods;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Override
    public boolean brandNameIsRepeat(String approveId, String brandId, String brandName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand_approve where status = 1 AND brand_name =:brand_name");
        if (StringUtils.isNotEmpty(approveId)) {
            sql.append(" and approve_id <> :approve_id");
        }
        if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" and brand_id <> :brand_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(BrandApprove.class);
        query.setParameter("brand_name", brandName);
        if (StringUtils.isNotEmpty(approveId)) {
            query.setParameter("approve_id", approveId);
        }
        if (StringUtils.isNotEmpty(brandId)) {
            query.setParameter("brand_id", brandId);
        }
        List<BrandApprove> list = query.list();
        return null != list && list.size() > 0;
    }

	@Override
	public List<BrandApprove> queryBrandByIdList(List<String> approveIds) {
		StringBuilder sql = new StringBuilder("select * from t_scm_brand_approve where status = 1");
        sql.append(" and approve_id in (" + Utils.listParseString(approveIds) + ")");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(BrandApprove.class);
        return query.list();
	}
}
