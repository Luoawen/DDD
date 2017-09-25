package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.dealer.Dealer;
import cn.m2c.goods.domain.dealer.DealerRepository;
import cn.m2c.goods.domain.templet.Brand;
import cn.m2c.goods.domain.templet.BrandRepository;
import cn.m2c.goods.exception.NegativeException;

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
