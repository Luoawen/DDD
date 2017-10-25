package cn.m2c.scm.port.adapter.persistence.hibernate.brand;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.brand.Brand;
import cn.m2c.scm.domain.model.brand.BrandRepository;
import org.apache.commons.lang3.StringUtils;
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
        StringBuilder sql = new StringBuilder("select * from t_scm_brand where brand_id =:brand_id and brand_status = 1");
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Brand.class);
        query.setParameter("brand_id", brandId);
        return (Brand) query.uniqueResult();
    }

    @Override
    public boolean brandNameIsRepeat(String brandId, String brandName) {
        StringBuilder sql = new StringBuilder("select * from t_scm_brand where brand_status = 1 AND brand_name =:brand_name");
        if (StringUtils.isNotEmpty(brandId)) {
            sql.append(" and brand_id <> :brand_id");
        }
        Query query = this.session().createSQLQuery(sql.toString()).addEntity(Brand.class);
        query.setParameter("brand_name", brandName);
        if (StringUtils.isNotEmpty(brandId)) {
            query.setParameter("brand_id", brandId);
        }
        List<Brand> list = query.list();
        return null != list && list.size() > 0;
    }

    @Override
    public void save(Brand brand) {
        this.session().save(brand);
    }
}
