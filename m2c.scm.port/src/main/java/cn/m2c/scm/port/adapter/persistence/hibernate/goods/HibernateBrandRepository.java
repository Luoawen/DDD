package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.goods.templet.Brand;
import cn.m2c.scm.domain.model.goods.templet.BrandRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 品牌持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernateBrandRepository extends HibernateSupperRepository implements  BrandRepository{

	public HibernateBrandRepository() {
		super();
	}

	@Override
	public void save(Brand brand) {
		this.session().saveOrUpdate(brand);
	}

	@Override
	public Brand getBrandDetail(String brandId) {
		Object result = this.session().createQuery(" FROM Brand WHERE brandId = :brandId")
				.setString("brandId", brandId).uniqueResult();
		Brand brand = (Brand) result;
		return brand;
	}



}
