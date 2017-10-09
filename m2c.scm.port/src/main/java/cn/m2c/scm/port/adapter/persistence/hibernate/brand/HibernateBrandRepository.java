package cn.m2c.scm.port.adapter.persistence.hibernate.brand;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.brand.BrandRepository;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 品牌信息
 */
@Repository
public class HibernateBrandRepository extends HibernateSupperRepository implements BrandRepository {
    @Override
    public Brand getBrandByBrandId(String brandId) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand where brand_id =:brand_id");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Brand.class);
        query.setParameter("brand_id", brandId);
        return (Brand) query.uniqueResult();
    }

    @Override
    public List<Brand> getBrandByBrandName(String brandName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand where brand_name =:brand_name");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Brand.class);
        query.setParameter("brand_id", brandName);
        return query.list();
    }

    @Override
    public void save(Brand brand) {
        this.session().save(brand);
    }
}
