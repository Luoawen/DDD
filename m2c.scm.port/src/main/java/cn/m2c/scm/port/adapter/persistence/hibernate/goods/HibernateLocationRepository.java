package cn.m2c.scm.port.adapter.persistence.hibernate.goods;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.goods.domain.location.LocRepository;
import cn.m2c.goods.domain.location.Location;

@Repository
public class HibernateLocationRepository extends HibernateSupperRepository
		implements LocRepository {

	public HibernateLocationRepository() {
		super();
	}
	@Override
	public Location getLocDetail(String locationId) {
		Location location = (Location) this.session().createQuery(" FROM Location WHERE locationId = :locationId")
				.setString("locationId", locationId).uniqueResult();
		return location;
		}

	@Override
	public void save(Location location) {
		this.session().saveOrUpdate(location);
	}

}
