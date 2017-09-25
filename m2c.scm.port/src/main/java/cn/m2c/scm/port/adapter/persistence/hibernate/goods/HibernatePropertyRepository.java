package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.templet.Property;
import cn.m2c.goods.domain.templet.PropertyRepository;

/**
 * @ClassName: HibernateDealerRepository
 * @Description: 品牌持久化实现
 * @author yezp
 * @date 2017年6月30日 下午3:41:10
 *
 */
@Repository
public class HibernatePropertyRepository extends HibernateSupperRepository implements  PropertyRepository{

	public HibernatePropertyRepository() {
		super();
	}

	@Override
	public void save(Property property) {
		this.session().saveOrUpdate(property);
	}

	@Override
	public Property getDetail(String propertyId) {
		Object result = this.session().createQuery(" FROM Property WHERE propertyId = :propertyId")
				.setString("propertyId", propertyId).uniqueResult();
		Property property = (Property) result;
		return property;
	}




}
