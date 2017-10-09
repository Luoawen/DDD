package cn.m2c.scm.port.adapter.persistence.hibernate.postage;

import org.springframework.stereotype.Repository;

import cn.m2c.ddd.common.port.adapter.persistence.hibernate.HibernateSupperRepository;
import cn.m2c.scm.domain.model.dealer.Dealer;
import cn.m2c.scm.domain.model.dealer.DealerRepository;


@Repository
public class HibernateDealerRepository extends HibernateSupperRepository implements DealerRepository{

	public HibernateDealerRepository() {
		super();
	}

	@Override
	public Dealer getDealer(String dealerId) {
		Dealer dealer = (Dealer) this.session().createQuery(" FROM Dealer WHERE  dealerId = :dealerId")
				.setString("dealerId", dealerId).uniqueResult();
		return dealer;
	}

	@Override
	public void save(Dealer dealer) {
		this.session().saveOrUpdate(dealer);
	}

}
